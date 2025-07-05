package com.chillrain.chillrainrandomideas.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ObjMtlModel {

    // --------------------- 基础结构 ---------------------
    public static class Vector3f {
        public float x, y, z;
        public Vector3f(float x, float y, float z) { this.x = x; this.y = y; this.z = z; }
    }

    public static class Vector2f {
        public float u, v;
        public Vector2f(float u, float v) { this.u = u; this.v = v; }
    }

    public static class Face {
        public int[] vertexIndices;
        public int[] uvIndices;
        public int[] normalIndices;
    }

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
        public boolean glowEffect = false;
    }

    // --------------------- 模型数据 ---------------------
    public final List<Vector3f> vertices = new ArrayList<>();
    public final List<Vector2f> uvs = new ArrayList<>();
    public final List<Vector3f> normals = new ArrayList<>();
    public final List<Group> groups = new ArrayList<>();
    public final Map<String, Material> materials = new HashMap<>();

    // --------------------- 加载构造 ---------------------
    public ObjMtlModel(String modelPath) {
        loadMtlFile(modelPath.replace(".obj", ".mtl"));
        loadObjFile(modelPath);
        System.out.println("模型加载完成，顶点数: " + this.vertices.size()); // 打印顶点数
        System.out.println("材质组: " + this.materials.keySet()); // 打印材质组
    }

    private void loadObjFile(String modelPath) {
        try {
            ResourceLocation objRes = new ResourceLocation(modelPath);
            InputStream input = Minecraft.getMinecraft().getResourceManager().getResource(objRes).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;
            Group currentGroup = new Group();
            currentGroup.name = "default";
            currentGroup.materialName = null;
            groups.add(currentGroup);
            String currentMaterial = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("v ")) {
                    String[] parts = line.split("\\s+");
                    vertices.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                } else if (line.startsWith("vt ")) {
                    String[] parts = line.split("\\s+");
                    uvs.add(new Vector2f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2])
                    ));
                } else if (line.startsWith("vn ")) {
                    String[] parts = line.split("\\s+");
                    normals.add(new Vector3f(
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])
                    ));
                } else if (line.startsWith("usemtl ")) {
                    currentMaterial = line.substring(7).trim();
                    currentGroup.materialName = currentMaterial;
                } else if (line.startsWith("g ")) {
                    currentGroup = new Group();
                    currentGroup.name = line.substring(2).trim();
                    currentGroup.materialName = currentMaterial;
                    groups.add(currentGroup);
                } else if (line.startsWith("f ")) {
                    Face face = new Face();
                    String[] parts = line.substring(2).trim().split(" ");
                    if (parts.length != 3) continue; // 只支持三角面

                    face.vertexIndices = new int[3];
                    face.uvIndices = new int[3];
                    face.normalIndices = new int[3];

                    for (int i = 0; i < 3; i++) {
                        String[] segs = parts[i].split("/"); // v/vt/vn
                        face.vertexIndices[i] = Integer.parseInt(segs[0]) - 1;

                        if (segs.length > 1 && !segs[1].isEmpty()) {
                            face.uvIndices[i] = Integer.parseInt(segs[1]) - 1;
                        }

                        if (segs.length > 2 && !segs[2].isEmpty()) {
                            face.normalIndices[i] = Integer.parseInt(segs[2]) - 1;
                        }
                    }

                    currentGroup.faces.add(face);
                }
            }

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load .obj: " + modelPath, e);
        }
    }

    private void loadMtlFile(String mtlPath) {
        try {
            ResourceLocation mtlRes = new ResourceLocation(mtlPath);
            InputStream input = Minecraft.getMinecraft().getResourceManager().getResource(mtlRes).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            Material current = null;
            String domain = mtlRes.getResourceDomain();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                if (line.startsWith("newmtl ")) {
                    current = new Material();
                    current.name = line.substring(7).trim();
                    materials.put(current.name, current);
                } else if (current != null) {
                    if (line.startsWith("map_Kd ")) {
                        String tex = line.substring(7).trim();
                        current.texture = new ResourceLocation(domain, tex);
                    } else if (line.startsWith("Ks ")) {
                        String[] parts = line.split("\\s+");
                        current.specular = new float[]{
                                Float.parseFloat(parts[1]),
                                Float.parseFloat(parts[2]),
                                Float.parseFloat(parts[3])
                        };
                    } else if (line.startsWith("d ")) {
                        current.transparency = Float.parseFloat(line.substring(2));
                    } else if (line.contains("glow")) {
                        current.glowEffect = true;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load .mtl: " + mtlPath, e);
        }
    }

    // 工具方法：通过名称获取 group
    public Group getGroup(String name) {
        for (Group g : groups) {
            if (g.name.equals(name)) return g;
        }
        return null;
    }
}
