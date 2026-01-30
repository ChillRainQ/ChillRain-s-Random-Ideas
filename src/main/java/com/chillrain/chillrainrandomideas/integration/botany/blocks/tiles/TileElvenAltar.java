package com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.common.block.tile.TileRuneAltar;

public class TileElvenAltar extends TileRuneAltar implements ISparkAttachable {
    public TileElvenAltar() {
        super();
    }
    private ISparkEntity attachedSpark;
    public int mana = 0;
    public int recipeKeepTicks = 0;

    @Override
    public boolean addItem(EntityPlayer player, ItemStack stack) {
        // 调用父类逻辑
//        boolean result = super.addItem(player, stack);
//        if (result) {
//            // 更新配方状态
//            updateRecipe();
//        }
        return super.addItem(player, stack);
    }

    @Override
    public boolean hasValidRecipe() {
        return super.hasValidRecipe();
    }

    @Override
    public void trySetLastRecipe(EntityPlayer player) {
        super.trySetLastRecipe(player);
    }

    @Override
    public void onWanded(EntityPlayer player, ItemStack wand) {
        super.onWanded(player, wand); // 调用父类魔杖逻辑
    }

    @Override
    public void updateRecipe() {
        super.updateRecipe(); // 保留父类配方匹配逻辑
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return true;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 16;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return "runeAltar";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }


    @Override
    public boolean canAttachSpark(ItemStack itemStack) {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity iSparkEntity) {
        attachedSpark = iSparkEntity;
    }

    @Override
    public int getAvailableSpaceForMana() {
        return Math.max(0, manaToGet - mana);
    }

    @Override
    public ISparkEntity getAttachedSpark() {
        return attachedSpark;
    }
    ///
    @Override
    public boolean canRecieveManaFromBursts() {
        // 如果魔力还没满且有配方，则允许接收魔力（包括发射器和火花）
        return manaToGet > 0 && mana < manaToGet;
    }
    @Override
    public synchronized void recieveMana(int mana) {
        // 确保存储到父类的 mana 变量中
        this.mana = Math.min(this.mana + mana, manaToGet);
        this.markDirty(); // 记得标记 NBT 改变
    }

    @Override
    public boolean areIncomingTranfersDone() {
        return mana >= manaToGet;
    }
}
