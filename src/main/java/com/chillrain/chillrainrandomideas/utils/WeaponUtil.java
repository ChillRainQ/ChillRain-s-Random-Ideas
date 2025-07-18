package com.chillrain.chillrainrandomideas.utils;

import com.chillrain.chillrainrandomideas.Constant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

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

    public static void attackTrueDamage(EntityPlayer player, EntityLivingBase target, float damage){
        DamageSource source = new DamageSource("trueDamage");
        target.attackEntityFrom(source, 0.0001f);
        float health = Math.max(target.getHealth() - damage, 0f);
        boolean isDeath = health == 0f;
        target.setHealth(health);
        if (isDeath){
            target.onDeath(source);
            target.setDead();
        }
    }

}
