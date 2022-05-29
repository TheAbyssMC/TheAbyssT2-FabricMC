package club.theabyss.server.global.listeners;

import club.theabyss.global.utils.timedTitle.TimedActionBar;
import club.theabyss.global.utils.timedTitle.TimedTitle;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import club.theabyss.server.global.events.GameDateEvents;
import club.theabyss.server.global.events.ServerPlayerConnectionEvents;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class GlobalServerListeners {

    private final TheAbyssServerManager serverCore;

    public GlobalServerListeners(final TheAbyssServerManager serverCore) {
        this.serverCore = serverCore;
    }

    public GlobalServerListeners load(boolean globalEnabled) {
        if (!globalEnabled) {
            registerCallbacks();
        }
        return this;
    }

    private void registerCallbacks() {
        onPlayerConnects();
        onPlayerDisconnects();
        onDayHasElapsed();
    }

    private void onDayHasElapsed() {
        GameDateEvents.DayHasElapsedEvent.EVENT.register(day -> {
            var worlds = serverCore.minecraftServer().getWorlds();
            boolean[] hasFailed = {false};

            worlds.forEach(world -> world.iterateEntities().forEach(entity -> {
                if (entity instanceof MobEntity mobEntity) {
                    try {
                        var goalSelector = mobEntity.getClass().getField("goalSelector");
                        goalSelector.setAccessible(true);

                        var targetSelector = mobEntity.getClass().getField("targetSelector");
                        targetSelector.setAccessible(true);

                        var goalSelectorInstance = ((GoalSelector)goalSelector.get(mobEntity));
                        var targetSelectorInstance = ((GoalSelector)targetSelector.get(mobEntity));

                        goalSelectorInstance.getRunningGoals().forEach(PrioritizedGoal::stop);
                        targetSelectorInstance.getRunningGoals().forEach(PrioritizedGoal::stop);

                        goalSelectorInstance.clear();
                        targetSelectorInstance.clear();

                        var initGoals = entity.getClass().getMethod("initGoals");
                        initGoals.setAccessible(true);

                        initGoals.invoke(entity);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException ex) {
                        hasFailed[0] = true;
                        ex.printStackTrace();
                    }
                }
            }));
            if (hasFailed[0]) {
                return ActionResult.FAIL;
            } else {
                return ActionResult.SUCCESS;
            }
        });
    }

    //TODO FIXEAR ERROR DE BOSSBAR DE UNA FORMA MENOS CUTRE.
    private void onPlayerConnects() {
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register((player, server) -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            if (server.isDedicated()) {
                if (bloodMoonManager.isActive()) {
                    if (!bossBar.getPlayers().contains(player)) {
                        bossBar.addPlayer(player);
                    }
                }
            } else if (!bloodMoonManager.isActive()) {
                bloodMoonManager.load();
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

            SkillTreeManager.updatePlayer(player);

            return ActionResult.PASS;
        });
    }

    private void onPlayerDisconnects() {
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register((player, server) -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            bossBar.removePlayer(player);

            // Process TimedTitle.
            var titleName = player.getName().asString();
            var titleQueue = TimedTitle.titleQueue.get(titleName);
            if (!(titleQueue == null || titleQueue.task == null || titleQueue.titles.size() == 0)) {
                titleQueue.task.cancel();
                titleQueue.task = null;
                var titleTimePassed = new Date().getTime() - titleQueue.lastTask;
                var titleCurrent = titleQueue.titles.get(0);
                if (titleTimePassed >= titleCurrent.fadeIn) {
                    titleCurrent.fadeIn = 0;
                    if (titleTimePassed < titleCurrent.minimumStayTime) { // Should never happen
                        titleCurrent.minimumStayTime -= titleTimePassed;
                        titleCurrent.stayTime -= titleTimePassed;
                    } else {
                        titleQueue.titles.remove(0);
                    }
                } else {
                    titleCurrent.fadeIn -= titleTimePassed; // This is not a perfect solution, but is the best possible one
                }
            }

            // Process TimedActionBar.
            var actionBarName = player.getName().asString();
            var actionBarQueue = TimedActionBar.actionBarQueue.get(actionBarName);
            if (!(actionBarQueue == null || actionBarQueue.task == null || actionBarQueue.actionBars.size() == 0)) {
                actionBarQueue.task.cancel();
                actionBarQueue.task = null;
                var actionBarTimePassed = new Date().getTime() - actionBarQueue.lastTask;
                var current = actionBarQueue.actionBars.get(0);
                if (actionBarTimePassed < current.stayTime) { // Should never happen
                    current.stayTime -= actionBarTimePassed;
                } else {
                    actionBarQueue.actionBars.remove(0);
                }
            }

            return ActionResult.PASS;
        });
    }

}
