package club.theabyss.server.global.utils.timedTitle;

import club.theabyss.TheAbyss;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import javax.annotation.Nonnull;
import java.util.*;

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

/**
 * Class designed to stack several action bar tasks.
 *
 * @author <a href="https://github.com/Clipi-12">Clipi</a>
 * @author <a href="https://github.com/zLofro">Lofro</a>
 */
public class TimedActionBar {

    public static final HashMap<String, Queue> actionBarQueue = new HashMap<>(0, 1);

    public static final class Queue {
        public Timer task;
        public long lastTask;
        public final ArrayList<ActionBar> actionBars = new ArrayList<>(1);
    }

    public static final class ActionBar {
        public final Text text;
        public int stayTime, minimumStayTime;

        public ActionBar(@Nonnull Text text, int stayTime, int minimumStayTime) throws InvalidTitleTimings, MinimumStayTimeIsGreaterThatStayTime {
            this.text = text;
            this.stayTime = stayTime;
            this.minimumStayTime = minimumStayTime;
            if (stayTime < 1 || minimumStayTime < 1) throw new InvalidTitleTimings(this);
            if (minimumStayTime > stayTime) throw new MinimumStayTimeIsGreaterThatStayTime(this);
        }
    }

    public static void send(ServerPlayerEntity p, Text text) {
        try {
            send(p, text, 60); // Minecraft defaults
        } catch (InvalidTitleTimings ignored) {
        } // Will never happen
    }

    public static void send(ServerPlayerEntity p, Text text, int stayTime) throws InvalidTitleTimings {
        try {
            send(p, text, stayTime, stayTime);
        } catch (MinimumStayTimeIsGreaterThatStayTime ignored) {
        } // Will never happen
    }

    public static void send(ServerPlayerEntity p, Text text, int stayTime, int minimumStayTime) throws InvalidTitleTimings, MinimumStayTimeIsGreaterThatStayTime {
        var name = p.getName().asString();
        actionBarQueue.computeIfAbsent(name, k -> new Queue());
        var queue = actionBarQueue.get(name);
        queue.actionBars.add(new ActionBar(text, stayTime, minimumStayTime));
        if (queue.task == null) processActionBars(name);
    }

    public static void processActionBars(String name) {
        var server = TheAbyss.getInstance().serverGameManager().minecraftServer();

        if (server == null) throw new IllegalStateException("The current client server does not exist.");

        var p = server.getPlayerManager().getPlayer(name);
        if (p == null || p.isDisconnected()) return;

        Queue queue = actionBarQueue.get(name);
        if (queue == null) return;
        if (queue.actionBars.isEmpty()) {
            actionBarQueue.remove(name);
            return;
        }
        ActionBar current = queue.actionBars.get(0);

        queue.lastTask = new Date().getTime();
        queue.task = new Timer();
        queue.task.scheduleAtFixedRate(new TimerTask() {
            int time = 0;
            @Override
            public void run() {
                if (time >= current.stayTime || (time >= current.minimumStayTime && queue.actionBars.size() > 1)) {
                    queue.task.cancel();
                    queue.actionBars.remove(0);
                    if (queue.actionBars.isEmpty())
                        p.sendMessage(ChatFormatter.stringFormatToText(""), true);
                    processActionBars(name);
                } else if (time % 1000 == 0) {
                    p.sendMessage(current.text, true);
                }
                time += 50;
            }
        }, 0L, 50L);

    }
}
