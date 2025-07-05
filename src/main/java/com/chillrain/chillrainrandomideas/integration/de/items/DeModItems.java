package com.chillrain.chillrainrandomideas.integration.de.items;

import com.chillrain.chillrainrandomideas.integration.de.ItemName;
import com.chillrain.chillrainrandomideas.integration.de.items.armors.ChaosDraconicArmor;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;

/**
 * ModItems
 *
 * @author Chill_Rain 2025/06/26
 */
public class DeModItems {
    public static ItemArmor.ArmorMaterial CHAOTIC_ARMOR = EnumHelper
            .addArmorMaterial("CHAOTIC_ARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);
    public static ItemArmor chaosDraconicHelm;
    public static ItemArmor chaosDraconicChest;
    public static ItemArmor chaosDraconicLeggs;
    public static ItemArmor chaosDraconicBoots;
    public static List<Item> items = Lists.newArrayList();
    public static void init(){
        chaosDraconicHelm = new ChaosDraconicArmor(CHAOTIC_ARMOR, 0, ItemName.ChaosDraconicHelm);
        chaosDraconicChest = new ChaosDraconicArmor(CHAOTIC_ARMOR, 1, ItemName.ChaosDraconicChest);
        chaosDraconicLeggs = new ChaosDraconicArmor(CHAOTIC_ARMOR, 2, ItemName.ChaosDraconicLeggs);
        chaosDraconicBoots = new ChaosDraconicArmor(CHAOTIC_ARMOR, 3, ItemName.ChaosDraconicBoots);
    }
    public static void registerItem(Item item, String name){
        items.add(item);
        GameRegistry.registerItem(item, name);
    }
    public static void postInit(){
        registerItem(chaosDraconicHelm, ItemName.ChaosDraconicHelm);
        registerItem(chaosDraconicChest, ItemName.ChaosDraconicChest);
        registerItem(chaosDraconicLeggs, ItemName.ChaosDraconicLeggs);
        registerItem(chaosDraconicBoots, ItemName.ChaosDraconicBoots);
    }

}
