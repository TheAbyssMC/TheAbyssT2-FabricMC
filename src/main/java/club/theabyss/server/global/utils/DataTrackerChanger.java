package club.theabyss.server.global.utils;

import club.theabyss.global.utils.NullableOptional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

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
