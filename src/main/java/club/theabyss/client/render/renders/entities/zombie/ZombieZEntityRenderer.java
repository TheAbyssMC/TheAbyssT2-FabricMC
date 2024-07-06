package club.theabyss.client.render.renders.entities.zombie;

import club.theabyss.client.render.models.entities.zombie.ZombieZEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.zombie.ZombieZEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class ZombieZEntityRenderer extends ZombieBaseEntityRenderer<ZombieZEntity, ZombieZEntityModel<ZombieZEntity>> {

    public ZombieZEntityRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public ZombieZEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer entityModelLayer, EntityModelLayer entityModelLayer2, EntityModelLayer entityModelLayer3) {
        super(context, new ZombieZEntityModel<>(context.getPart(entityModelLayer)), new ZombieZEntityModel<>(context.getPart(entityModelLayer2)), new ZombieZEntityModel<>(context.getPart(entityModelLayer3)));
    }

    @Override
    public Identifier getTexture(ZombieZEntity entity) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/zombie/zombie_z.png");
    }

}
