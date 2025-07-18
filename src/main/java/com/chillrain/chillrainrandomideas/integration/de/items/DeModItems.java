package com.chillrain.chillrainrandomideas.integration.de.items;

import com.chillrain.chillrainrandomideas.integration.de.items.armors.ChaosDraconicArmor;
import com.chillrain.chillrainrandomideas.interfaces.IModItems;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

/**
 * ModItems
 *
 * @author Chill_Rain 2025/06/26
 */
public class DeModItems extends IModItems {
    public static ItemArmor.ArmorMaterial CHAOTIC_ARMOR = EnumHelper
            .addArmorMaterial("CHAOTIC_ARMOR", -1, new int[] { 3, 8, 6, 3 }, 30);
    public static ItemArmor chaosDraconicHelm;
    public static ItemArmor chaosDraconicChest;
    public static ItemArmor chaosDraconicLeggs;
    public static ItemArmor chaosDraconicBoots;
//    public static List<Item> items = Lists.newArrayList();
    public void init(){
//    public static void init(){
        chaosDraconicHelm = new ChaosDraconicArmor(CHAOTIC_ARMOR, 0, ItemName.ChaosDraconicHelm);
        chaosDraconicChest = new ChaosDraconicArmor(CHAOTIC_ARMOR, 1, ItemName.ChaosDraconicChest);
        chaosDraconicLeggs = new ChaosDraconicArmor(CHAOTIC_ARMOR, 2, ItemName.ChaosDraconicLeggs);
        chaosDraconicBoots = new ChaosDraconicArmor(CHAOTIC_ARMOR, 3, ItemName.ChaosDraconicBoots);
    }
//    public static void registerItem(Item item, String name){
//        items.add(item);
//        GameRegistry.registerItem(item, name);
//    }
    public void postInit(){
//    public static void postInit(){
        this.registerItem(chaosDraconicHelm, ItemName.ChaosDraconicHelm);
        this.registerItem(chaosDraconicChest, ItemName.ChaosDraconicChest);
        this.registerItem(chaosDraconicLeggs, ItemName.ChaosDraconicLeggs);
        this.registerItem(chaosDraconicBoots, ItemName.ChaosDraconicBoots);
    }

}
