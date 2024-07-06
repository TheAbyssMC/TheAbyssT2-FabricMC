package club.theabyss.server.game.item.items.twilight.material;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class TwilightToolMaterial implements ToolMaterial {

    @Override
    public int getDurability() {
        return Integer.MAX_VALUE;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 40;
    }

    @Override
    public float getAttackDamage() {
        return 7;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 16;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

}
