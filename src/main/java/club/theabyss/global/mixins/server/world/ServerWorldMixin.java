package club.theabyss.global.mixins.server.world;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SleepManager;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

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

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Shadow @Final List<ServerPlayerEntity> players;

    @Shadow @Final private ServerWorldProperties worldProperties;

    @Shadow @Final private SleepManager sleepManager;

    // Sends the WorldTimeUpdateS2CPacket to all the online players immediately after changing the world time to fix some timing bugs.
    @Inject(at = @At("TAIL"), method = "setTimeOfDay")
    public void fixTimePacketBug(long timeOfDay, CallbackInfo ci) {
        players.forEach(p -> p.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(worldProperties.getTime(), worldProperties.getTimeOfDay(), worldProperties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))));
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/SleepManager;canSkipNight(I)Z"))
    private boolean canSkipNight(SleepManager instance, int percentage) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            players.forEach(p -> {
                if (p.isSleeping()) p.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&cNo puedes dormir a partir del día 7."), true);
            });
            return false;
        } else {
            if (GlobalDataAccess.isBloodMoonActive()) {
                players.forEach(p -> {
                    if (p.isSleeping()) p.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&cNo puedes dormir durante una BloodMoon."), true);
                });
                return false;
            } else {
                return sleepManager.canSkipNight(percentage);
            }
        }
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setTimeOfDay(J)V"))
    private void handleNaturalBloodMoonSchedule(ServerWorld instance, long l) {
        var bloodMoonManager = TheAbyss.getInstance().serverGameManager().bloodMoonManager();
        var naturalBloodMoonIn = bloodMoonManager.bloodMoonData().getNaturalBloodMoonIn();
        var skipAmount = 24_000 - instance.getTime();

        if (skipAmount >= bloodMoonManager.getNaturalBloodMoonRemainingTime()) {
            skipAmount = naturalBloodMoonIn;
        }
        naturalBloodMoonIn -= skipAmount;
        bloodMoonManager.bloodMoonData().setNaturalBloodMoonIn(naturalBloodMoonIn);

        bloodMoonManager.createNaturalBloodMoonTask(naturalBloodMoonIn);

        worldProperties.setTimeOfDay(l);
    }

}
