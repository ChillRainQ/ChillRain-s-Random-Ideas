package com.chillrain.chillrainrandomideas.interfaces;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * IModTiles
 *
 * @author Chill_Rain 2025/10/05
 */
public abstract class IModTiles {
    public List<TileEntity> tiles = Lists.newArrayList();
    public void registerTiles(TileEntity tile, String name){
        tiles.add(tile);
        GameRegistry.registerTileEntity(tile.getClass(), name);
    }
    abstract public void postInit();

    abstract public void init();
}
