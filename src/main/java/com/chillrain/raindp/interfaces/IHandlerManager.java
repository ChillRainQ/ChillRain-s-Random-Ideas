package com.chillrain.raindp.interfaces;

import cpw.mods.fml.common.eventhandler.EventBus;

public interface IHandlerManager {
    void register(EventBus bus);
    void registerClientSide(EventBus bus);
}
