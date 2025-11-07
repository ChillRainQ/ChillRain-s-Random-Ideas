package com.chillrain.chillrainrandomideas.utils;

import net.minecraft.world.World;

/**
 * ClientServerUtil
 *
 * @author Chill_Rain 2025/11/06
 */
public class ClientServerUtil {
    public static boolean isClient(World world){
        return world.isRemote;
    }
    public static boolean isServer(World world){
        return !isClient(world);
    }
}
