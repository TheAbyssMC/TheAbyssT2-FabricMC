package club.theabyss.server.game.entity.entities.fox;

import club.theabyss.global.interfaces.entity.fox.IFoxEntity;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class ThiefFoxEntity extends FoxEntity implements Monster {

    public ThiefFoxEntity(EntityType<? extends ThiefFoxEntity> entityType, World world) {
        super(entityType, world);
        setCustomName(ChatFormatter.stringFormatToText("&cThief Fox"));
        setHealth(60);
    }

    public static DefaultAttributeContainer.Builder createThiefFoxAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15);
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        var uselessGoals = goalSelector.getGoals().stream().filter(g -> g.getGoal() instanceof FleeEntityGoal || g.getGoal() instanceof EscapeDangerGoal || g.getGoal() instanceof MoveToHuntGoal).toList();
        uselessGoals.forEach(g -> goalSelector.remove(g.getGoal()));

        goalSelector.add(5, new MoveToHuntGoal());

        targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        targetSelector.add(4, new ActiveTargetGoal<>(this, WolfEntity.class, true));
        targetSelector.add(4, new ActiveTargetGoal<>(this, PolarBearEntity.class, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            var entityType = target.getType();

            if (entityType.equals(EntityType.PLAYER)) {
                PlayerEntity player = (PlayerEntity) target;

                stealItem(player, this);
            }
            return true;
        } else {
            return false;
        }
    }

    private void stealItem(PlayerEntity player, Entity entity) {
        Random random = new Random();

        int randomSlot = random.nextInt(5, 46);
        int percentage = random.nextInt(100);

        if (percentage <= 60) return;

        var slotItem = player.getInventory().getStack(randomSlot);
        if (slotItem.getItem() == Items.AIR) return;
        player.getInventory().setStack(randomSlot, ItemStack.EMPTY);

        entity.dropStack(slotItem);
    }

    class MoveToHuntGoal extends Goal {
        public MoveToHuntGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (ThiefFoxEntity.this.isSleeping()) {
                return false;
            }
            LivingEntity livingEntity = ThiefFoxEntity.this.getTarget();
            return livingEntity != null && livingEntity.isAlive() && ThiefFoxEntity.this.squaredDistanceTo(livingEntity) > 36.0 && !ThiefFoxEntity.this.isInSneakingPose() && !ThiefFoxEntity.this.isRollingHead() && !ThiefFoxEntity.this.jumping;
        }

        @Override
        public void start() {
            ThiefFoxEntity.this.setSitting(false);
            ((IFoxEntity)ThiefFoxEntity.this).setWalking$0(false);
        }

        @Override
        public void stop() {
            LivingEntity livingEntity = ThiefFoxEntity.this.getTarget();
            if (livingEntity != null && ThiefFoxEntity.canJumpChase(ThiefFoxEntity.this, livingEntity)) {
                ThiefFoxEntity.this.setRollingHead(true);
                ThiefFoxEntity.this.setCrouching(true);
                ThiefFoxEntity.this.getNavigation().stop();
                ThiefFoxEntity.this.getLookControl().lookAt(livingEntity, ThiefFoxEntity.this.getMaxHeadRotation(), ThiefFoxEntity.this.getMaxLookPitchChange());
            } else {
                ThiefFoxEntity.this.setRollingHead(false);
                ThiefFoxEntity.this.setCrouching(false);
            }
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = ThiefFoxEntity.this.getTarget();
            if (livingEntity == null) {
                return;
            }
            ThiefFoxEntity.this.getLookControl().lookAt(livingEntity, ThiefFoxEntity.this.getMaxHeadRotation(), ThiefFoxEntity.this.getMaxLookPitchChange());
            if (ThiefFoxEntity.this.squaredDistanceTo(livingEntity) <= 36.0) {
                ThiefFoxEntity.this.setRollingHead(true);
                ThiefFoxEntity.this.setCrouching(true);
                ThiefFoxEntity.this.getNavigation().stop();
            } else {
                ThiefFoxEntity.this.getNavigation().startMovingTo(livingEntity, 1.5);
            }
        }
    }

}
