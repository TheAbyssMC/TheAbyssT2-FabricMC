package club.theabyss.server.global.utils.timedTitle;

import club.theabyss.TheAbyss;
import club.theabyss.server.global.utils.titles.SendTitle;
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
 * Class designed to stack several title tasks.
 *
 * @author <a href="https://github.com/Clipi-12">Clipi</a>
 * @author <a href="https://github.com/zLofro">Lofro</a>
 */
public class TimedTitle {

    public static final HashMap<String, Queue> titleQueue = new HashMap<>(0, 1);

    public static final class Queue {
        public Timer task;
        public long lastTask;
        public final ArrayList<Title> titles = new ArrayList<>(1);
    }

    public static final class Title {
        public final String title, subtitle;
        public final int fadeOut;
        public int fadeIn, stayTime, minimumStayTime;

        public Title(@Nonnull String title, @Nonnull String subtitle, int fadeIn, int stayTime, int fadeOut, int minimumStayTime) throws MinimumStayTimeIsGreaterThatStayTime, InvalidTitleTimings {
            this.title = title;
            this.subtitle = subtitle;
            this.fadeIn = fadeIn;
            this.stayTime = stayTime;
            this.fadeOut = fadeOut;
            this.minimumStayTime = minimumStayTime;
            if (fadeIn < 0 || stayTime < 0 || fadeOut < 0 || minimumStayTime < 0) throw new InvalidTitleTimings(this);
            if (minimumStayTime > stayTime) throw new MinimumStayTimeIsGreaterThatStayTime(this);
        }
    }

    public static void send(ServerPlayerEntity p, String title, String subtitle) {
        try {
            send(p, title, subtitle, 10, 70, 20); // Minecraft defaults
        } catch (InvalidTitleTimings ignored) {} // Will never happen
    }

    public static void send(ServerPlayerEntity p, String title, String subtitle, int fadeIn, int stayTime, int fadeOut) throws InvalidTitleTimings {
        try {
            send(p, title, subtitle, fadeIn, stayTime, fadeOut, stayTime);
        } catch (MinimumStayTimeIsGreaterThatStayTime ignored) {} // Will never happen
    }
    public static void send(ServerPlayerEntity p, String title, String subtitle, int fadeIn, int stayTime, int fadeOut, int minimumStayTime) throws MinimumStayTimeIsGreaterThatStayTime, InvalidTitleTimings {
        var name = p.getName().asString();
        titleQueue.computeIfAbsent(name, k -> new Queue());
        var queue = titleQueue.get(name);
        queue.titles.add(new Title(title, subtitle, fadeIn, stayTime, fadeOut, minimumStayTime));
        if (queue.task == null) processTitles(name);
    }

    public static void processTitles(String name) {
        var server = TheAbyss.getInstance().serverGameManager().minecraftServer();

        if (server == null) throw new IllegalStateException("The current client server does not exist.");

        var p = server.getPlayerManager().getPlayer(name);
        if (p == null || p.isDisconnected()) return;

        var queue = titleQueue.get(name);
        if (queue == null) return;
        if (queue.titles.isEmpty()) {
            titleQueue.remove(name);
            return;
        }
        var current = queue.titles.get(0);
        SendTitle.send(p, Text.of(current.title), Text.of(current.subtitle), current.fadeIn, current.stayTime, current.fadeOut);

        queue.lastTask = new Date().getTime();
        queue.task = new Timer();
        queue.task.schedule(new TimerTask() {
            @Override
            public void run() {
                queue.titles.remove(0);
                processTitles(name);
            }
        }, (current.fadeIn + current.minimumStayTime) * 50L);
    }
}
