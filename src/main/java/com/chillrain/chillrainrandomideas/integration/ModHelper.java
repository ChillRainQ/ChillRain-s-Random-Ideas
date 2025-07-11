package com.chillrain.chillrainrandomideas.integration;


import cpw.mods.fml.common.Loader;
/**
 * ModHelper
 *
 * @author Chill_Rain 2025/07/05
 */

public class ModHelper {

    public static final boolean isDeInstalled;
    public static final boolean isBotanyInstalled;
    static {
        isDeInstalled = Loader.isModLoaded("DraconicEvolution");
        isBotanyInstalled = Loader.isModLoaded("Botania");
    }
}
