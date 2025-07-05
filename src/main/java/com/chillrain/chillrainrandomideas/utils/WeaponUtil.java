package com.chillrain.chillrainrandomideas.utils;

import com.brandon3055.draconicevolution.common.ModItems;
import com.brandon3055.draconicevolution.common.entity.EntityDragonProjectile;
import com.brandon3055.draconicevolution.common.items.weapons.IEnergyContainerWeaponItem;
import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
import com.chillrain.chillrainrandomideas.Constant;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;

/**
 * WeaponUtil
 *
 * @author Chill_Rain 2025/07/02
 */
public class WeaponUtil {
    public static void AOEAttack(EntityPlayer player, Entity entity, int range, float damage) {
        World world = player.worldObj;
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                entity.posX - range,
                entity.posY - range,
                entity.posZ - range,
                entity.posX + range,
                entity.posY + range,
                entity.posZ + range).expand(1.0D, 1.0D, 1.0D);
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, box);
        if (range == 0) return;
        for (Entity entity1 : list) {
            if (!(entity1 instanceof EntityLivingBase)){
                continue;
            }
            EntityLivingBase living = (EntityLivingBase) entity1;
            if (living == player || living == entity){
                continue;
            }
            living.attackEntityFrom(Constant.ADMIN_KILL, damage);
        }

    }

}
