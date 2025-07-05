package com.chillrain.chillrainrandomideas.integration.de.client.handler;

import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.chillrain.chillrainrandomideas.integration.de.items.armors.ChaosDraconicArmor;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

/**
 * ClientEventHandler
 *
 * @author Chill_Rain 2025/07/01
 */
public class ClientEventHandler {
    private static IModelCustom shieldSphere;
    public ClientEventHandler() {
        shieldSphere = AdvancedModelLoader.loadModel(ResourceHandler.getResource("models/shieldSphere.obj"));
    }

    @SubscribeEvent
    public void renderArmorEvent(RenderPlayerEvent.SetArmorModel event) {
        if (ConfigHandler.useOriginal3DArmorModel || ConfigHandler.useOldArmorModel || event.isCanceled()) return;
        if (event.stack != null
                && (event.stack.getItem() instanceof ChaosDraconicArmor)) {
            ItemArmor itemarmor = (ItemArmor) event.stack.getItem();
            ModelBiped modelbiped = itemarmor.getArmorModel(event.entityPlayer, event.stack, event.slot);
            event.renderer.setRenderPassModel(modelbiped);
            modelbiped.onGround = event.renderer.modelBipedMain.onGround;
            modelbiped.isRiding = event.renderer.modelBipedMain.isRiding;
            modelbiped.isChild = event.renderer.modelBipedMain.isChild;
            event.result = 1;
        }
    }
}
