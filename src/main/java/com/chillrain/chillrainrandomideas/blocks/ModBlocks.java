package com.chillrain.chillrainrandomideas.blocks;

import com.chillrain.chillrainrandomideas.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.botany.blocks.BotanyModBlocks;
import com.chillrain.chillrainrandomideas.integration.botany.items.BotanyModItem;
import com.chillrain.chillrainrandomideas.integration.de.items.DeModItems;
import com.chillrain.chillrainrandomideas.interfaces.IModBlocks;
import com.chillrain.chillrainrandomideas.interfaces.IModItems;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.List;

/**
 * ModBlocks
 *
 * @author Chill_Rain 2025/10/02
 */
public class ModBlocks{
    public static List<Block> blocks = Lists.newArrayList();
    public static IModBlocks botanyModBlocks;
    static {
        if (ModHelper.isBotanyInstalled){
            botanyModBlocks = new BotanyModBlocks();
        }
    }
    public static void init() {
        if (ModHelper.isBotanyInstalled){
            botanyModBlocks.init();
        }
    }

    public static void postInit() {
        if (ModHelper.isBotanyInstalled){
            botanyModBlocks.postInit();
            blocks.addAll(botanyModBlocks.blocks);
        }
    }
}
