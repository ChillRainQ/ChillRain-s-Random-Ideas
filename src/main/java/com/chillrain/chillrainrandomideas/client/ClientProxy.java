package com.chillrain.chillrainrandomideas.client;

import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.client.handler.ClientEventHandler;
import com.brandon3055.draconicevolution.client.handler.HudHandler;
import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
import com.brandon3055.draconicevolution.client.keybinding.KeyInputHandler;
import com.brandon3055.draconicevolution.client.render.block.*;
import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.client.interfaces.IRenderTweak;
import com.chillrain.chillrainrandomideas.client.render.RenderArmor;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.brandon3055.draconicevolution.common.lib.References;
import com.chillrain.chillrainrandomideas.client.render.RenderToolWithMTL;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import com.chillrain.chillrainrandomideas.items.ModItems;
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

    public void preInit(FMLPreInitializationEvent event) {
        if (debug) System.out.println("on Client side");
//        super.preInit(event);
        ResourceHandler.init(event);
    }

    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(new KeyInputHandler());
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new HudHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        registerRenderIDs();
        registerRendering();
        ResourceHandler.instance.tick(null);
        IMCSender();
    }
    public void registerRendering() {
        if (!ConfigHandler.useOldArmorModel) {
            MinecraftForgeClient.registerItemRenderer(ModItems.chaosDraconicHelm, new RenderArmor(ModItems.chaosDraconicHelm));
            MinecraftForgeClient.registerItemRenderer(ModItems.chaosDraconicChest, new RenderArmor(ModItems.chaosDraconicChest));
            MinecraftForgeClient.registerItemRenderer(ModItems.chaosDraconicLeggs, new RenderArmor(ModItems.chaosDraconicLeggs));
            MinecraftForgeClient.registerItemRenderer(ModItems.chaosDraconicBoots, new RenderArmor(ModItems.chaosDraconicBoots));
        }
        // ISimpleBlockRendering
        RenderingRegistry.registerBlockHandler(new RenderTeleporterStand());
        RenderingRegistry.registerBlockHandler(new RenderPortal());
        if (!ConfigHandler.useOldD2DToolTextures) {

        }else{

        }
    }
    public void registerRenderIDs() {
        References.idTeleporterStand = RenderingRegistry.getNextAvailableRenderId();
        References.idPortal = RenderingRegistry.getNextAvailableRenderId();
    }

    public void postInit(FMLPostInitializationEvent event) {
        ResourceHandler.instance.tick(null);
    }
}
