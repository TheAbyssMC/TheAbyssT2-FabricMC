package club.theabyss.server.game.bloodmoon.listeners;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import club.theabyss.server.global.utils.customGlyphs.Animation;
import club.theabyss.server.global.utils.customGlyphs.NoSuchAnimationException;
import club.theabyss.server.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.server.global.utils.timedTitle.MinimumStayTimeIsGreaterThatStayTime;
import club.theabyss.server.global.utils.timedTitle.TimedActionBar;
import club.theabyss.server.game.bloodmoon.BloodMoonEvents;
import club.theabyss.server.game.entity.events.player.ServerPlayerEntityEvents;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;

import java.util.Timer;
import java.util.TimerTask;

public class BloodMoonListeners {

    public static void init() {
        onPlayerDeath();
        onBloodMoonStart();
        onBloodMoonEnd();
    }

    private static void onPlayerDeath() {
        ServerPlayerEntityEvents.PlayerDeath.EVENT.register((player, damageSource) -> {
            var bloodMoonManager = TheAbyssManager.getInstance().serverManager().serverGameManager().bloodMoonManager();

            var world = player.getWorld();
            var name = player.getName().asString();

            var day = bloodMoonManager.getServerCore().serverGameManager().day();

            var deathMessages = bloodMoonManager.getServerCore().deathMessagesManager().deathMessages();
            var deathMessage = deathMessages.deathMessage(player);

            player.changeGameMode(GameMode.SPECTATOR);

            world.getPlayers().forEach(online -> {

                online.sendMessage(ChatFormatter.stringFormatToText("&7&lEl alma de &c&l" + name + " &7&lha caído ante el &8&lABISMO&7&l."), false);

                online.sendMessage(ChatFormatter.stringFormatToText("&8" + deathMessage), false);

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

            return ActionResult.PASS;
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            SkillTreeManager.updatePlayerHealth(newPlayer);
        });
    }

    private static void onBloodMoonStart() {
        BloodMoonEvents.BloodMoonStarted.EVENT.register(manager -> {
            var bloodMoonManager = TheAbyssManager.getInstance().serverManager().serverGameManager().bloodMoonManager();

            if (bloodMoonManager.updateBossBarTask == null) bloodMoonManager.updateBossBarTask();

            bloodMoonManager.showBossBarToAll(manager.getServerCore().minecraftServer());
            return ActionResult.PASS;
        });
    }

    private static void onBloodMoonEnd() {
        BloodMoonEvents.BloodMoonEnded.EVENT.register(manager -> {
            var bloodMoonManager = TheAbyssManager.getInstance().serverManager().serverGameManager().bloodMoonManager();

            var server = manager.getServerCore().serverGameManager().minecraftServer();

            bloodMoonManager.cancelBossBarTask(server);

            return ActionResult.PASS;
        });
    }

}
