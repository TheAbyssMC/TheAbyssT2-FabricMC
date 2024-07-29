package club.theabyss.global.mixins.server.data;

import club.theabyss.TheAbyss;
import club.theabyss.global.interfaces.server.entity.IDataTracker;
import club.theabyss.server.global.utils.DataTrackerChanger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

@Mixin(DataTracker.class)
public abstract class DataTrackerMixin implements IDataTracker {

    @Shadow @Final private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Shadow protected abstract <T> void addTrackedData(TrackedData<T> trackedData, T object);

    @Shadow @Final private Entity trackedEntity;

    @Override
    public void reloadTrackedDataEntries() {
        this.lock.writeLock().lock();

        var entries = DataTrackerChanger.ofOrNull(trackedEntity);

        if (entries != null) {
            entries.entries.forEach((k, v) -> {
                var value = v.get();
                addTrackedData(k, value.isPresent() ? value.get() : entries.defaultValues.get(k));
            });

            TheAbyss.getInstance().serverManager().minecraftServer().getPlayerManager().getPlayerList().forEach(p -> p.networkHandler.sendPacket(new EntityTrackerUpdateS2CPacket(trackedEntity.getId(), trackedEntity.getDataTracker(), true)));
        }

        this.lock.writeLock().unlock();
    }

}
