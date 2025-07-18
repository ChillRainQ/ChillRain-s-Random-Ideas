package com.chillrain.chillrainrandomideas.enums;


import com.chillrain.chillrainrandomideas.interfaces.AdvancedTool;
import com.chillrain.chillrainrandomideas.utils.WeaponUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public enum SwordMode {
    DEFAULT_MODE("adminBlade0", 0),
    PERCENT_MODE("adminBlade1", 1),
    HEIGHT_MODE("adminBlade2", 2);
    private final String textKey;
    private final int modeId;

    SwordMode(String textKey, int id) {
        this.textKey = textKey;
        this.modeId = id;
    }

    public String getTextKey() {
        return textKey;
    }

    public int getModeId() {
        return modeId;
    }
    public static SwordMode getSwordModeById(int modeId){
        for (SwordMode mode : SwordMode.values()){
            if (mode.getModeId() == modeId){
                return mode;
            }
        }
        return DEFAULT_MODE;
    }

    public void hurtMode(ItemStack stack, Entity target, EntityPlayer attacker){
        if (!(stack.getItem() instanceof ItemSword && stack.getItem() instanceof AdvancedTool)){
            return;
        }
        if (!(target instanceof EntityLivingBase)){
            return;
        }
        EntityLivingBase live = (EntityLivingBase) target;
        float damage = 0f;
        live.getHealth();
        float percent = 0.0f;
        switch (this.getModeId()){
            case 1:
                percent = 0.25f;
                break;
            case 2:
                damage = 100f;
                break;
            case 0:
                WeaponUtil.AOEAttack(attacker, target, 2, 5f);
                return;
            default:
                break;
        }
        damage += percent * live.getMaxHealth();
        target.attackEntityFrom(new DamageSource("").setDamageBypassesArmor(), damage);
    }
}
