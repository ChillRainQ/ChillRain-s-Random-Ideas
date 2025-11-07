package com.chillrain.chillrainrandomideas.integration.botany.blocks;

import com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles.TileElvenAltar;
import com.chillrain.chillrainrandomideas.interfaces.IModBlocks;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * BotanyModBlocks
 *
 * @author Chill_Rain 2025/07/10
 */
public class BotanyModBlocks extends IModBlocks {
    public static BlockElvenAltar elvenAltar;

    public void init(){
        elvenAltar = new BlockElvenAltar();
    }
    public void postInit(){
        this.registerBlock(elvenAltar, BlockName.ElvenAlter);
    }
}
