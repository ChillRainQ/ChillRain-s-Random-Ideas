package com.chillrain.chillrainrandomideas.client.utils;

import net.minecraft.util.ResourceLocation;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MTLModel {
    public static class Group {
        public String name;
        public String materialName;
        public List<Face> faces = new ArrayList<>();
    }

    public static class Material {
        public String name;
        public ResourceLocation texture;
        public float[] specular = new float[]{0.5f, 0.5f, 0.5f};
        public float transparency = 1.0f;
        public boolean glowEffect;
    }

    public static class Face {
        public int[] vertexIndices;
        public int[] uvIndices;
        public int[] normalIndices;
    }

    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector2f> uvs = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Group> groups = new ArrayList<>();
    public Map<String, Material> materials = new HashMap<>();
}
