package club.theabyss.server.game.bloodmoon;

import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import club.theabyss.server.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.server.global.utils.timedTitle.TimedTitle;
import club.theabyss.server.TheAbyssServer;
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

    private final @Getter TheAbyssServer serverManager;

    private long lastTimeCheckedBloodMoon;

    private long lastTimeCheckedNaturalBloodMoon;

    private boolean animationIsActive = false;

    private boolean bloodMoonPaused = false;

    private boolean naturalBloodMoonPaused = false;

    public ServerBossBar serverBossBar;

    private Text title;

    public static ScheduledExecutorService bloodMoonExecutorService;
    public static ScheduledExecutorService naturalBloodMoonExecutorService;
    private static ScheduledFuture<?> endBloodMoonTask = null;
    public ScheduledFuture<?> updateBossBarTask = null;
    private ScheduledFuture<?> naturalBloodMoonTask = null;

    public BloodMoonManager(final TheAbyssServer serverCore) {
        this.serverManager = serverCore;
        bloodMoonExecutorService = Executors.newSingleThreadScheduledExecutor();
        naturalBloodMoonExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.serverBossBar = new ServerBossBar(Text.of(""), BossBar.Color.RED, BossBar.Style.NOTCHED_6);
    }

    public void load() {
        lastTimeCheckedBloodMoon = new Date().getTime();
        lastTimeCheckedNaturalBloodMoon = new Date().getTime();
        var endsIn = bloodMoonData().getEndsIn();
        var naturalBloodMoonIn = bloodMoonData().getNaturalBloodMoonIn();
        if (endsIn > 0) {
            startBloodMoon(false);
        } else {
            if (naturalBloodMoonIn == 0) {
                scheduleNaturalBloodMoon();
            } else if (naturalBloodMoonIn > 0) {
                createNaturalBloodMoonTask(naturalBloodMoonIn);
            }
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

        cancelNaturalBloodMoonTask();

        var endsIn = bloodMoonData().getEndsIn();
        lastTimeCheckedBloodMoon = new Date().getTime();

        if (endBloodMoonTask != null) endBloodMoonTask.cancel(false);
        endBloodMoonTask = bloodMoonExecutorService.schedule(this::end, endsIn / 1000, TimeUnit.SECONDS);

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

        scheduleNaturalBloodMoon();
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
        endsIn -= now - lastTimeCheckedBloodMoon;

        lastTimeCheckedBloodMoon = now;
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
        updateBossBarTask = bloodMoonExecutorService.scheduleAtFixedRate(() -> {
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
    public void resumeBloodMoon() {
        if (!bloodMoonPaused) return;
        lastTimeCheckedBloodMoon = new Date().getTime();
        getMillisToEnd();
        assert bloodMoonExecutorService == null;
        bloodMoonExecutorService = Executors.newSingleThreadScheduledExecutor();
        assert endBloodMoonTask == null;
        endBloodMoonTask = bloodMoonExecutorService.schedule(this::end, bloodMoonData().getEndsIn() / 1000, TimeUnit.SECONDS);
        assert updateBossBarTask == null;
        updateBossBarTask();
        bloodMoonPaused = false;
    }

    /**
     * Pauses all the BloodMoon logic.
     */
    public void pauseBloodMoon() {
        if (bloodMoonPaused) return;
        if (endBloodMoonTask != null) endBloodMoonTask.cancel(false);
        if (updateBossBarTask != null) updateBossBarTask.cancel(false);
        endBloodMoonTask = null;
        updateBossBarTask = null;
        bloodMoonExecutorService.shutdownNow();
        bloodMoonExecutorService = null;
        getMillisToEnd();
        bloodMoonPaused = true;
    }

    /**
     * Pauses all the Natural BloodMoon logic.
     */
    public void pauseNaturalBloodMoon() {
        if (naturalBloodMoonPaused) return;
        if (naturalBloodMoonTask != null) naturalBloodMoonTask.cancel(false);
        naturalBloodMoonTask = null;
        naturalBloodMoonExecutorService.shutdownNow();
        bloodMoonExecutorService = null;
        getNaturalBloodMoonRemainingTime();
        naturalBloodMoonPaused = true;
    }

    /**
     * Resumes all the Natural BloodMoon logic.
     */
    public void resumeNaturalBloodMoon() {
        if (!naturalBloodMoonPaused) return;
        lastTimeCheckedNaturalBloodMoon = new Date().getTime();
        getNaturalBloodMoonRemainingTime();
        assert naturalBloodMoonExecutorService == null;
        naturalBloodMoonExecutorService = Executors.newSingleThreadScheduledExecutor();
        assert naturalBloodMoonTask == null;
        naturalBloodMoonTask = naturalBloodMoonExecutorService.schedule(() -> createNaturalBloodMoonTask(bloodMoonData().getNaturalBloodMoonIn()), bloodMoonData().getNaturalBloodMoonIn() / 20, TimeUnit.SECONDS);
        naturalBloodMoonPaused = false;
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

    public void cancelNaturalBloodMoonTask() {
        if (naturalBloodMoonTask != null) naturalBloodMoonTask.cancel(true);
        naturalBloodMoonTask = null;
    }

    private void scheduleNaturalBloodMoon() {
        if (naturalBloodMoonTask != null) return;

        double minDaysBeforeNaturalBloodMoon; // Inclusive
        double maxDaysBeforeNaturalBloodMoon; // Exclusive

        if (GlobalDataAccess.getNowDay() >= 14) {
            maxDaysBeforeNaturalBloodMoon = 10;
            minDaysBeforeNaturalBloodMoon = 6;
        } else {
            minDaysBeforeNaturalBloodMoon = 8;
            maxDaysBeforeNaturalBloodMoon = 12;
        }

        var naturalBloodMoonIn = 24_000 * (long) (minDaysBeforeNaturalBloodMoon +
                Math.random()*(maxDaysBeforeNaturalBloodMoon -1/* -1 to make the maxNumber exclusive */ - minDaysBeforeNaturalBloodMoon)
                - 1 /* -1 to counteract the +24_000 in the next line */);

        naturalBloodMoonIn += /* 18_000 + 24_000 */ 42_000 -  serverManager.serverGameManager().minecraftServer().getOverworld().getTime();

        bloodMoonData().setNaturalBloodMoonIn(naturalBloodMoonIn);

        createNaturalBloodMoonTask(naturalBloodMoonIn);
    }

    public void createNaturalBloodMoonTask(long naturalBloodMoonIn) {
        if (isActive()) return;
        cancelNaturalBloodMoonTask();

        naturalBloodMoonTask = naturalBloodMoonExecutorService.schedule(this::startNaturalBloodMoon, (naturalBloodMoonIn / 20), TimeUnit.SECONDS);

        lastTimeCheckedNaturalBloodMoon = new Date().getTime();
    }

    private void startNaturalBloodMoon() {
        double naturalBloodMoonDuration;

        long day = GlobalDataAccess.getNowDay();

        if (day >= 7 && day < 14) {
            naturalBloodMoonDuration = 0.75;
        } else if (day >= 14) {
            naturalBloodMoonDuration = 1;
        } else {
            naturalBloodMoonDuration = 0.5;
        }

        serverManager.serverGameManager().minecraftServer().getOverworld().setTimeOfDay(18_000);

        this.start(naturalBloodMoonDuration, false, true);
    }

    public long getNaturalBloodMoonRemainingTime() {
        long now = new Date().getTime();
        // This counter is read in ticks, but it still works because the real time scheduler is calculated correctly
        bloodMoonData().setNaturalBloodMoonIn(bloodMoonData().getNaturalBloodMoonIn() - ((now - lastTimeCheckedNaturalBloodMoon) / 50)); /* 20*(now - lastTimeChecked) / 1000 */;
        lastTimeCheckedNaturalBloodMoon = now;
        return bloodMoonData().getNaturalBloodMoonIn();
    }

    /**
     * @return the BloodMoonData instance.
     */
    public BloodMoonData bloodMoonData() {
        return serverManager.serverGameManager().gameData().bloodMoonData();
    }

}
