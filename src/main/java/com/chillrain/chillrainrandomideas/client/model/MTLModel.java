package com.chillrain.chillrainrandomideas.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.WavefrontObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * MTLModel
 *
 * @author Chill_Rain 2025/07/05
 */
// 自定义MTL加载器（需继承 WavefrontObject）
public class MTLModel extends WavefrontObject {
    private final Map<String, Material> materials = new HashMap<>();

    public MTLModel(ResourceLocation modelLocation) {
        super(modelLocation);
        loadMTL(modelLocation);
    }

    private void loadMTL(ResourceLocation objLocation) {
        try {
            String mtlPath = objLocation.getResourcePath().replace(".obj", ".mtl");
            ResourceLocation mtlLocation = new ResourceLocation(objLocation.getResourceDomain(), mtlPath);

            InputStream mtlStream = Minecraft.getMinecraft().getResourceManager()
                    .getResource(mtlLocation).getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(mtlStream));
            String line;
            Material currentMat = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("newmtl ")) {
                    currentMat = new Material();
                    materials.put(line.substring(7), currentMat);
                } else if (currentMat != null) {
                    if (line.startsWith("map_Kd ")) {
                        currentMat.texture = new ResourceLocation(
                                objLocation.getResourceDomain(),
                                "textures/items/" + line.substring(7)
                        );
                    } else if (line.startsWith("Ks ")) {
                        String[] rgb = line.split("\\s+");
                        currentMat.specular = new float[] {
                                Float.parseFloat(rgb[1]),
                                Float.parseFloat(rgb[2]),
                                Float.parseFloat(rgb[3])
                        };
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Material {
        public ResourceLocation texture;
        public float[] specular = new float[]{0.5f, 0.5f, 0.5f};
    }
}
