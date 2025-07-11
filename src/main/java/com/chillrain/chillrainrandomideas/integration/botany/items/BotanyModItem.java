package com.chillrain.chillrainrandomideas.integration.botany.items;

import com.chillrain.chillrainrandomideas.integration.botany.ItemName;
import com.chillrain.chillrainrandomideas.integration.botany.items.relic.ItemMasterManaRing;
import com.chillrain.chillrainrandomideas.interfaces.IModItems;

/**
 * ExBotanyModItem
 *
 * @author Chill_Rain 2025/07/05
 */
public class BotanyModItem extends IModItems {
    public static ItemMasterManaRing masterManaRing;

    @Override
    public void postInit() {
        this.registerItem(masterManaRing, ItemName.masterManaRing);
    }

    public void init(){
        masterManaRing = new ItemMasterManaRing();
    }
}
