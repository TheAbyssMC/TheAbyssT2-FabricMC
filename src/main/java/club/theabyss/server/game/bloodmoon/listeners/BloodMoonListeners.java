package club.theabyss.server.game.bloodmoon.listeners;

import club.theabyss.TheAbyss;
import club.theabyss.server.game.bloodmoon.BloodMoonEvents;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.entity.events.player.ServerPlayerEntityEvents;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import club.theabyss.server.global.utils.customGlyphs.Animation;
import club.theabyss.server.global.utils.customGlyphs.NoSuchAnimationException;
import club.theabyss.server.global.utils.timedTitle.InvalidTitleTimings;
import club.theabyss.server.global.utils.timedTitle.MinimumStayTimeIsGreaterThatStayTime;
import club.theabyss.server.global.utils.timedTitle.TimedActionBar;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class BloodMoonListeners {
    public static void init() {
        onPlayerDeath();
        onBloodMoonStart();
        onBloodMoonEnd();
    }

    private static void onPlayerDeath() {
        ServerPlayerEntityEvents.PlayerDeath.EVENT.register((player, damageSource) -> {
            var bloodMoonManager = TheAbyss.getInstance().serverManager().serverGameManager().bloodMoonManager();

            var world = player.getWorld();
            var name = player.getName().asString();

            var day = bloodMoonManager.getServerManager().serverGameManager().day();

            var deathMessages = bloodMoonManager.getServerManager().deathMessagesManager().deathMessages();
            var deathMessage = deathMessages.deathMessage(player);

            player.setSpawnPoint(player.getWorld().getRegistryKey(), player.getBlockPos(), player.getYaw(), true, false);

            player.changeGameMode(GameMode.SPECTATOR);

            world.getPlayers().forEach(online -> {

                online.sendMessage(ChatFormatter.stringFormatToText("&7&lEl alma de &c&l" + name + " &7&lha caído ante el &8&lABISMO&7&l."), false);

                online.sendMessage(ChatFormatter.stringFormatToText("&8" + deathMessage), false);

                BloodMoonManager.bloodMoonExecutorService.schedule(() -> online.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 1000F, -8.0F), 50, TimeUnit.MILLISECONDS);

                try {
                    Animation.play(online, "bloodmoonanimation", 20, 0);
                    TimedActionBar.send(online, damageSource.getDeathMessage(player), 1000 * 6);
                } catch (NoSuchAnimationException | InvalidTitleTimings | MinimumStayTimeIsGreaterThatStayTime e) {
                    e.printStackTrace();
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        online.playSound(SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.MASTER, 1000F, 100F);
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
            var bloodMoonManager = TheAbyss.getInstance().serverManager().serverGameManager().bloodMoonManager();

            if (bloodMoonManager.updateBossBarTask == null) bloodMoonManager.updateBossBarTask();

            bloodMoonManager.showBossBarToAll(manager.getServerManager().minecraftServer());
            return ActionResult.PASS;
        });
    }

    private static void onBloodMoonEnd() {
        BloodMoonEvents.BloodMoonEnded.EVENT.register(manager -> {
            var bloodMoonManager = TheAbyss.getInstance().serverManager().serverGameManager().bloodMoonManager();

            var server = manager.getServerManager().serverGameManager().minecraftServer();

            bloodMoonManager.cancelBossBarTask(server);

            return ActionResult.PASS;
        });
    }

}
