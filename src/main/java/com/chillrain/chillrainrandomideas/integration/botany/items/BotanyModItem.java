package com.chillrain.chillrainrandomideas.integration.botany.items;

import com.chillrain.chillrainrandomideas.integration.botany.items.relic.ItemGemOfConquest;
import com.chillrain.chillrainrandomideas.integration.botany.items.relic.ItemMasterManaRing;
import com.chillrain.chillrainrandomideas.interfaces.IModItems;
import vazkii.botania.common.item.relic.ItemRelic;

/**
 * ExBotanyModItem
 *
 * @author Chill_Rain 2025/07/05
 */
public class BotanyModItem extends IModItems {
    public static ItemRelic masterManaRing;
    public static ItemRelic gemOfConquest;

    @Override
    public void postInit() {
        this.registerItem(masterManaRing, ItemName.masterManaRing);
        this.registerItem(gemOfConquest, ItemName.gemOfConquest);
    }

    public void init(){
        masterManaRing = new ItemMasterManaRing();
        gemOfConquest = new ItemGemOfConquest();
    }
}
