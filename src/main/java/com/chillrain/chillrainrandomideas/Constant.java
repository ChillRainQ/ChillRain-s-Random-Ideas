package com.chillrain.chillrainrandomideas;

import net.minecraft.util.DamageSource;

/**
 * Constant
 *
 * @author Chill_Rain 2025/07/01
 */
public class Constant {
    public static String NAMESPACE = ChillRainRandomIdeas.MODID.toLowerCase() + ":";
    public static String BOTANIA_NAMESPACE = "Botania".toLowerCase() + ":";
    public static String DRACONICEVOLUTION_NAMESPACE = "DraconicEvolution".toLowerCase() + ":";
    public static final String DE = "DraconicEvolution";
    public static final String CLIENTPROXY = "com.chillrain.chillrainrandomideas.proxy.ClientProxy";
    public static final String COMMONPROXY = "com.chillrain.chillrainrandomideas.proxy.CommonProxy";
    public static final DamageSource ADMIN_KILL = (new DamageSource("administrative.kill")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();


}
