package club.theabyss.server.global.utils.customGlyphs;

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

class AliasAlreadyExistsException extends Exception {
    AliasAlreadyExistsException(String alias_1, char glyph_1, String alias_2) {
        super("The alias \"" + alias_1 + "\" could not be assigned to the glyph '\\u" + Integer.toHexString(glyph_1) + "' because it conflicts with the alias \"" + alias_2 + "\"");
    }
}
