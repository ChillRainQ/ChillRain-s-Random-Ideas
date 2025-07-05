package com.chillrain.chillrainrandomideas.client.interfaces;

import net.minecraftforge.client.IItemRenderer;

public interface IRenderTweak extends com.brandon3055.draconicevolution.client.render.IRenderTweak{
    void tweakRender(IItemRenderer.ItemRenderType var1);
    void transform(IItemRenderer.ItemRenderType type);
}