package com.chillrain.chillrainrandomideas.interfaces;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class IClientProxy {
    public abstract void preInit(FMLPreInitializationEvent event);
    public abstract void init(FMLInitializationEvent event);
    public abstract void postInit(FMLPostInitializationEvent event);
}
