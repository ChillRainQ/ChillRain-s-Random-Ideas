package com.chillrain.chillrainrandomideas.client.model;

import com.chillrain.chillrainrandomideas.client.model.ObjMtlModel;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.Map;

/**
 * 渲染解析后的 OBJ+MTL 模型
 * 用于替代 AdvancedModelLoader 模型的渲染流程
 */
public class ObjMtlRender {

    private final ObjMtlModel model;

    public ObjMtlRender(ObjMtlModel model) {
        this.model = model;
    }

    public void renderAll() {
        for (ObjMtlModel.Group group : model.groups) {
            renderGroup(group);
        }
    }

    public void renderGroup(ObjMtlModel.Group group) {
        ObjMtlModel.Material mat = model.materials.get(group.materialName);

        if (mat != null && mat.texture != null) {
            Minecraft.getMinecraft().renderEngine.bindTexture(mat.texture);
        }

        if (mat != null) {
            applyMaterial(mat);
        }

        GL11.glBegin(GL11.GL_TRIANGLES);
        for (ObjMtlModel.Face face : group.faces) {
            for (int i = 0; i < face.vertexIndices.length; i++) {
                if (face.normalIndices != null && i < face.normalIndices.length) {
                    ObjMtlModel.Vector3f normal = model.normals.get(face.normalIndices[i]);
                    GL11.glNormal3f(normal.x, normal.y, normal.z);
                }

                if (face.uvIndices != null && i < face.uvIndices.length) {
                    ObjMtlModel.Vector2f uv = model.uvs.get(face.uvIndices[i]);
                    GL11.glTexCoord2f(uv.u, 1.0f - uv.v); // Forge 的纹理v轴反了
                }

                ObjMtlModel.Vector3f vert = model.vertices.get(face.vertexIndices[i]);
                GL11.glVertex3f(vert.x, vert.y, vert.z);
            }
        }
        GL11.glEnd();

        resetMaterial(mat);
    }

    private void applyMaterial(ObjMtlModel.Material mat) {
        if (mat.transparency < 1.0f || mat.glowEffect) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glColor4f(1f, 1f, 1f, mat.transparency);
        } else {
            GL11.glColor4f(1f, 1f, 1f, 1f);
        }

        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(mat.specular));
        GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 64f);
    }

    private void resetMaterial(ObjMtlModel.Material mat) {
        if (mat.transparency < 1.0f || mat.glowEffect) {
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
