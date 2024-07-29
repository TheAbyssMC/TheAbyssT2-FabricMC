package club.theabyss.global.mixins.client.world;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import club.theabyss.networking.packet.c2s.FlashBangC2SDisableFlashPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void injectIntoDisconnect(CallbackInfo ci) {
        ClientPlayNetworking.send(FlashBangC2SDisableFlashPacket.ID, new FlashBangC2SDisableFlashPacket(FlashBangClientManager.getOpacity(), FlashBangClientManager.getSoundVolume(), FlashBangClientManager.getFlashSeconds(), FlashBangClientManager.getOpaqueTicks()).write());
        FlashBangClientManager.reset();
    }

}
