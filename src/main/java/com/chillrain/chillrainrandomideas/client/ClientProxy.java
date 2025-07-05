package com.chillrain.chillrainrandomideas.client;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
import com.brandon3055.draconicevolution.client.handler.HudHandler;
import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
import com.brandon3055.draconicevolution.client.keybinding.KeyInputHandler;
import com.brandon3055.draconicevolution.client.render.block.RenderPortal;
import com.brandon3055.draconicevolution.client.render.block.RenderTeleporterStand;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.brandon3055.draconicevolution.common.lib.References;
import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.de.client.DeClientProxy;
import com.chillrain.chillrainrandomideas.integration.de.client.render.RenderArmor;
import com.chillrain.chillrainrandomideas.integration.de.items.DeModItems;
import com.chillrain.chillrainrandomideas.interfaces.ClientProxyInterface;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import static com.brandon3055.draconicevolution.integration.nei.IMCForNEI.IMCSender;

/**
 * ClientProxy
 *
 * @author Chill_Rain 2025/06/27
 */
public class ClientProxy {
    private static final boolean debug = DraconicEvolution.debug;
    public static String downloadLocation;
    @SidedProxy(clientSide = Constant.DECLIENTPROXY, serverSide = References.SERVERPROXYLOCATION)
    public static ClientProxyInterface deClient;
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
//    public void registerRendering() {
//        if (!ConfigHandler.useOldArmorModel) {
//            MinecraftForgeClient.registerItemRenderer(DeModItems.chaosDraconicHelm, new RenderArmor(DeModItems.chaosDraconicHelm));
//            MinecraftForgeClient.registerItemRenderer(DeModItems.chaosDraconicChest, new RenderArmor(DeModItems.chaosDraconicChest));
//            MinecraftForgeClient.registerItemRenderer(DeModItems.chaosDraconicLeggs, new RenderArmor(DeModItems.chaosDraconicLeggs));
//            MinecraftForgeClient.registerItemRenderer(DeModItems.chaosDraconicBoots, new RenderArmor(DeModItems.chaosDraconicBoots));
//        }
//        // ISimpleBlockRendering
//        RenderingRegistry.registerBlockHandler(new RenderTeleporterStand());
//        RenderingRegistry.registerBlockHandler(new RenderPortal());
//        if (!ConfigHandler.useOldD2DToolTextures) {
//
//        }else{
//
//        }
//    }
//    public void registerRenderIDs() {
//        References.idTeleporterStand = RenderingRegistry.getNextAvailableRenderId();
//        References.idPortal = RenderingRegistry.getNextAvailableRenderId();
//    }

    public void postInit(FMLPostInitializationEvent event) {
        if (deClient != null && ModHelper.isDeInstalled){
            deClient.postInit(event);
        }
    }
}
