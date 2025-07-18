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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.IManaTooltipDisplay;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.item.relic.ItemRelic;

/**
 * ItemMasterManaRing
 *
 * @author Chill_Rain 2025/07/05
 */
public class ItemMasterManaRing extends ItemRelic implements IBauble, IManaItem, IManaTooltipDisplay{
    private static final int MAX_MANA = Integer.MAX_VALUE;
//    private Achievement achievement;
    private static IIcon icon;

    public ItemMasterManaRing() {
        super(ItemName.masterManaRing);
    }
    public int getDamage(ItemStack stack) {
        float mana = (float)this.getMana(stack);
        return 1000 - (int)(mana / (float)this.getMaxMana(stack) * 1000.0F);
    }
    public int getDisplayDamage(ItemStack stack) {
        return this.getDamage(stack);
    }
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
       icon = iconRegister.registerIcon(Constant.BOTANIA_NAMESPACE + ItemName.masterManaRing);
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
    public int getMana(ItemStack stack) {
        return NBTUtil.getNBTInt(stack, NBTConstant.MANA, 0);
    }

    public int getMaxMana(ItemStack stack) {
        return MAX_MANA;
    }

    @Override
    public void addMana(ItemStack stack, int value) {
        NBTUtil.setNBT(stack, NBTConstant.MANA, NBTUtil.getNBTInt(stack, NBTConstant.MANA, 0) + value);
        stack.setItemDamage(this.getDamage(stack));
    }

    @Override
    public boolean canReceiveManaFromPool(ItemStack itemStack, TileEntity tileEntity) {
        return true;
    }

    @Override
    public boolean canReceiveManaFromItem(ItemStack itemStack, ItemStack itemStack1) {
        return true;
    }

    @Override
    public boolean canExportManaToPool(ItemStack itemStack, TileEntity tileEntity) {
        return true;
    }

    @Override
    public boolean canExportManaToItem(ItemStack itemStack, ItemStack itemStack1) {
        return true;
    }

    @Override
    public boolean isNoExport(ItemStack itemStack) {
        return false;
    }


    @Override
    public float getManaFractionForDisplay(ItemStack stack) {
        return (float)this.getMana(stack) / (float)this.getMaxMana(stack);
    }
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true; // 总是显示，或者 mana < maxMana 时显示
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    public static void setLastPlayerHashcode(ItemStack stack, int hashcode){
        NBTUtil.setNBT(stack, NBTConstant.PLAYER_HASHCODE, hashcode);
    }
    public int getLastPlayerHashcode(ItemStack stack){
        return NBTUtil.getNBTInt(stack, NBTConstant.PLAYER_HASHCODE, 0);
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        if (getLastPlayerHashcode(itemStack) != entityLivingBase.hashCode()) {
            setLastPlayerHashcode(itemStack, entityLivingBase.hashCode());
        }
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        if (player != null) {
            // 只在服务器端播放音效
            if (!player.worldObj.isRemote) {
                player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ,
                        "botania:equipBauble", 0.1F, 1.3F);
            }
            // 给玩家触发成就
            if (player instanceof EntityPlayer) {
                ((EntityPlayer) player).addStat(ModAchievements.baubleWear, 1);
            }
            // 自定义的装备或加载时处理
            this.onEquippedOrLoadedIntoWorld(stack, player);

            // 记录玩家的哈希码，防止重复初始化
            setLastPlayerHashcode(stack, player.hashCode());
        }
    }

    private void onEquippedOrLoadedIntoWorld(ItemStack stack, EntityLivingBase player) {
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
}
