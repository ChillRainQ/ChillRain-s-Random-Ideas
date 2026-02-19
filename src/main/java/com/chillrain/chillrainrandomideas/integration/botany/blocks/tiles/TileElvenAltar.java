package com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.tile.TileRuneAltar;

import java.util.List;

public class TileElvenAltar extends TileRuneAltar implements ISparkAttachable {
    private ISparkEntity attachedSpark;
    RecipeRuneAltar currentRecipe;
    public int mana;
    public int recipeKeepTicks = 0;
    public TileElvenAltar() {
        super();
    }


    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {

    }

    @Override
    public int getAvailableSpaceForMana() {
        return this.manaToGet - this.mana;
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        List<ISparkEntity> sparks = this.worldObj.getEntitiesWithinAABB(ISparkEntity.class, AxisAlignedBB.getBoundingBox((double)this.xCoord, (double)(this.yCoord + 1), (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 2), (double)(this.zCoord + 1)));
        if (sparks.size() == 1) {
            Entity e = (Entity)sparks.get(0);
            return (ISparkEntity)e;
        } else {
            return null;
        }
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return false;
    }
}
