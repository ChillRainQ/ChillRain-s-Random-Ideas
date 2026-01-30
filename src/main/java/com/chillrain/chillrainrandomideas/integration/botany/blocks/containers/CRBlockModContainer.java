package com.chillrain.chillrainrandomideas.integration.botany.blocks.containers;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.core.BotaniaCreativeTab;
import vazkii.botania.common.item.block.ItemBlockMod;

/**
 * CRBlockModContainer
 *
 * @author Chill_Rain 2026/01/30
 */
public abstract class CRBlockModContainer <T extends TileEntity> extends BlockContainer {
    public int originalLight;

    protected CRBlockModContainer(Material par2Material) {
        super(par2Material);
        if (this.registerInCreative()) {
            this.setCreativeTab(BotaniaCreativeTab.INSTANCE);
        }

    }

    protected boolean shouldRegisterInNameSet() {
        return true;
    }

    public Block setLightLevel(float p_149715_1_) {
        this.originalLight = (int)(p_149715_1_ * 15.0F);
        return super.setLightLevel(p_149715_1_);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = IconHelper.forBlock(par1IconRegister, this);
    }

    public boolean registerInCreative() {
        return true;
    }

    public abstract T createNewTileEntity(World var1, int var2);
}
