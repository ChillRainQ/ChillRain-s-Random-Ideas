package com.chillrain.chillrainrandomideas.proxy;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.de.client.DeClientProxy;
import com.chillrain.chillrainrandomideas.interfaces.IClientProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * ClientProxy
 *
 * @author Chill_Rain 2025/06/27
 */
public class ClientProxy extends CommonProxy{
    private static final boolean debug = DraconicEvolution.debug;
    public static IClientProxy deClient;
    static {
        deClient = Loader.isModLoaded("DraconicEvolution") ? new DeClientProxy() : null;
    }

    public void preInit(FMLPreInitializationEvent event) {
        if (debug) System.out.println("on Client side");
        if (ModHelper.isDeInstalled && deClient != null){
            deClient.preInit(event);
        }
    }

    public void init(FMLInitializationEvent event) {
        if (ModHelper.isDeInstalled && deClient != null){
            deClient.init(event);
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (deClient != null && ModHelper.isDeInstalled){
            deClient.postInit(event);
        }
    }
}
