package com.chillrain.chillrainrandomideas.items;

import com.chillrain.chillrainrandomideas.ItemName;
import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.de.items.DeModItems;
import com.chillrain.chillrainrandomideas.integration.de.items.armors.ChaosDraconicArmor;
import com.chillrain.chillrainrandomideas.items.weapons.ItemAdminBlade;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.Mod;
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
    public static Item.ToolMaterial KAMI_CORE = EnumHelper
            .addToolMaterial("KAMI_CORE", 10, 2000, 30.0F,10.0F, 22           );
    public static ItemSword adminBlade;
    public static List<Item> items = Lists.newArrayList();
    public static void init(){
        adminBlade = new ItemAdminBlade(KAMI_CORE);
        if (ModHelper.isDeInstalled){
            DeModItems.init();
        }
    }
    public static void registerItem(Item item, String name){
        items.add(item);
        GameRegistry.registerItem(item, name);
    }
    public static void preInit(){

    }
    public static void postInit(){
        if (ModHelper.isDeInstalled){
            DeModItems.postInit();
            items.addAll(DeModItems.items);
        }
    }

}
