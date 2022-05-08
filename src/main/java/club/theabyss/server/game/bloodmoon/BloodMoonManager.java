package club.theabyss.server.game.bloodmoon;

import club.theabyss.global.utils.chat.ChatFormatter;
import club.theabyss.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.global.utils.timedTitle.TimedTitle;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.game.bloodmoon.events.BloodMoonEvents;
import club.theabyss.server.game.bloodmoon.listeners.BloodMoonListener;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import lombok.Getter;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BloodMoonManager {

    private final @Getter TheAbyssServerManager serverCore;
    private final @Getter BloodMoonListener bloodMoonListener;

    private long lastTimeChecked;

    private boolean animationIsActive = false;

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> endBloodMoonTask = null;

    public BloodMoonManager(final TheAbyssServerManager serverCore, boolean enabled) {
        this.serverCore = serverCore;

        this.bloodMoonListener = new BloodMoonListener(this).load(enabled);
    }

    public void load() {
        lastTimeChecked = new Date().getTime();
        var endsIn = bloodMoonData().getEndsIn();
        if (endsIn > 0) {
            startBloodMoon(false);
        }
    }

    /**
     * Starts the BloodMoon.
     *
     * @param hours of duration.
     */
    public void start(double hours) {
        start(hours, true, true);
    }

    /**
     *
     * @param playEffect whether the effect should be displayed is active or not.
     * @param hours of duration.
     */
    public void start(double hours, boolean playEffect) {
        start(hours, true, playEffect);
    }

    /**
     * Starts the BloodMoon.
     *
     * @param hours of duration.
     * @param enableAnimation whether the animation should be displayed is active or not.
     * @param playEffect whether the effect should be displayed is active or not.
     */
    public void start(double hours, boolean enableAnimation, boolean playEffect) {
        long addedTime = (long) (hours * 3_600_000);

        var endsIn = bloodMoonData().getEndsIn();
        var totalTime = bloodMoonData().getTotalTime();

        var isActive = isActive();

        if (!isActive) endsIn = 0;
        endsIn += addedTime;
        totalTime += addedTime;

        bloodMoonData().setEndsIn(endsIn);
        bloodMoonData().setTotalTime(totalTime);

        if (!animationIsActive) {
            if (isActive || !enableAnimation) {
                startBloodMoon(playEffect);
            } else {
                startAnimation(24, 5, playEffect);
            }
        }
    }

    //TODO FIX ANIMATION.
    /**
     * Starts the BloodMoon animation.
     *
     * @param fps of the animation.
     * @param seconds of duration.
     */
    private void startAnimation(int fps, int seconds, boolean playEffect) {
        if (animationIsActive) return;
        animationIsActive = true;

        var world = serverCore.serverGameManager().minecraftServer().getOverworld();

        final long gameTime = world.getTime();
        final int totalIterations = seconds * fps;
        final float gameTimeStep = ((gameTime <= 18_000 ? 18_000f : 42_000f) - gameTime) / totalIterations;

        final float[] total = {0};
        final int[] iterations = {0};
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                iterations[0]++;
                if (iterations[0] >= totalIterations) {
                    this.cancel();
                    startBloodMoon(playEffect);
                    animationIsActive = false;
                    world.setTimeOfDay(18_000);
                    return;
                }
                total[0] += gameTimeStep;
                world.setTimeOfDay((long) (gameTime + total[0]));
            }
        }, 0, 50L / fps); //???
    }

    /**
     * Starts the BloodMoon.
     */
    public void startBloodMoon(boolean playEffect) {
        var world = serverCore.serverGameManager().minecraftServer().getOverworld();

        world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, world.getServer());

        BloodMoonEvents.BloodMoonStarted.EVENT.invoker().start(this);

        var endsIn = bloodMoonData().getEndsIn();
        lastTimeChecked = new Date().getTime();

        if (endBloodMoonTask != null) endBloodMoonTask.cancel(false);
        endBloodMoonTask = executorService.schedule(this::end, endsIn / 1000, TimeUnit.SECONDS);

        var subtitle = getFormattedRemainingTime().replaceFirst(".{3}$", "")+"m";
        if (subtitle.length() > 6) subtitle = subtitle.replaceFirst(":", "d:");
        subtitle = new StringBuilder(subtitle).insert(subtitle.length()-4, "h").toString();

        String finalSubtitle = subtitle;
        if (!playEffect) return;
        world.getPlayers().forEach(online -> {
            online.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.MASTER, 1F, -18F);
            online.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.MASTER, 100F, -9.0F);

            try {
                TimedTitle.send(online, ChatFormatter.format("&5&k| &cBLOODMOON &5&k|"), ChatFormatter.format("&cComienza una &4Bloodmoon&c con duración de &c" + finalSubtitle + "&c."), 10, 70, 20);
            } catch (InvalidTitleTimings e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Ends the BloodMoon.
     */
    public void end() {
        var world = serverCore.serverGameManager().minecraftServer().getOverworld();

        bloodMoonData().setEndsIn(0);
        bloodMoonData().setTotalTime(0);

        endBloodMoonTask = null;

        world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, world.getServer());

        BloodMoonEvents.BloodMoonEnded.EVENT.invoker().end(this);
    }

    /**
     * @return whether the Blood Moon is active or not.
     */
    public boolean isActive() {
        return getMillisToEnd() > 0;
    }

    /**
     * @return the time in milliseconds to end.
     */
    public long getMillisToEnd() {
        long now = new Date().getTime();

        var endsIn = bloodMoonData().getEndsIn();
        endsIn -= now - lastTimeChecked;

        lastTimeChecked = now;
        bloodMoonData().setEndsIn(endsIn);
        return endsIn;
    }

    /**
     * @return a string with the remaining time formatted to the standard format.
     */
    public String getFormattedRemainingTime() {
        if (isActive()) {
            long secs = getMillisToEnd() / 1000L;
            long days = secs / 86400L;
            long hours = secs % 86400L / 3600L;
            long mins = secs % 3600L / 60L;
            secs %= 60L;

            return ((days > 0) ? (String.format("%02d", days) + ":") : "") + String.format("%02d:%02d:%02d", hours, mins, secs);
        } else {
            return "";
        }
    }

    /**
     * @return the BloodMoonData instance.
     */
    public BloodMoonData bloodMoonData() {
        return serverCore.serverGameManager().gameData().bloodMoonData();
    }

}
