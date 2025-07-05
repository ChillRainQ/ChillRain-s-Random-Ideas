package com.chillrain.chillrainrandomideas.integration;


import cpw.mods.fml.common.Loader;

/**
 * Created by brandon3055 on 29/9/2015.
 */
public class ModHelper {

    public static final boolean isDeInstalled;
    static {
        isDeInstalled = Loader.isModLoaded("DraconicEvolution");
    }
}
