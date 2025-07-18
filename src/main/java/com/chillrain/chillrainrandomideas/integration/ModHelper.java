package com.chillrain.chillrainrandomideas.integration;


import com.chillrain.chillrainrandomideas.ModConfig;
import cpw.mods.fml.common.Loader;
/**
 * ModHelper
 *
 * @author Chill_Rain 2025/07/05
 */

public class ModHelper {

    public static final boolean isDeInstalled;
    public static final boolean isBotanyInstalled;
    public static final boolean isAEInstalled;
    static {
        isDeInstalled = Loader.isModLoaded("DraconicEvolution") && ModConfig.openDE;
        isBotanyInstalled = Loader.isModLoaded("Botania") && ModConfig.openBotany;
        isAEInstalled = Loader.isModLoaded("appliedenergistics2") && ModConfig.openAE;
    }
}
