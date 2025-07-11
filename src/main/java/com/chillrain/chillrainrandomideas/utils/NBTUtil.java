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
    public static String getNBTString(ItemStack stack, String name, String defaultValue){
        return verifyExistance(stack, name) ? stack.getTagCompound().getString(name) : defaultValue;
    }
    public static <T> ItemStack setNBT(ItemStack stack, String name, T value){
        NBTTagCompound compound = getCompound(stack);
        if (value instanceof Integer){
            compound.setInteger(name, (Integer)value);
        }else if (value instanceof String){
            compound.setString(name, (String)value);
        }
        stack.setTagCompound(compound);
        return stack;
    }
}
