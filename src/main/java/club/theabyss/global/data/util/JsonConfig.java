package club.theabyss.global.data.util;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.adapters.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

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
 * A class to save any state from objects to json files.
 *
 * @author Jcedeno.
 * @author zLofro.
 */
public class JsonConfig {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    private @Getter @Setter JsonObject jsonObject = new JsonObject();
    private final @Getter File file;

    public JsonConfig(String filename, String path) throws Exception {
        this.file = new File(path + File.separatorChar + filename);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            writeFile(file);
        } else {
            readFile(file);
        }
    }

    /**
     * A static constructor that creates a new {@link JsonConfig} object in the
     * specified save's folder.
     * 
     * @param filename The name of the file to create.
     * @return A new {@link JsonConfig} object.
     * @throws Exception If the file cannot be created.
     */
    public static JsonConfig savesConfig(String filename, String folderName, MinecraftServer server) throws Exception {
        return new JsonConfig(filename, server.getRunDirectory().toPath().resolve("saves").resolve(server.getSavePath(WorldSavePath.ROOT)).toFile().getAbsolutePath() + File.separatorChar + folderName);
    }

    /**
     * A static constructor that creates a new {@link JsonConfig} object in the
     * specified save's folder.
     *
     * @param filename The name of the file to create.
     * @return A new {@link JsonConfig} object.
     * @throws Exception If the file cannot be created.
     */
    public static JsonConfig serverConfig(String filename, String folderName, MinecraftServer server) throws Exception {
        return new JsonConfig(filename, server.getRunDirectory().getAbsolutePath() + File.separatorChar + folderName);
    }

    /**
     * A static constructor that creates a new {@link JsonConfig} object in the
     * specified mod's folder.
     *
     * @param filename The name of the file to create.
     * @return A new {@link JsonConfig} object.
     * @throws Exception If the file cannot be created.
     */
    public static JsonConfig modConfig(String filename, String folderName) throws Exception {
        return new JsonConfig(filename, folderName);
    }

    /**
     * A static constructor that creates a new {@link JsonConfig} object in the
     * specified mod's folder.
     *
     * @param filename The name of the file to create.
     * @return A new {@link JsonConfig} object.
     * @throws Exception If the file cannot be created.
     */
    public static JsonConfig modConfig(String filename) throws Exception {
        return new JsonConfig(filename);
    }

    public JsonConfig(String filename) throws Exception {
        this(filename, String.valueOf(FabricLoader.getInstance().getConfigDir()) + File.separatorChar + TheAbyss.class.getSimpleName());
    }

    public void save() throws Exception {
        writeFile(file);
    }

    public void load() throws Exception {
        readFile(file);
    }

    private void writeFile(File path) throws Exception {
        var writer = new FileWriter(path);

        gson.toJson(jsonObject, writer);
        writer.flush();
        writer.close();

    }

    private void readFile(File path) throws Exception {
        var reader = Files.newBufferedReader(Paths.get(path.getPath()));
        var object = gson.fromJson(reader, JsonObject.class);
        reader.close();

        jsonObject = object;
    }

    public String getRedisUri() {
        var uri = jsonObject.get("redisUri");
        return uri != null ? uri.getAsString() : null;
    }

}