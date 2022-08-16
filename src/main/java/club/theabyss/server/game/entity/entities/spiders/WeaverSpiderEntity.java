package club.theabyss.server.game.entity.entities.spiders;

import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WeaverSpiderEntity extends SpiderEntity {

    public WeaverSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.setHealth(60f);
        setCustomName(ChatFormatter.stringFormatToText("&6Weaver Spider&r"));
    }

    public static DefaultAttributeContainer.Builder createWeaverSpiderAttributes() {
        return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 60).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 18).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            var world = target.world;
            var blockPos = target.getBlockPos();
            var blockEntity = world.getBlockEntity(blockPos);

            if (!world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                var upperBlockPos = blockPos.up();

                if (!world.getBlockState(upperBlockPos).getBlock().equals(Blocks.AIR)) return true;

                placeCobWeb(world, upperBlockPos, blockEntity);

                return true;
            }

            placeCobWeb(world, blockPos, blockEntity);

            return true;
        }
        return false;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(4, new AttackGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        // Always aggressive :]
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
    }

    private void placeCobWeb(World world, BlockPos blockPos, BlockEntity blockEntity) {
        Clearable.clear(blockEntity);

        var blockState = Block.postProcessState(Blocks.COBWEB.getDefaultState(), world, blockPos);

        world.setBlockState(blockPos, blockState, 2);
        world.updateNeighbors(blockPos, blockState.getBlock());
    }

}
