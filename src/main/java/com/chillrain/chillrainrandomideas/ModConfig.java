package com.chillrain.chillrainrandomideas;

import net.minecraftforge.common.config.Configuration;

/**
 * ModConfig
 *
 * @author Chill_Rain 2025/07/14
 */
public class ModConfig {
    public static Configuration config;
    public static boolean openDE;
    public static boolean openBotany;
    public static boolean openChaoticArrmorCapacitorFirst;
    public static boolean openBugfix;

    public static void init(Configuration config){
        ModConfig.config = config;
        syncConfig();
    }
    public static void syncConfig(){
        String categoryGeneral = Configuration.CATEGORY_GENERAL;
        openDE = config.getBoolean("openDE", categoryGeneral, true, "open DE integration");
        openBotany = config.getBoolean("openBotany", categoryGeneral, true, "open Botany integration");
        openChaoticArrmorCapacitorFirst = config.getBoolean("openChaoticArrmorCapacitorFirst", categoryGeneral, true, "Advance func");
        openBugfix = config.getBoolean("openBugfix", categoryGeneral, false, "fixed some bug");
        if (config.hasChanged()){
            config.save();
        }
    }
}
