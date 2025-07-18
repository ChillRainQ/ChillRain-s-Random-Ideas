package com.chillrain.chillrainrandomideas.integration.botany.handler;

import com.chillrain.chillrainrandomideas.interfaces.IHandlerManager;
import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * HandlerManager
 *
 * @author Chill_Rain 2025/07/12
 */
public class BotanyHandlerManager implements IHandlerManager {
    @Override
    public void register(EventBus bus) {
        bus.register(new GemOfConquestHandler());
    }

    @Override
    public void registerClientSide(EventBus bus) {

    }
}
