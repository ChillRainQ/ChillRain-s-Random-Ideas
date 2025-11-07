package com.chillrain.chillrainrandomideas.bugfix.containerfix;

import net.minecraftforge.common.config.Configuration;

/**
 * Config
 *
 * @author Chill_Rain 2025/07/24
 */
public class CRBugFixConfig {
    public static Configuration config;
    public static boolean containerEventHandlerOpen;
    public static void init(Configuration config){
        CRBugFixConfig.config = config;
        syncConfig();
    }
    public static void syncConfig(){
        String categoryGeneral = Configuration.CATEGORY_GENERAL;
        containerEventHandlerOpen = config.getBoolean("containerBreakHandlerOpen", categoryGeneral, true, "open ContainerEventHandler");
        if (config.hasChanged()){
            config.save();
        }
    }
}
