package club.theabyss.global.mixins.server.entites.projectile;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import club.theabyss.global.utils.TheAbyssConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @Inject(method = "createSpawnPacket", at = @At("HEAD"))
    public void sendTridentStackOnSpawn(CallbackInfoReturnable<Packet<?>> cir) {
        if ((Object) this instanceof TridentEntity tridentEntity) {
            PacketByteBuf passedData = PacketByteBufs.create();
            passedData.writeInt(Registry.ITEM.getRawId(((ITridentEntity)tridentEntity).getTridentStack$0().getItem()));

            ((Entity)(Object)this).getServer().getPlayerManager().getPlayerList().forEach(player -> {
                ServerPlayNetworking.send(player, new Identifier(TheAbyssConstants.MOD_ID, "twilight_trident"), passedData);
            });
        }
    }

}
