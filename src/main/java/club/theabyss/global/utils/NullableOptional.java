package club.theabyss.global.utils;

import java.util.NoSuchElementException;

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
 * @author <a href="https://github.com/zLofro">Lofro</a>
 */
public class NullableOptional<T> {

    public static <T> NullableOptional<T> of(T value) {
        return new NullableOptional<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> NullableOptional<T> absent() {
        return (NullableOptional<T>) absent;
    }

    private static final NullableOptional<?> absent = new NullableOptional<>();

    private final T value;

    private NullableOptional() {
        this(null);
    }

    private NullableOptional(T value) {
        this.value = value;
    }

    public boolean isPresent() {
        return this != absent;
    }

    public T get() {
        if (!isPresent()) throw new NoSuchElementException();
        return value;
    }

    public T getOrDefault(T def) {
        return isPresent() ? value : def;
    }

}
