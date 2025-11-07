package com.chillrain.chillrainrandomideas.bugfix;

import com.chillrain.chillrainrandomideas.bugfix.containerfix.CRBugFixConfig;
import com.chillrain.chillrainrandomideas.bugfix.containerfix.ContainerEventHandler;
import com.chillrain.chillrainrandomideas.interfaces.IHandlerManager;
import cpw.mods.fml.common.eventhandler.EventBus;

/**
 * HandlerManager
 *
 * @author Chill_Rain 2025/11/07
 */
public class BugFixHandlerManager implements IHandlerManager {
    @Override
    public void register(EventBus bus) {
        if(CRBugFixConfig.containerEventHandlerOpen)
            bus.register(new ContainerEventHandler());
    }

    @Override
    public void registerClientSide(EventBus bus) {

    }
}
