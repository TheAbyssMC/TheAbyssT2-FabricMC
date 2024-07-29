package club.theabyss.global.mixins.server.items;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

@Mixin(ItemGroup.class)
public class ItemGroupMixin {

    @Shadow @Final private String id;

    @Mutable @Shadow @Final private Text displayName;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyInit(int i, String string, CallbackInfo ci) {
        if ((TheAbyssConstants.MOD_ID + ".twilight").equals(string)) displayName = ChatFormatter.stringFormatToText("&6Twilight");
    }

    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void modifyDisplayName(CallbackInfoReturnable<Text> cir) {
        if ((TheAbyssConstants.MOD_ID + ".twilight").equals(id)) {
            cir.setReturnValue(ChatFormatter.stringFormatToText("&6Twilight"));
        }
    }



}
