package club.theabyss.server.game.item.items.twilight;

import club.theabyss.TheAbyss;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TwilightTridentItem extends TridentItem {

    public TwilightTridentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int i) {
        if (livingEntity instanceof PlayerEntity playerEntity) {
            if (playerEntity.isTouchingWaterOrRain()) {
                int j = this.getMaxUseTime(itemStack) - i;
                if (j >= 10) {
                    int riptideLevel = 5;
                    if (playerEntity.isTouchingWaterOrRain()) {
                        if (!world.isClient) {
                            itemStack.damage(1, playerEntity, (playerEntityx) -> playerEntityx.sendToolBreakStatus(livingEntity.getActiveHand()));
                        }

                        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

                        float f = playerEntity.getYaw();
                        float g = playerEntity.getPitch();
                        float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float l = -MathHelper.sin(g * 0.017453292F);
                        float m = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float n = MathHelper.sqrt(h * h + l * l + m * m);
                        float o = 3.0F * ((1.0F + (float)riptideLevel) / 4.0F);
                        h *= o / n;
                        l *= o / n;
                        m *= o / n;
                        playerEntity.addVelocity(h, l, m);
                        playerEntity.useRiptide(20);
                        if (playerEntity.isOnGround()) {
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
                        }

                        TheAbyss.getLogger().info("uwu");

                        SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;

                        world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        if (!playerEntity.isTouchingWaterOrRain()) {
            return TypedActionResult.fail(itemStack);
        } else {
            playerEntity.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public boolean postHit(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, true, true));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, true, true));

        return true;
    }

    @Override
    public Text getName() {
        return ChatFormatter.stringFormatToText("&6Twilight Trident");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return ChatFormatter.stringFormatToText("&6Twilight Trident");
    }

    @Override
    public void postProcessNbt(NbtCompound nbtCompound) {
        super.postProcessNbt(nbtCompound);
        nbtCompound.putBoolean("Unbreakable", true);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> list, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, list, tooltipContext);

        list.add(ChatFormatter.stringFormatToText(""));
        list.add(ChatFormatter.stringFormatToText("&7Fabricado con restos del crepúsculo."));
        list.add(ChatFormatter.stringFormatToText(""));
        list.add(ChatFormatter.stringFormatToText("&6&lHABILIDAD: &6Twilight Boost"));
        list.add(ChatFormatter.stringFormatToText("&7Su brillo permite impulsarse de una forma"));
        list.add(ChatFormatter.stringFormatToText("&7mucho m�s potente. Sus golpes tambi�n tienen"));
        list.add(ChatFormatter.stringFormatToText("&7la capacidad de hacer brillar y paralizar."));
    }

}
