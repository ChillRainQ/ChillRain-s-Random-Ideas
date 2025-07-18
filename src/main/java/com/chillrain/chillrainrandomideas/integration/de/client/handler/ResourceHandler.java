package com.chillrain.chillrainrandomideas.integration.de.client.handler;

import com.chillrain.chillrainrandomideas.Constant;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * ResourceHandler
 *
 * @author Chill_Rain 2025/06/27
 */
public class ResourceHandler extends com.brandon3055.draconicevolution.client.handler.ResourceHandler{
    private static final Map<String, ResourceLocation> cachedResources = Maps.newHashMap();
    public static ResourceLocation getResource(String rs) {
        if (!cachedResources.containsKey(rs)) {
            cachedResources.put(rs, new ResourceLocation(Constant.DRACONICEVOLUTION_NAMESPACE + rs));
        }

        return (ResourceLocation)cachedResources.get(rs);
    }
}
