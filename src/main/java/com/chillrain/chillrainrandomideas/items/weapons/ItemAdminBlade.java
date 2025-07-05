package com.chillrain.chillrainrandomideas.items.weapons;

import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
import com.brandon3055.draconicevolution.common.items.armor.ICustomArmor;
import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.ItemName;
import com.chillrain.chillrainrandomideas.NBTConstant;
import com.chillrain.chillrainrandomideas.enums.SwordMode;
import com.chillrain.chillrainrandomideas.handler.SpecialArmorHandler;
import com.chillrain.chillrainrandomideas.interfaces.AdvancedTool;
import com.chillrain.chillrainrandomideas.items.ModItems;
import com.chillrain.chillrainrandomideas.utils.NBTUtil;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

/**
 * ItemAdminBlade
 *
 * @author Chill_Rain 2025/07/02
 */
public class ItemAdminBlade extends ItemSword implements AdvancedTool {
    private static IIcon[] icons = new IIcon[3];

    public ItemAdminBlade(ToolMaterial material) {
        super(material);
        this.setUnlocalizedName(Constant.NAMESPACE + ItemName.ItemAdminBlade);
        ModItems.registerItem(this, ItemName.ItemAdminBlade);
//        this.setTextureName(Constant.NAMESPACE + ItemName.ItemAdminBlade);
    }


    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack stack = new ItemStack(this);
        NBTUtil.setNBTInt(stack, NBTConstant.WEAPON_MODE, 0);
        list.add(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {
        return icons[this.getModeId(stack)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons[0] = iconRegister.registerIcon(Constant.NAMESPACE + ItemName.ItemAdminBlade + 0);
        icons[1] = iconRegister.registerIcon(Constant.NAMESPACE + ItemName.ItemAdminBlade + 1);
        icons[2] = iconRegister.registerIcon(Constant.NAMESPACE + ItemName.ItemAdminBlade + 2);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return icons[this.getModeId(stack)];
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        World world = player.worldObj;
        if (world.isRemote){
            return false;
        }
        if (entity instanceof EntityPlayer ){
            EntityPlayer player1 = (EntityPlayer) entity;
            if (player1 instanceof EntityPlayerMP){
                EntityPlayerMP playerMP = (EntityPlayerMP) player1;
                WorldSettings.GameType gameType = playerMP.theItemInWorldManager.getGameType();
                if (gameType == WorldSettings.GameType.CREATIVE){
                    return false;
                }
            }

            ItemStack[] armor = player1.inventory.armorInventory;
            for(int i = 0; i < armor.length; i ++){
                if (armor[i] != null && armor[i].getItem() != null && armor[i].getItem() instanceof ICustomArmor){
                    player1.attackEntityFrom(Constant.ADMIN_KILL, player1.getMaxHealth() * 0.35f);
                    break;
                }
            }
            return false;
        }
        return false;
    }
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.none; // 默认是 block（格挡）
    }
    @Override
    public int getMaxDamage() {
        return 0;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote){
            this.switchMode(stack);
        }
        return stack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        World world = attacker.worldObj;
        if (!world.isRemote){
            AdvancedTool advancedTool = (AdvancedTool) stack.getItem();
            if (attacker instanceof EntityPlayer){
                SwordMode.getSwordModeById(advancedTool.getModeId(stack)).hurtMode(stack, target, (EntityPlayer) attacker);
            }
        }
        return true;
    }
}
