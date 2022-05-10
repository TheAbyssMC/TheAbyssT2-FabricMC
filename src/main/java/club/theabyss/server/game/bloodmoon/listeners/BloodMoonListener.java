package club.theabyss.server.game.bloodmoon.listeners;

import club.theabyss.global.utils.chat.ChatFormatter;
import club.theabyss.global.utils.customGlyphs.Animation;
import club.theabyss.global.utils.customGlyphs.NoSuchAnimationException;
import club.theabyss.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.global.utils.timedTitle.MinimumStayTimeIsGreaterThatStayTime;
import club.theabyss.global.utils.timedTitle.TimedActionBar;
import club.theabyss.global.utils.titles.SendActionBar;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.bloodmoon.events.BloodMoonEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BloodMoonListener {

    private final BloodMoonManager bloodMoonManager;

    private final ServerBossBar serverBossbar = new ServerBossBar(Text.of(""), BossBar.Color.RED, BossBar.Style.NOTCHED_6);

    private Text title;

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> endBloodMoonTask = null;

    public BloodMoonListener(final BloodMoonManager bloodMoonManager) {
        this.bloodMoonManager = bloodMoonManager;
    }

    public BloodMoonListener load(boolean enabled) {
        if (!enabled) {
            onPlayerDeath();
            onBloodMoonStart();
            onBloodMoonEnd();
        }
        return this;
    }

    private void onPlayerDeath() {
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            var world = player.getWorld();
            var name = player.getName().asString();

            var day = bloodMoonManager.getServerCore().serverGameManager().day();

            player.changeGameMode(GameMode.SPECTATOR);

            world.getPlayers().forEach(online -> {

                online.sendMessage(ChatFormatter.textFormat("&7&lEl alma de &c&l" + name + " &7&lha caído ante el &8&lABISMO&7&l."), false);
                online.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 50F, -8.0F);

                try {
                    Animation.play(online, "bloodmoonanimation", 20, 0);
                    TimedActionBar.send(online, damageSource.getDeathMessage(player), 1000 * 6);
                } catch (NoSuchAnimationException | InvalidTitleTimings | MinimumStayTimeIsGreaterThatStayTime e) {
                    e.printStackTrace();
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        online.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 50F, 100F);
                    }
                }, 1000);
            });

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (day == 0) {
                        bloodMoonManager.start(0.5);
                    } else {
                        bloodMoonManager.start(day);
                    }
                }
            }, 3*1000);
            return true;
        });
    }

    /**
     * Creates the task that updates the BloodMoon boss-bar each second.
     */
    private void endBloodMoonTask() {
        endBloodMoonTask = executorService.scheduleAtFixedRate(() -> {
            var remainBloodMoon = bloodMoonManager.getFormattedRemainingTime();

            title = ChatFormatter.textFormat("&c&ka &r☠ &c&lBLOODMOON: " + Formatting.YELLOW + (remainBloodMoon.equals("") ? "No hay una BloodMoon activa en este momento." : remainBloodMoon) + " &r☠ &c&ka");
            serverBossbar.setName(title);
            serverBossbar.setPercent((float) Math.max(0d, Math.min(1d, (double) bloodMoonManager.getMillisToEnd() / bloodMoonManager.bloodMoonData().getTotalTime())));
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void onBloodMoonStart() {
        BloodMoonEvents.BloodMoonStarted.EVENT.register(manager -> {
            if (endBloodMoonTask == null) endBloodMoonTask();
            var world = manager.getServerCore().serverGameManager().minecraftServer().getOverworld();

            world.getPlayers().forEach(player -> {
                if (!serverBossbar.getPlayers().contains(player)) {
                    serverBossbar.addPlayer(player);
                }
            });
            return ActionResult.PASS;
        });
    }

    private void onBloodMoonEnd() {
        BloodMoonEvents.BloodMoonEnded.EVENT.register(manager -> {
            var server = manager.getServerCore().serverGameManager().minecraftServer();

            if (endBloodMoonTask != null) {
                endBloodMoonTask.cancel(false);
                endBloodMoonTask = null;
            }

            disable(server);

            return ActionResult.PASS;
        });
    }

    /**
     * Disables the boss-bar.
     */
    public void disable(MinecraftServer server) {
        var players = server.getOverworld().getPlayers();
        players.forEach(player -> {
            if (serverBossbar.getPlayers().contains(player)) {
                serverBossbar.removePlayer(player);
            }
        });
    }

    /**
     *
     * @return the BloodMoon ServerBossBar.
     */
    public ServerBossBar bossBar() {
        return serverBossbar;
    }

}
