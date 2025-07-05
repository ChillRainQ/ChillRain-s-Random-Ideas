package com.chillrain.chillrainrandomideas.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBTUtil
 *
 * @author Chill_Rain 2025/06/27
 */
public class NBTUtil {
    public static NBTTagCompound getCompound(ItemStack stack) {
        if (stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
        return stack.getTagCompound();
    }
    public static boolean verifyExistance(ItemStack stack, String tag) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) return false;
        else return stack.getTagCompound().hasKey(tag);
    }
    public static ItemStack setNBTInt(ItemStack stack, String name, int value){
        NBTTagCompound compound = getCompound(stack);
        compound.setInteger(name, value);
        stack.setTagCompound(compound);
        return stack;

    }
    public static int getNBTInt(ItemStack stack, String name, int defaultExpected){
        return verifyExistance(stack, name) ? stack.getTagCompound().getInteger(name) : defaultExpected;

    }
}
