package com.chillrain.chillrainrandomideas;

import com.chillrain.chillrainrandomideas.blocks.ModBlocks;
import com.chillrain.chillrainrandomideas.blocks.tiles.ModTiles;
import com.chillrain.chillrainrandomideas.handler.HandlerManager;
import com.chillrain.chillrainrandomideas.items.ModItems;
import com.chillrain.chillrainrandomideas.proxy.CommonProxy;
import com.chillrain.chillrainrandomideas.tabs.RandomIdeasTab;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mod(modid = ChillRainRandomIdeas.MODID, version = ChillRainRandomIdeas.VERSION, dependencies = "required-after:DraconicEvolution")
public class ChillRainRandomIdeas
{
    public static final String MODID = "chillrainrandomideas";
    @Mod.Instance(ChillRainRandomIdeas.MODID)
    public static ChillRainRandomIdeas instance;

    public static RandomIdeasTab tab = new RandomIdeasTab("randomIdeas");
    public static final String VERSION = "1.3.0";
    @SidedProxy(clientSide = Constant.CLIENTPROXY, serverSide = Constant.COMMONPROXY)
    public static CommonProxy proxy;
    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
    @EventHandler
    public void register(FMLInitializationEvent event) {
       EVENT_BUS.register(this);
       HandlerManager.register(EVENT_BUS);

    }
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        File configFile = event.getSuggestedConfigurationFile();
        ModConfig.init(new Configuration(configFile));
        ModItems.preInit();
        proxy.preInit(event);
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        ModItems.init();
        ModBlocks.init();
        ModTiles.init();
        tab.init();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
        ModItems.postInit();
        ModBlocks.postInit();
        ModTiles.postInit();
        tab.postInit();
    }
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        event.player.addChatMessage(new ChatComponentText("Welcome to use Chill_Rain's mod!"));
        System.out.println("Welcome to use Chill_Rain's mod!");
    }
}
