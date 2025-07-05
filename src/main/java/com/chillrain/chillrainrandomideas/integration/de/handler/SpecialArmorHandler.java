package com.chillrain.chillrainrandomideas.integration.de.handler;

import cofh.api.energy.IEnergyContainerItem;
import com.brandon3055.brandonscore.common.utills.ItemNBTHelper;
import com.brandon3055.draconicevolution.DraconicEvolution;
import com.brandon3055.draconicevolution.common.handler.BalanceConfigHandler;
import com.brandon3055.draconicevolution.common.items.armor.CustomArmorHandler;
import com.brandon3055.draconicevolution.common.items.armor.DraconicArmor;
import com.brandon3055.draconicevolution.common.network.ShieldHitPacket;
import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
import com.brandon3055.draconicevolution.integration.ModHelper;
import com.chillrain.chillrainrandomideas.integration.de.Config;
import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.integration.de.interfaces.ISpecialShieldArmor;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.Arrays;

/**
 * SpecialArmorHandler
 *
 * @author Chill_Rain 2025/06/26
 */
public class SpecialArmorHandler {
    public static final DamageSource ADMIN_KILL = (new DamageSource("administrative.kill")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
    /**
     * 玩家被攻击时事件响应
     * @param event
     */
    @Optional.Method(modid = Constant.DE)
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerAttacked(LivingAttackEvent event){
        if (!(event.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        SpecialArmorSummary summery = new SpecialArmorSummary().getSummery(player);
        if (summery != null && summery.allSpecialArmor){
            allChaos(event, summery);
        }
        else if (summery != null && summery.hasSpecialArmor){
            hasChaos(event, summery);
        }

    }
    @Optional.Method(modid = Constant.DE)
    private static void hasChaos(LivingAttackEvent event, SpecialArmorSummary summery){
        if (event.isCanceled()) {
            return;
        }
        EntityPlayer player = (EntityPlayer) event.entityLiving;
        float hitAmount = ModHelper.applyModDamageAdjustments(summery, event);

        if (applySpecialArmorDamageBlocking(event, summery)) {
            return;
        }
        if (summery == null || summery.protectionPoints <= 0 || ADMIN_KILL.damageType.equals(event.source.damageType)) {
            return;
        }
        event.setCanceled(true);
        if (hitAmount == Float.MAX_VALUE && !event.source.damageType.equals(ADMIN_KILL.damageType)) {
            player.attackEntityFrom(ADMIN_KILL, Float.MAX_VALUE);
            return;
        }
        if ((float) player.hurtResistantTime > (float) player.maxHurtResistantTime / 2.0F) return;

        float newEntropy = Math.min(summery.entropy + 1 + (hitAmount / 20), 100F);

        // Divide the damage between the armor peaces based on how many of the protection points each peace has
        float totalAbsorbed = 0;
        int remainingPoints = 0;
        for (int i = 0; i < summery.allocation.length; i++) {
            if (summery.allocation[i] == 0) continue;
            ItemStack armorPeace = summery.armorStacks[i];

            float dmgShear = summery.allocation[i] / summery.protectionPoints;
            float dmg = dmgShear * hitAmount;

            float absorbed = Math.min(dmg, summery.allocation[i]);
            totalAbsorbed += absorbed;
            summery.allocation[i] -= absorbed;
            remainingPoints += summery.allocation[i];
            ItemNBTHelper.setFloat(armorPeace, "ProtectionPoints", summery.allocation[i]);
            ItemNBTHelper.setFloat(armorPeace, "ShieldEntropy", newEntropy);
        }

        if (summery.protectionPoints > 0) {
            DraconicEvolution.network.sendToAllAround(
                    new ShieldHitPacket(player, summery.protectionPoints / summery.maxProtectionPoints),
                    new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
            player.worldObj.playSoundEffect(
                    player.posX + 0.5D,
                    player.posY + 0.5D,
                    player.posZ + 0.5D,
                    "draconicevolution:shieldStrike",
                    0.9F,
                    player.worldObj.rand.nextFloat() * 0.1F + 1.055F);
        }

        if (remainingPoints > 0) {
            player.hurtResistantTime = 20;
        } else if (hitAmount - totalAbsorbed > 0) {
            player.attackEntityFrom(event.source, hitAmount - totalAbsorbed);
        }
    }
    @Optional.Method(modid = Constant.DE)
    private static void allChaos(LivingAttackEvent event, SpecialArmorSummary summery){
        //todo 混沌套套装效果被攻击时逻辑 1.次数盾（避免高额伤害）
        if (event.isCanceled() || ADMIN_KILL.damageType.equals(event.source.damageType) || summery == null){
            return;
        }
        float amount = ModHelper.applyModDamageAdjustments(summery, event);
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        DamageSource source = event.source;
        if (applySpecialArmorDamageBlocking(event, summery)){
            return;
        }
        if (summery.protectionPoints <= 0) {
            return;
        }
        event.setCanceled(true);
        // 极大值攻击转为管理员攻击
        if (amount == Float.MAX_VALUE && !ADMIN_KILL.damageType.equals(source.damageType)){
            player.attackEntityFrom(ADMIN_KILL, 0.001f);
            player.setHealth(Math.max(player.getHealth() - 1.0f, 0.0f));
            if (player.getHealth() <= 0.0f){
                player.attackEntityFrom(ADMIN_KILL, Float.MAX_VALUE);
            }
        } else if (!((float)player.hurtResistantTime > (float)player.maxHurtResistantTime / 2.0F)) {
            float newEntropy = Math.min(summery.entropy + 1.0F + amount / 20.0F, 100.0F);
            int remainingPoints = 0;
            // 次数盾逻辑 盾容不足时
            float sheild = 0;
//            int time = 1;
            float toConsume = Config.timeShieldValue;
            for(int i = 0; i < summery.armorStacks.length; ++i){
//                float toConsume = Config.timeShieldValue * time;
                if (summery.allocation[i] != 0){
                    ItemStack armorPeace = summery.armorStacks[i];
                    // 本件装备阻挡成功
                    if (summery.allocation[i] >= toConsume) {
                        summery.allocation[i] -= toConsume;
                        sheild += toConsume;
                        toConsume = Config.timeShieldValue;
                        //盾容不足 扣除盾容，并且尝试下一件装备扣除
                    } else if (summery.allocation[i] > 0) {
                        toConsume += (Config.timeShieldValue - summery.allocation[i]);
                        sheild += summery.allocation[i];
                        summery.allocation[i] = 0;
                    }
//                    if (summery.allocation[i] >= 5.0f * time){
//                        summery.allocation[i] -= 5.0f * time;
//                        time = 1;
//                        sheild += 5.0f * time;
//                    }else {
//                        time ++;
//                    }
                    remainingPoints = (int)(remainingPoints + summery.allocation[i]);
                    ItemNBTHelper.setFloat(armorPeace, "ProtectionPoints", summery.allocation[i]);
                    ItemNBTHelper.setFloat(armorPeace, "ShieldEntropy", newEntropy);
                }else {
                    toConsume += Config.timeShieldValue;
                }
            }
            // 护盾效果
            if (summery.protectionPoints > 0.0F) {
                DraconicEvolution.network.sendToAllAround(
                        new ShieldHitPacket(player, summery.protectionPoints / summery.maxProtectionPoints),
                        new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64.0)
                );

                player.worldObj.playSoundEffect(
                        player.posX + 0.5, player.posY + 0.5, player.posZ + 0.5,
                        Constant.NAMESPACE + "shieldStrike", 0.9F, player.worldObj.rand.nextFloat() * 0.1F + 1.055F
                );
            }
            // 剩余的伤害
            if (remainingPoints > 0) {
                player.hurtResistantTime = 20;
                // 如果有剩余伤害（未被吸收），则正常造成伤害
            }
            // 阻挡不完全 造成百分比的伤害
            if (sheild < Config.timeShieldValue * 4){
                player.attackEntityFrom(event.source,
                        ((Config.timeShieldValue * 4) - sheild) / (Config.timeShieldValue * 4) * amount);
            }
        }
    }

    /**
     * 阻挡特殊类型伤害
     * @param event
     * @param summary
     * @return
     */

    @Optional.Method(modid = Constant.DE)
    private static boolean applySpecialArmorDamageBlocking(LivingAttackEvent event, SpecialArmorSummary summary){
        // 无Special护甲
        if (summary == null){
            return false;
        }
        // 火焰免疫
        if (event.source.isFireDamage()){
            event.setCanceled(true);
            return true;
        }
        // 摔落伤害免疫
        else if (event.source.damageType.equals("fall") && summary.jumpModifier > 0.0F) {
            // 如果跌落伤害小于 jumpModifier*5，则取消伤害
            if (event.ammount < summary.jumpModifier * 5.0F) {
                event.setCanceled(true);
            }
            return true;  // 跌落伤害类型已被处理过
        }
        // 窒息免疫
        else if ((event.source.damageType.equals("inWall") || event.source.damageType.equals("drown"))
                && summary.armorStacks[3] != null) {
            // 若伤害值不超过2，阻挡伤害
            if (event.ammount <= 2.0F) {
                event.setCanceled(true);
            }
            return true;  // 该伤害类型被处理
        }
        return false;
    }

    /**
     *  玩家死亡时事件响应
     * @param event
     */
    @Optional.Method(modid = Constant.DE)
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onPlayerDeath(LivingDeathEvent event){
//        CustomArmorHandler.onPlayerDeath(event);
        if (!(event.entityLiving instanceof EntityPlayer)) return;

        if (event.isCanceled() || ADMIN_KILL.damageType.equals(event.source.damageType)){
            return;
        }
        EntityPlayer player = (EntityPlayer)event.entityLiving;
        SpecialArmorSummary summery = new SpecialArmorSummary().getSummery(player);
        if (summery != null && summery.allSpecialArmor){
            // todo 范围击退与伤害生物
            int[] charges = summery.getCharges();
            int totalCharge = Arrays.stream(charges).sum();
            //不触发锁血机制
            if (totalCharge < BalanceConfigHandler.draconicArmorBaseStorage){
                return;
            }
            // 扣除电量
            for (int i = 0; i < summery.armorStacks.length; ++i) {
                if (summery.armorStacks[i] != null) {
                    ((IEnergyContainerItem)summery.armorStacks[i].getItem()).extractEnergy(
                            summery.armorStacks[i],
                            (int)((double)charges[i] / (double)totalCharge * (double)BalanceConfigHandler.draconicArmorBaseStorage),
                            false);
                }
            }
            player.addChatComponentMessage(
                    (new ChatComponentTranslation("msg.de.shieldDepleted.txt"))
                            .setChatStyle((new ChatStyle()).setColor(EnumChatFormatting.DARK_RED))
            );
            // 10s抗性10
            PotionEffect resistance = new PotionEffect(Potion.resistance.id, 200, 10);
            player.addPotionEffect(resistance);
            // 取消死亡事件
            event.setCanceled(true);
            player.setHealth(player.getMaxHealth());
        }
        else if (summery != null && summery.hasSpecialArmor) {
            if (ADMIN_KILL.damageType.equals(event.source.damageType)) return;

            if (summery.protectionPoints > 500) {
                event.setCanceled(true);
                event.entityLiving.setHealth(10);
                return;
            }

            int[] charge = new int[summery.armorStacks.length];
            int totalCharge = 0;
            for (int i = 0; i < summery.armorStacks.length; i++) {
                if (summery.armorStacks[i] != null) {
                    charge[i] = ((IEnergyContainerItem) summery.armorStacks[i].getItem())
                            .getEnergyStored(summery.armorStacks[i]);
                    totalCharge += charge[i];
                }
            }

            if (totalCharge < BalanceConfigHandler.draconicArmorBaseStorage) return;

            for (int i = 0; i < summery.armorStacks.length; i++) {
                if (summery.armorStacks[i] != null) {
                    ((IEnergyContainerItem) summery.armorStacks[i].getItem()).extractEnergy(
                            summery.armorStacks[i],
                            (int) ((charge[i] / (double) totalCharge) * BalanceConfigHandler.draconicArmorBaseStorage),
                            false);
                }
            }
            player.addChatComponentMessage(
                    new ChatComponentTranslation("msg.de.shieldDepleted.txt")
                            .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)));
            event.setCanceled(true);
            player.setHealth(1);

        }
    }

    public static class SpecialArmorSummary extends CustomArmorHandler.ArmorSummery{
        public boolean allSpecialArmor = false;
        public boolean hasSpecialArmor = false;
        @Override
        public SpecialArmorSummary getSummery(EntityPlayer player) {
            ItemStack[] armorSlots = player.inventory.armorInventory;
            float totalEntropy = 0.0F;
            int totalRecoveryPoints = 0;
            this.allocation = new float[armorSlots.length];
            this.armorStacks = new ItemStack[armorSlots.length];
            this.pointsDown = new float[armorSlots.length];
            this.energyAllocation = new int[armorSlots.length];
            int count = 0;
            for(int i = 0; i < armorSlots.length; ++i) {
                ItemStack stack = armorSlots[i];
                if (stack != null && stack.getItem() instanceof ISpecialShieldArmor) {
                    count ++;
                    hasSpecialArmor = true;
                    ISpecialShieldArmor armor = (ISpecialShieldArmor)stack.getItem();
                    ++this.peaces;
                    this.allocation[i] = ItemNBTHelper.getFloat(stack, "ProtectionPoints", 0.0F);
                    this.protectionPoints += this.allocation[i];
                    totalEntropy += ItemNBTHelper.getFloat(stack, "ShieldEntropy", 0.0F);
                    this.armorStacks[i] = stack;
                    totalRecoveryPoints += IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.getUpgradePoints(stack);
                    float maxPoints = armor.getProtectionPoints(stack);
                    this.pointsDown[i] = maxPoints - this.allocation[i];
                    this.maxProtectionPoints += maxPoints;
                    this.energyAllocation[i] = armor.getEnergyStored(stack);
                    this.totalEnergyStored += (long)this.energyAllocation[i];
                    this.maxTotalEnergyStorage += (long)armor.getMaxEnergyStored(stack);
                    if (stack.getItem() instanceof DraconicArmor) {
                        this.hasDraconic = true;
                    }

                    this.fireResistance += armor.getFireResistance(stack);
                    switch (i) {
                        case 0:
                            this.hasHillStep = armor.hasHillStep(stack, player);
                            this.jumpModifier = armor.getJumpModifier(stack, player);
                            break;
                        case 1:
                            this.speedModifier = armor.getSpeedModifier(stack, player);
                            break;
                        case 2:
                            this.flight = armor.hasFlight(stack);
                            if (this.flight[0]) {
                                this.flightVModifier = armor.getFlightVModifier(stack, player);
                                this.flightSpeedModifier = armor.getFlightSpeedModifier(stack, player);
                            }
                    }
                }
                if (count == 4){
                    this.allSpecialArmor = true;
                }
            }

            if (this.peaces == 0) {
                return null;
            } else {
                this.entropy = totalEntropy / (float)this.peaces;
                this.meanRecoveryPoints = totalRecoveryPoints / this.peaces;
                return this;
            }
//            CustomArmorHandler.ArmorSummery summery = super.getSummery(player);
//            int count = 0;
//            for(int i = 0; i < summery.armorStacks.length; i ++){
//                ItemStack armorStack = summery.armorStacks[i];
//                if (armorStack.getItem() instanceof ISpecialShieldArmor){
//                    count ++;
//                    hasSpecialArmor = true;
//                }
//            }
//            if (count == 4){
//                this.allSpecialArmor = true;
//            }
//            return this;
        }
        public int[] getCharges(){
            if (this.armorStacks.length > 0){
                int[] charges = new int[this.armorStacks.length];
                for(int i = 0;i < this.armorStacks.length; i ++){
                    if ((this.armorStacks[i] == null))continue;
                    charges[i] = ((IEnergyContainerItem)this.armorStacks[i].getItem()).getEnergyStored(this.armorStacks[i]);
                }
                return charges;
            }else {
                return null;
            }
        }
    }
}
