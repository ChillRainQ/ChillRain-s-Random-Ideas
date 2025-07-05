package com.chillrain.chillrainrandomideas.handler;

import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.de.client.handler.ClientEventHandler;
import com.chillrain.chillrainrandomideas.integration.de.client.handler.ResourceHandler;
import com.chillrain.chillrainrandomideas.integration.de.handler.DeHandlerManager;
import com.chillrain.chillrainrandomideas.integration.de.handler.SpecialArmorHandler;
import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * HandlerManager
 *
 * @author Chill_Rain 2025/07/05
 */
public class HandlerManager {
    public static void register(EventBus bus){
        if (ModHelper.isDeInstalled){
            DeHandlerManager.register(bus);
        }
    }
}
