package com.chillrain.chillrainrandomideas.interfaces;

import com.chillrain.chillrainrandomideas.NBTConstant;
import com.chillrain.chillrainrandomideas.utils.NBTUtil;
import net.minecraft.item.ItemStack;

public interface AdvancedTool extends AdvancedItem{

    default void switchMode(ItemStack stack){
        int mode = (getModeId(stack) + 1) % 3;
        setModeId(stack, mode);
    }
    default int getModeId(ItemStack stack){
        return NBTUtil.getNBTInt(stack, NBTConstant.WEAPON_MODE, 0);
    }
    default void setModeId(ItemStack stack, int mode){
        NBTUtil.setNBTInt(stack, NBTConstant.WEAPON_MODE, mode);
    }
}
