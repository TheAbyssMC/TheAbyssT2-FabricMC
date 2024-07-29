package club.theabyss.global.interfaces.server.data;

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
 * A class that adds utility method to all managers classes.
 */
public abstract class Instantiable<T> {
    private final T instance;

    /**
     * Default constructor for bukkit instantiation.
     * 
     * @param instance The plugin instance.
     */
    public Instantiable(T instance) {
        this.instance = instance;
    }

    /**
     * A getter that returns the plugin instance.
     * 
     * @return The plugin instance.
     */
    protected T instance() {
        return instance;
    }

}
