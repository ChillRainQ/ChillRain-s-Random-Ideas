package com.chillrain.chillrainrandomideas.integration.botany.handler;

import baubles.api.BaublesApi;
import com.chillrain.chillrainrandomideas.integration.botany.items.relic.ItemGemOfConquest;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

/**
 * GemOfConquestHandler
 *
 * @author Chill_Rain 2025/07/12
 */
public class GemOfConquestHandler {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void autoCounterattack(LivingAttackEvent event){
        if (!(event.entity instanceof EntityPlayer) || event.isCanceled()){
            return;
        }
        EntityPlayer player = (EntityPlayer) event.entity;
        IInventory baubles = BaublesApi.getBaubles(player);
        if (baubles == null) {
            return;
        }
//        boolean hasGem = false;
        ItemStack gem = null;
        for (int i = 0; i < baubles.getSizeInventory(); i ++){
            ItemStack stack = baubles.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemGemOfConquest){
//                hasGem = true;
                gem = stack;
                break;
            }
        }
        if (!(event.source.getEntity() instanceof EntityLivingBase)){
            return;
        }
        EntityLivingBase attacker = (EntityLivingBase) event.source.getEntity();
        ItemStack stack = player.getHeldItem();
        if (gem == null) return;
        ItemGemOfConquest gemOfConquest = (ItemGemOfConquest) gem.getItem();

        if (gemOfConquest.extractEnergy(gem, 50) && stack != null && stack.getItem() instanceof ItemSword){
            if (!player.worldObj.isRemote){
                player.attackTargetEntityWithCurrentItem(attacker);
                ((WorldServer) player.worldObj).getEntityTracker()
                        .func_151248_b(player, new S0BPacketAnimation(player, 0));
            }else{
                player.swingItem();
            }
        }
    }
}
