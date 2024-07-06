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
