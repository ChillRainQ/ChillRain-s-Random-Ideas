package com.chillrain.chillrainrandomideas.integration.de.handler;

import com.chillrain.chillrainrandomideas.integration.de.client.handler.ClientEventHandler;
import com.chillrain.chillrainrandomideas.integration.de.client.handler.ResourceHandler;
import com.chillrain.chillrainrandomideas.interfaces.IHandlerManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.relauncher.Side;

/**
 * HandlerManager
 * Handler管理器
 * @author Chill_Rain 2025/06/26
 */
public class DeHandlerManager implements IHandlerManager {
    /**
     * 注册Handler
     * @param bus Forge 事件总线
     */
    public void register(EventBus bus){
        bus.register(new SpecialArmorHandler());

    }
    public void registerClientSide(EventBus bus){
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT){
            bus.register(new ClientEventHandler());
            bus.register(new ResourceHandler());
        }
    }
}
