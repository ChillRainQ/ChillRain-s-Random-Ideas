package com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles;

import com.chillrain.chillrainrandomideas.interfaces.IModTiles;

/**
 * BotanyModBlocksTiles
 *
 * @author Chill_Rain 2025/09/30
 */
public class BotanyModBlocksTiles extends IModTiles {
    public static TileElvenAltar tileElvenAltar;

    @Override
    public void postInit() {
        this.registerTiles(tileElvenAltar, BotanyTileName.TILE_ELVEN_ALTAR);
    }

    @Override
    public void init() {
        tileElvenAltar = new TileElvenAltar();
    }

}
