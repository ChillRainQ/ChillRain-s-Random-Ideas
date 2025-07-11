package com.chillrain.chillrainrandomideas.integration.botany.items;

import java.util.List;

import com.chillrain.chillrainrandomideas.Constant;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IRelic;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ItemMod;
import vazkii.botania.common.item.ModItems;

/**
 * ItemRelic 是 Botania 的神器类物品基类，实现了 IRelic 接口
 */
public class ItemRelic extends Item implements IRelic {
    private static final String TAG_SOULBIND = "soulbind"; // NBT中绑定玩家的键名
    private Achievement achievement; // 绑定该物品时给玩家的成就

    public ItemRelic(String name) {
        this.setUnlocalizedName(Constant.NAMESPACE + name);
        this.setMaxStackSize(1);
    }

    /**
     * 物品每tick更新调用，p_77663_3_是持有实体，如果是玩家则调用updateRelic处理
     */
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isCurrentItem) {
        if (entity instanceof EntityPlayer) {
            updateRelic(stack, (EntityPlayer) entity);
        }
    }

    /**
     * 添加物品悬浮提示内容，传递给addBindInfo
     */
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        addBindInfo(tooltip, stack, player);
    }

    /**
     * 添加绑定信息和额外的悬浮提示文本
     */
    public static void addBindInfo(List<String> tooltip, ItemStack stack, EntityPlayer player) {
        if (GuiScreen.isShiftKeyDown()) {
            String bind = getSoulbindUsernameS(stack);
            if (bind.isEmpty()) {
                // 没绑定玩家时显示未绑定提示
                addStringToTooltip(StatCollector.translateToLocal("botaniamisc.relicUnbound"), tooltip);
            } else {
                // 显示绑定玩家名字
                addStringToTooltip(String.format(StatCollector.translateToLocal("botaniamisc.relicSoulbound"), bind), tooltip);
                // 如果当前玩家不是绑定玩家，显示警告
                if (!isRightPlayer(player, stack)) {
                    addStringToTooltip(String.format(StatCollector.translateToLocal("botaniamisc.notYourSagittarius"), bind), tooltip);
                }
            }

            // 下面是 Botania 里两个特殊物品的额外提示
            if (stack.getItem() == ModItems.aesirRing) {
                addStringToTooltip(StatCollector.translateToLocal("botaniamisc.dropIkea"), tooltip);
            }

            if (stack.getItem() == ModItems.dice) {
                addStringToTooltip("", tooltip);
                String name = stack.getUnlocalizedName() + ".poem";

                for (int i = 0; i < 4; ++i) {
                    addStringToTooltip(EnumChatFormatting.ITALIC + StatCollector.translateToLocal(name + i), tooltip);
                }
            }
        } else {
            addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), tooltip);
        }
    }

    /**
     * 添加字符串到提示列表，支持将 & 转换为 § 颜色符号
     */
    static void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replace("&", "§"));
    }

    /**
     * 获取物品绑定的玩家名（Soulbind）
     */
    public static String getSoulbindUsernameS(ItemStack stack) {
        return ItemNBTHelper.getString(stack, TAG_SOULBIND, "");
    }

    /**
     * 更新神器状态，绑定玩家或对非绑定玩家造成伤害
     */
    public static void updateRelic(ItemStack stack, EntityPlayer player) {
        if (stack != null && stack.getItem() instanceof IRelic) {
            String soulbind = getSoulbindUsernameS(stack);
            if (soulbind.isEmpty()) {
                // 未绑定，给玩家绑定成就并绑定玩家
                player.addStat(((IRelic) stack.getItem()).getBindAchievement(), 1);
                bindToPlayer(player, stack);
                soulbind = getSoulbindUsernameS(stack);
            }

            // 如果不是绑定玩家，并且每10 ticks伤害一次
            if (!isRightPlayer(player, stack) && player.ticksExisted % 10 == 0 &&
                    (!(stack.getItem() instanceof ItemRelic) || ((ItemRelic) stack.getItem()).shouldDamageWrongPlayer())) {
                player.attackEntityFrom(damageSource(), 2.0F);
            }
        }
    }

    /**
     * 绑定物品到玩家名
     */
    public static void bindToPlayer(EntityPlayer player, ItemStack stack) {
        bindToUsernameS(player.getCommandSenderName(), stack);
    }

    public static void bindToUsernameS(String username, ItemStack stack) {
        ItemNBTHelper.setString(stack, TAG_SOULBIND, username);
    }

    /**
     * 判断当前玩家是否是绑定玩家
     */
    public static boolean isRightPlayer(EntityPlayer player, ItemStack stack) {
        return isRightPlayer(player.getCommandSenderName(), stack);
    }

    public static boolean isRightPlayer(String playerName, ItemStack stack) {
        return getSoulbindUsernameS(stack).equals(playerName);
    }

    /**
     * 获取特殊伤害源，用于伤害非绑定玩家
     */
    public static DamageSource damageSource() {
        return new DamageSource("botania-relic");
    }

    /**
     * 是否对非绑定玩家造成伤害，默认是true
     */
    public boolean shouldDamageWrongPlayer() {
        return true;
    }

    /**
     * 物品寿命，神器不消失
     */
    @Override
    public int getEntityLifespan(ItemStack itemStack, World world) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void bindToUsername(String s, ItemStack itemStack) {
        bindToUsernameS(s, itemStack);
    }

    @Override
    public String getSoulbindUsername(ItemStack itemStack) {
        return getSoulbindUsernameS(itemStack);
    }

    /**
     * 绑定绑定玩家的成就
     */
    @Override
    public void setBindAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public Achievement getBindAchievement() {
        return this.achievement;
    }

    /**
     * 物品稀有度，神器级别
     */
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return BotaniaAPI.rarityRelic;
    }
}
