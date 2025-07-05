package com.chillrain.chillrainrandomideas.client.render;

import com.chillrain.chillrainrandomideas.client.interfaces.IRenderTweak;
import com.chillrain.chillrainrandomideas.client.model.ObjMtlModel;
import com.chillrain.chillrainrandomideas.client.model.ObjMtlRender;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * 物品渲染器：支持加载 .obj 和 .mtl 材质并渲染
 */
public class RenderToolWithMTL implements IItemRenderer {

    private final ObjMtlModel model;
    private final ObjMtlRender renderer;
    private final float scale;
    private IRenderTweak tool;

    public RenderToolWithMTL(String modelPath, IRenderTweak tool,  float scale) {
        this.model = new ObjMtlModel(modelPath);
        this.renderer = new ObjMtlRender(model);
        this.scale = scale;
        this.tool = tool;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();

        // 渲染位置调整
        switch (type) {
            case ENTITY:
                GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
                break;
            case EQUIPPED:
                GL11.glTranslatef(0.5f, 0.5f, 0.5f);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glTranslatef(1.0f, 1.0f, 1.0f);
                break;
            case INVENTORY:
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                break;
            default:
                break;
        }
        tool.tweakRender(type);
        // 缩放
        GL11.glScalef(scale, scale, scale);

        // 渲染模型
        renderer.renderAll();

        GL11.glPopMatrix();
    }
}
