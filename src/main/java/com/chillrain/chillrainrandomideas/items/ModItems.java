package com.chillrain.chillrainrandomideas.items;

import com.brandon3055.draconicevolution.common.items.ItemDE;
import com.chillrain.chillrainrandomideas.ItemName;
import com.chillrain.chillrainrandomideas.items.armors.ChaosDraconicArmor;
import com.chillrain.chillrainrandomideas.items.weapons.ItemAdminBlade;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;

/**
 * ModItems
 *
 * @author Chill_Rain 2025/06/26
 */
public class ModItems {
    public static ItemArmor.ArmorMaterial CHAOTIC_ARMOR = EnumHelper
            .addArmorMaterial("CHAOTIC_ARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);
    public static Item.ToolMaterial KAMI_CORE = EnumHelper
            .addToolMaterial("KAMI_CORE", 10, 2000, 30.0F,10.0F, 22           );
    public static ItemArmor chaosDraconicHelm;
    public static ItemArmor chaosDraconicChest;
    public static ItemArmor chaosDraconicLeggs;
    public static ItemArmor chaosDraconicBoots;
    public static ItemSword adminBlade;
    public static List<Item> items = Lists.newArrayList();
    public static void init(){
        chaosDraconicHelm = new ChaosDraconicArmor(CHAOTIC_ARMOR, 0, ItemName.ChaosDraconicHelm);
        chaosDraconicChest = new ChaosDraconicArmor(CHAOTIC_ARMOR, 1, ItemName.ChaosDraconicChest);
        chaosDraconicLeggs = new ChaosDraconicArmor(CHAOTIC_ARMOR, 2, ItemName.ChaosDraconicLeggs);
        chaosDraconicBoots = new ChaosDraconicArmor(CHAOTIC_ARMOR, 3, ItemName.ChaosDraconicBoots);
        adminBlade = new ItemAdminBlade(KAMI_CORE);
    }
    public static void registerItem(Item item, String name){
        items.add(item);
        GameRegistry.registerItem(item, name);
    }
    public static void register(){

    }

}
