package com.chillrain.chillrainrandomideas.interfaces;

import com.chillrain.chillrainrandomideas.Constant;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.List;

/**
 * IModItems
 *
 * @author Chill_Rain 2025/07/05
 */
public abstract class IModItems {
    public List<Item> items = Lists.newArrayList();

    public void registerItem(Item item, String name){
        items.add(item);
        GameRegistry.registerItem(item, name);
    }

    abstract public void postInit();

    abstract public void init();
}
