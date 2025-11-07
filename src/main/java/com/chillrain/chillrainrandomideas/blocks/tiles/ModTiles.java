package com.chillrain.chillrainrandomideas.blocks.tiles;

import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.botany.blocks.BotanyModBlocks;
import com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles.BotanyModBlocksTiles;
import com.chillrain.chillrainrandomideas.interfaces.IModBlocks;
import com.chillrain.chillrainrandomideas.interfaces.IModTiles;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * ModTiles
 *
 * @author Chill_Rain 2025/10/05
 */
public class ModTiles {
    public static List<TileEntity> tiles = Lists.newArrayList();
    public static IModTiles botanyModTiles;
    static {
        if (ModHelper.isBotanyInstalled){
            botanyModTiles = new BotanyModBlocksTiles();
        }
    }
    public static void init() {
        if (ModHelper.isBotanyInstalled){
            botanyModTiles.init();
        }
    }

    public static void postInit() {
        if (ModHelper.isBotanyInstalled){
            botanyModTiles.postInit();
            tiles.addAll(botanyModTiles.tiles);
        }
    }
}
