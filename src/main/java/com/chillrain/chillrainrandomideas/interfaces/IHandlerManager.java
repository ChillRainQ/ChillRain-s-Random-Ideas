package com.chillrain.chillrainrandomideas.interfaces;

import cpw.mods.fml.common.eventhandler.EventBus;

public interface IHandlerManager {
    void register(EventBus bus);
    void registerClientSide(EventBus bus);
}
