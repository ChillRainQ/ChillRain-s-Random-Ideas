package com.chillrain.chillrainrandomideas.tabs;

import com.chillrain.chillrainrandomideas.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * RandomIdeasTab
 *
 * @author Chill_Rain 2025/07/02
 */
public class RandomIdeasTab extends CreativeTabs {
    static ItemStack iconStackStaff;

    public RandomIdeasTab(String lable) {
        super(lable);
    }

    public void init() {
        iconStackStaff = new ItemStack(ModItems.adminBlade);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return iconStackStaff.getItem();
    }

    public void postInit() {
        ModItems.items.forEach(item -> item.setCreativeTab(this));
    }
}
