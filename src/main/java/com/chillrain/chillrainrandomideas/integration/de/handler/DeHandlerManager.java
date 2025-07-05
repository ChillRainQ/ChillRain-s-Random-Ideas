package com.chillrain.chillrainrandomideas.integration.de.handler;

import com.chillrain.chillrainrandomideas.integration.de.client.handler.ClientEventHandler;
import com.chillrain.chillrainrandomideas.integration.de.client.handler.ResourceHandler;
import com.chillrain.chillrainrandomideas.interfaces.IHandlerManager;
import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * HandlerManager
 * Handler管理器
 * @author Chill_Rain 2025/06/26
 */
public class DeHandlerManager {
    /**
     * 注册Handler
     * @param bus Forge 事件总线
     */
    public static void register(EventBus bus){
        bus.register(new SpecialArmorHandler());
        bus.register(new ClientEventHandler());
        bus.register(new ResourceHandler());
    }
}
