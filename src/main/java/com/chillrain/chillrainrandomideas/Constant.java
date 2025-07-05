package com.chillrain.chillrainrandomideas;

import net.minecraft.util.DamageSource;

/**
 * Constant
 *
 * @author Chill_Rain 2025/07/01
 */
public class Constant {
    public static String NAMESPACE = ChillRainRandomIdeas.MODID.toLowerCase() + ":";
    public static final String DE = "DraconicEvolution";
    public static final String DECLIENTPROXY = "com.chillrain.chillrainrandomideas.integration.de.client.DeClientProxy";
    public static final String CLIENTPROXY = "com.chillrain.chillrainrandomideas.client.ClientProxy";
    public static final DamageSource ADMIN_KILL = (new DamageSource("administrative.kill")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();


}
