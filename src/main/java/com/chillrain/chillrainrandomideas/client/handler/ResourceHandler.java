package com.chillrain.chillrainrandomideas.client.handler;

import com.brandon3055.draconicevolution.client.utill.CustomResourceLocation;
import com.brandon3055.draconicevolution.common.lib.References;
import com.chillrain.chillrainrandomideas.Constant;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceHandler
 *
 * @author Chill_Rain 2025/07/01
 */
public class ResourceHandler {
    public static com.brandon3055.draconicevolution.client.handler.ResourceHandler instance = new com.brandon3055.draconicevolution.client.handler.ResourceHandler();
    private static ResourceLocation defaultParticles;
    private static ResourceLocation particles = new ResourceLocation(
            References.RESOURCESPREFIX + "textures/particle/particles.png");
    private static Map<String, ResourceLocation> cachedResources = new HashMap<String, ResourceLocation>();
    public static Map<String, CustomResourceLocation> downloadedImages = new HashMap<String, CustomResourceLocation>();

    private static String savePath;
    private static File saveFolder;
    private static File imagesFolder;
    private static com.brandon3055.draconicevolution.client.handler.ResourceHandler.DownloadThread downloadThread;
    public static int downloadStatus = 0;

    // -------------------- File Handling -----------------------//



    public static File getConfigFolder() {
        if (saveFolder == null) {
            saveFolder = new File(savePath);
        }
        if (!saveFolder.exists()) saveFolder.mkdir();

        return saveFolder;
    }

    public static File getImagesFolder() {
        if (imagesFolder == null) {
            imagesFolder = new File(getConfigFolder(), "/resources/assets/draconicevolution/textures/gui/manualimages");
        }
        if (!imagesFolder.exists()) imagesFolder.mkdirs();

        return imagesFolder;
    }

    // ----------------------------------------------------------//

    public static void bindTexture(ResourceLocation texture) {
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
    }

    /**
     * Binds the vanilla particle sheet
     */
    public static void bindDefaultParticles() {
        if (defaultParticles == null) {
            try {
                defaultParticles = (ResourceLocation) ReflectionHelper
                        .getPrivateValue(EffectRenderer.class, null, "particleTextures", "field_110737_b");
            } catch (Exception e) {}
        }
        if (defaultParticles != null) bindTexture(defaultParticles);
    }

    public static void bindParticles() {
        bindTexture(particles);
    }

    public static ResourceLocation getResource(String rs) {
        if (!cachedResources.containsKey(rs))
            cachedResources.put(rs, new ResourceLocation(Constant.NAMESPACE + rs));
        return cachedResources.get(rs);
    }

    public static ResourceLocation getResourceWOP(String rs) {
        if (!cachedResources.containsKey(rs)) cachedResources.put(rs, new ResourceLocation(rs));
        return cachedResources.get(rs);
    }

    public static void bindResource(String rs) {
        bindTexture(ResourceHandler.getResource(rs));
    }
//    private static final Map<String, ResourceLocation> cachedResources = new HashMap();
//
//    public ResourceHandler() {
//    }
//
//    public static void bindTexture(ResourceLocation texture) {
//        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
//    }
//
//    public static ResourceLocation getResource(String rs) {
//        if (!cachedResources.containsKey(rs)) {
//            cachedResources.put(rs, new ResourceLocation(Constant.NAMESPACE + rs));
//        }
//
//        return (ResourceLocation)cachedResources.get(rs);
//    }
//
//    public static ResourceLocation getResource(String rs, String rs1) {
//        if (!cachedResources.containsKey(rs)) {
//            cachedResources.put(rs, new ResourceLocation(rs1 + rs));
//        }
//
//        return cachedResources.get(rs);
//    }
//
//    public static void bindResource(String rs) {
//        bindTexture(getResource(rs));
//    }
}
