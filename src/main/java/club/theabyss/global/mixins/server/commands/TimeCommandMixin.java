package club.theabyss.global.mixins.server.commands;

import club.theabyss.global.utils.GlobalGameManager;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TimeCommand;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TimeCommand.class)
public abstract class TimeCommandMixin {

    @Shadow
    private static int getDayTime(ServerWorld world) {
        return 0;
    }

    @Inject(method = "executeSet", at = @At("HEAD"), cancellable = true)
    private static void addBloodMoonBoolToSet(ServerCommandSource source, int time, CallbackInfoReturnable<Integer> cir) {

        if (GlobalGameManager.isBloodMoonActive()) {
            source.sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cNo es posible usar el comando time para cambiar la hora si hay una BloodMoon activa (termina la BloodMoon primero si es completamente necesario usar el comando)."), false);
            cir.setReturnValue(getDayTime(source.getWorld()));
        }
    }

    @Inject(method = "executeAdd", at = @At("HEAD"), cancellable = true)
    private static void addBloodMoonBoolToAdd(ServerCommandSource source, int time, CallbackInfoReturnable<Integer> cir) {

        if (GlobalGameManager.isBloodMoonActive()) {
            source.sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cNo es posible usar el comando time para cambiar la hora si hay una BloodMoon activa (termina la BloodMoon primero si es completamente necesario usar el comando)."), false);
            cir.setReturnValue(getDayTime(source.getWorld()));
        }
    }

}
