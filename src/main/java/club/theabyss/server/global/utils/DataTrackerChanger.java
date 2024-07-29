package club.theabyss.server.global.utils;

import club.theabyss.global.utils.NullableOptional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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

public class DataTrackerChanger<Data> {

    private DataTrackerChanger() {}

    public final Map<TrackedData<Data>, Supplier<NullableOptional<Data>>> entries = new HashMap<>();
    public final Map<TrackedData<Data>, Data> defaultValues = new HashMap<>();

    private static final Map<Entity, DataTrackerChanger<?>> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <Data> DataTrackerChanger<Data> of(Entity entity) {
        return (DataTrackerChanger<Data>) instances.computeIfAbsent(entity, k -> new DataTrackerChanger<>());
    }

    @SuppressWarnings("unchecked")
    public static <Data> DataTrackerChanger<Data> ofOrNull(Entity entity) {
        return (DataTrackerChanger<Data>) instances.getOrDefault(entity, null);
    }

    public static <Data> void computeFlag(Entity entity, TrackedData<Data> data, Supplier<NullableOptional<Data>> func, Data defaultVal) {
        var dataOf = DataTrackerChanger.<Data>of(entity);
        dataOf.entries.put(data, func);
        dataOf.defaultValues.put(data, defaultVal);
    }

}
