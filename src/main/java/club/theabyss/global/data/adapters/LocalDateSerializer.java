package club.theabyss.global.data.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
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

public class LocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var jsonObject = json.getAsJsonObject();

        var year = jsonObject.get("year");
        var month = jsonObject.get("month");
        var day = jsonObject.get("day");

        String yearVal = year.getAsString();
        String monthVal = month.getAsString();
        String dayVal = day.getAsString();

        return LocalDate.parse(yearVal+"-"+monthVal+"-"+dayVal);
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        var json = new JsonObject();

        json.addProperty("year", localDate.getYear() < 10 ? "0" + localDate.getYear() : String.valueOf(localDate.getYear()));
        json.addProperty("month", localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue() : String.valueOf(localDate.getMonthValue()));
        json.addProperty("day", localDate.getDayOfMonth() < 10 ? "0" + localDate.getDayOfMonth() : String.valueOf(localDate.getDayOfMonth()));

        return json;
    }

    /**
     * Static constructor from gson.
     *
     * @return A new instance of {@link LocalDateSerializer}.
     */
    public static LocalDateSerializer getSerializer() {
        return new LocalDateSerializer();
    }

}
