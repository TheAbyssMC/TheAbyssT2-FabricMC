package club.theabyss.server.global.listeners;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.game.entity.EntityManager;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import club.theabyss.server.global.events.GameDateEvents;
import club.theabyss.server.global.utils.timedTitle.TimedActionBar;
import club.theabyss.server.global.utils.timedTitle.TimedTitle;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.ActionResult;

import java.util.Date;

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

public class GlobalServerListeners {

    public static void init() {
        registerCallbacks();
    }

    private static void registerCallbacks() {
        onPlayerConnect();
        onPlayerDisconnect();
        onDayHasElapsed();
    }

    private static void onDayHasElapsed() {
        GameDateEvents.DayHasElapsedEvent.EVENT.register(day -> {
            EntityManager.reloadEntities();
            return ActionResult.PASS;
        });
    }

    private static void onPlayerConnect() {
        ServerPlayConnectionEvents.JOIN.register((networkHandler, packetSender, server) -> {
            var player = networkHandler.getPlayer();
            var serverManager = TheAbyss.getInstance().serverManager();
            var bloodMoonManager = serverManager.serverGameManager().bloodMoonManager();

            // Process BloodMoon boss-bar.
            if (bloodMoonManager.isActive()) {
                bloodMoonManager.showBossBar(player);
            }

            // Process TimedTitle.
            var titleName = player.getName().asString();
            var titleQueue = TimedTitle.titleQueue.get(titleName);
            if (titleQueue != null) {
                if (titleQueue.task == null) TimedTitle.processTitles(titleName);
            }

            // Process TimedActionBar.
            var actionBarName = player.getName().asString();
            var actionBarQueue = TimedActionBar.actionBarQueue.get(actionBarName);
            if (actionBarQueue != null) {
                if (actionBarQueue.task == null) TimedActionBar.processActionBars(actionBarName);
            }

            // Process FlashBang data.
            var flashBangManager = GlobalDataAccess.getFlashBangManager();
            if (flashBangManager != null) {
                var opacityData = flashBangManager.getFlashBangData().getFlashBangDataMap().get(player.getUuid());
                if (opacityData != null) {
                    flashBangManager.updateData(player, opacityData.getOpacity(), opacityData.getSoundVolume(), opacityData.getFlashSeconds(), opacityData.getOpaqueTicks(), false); // TODO FIND A BETTER SOLUTION.
                }
            }

            SkillTreeManager.updatePlayerHealth(player);
        });
    }

    private static void onPlayerDisconnect() {
        ServerPlayConnectionEvents.DISCONNECT.register((networkHandler, server) -> {
            var player = networkHandler.getPlayer();

            var serverCore = TheAbyss.getInstance().serverManager();
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();

            bloodMoonManager.hideBossBar(player);

            // Process TimedTitle.
            var titleName = player.getName().asString();
            var titleQueue = TimedTitle.titleQueue.get(titleName);
            if (!(titleQueue == null || titleQueue.task == null || titleQueue.titles.isEmpty())) {
                titleQueue.task.cancel();
                titleQueue.task = null;
                var titleTimePassed = new Date().getTime() - titleQueue.lastTask;
                var titleCurrent = titleQueue.titles.get(0);
                if (titleTimePassed >= titleCurrent.fadeIn) {
                    titleCurrent.fadeIn = 0;
                    if (titleTimePassed < titleCurrent.minimumStayTime) { // Should never happen
                        titleCurrent.minimumStayTime -= (int) titleTimePassed;
                        titleCurrent.stayTime -= (int) titleTimePassed;
                    } else {
                        titleQueue.titles.remove(0);
                    }
                } else {
                    titleCurrent.fadeIn -= (int) titleTimePassed; // This is not a perfect solution, but is the best possible one
                }
            }

            // Process TimedActionBar.
            var actionBarName = player.getName().asString();
            var actionBarQueue = TimedActionBar.actionBarQueue.get(actionBarName);
            if (!(actionBarQueue == null || actionBarQueue.task == null || actionBarQueue.actionBars.isEmpty())) {
                actionBarQueue.task.cancel();
                actionBarQueue.task = null;
                var actionBarTimePassed = new Date().getTime() - actionBarQueue.lastTask;
                var current = actionBarQueue.actionBars.get(0);
                if (actionBarTimePassed < current.stayTime) { // Should never happen
                    current.stayTime -= (int) actionBarTimePassed;
                } else {
                    actionBarQueue.actionBars.remove(0);
                }
            }
        });
    }

}
