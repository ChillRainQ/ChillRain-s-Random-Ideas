package com.chillrain.chillrainrandomideas.interfaces;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

/**
 * IModBlocks
 *
 * @author Chill_Rain 2025/10/02
 */
public abstract class IModBlocks {
    public List<Block> blocks = Lists.newArrayList();
    public void registerBlock(Block block, String name){
        blocks.add(block);
        GameRegistry.registerBlock(block, name);
    }
    abstract public void postInit();

    abstract public void init();
}
