package club.theabyss.server.game.bloodmoon;

import club.theabyss.server.global.utils.chat.ChatFormatter;
import club.theabyss.server.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.server.global.utils.timedTitle.TimedTitle;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import lombok.Getter;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BloodMoonManager {

    private final @Getter TheAbyssServerManager serverManager;

    private long lastTimeChecked;

    private boolean animationIsActive = false;

    private boolean paused = false;

    public ServerBossBar serverBossBar;

    private Text title;

    public static ScheduledExecutorService executorService;
    private static ScheduledFuture<?> endBloodMoonTask = null;
    public ScheduledFuture<?> updateBossBarTask = null;

    public BloodMoonManager(final TheAbyssServerManager serverCore) {
        this.serverManager = serverCore;
        executorService = Executors.newSingleThreadScheduledExecutor();
        this.serverBossBar = new ServerBossBar(Text.of(""), BossBar.Color.RED, BossBar.Style.NOTCHED_6);
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
                startAnimation(5, playEffect);
            }
        }
    }

    /**
     * Starts the BloodMoon animation.
     *
     * @param seconds of duration.
     */
    private void startAnimation(int seconds, boolean playEffect) {
        if (animationIsActive) return;
        animationIsActive = true;

        var world = serverManager.serverGameManager().minecraftServer().getOverworld();

        final double realTime = 1000.0 / 20;
        final long gameTime = world.getTimeOfDay();
        final int totalFrames = seconds * 20;
        final float gameTimeStep = ((gameTime <= 18_000 ? 18_000f : 42_000f) - gameTime) / totalFrames;

        final float[] nightIncrement = {0};
        final int[] frame = {0};
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                frame[0]++;
                if (frame[0] >= totalFrames) {
                    this.cancel();
                    startBloodMoon(playEffect);
                    animationIsActive = false;
                    world.setTimeOfDay(18_000);
                    return;
                }
                nightIncrement[0] += gameTimeStep;
                world.setTimeOfDay((long) (gameTime + nightIncrement[0]));

                //world.getPlayers().forEach(p -> p.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(world.getTime(), world.getTimeOfDay(), world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))));
            }
        }, 0, (long) realTime);
    }

    /**
     * Starts the BloodMoon.
     *
     * @param playEffect defines whether the screen effects should be executed or not.
     */
    public void startBloodMoon(boolean playEffect) {
        var minecraftSever = serverManager.minecraftServer();
        var world = minecraftSever.getOverworld();

        world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, world.getServer());

        BloodMoonEvents.BloodMoonStarted.EVENT.invoker().start(this);

        var endsIn = bloodMoonData().getEndsIn();
        lastTimeChecked = new Date().getTime();

        if (endBloodMoonTask != null) endBloodMoonTask.cancel(false);
        endBloodMoonTask = executorService.schedule(this::end, endsIn / 1000, TimeUnit.SECONDS);

        world.setTimeOfDay(18000);

        if (!playEffect) return;

        var subtitle = getFormattedRemainingTime().replaceFirst(".{3}$", "")+"m";
        if (subtitle.length() > 6) subtitle = subtitle.replaceFirst(":", "d:");
        subtitle = new StringBuilder(subtitle).insert(subtitle.length()-4, "h").toString();

        String finalSubtitle = subtitle;

        minecraftSever.getPlayerManager().getPlayerList().forEach(online -> {
            online.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.MASTER, 1F, -18F);
            online.playSound(SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.MASTER, 100F, -9.0F);

            try {
                TimedTitle.send(online, ChatFormatter.stringFormatToString("&5&k| &cBLOODMOON &5&k|"),
                        ChatFormatter.stringFormatToString("&cComienza una &4Bloodmoon&c con duración de &c" + finalSubtitle + "&c."), 10, 70, 20);
            } catch (InvalidTitleTimings e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Ends the BloodMoon.
     */
    public void end() {
        var world = serverManager.serverGameManager().minecraftServer().getOverworld();

        bloodMoonData().setEndsIn(0);
        bloodMoonData().setTotalTime(0);

        endBloodMoonTask = null;

        world.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, world.getServer());

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
     * Creates the task that updates the BloodMoon boss-bar each second.
     */
    public void updateBossBarTask() {
        if (updateBossBarTask != null) return;
        updateBossBarTask = executorService.scheduleAtFixedRate(() -> {
            var remainBloodMoon = getFormattedRemainingTime();

            title = ChatFormatter.stringFormatToText("&c&ka &r☠ &c&lBLOODMOON: " + Formatting.YELLOW + (remainBloodMoon.equals("") ? "No hay una BloodMoon activa en este momento." : remainBloodMoon) + " &r☠ &c&ka");
            serverBossBar.setName(title);
            serverBossBar.setPercent((float) Math.max(0d, Math.min(1d, (double) getMillisToEnd() / bloodMoonData().getTotalTime())));

        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Cancels the task that updates the BloodMoon boss-bar.
     * @param server the Minecraft Server the boss-bar is being cancelled.
     */
    public void cancelBossBarTask(MinecraftServer server) {
        if (updateBossBarTask != null) {
            updateBossBarTask.cancel(false);
            updateBossBarTask = null;
        }

        hideBossBarToAll(server);
    }

    /**
     * Resumes all the BloodMoon logic if it has been paused before.
     */
    public void resume() {
        if (!paused) return;
        lastTimeChecked = new Date().getTime();
        getMillisToEnd();
        assert executorService == null;
        executorService = Executors.newSingleThreadScheduledExecutor();
        assert endBloodMoonTask == null;
        endBloodMoonTask = executorService.schedule(this::end, bloodMoonData().getEndsIn() / 1000, TimeUnit.SECONDS);
        assert updateBossBarTask == null;
        updateBossBarTask();
        paused = false;
    }

    /**
     * Pauses all the BloodMoon logic.
     */
    public void pause() {
        if (paused) return;
        if (endBloodMoonTask != null) endBloodMoonTask.cancel(false);
        if (updateBossBarTask != null) updateBossBarTask.cancel(false);
        endBloodMoonTask = null;
        updateBossBarTask = null;
        executorService.shutdownNow();
        executorService = null;
        getMillisToEnd();
        paused = true;
    }

    /**
     * Hides the BloodMoon boss-bar to all the players in the given server.
     * @param server the Minecraft Server the function will affect.
     */
    public void hideBossBarToAll(MinecraftServer server) {
        var players = server.getPlayerManager().getPlayerList();
        players.forEach(this::hideBossBar);
    }

    /**
     * Shows the BloodMoon boss-bar to a player.
     * @param player the boss-bar will be showed to.
     */
    public void hideBossBar(ServerPlayerEntity player) {
        if (serverBossBar.getPlayers().contains(player)) {
            serverBossBar.removePlayer(player);
        }
    }

    /**
     * Shows the BloodMoon boss-bar to all the players in the given server.
     * @param server the Minecraft Server the function will affect.
     */
    public void showBossBarToAll(MinecraftServer server) {
        var players = server.getPlayerManager().getPlayerList();
        players.forEach(this::showBossBar);
    }

    /**
     * Hides the BloodMoon boss-bar to a player.
     * @param player the boss-bar will be hidden to.
     */
    public void showBossBar(ServerPlayerEntity player) {
        if (!serverBossBar.getPlayers().contains(player)) {
            serverBossBar.addPlayer(player);
        }
    }

    /**
     * @return the BloodMoonData instance.
     */
    public BloodMoonData bloodMoonData() {
        return serverManager.serverGameManager().gameData().bloodMoonData();
    }

}
