package com.chillrain.chillrainrandomideas.handler;

import com.chillrain.chillrainrandomideas.ModConfig;
import com.chillrain.chillrainrandomideas.bugfix.BugFixHandlerManager;
import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.botany.handler.BotanyHandlerManager;
import com.chillrain.chillrainrandomideas.integration.de.handler.DeHandlerManager;
import com.chillrain.chillrainrandomideas.interfaces.IHandlerManager;
import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * HandlerManager
 *
 * @author Chill_Rain 2025/07/05
 */
public class HandlerManager {
    public static IHandlerManager deHandler;
    public static IHandlerManager botanyHandler;
    public static IHandlerManager bugfixHandler;
    static {
        deHandler = ModHelper.isDeInstalled ? new DeHandlerManager() : null;
        botanyHandler = ModHelper.isBotanyInstalled ? new BotanyHandlerManager() : null;
        bugfixHandler = ModConfig.openBugfix ? new BugFixHandlerManager() : null;
    }
    public static void register(EventBus bus){
        if (ModHelper.isDeInstalled && deHandler != null){
            deHandler.register(bus);
            deHandler.registerClientSide(bus);
        }
        if (ModHelper.isBotanyInstalled && botanyHandler != null){
            botanyHandler.register(bus);
            botanyHandler.registerClientSide(bus);
        }
        if (ModConfig.openBugfix && bugfixHandler != null){
            bugfixHandler.register(bus);
            bugfixHandler.registerClientSide(bus);
        }
    }
}
