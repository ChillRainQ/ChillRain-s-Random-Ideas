package com.chillrain.chillrainrandomideas.handler;

import com.brandon3055.draconicevolution.client.gui.GuiHudConfig;
import com.brandon3055.draconicevolution.client.handler.HudHandler;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
import com.brandon3055.draconicevolution.common.utills.IHudDisplayBlock;
import com.brandon3055.draconicevolution.common.utills.IHudDisplayItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * HUDHandler
 *
 * @author Chill_Rain 2025/06/27
 */
public class HUDHandler extends HudHandler {
    private static List<String> hudList = null;
    private static List<String> ltHudList = null;
    private static float toolTipFadeOut = 0F;
    private static float armorStatsFadeOut = 0F;
    private static boolean showShieldHud = false;
    private static int shieldPercentCharge = 0;
    private static float shieldPoints = 0F;
    private static float maxShieldPoints = 0F;
    private static float shieldEntropy = 0F;
    private static int rfCharge = 0;
    private static long rfTotal = 0;
    @Override
    public void drawHUD(RenderGameOverlayEvent.Post event) {
        super.drawHUD(event);
    }

    @SideOnly(Side.CLIENT)
    public static void clientTick() {
        if (ConfigHandler.hudSettings[6] > 0 && toolTipFadeOut > 1F - ((float) ConfigHandler.hudSettings[6] * 0.25F)) {
            toolTipFadeOut -= 0.1F;
        }
        if (hudList != null && (ltHudList == null || !hudList.equals(ltHudList))) toolTipFadeOut = 5F;
        if (ConfigHandler.hudSettings[7] > 0
                && armorStatsFadeOut > 1F - ((float) ConfigHandler.hudSettings[7] * 0.25F)) {
            armorStatsFadeOut -= 0.1F;
            if (armorStatsFadeOut < 0) armorStatsFadeOut = 0;
        }

        ltHudList = hudList;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.thePlayer == null) return;

        hudList = null;

        if (mc.currentScreen != null) {
            if (mc.currentScreen instanceof GuiHudConfig) {
                hudList = new ArrayList<String>();
                hudList.add(StatCollector.translateToLocal("info.de.hudDisplayConfigTxt1.txt"));
                hudList.add("");
                hudList.add("");
                hudList.add("");
                hudList.add(StatCollector.translateToLocal("info.de.hudDisplayConfigTxt3.txt"));
                toolTipFadeOut = 1F;
                armorStatsFadeOut = 1F;
            }
        } else
        if (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof IHudDisplayItem) {
            hudList = ((IHudDisplayItem) mc.thePlayer.getHeldItem().getItem())
                    .getDisplayData(mc.thePlayer.getHeldItem());
        }

        MovingObjectPosition mop = mc.thePlayer.rayTrace(5, 0);
        if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                && mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ) instanceof IHudDisplayBlock) {
            hudList = ((IHudDisplayBlock) mc.theWorld.getBlock(mop.blockX, mop.blockY, mop.blockZ))
                    .getDisplayData(mc.theWorld, mop.blockX, mop.blockY, mop.blockZ);
        }

        CustomArmorHandler.ArmorSummery summery = new CustomArmorHandler.ArmorSummery().getSummery(mc.thePlayer);

        if (summery == null) {
            showShieldHud = false;
            return;
        }
        showShieldHud = armorStatsFadeOut > 0F;

        if (maxShieldPoints != summery.maxProtectionPoints || shieldPoints != summery.protectionPoints
                || shieldEntropy != summery.entropy
                || rfTotal != summery.totalEnergyStored)
            armorStatsFadeOut = 5F;

        maxShieldPoints = summery.maxProtectionPoints;
        shieldPoints = summery.protectionPoints;
        shieldPercentCharge = (int) (summery.protectionPoints / summery.maxProtectionPoints * 100D);
        shieldEntropy = summery.entropy;
        rfCharge = (int) ((double) summery.totalEnergyStored / Math.max((double) summery.maxTotalEnergyStorage, 1D)
                * 100D);
        rfTotal = summery.totalEnergyStored;
    }

}
