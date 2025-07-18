package com.chillrain.chillrainrandomideas.integration.botany.items.relic;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.integration.botany.items.ItemName;
import com.chillrain.chillrainrandomideas.integration.botany.NBTConstant;
import com.chillrain.chillrainrandomideas.utils.NBTUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.common.item.relic.ItemRelic;

import java.util.List;

/**
 * ItemGemOfConquest
 *
 * @author Chill_Rain 2025/07/12
 */
public class ItemGemOfConquest extends ItemRelic implements IBauble, IManaTooltipDisplay {
    public static IIcon icon;
    public static int MAX_ENERGY = 600;
    public ItemGemOfConquest() {
        super(ItemName.gemOfConquest);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack stack = new ItemStack(this);
        setEnergy(stack, 0);
        list.add(stack);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        super.addInformation(stack, player, list, advanced);
        if (org.lwjgl.input.Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            list.add(StatCollector.translateToLocal("tooltips.gemOfConquest.ctrl.show.txt"));
        } else {
            list.add(StatCollector.translateToLocal("tooltips.key.ctrl.show.txt"));
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        NBTUtil.setNBT(stack, NBTConstant.ENERGY, 0);
    }
    public int getEnergy(ItemStack stack){
        return NBTUtil.getNBTInt(stack, NBTConstant.ENERGY, 0);
    }
    public void setEnergy(ItemStack stack, int value){
        NBTUtil.setNBT(stack, NBTConstant.ENERGY, value);
    }
    public void addEnergy(ItemStack stack, int value){
        int currentEnergy = getEnergy(stack);
        if (currentEnergy - MAX_ENERGY >= 0){
            return;
        }
        int energy = Math.min(currentEnergy + value, MAX_ENERGY);
        setEnergy(stack, energy);
    }
    public boolean extractEnergy(ItemStack stack, int value){
        if (getEnergy(stack) < value){
            return false;
        }
        int energy = Math.max(getEnergy(stack) - value, 0);
        setEnergy(stack, energy);
        return true;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icon = iconRegister.registerIcon(Constant.BOTANIA_NAMESPACE + ItemName.gemOfConquest);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {
        return icon;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return icon;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        addEnergy(itemStack, 2);
    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public float getManaFractionForDisplay(ItemStack itemStack) {
        return (float) getEnergy(itemStack) / (float) MAX_ENERGY;
    }
}
