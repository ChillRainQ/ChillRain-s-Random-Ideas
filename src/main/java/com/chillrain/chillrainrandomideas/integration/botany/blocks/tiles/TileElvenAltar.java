package com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.mana.IManaPool;
import vazkii.botania.api.mana.IManaReceiver;
import vazkii.botania.api.mana.spark.ISparkAttachable;
import vazkii.botania.api.mana.spark.ISparkEntity;
import vazkii.botania.api.mana.spark.SparkHelper;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.TileAltar;
import vazkii.botania.common.block.tile.TileSimpleInventory;

import vazkii.botania.common.core.helper.Vector3;
import vazkii.botania.common.item.ModItems;

import org.lwjgl.opengl.GL11;

public class TileElvenAltar extends TileSimpleInventory implements IManaReceiver, ISparkAttachable
{
    private static final int SET_KEEP_TICKS_EVENT = 0;
    private static final int SET_COOLDOWN_EVENT = 1;
    private static final int CRAFT_EFFECT_EVENT = 2;

    public RecipeRuneAltar currentRecipe;

    public int manaToGet = 0;
    public int mana = 0;
    public int cooldown = 0;
    public int signal = 0;

    public List<ItemStack> lastRecipe = null;
    public int recipeKeepTicks = 0;

    @Override
    public void updateEntity()
    {
        recieveMana(0);

        if (!worldObj.isRemote)
        {
            if (manaToGet == 0)
            {
                List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class,
                        AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1));
                for (EntityItem item : items)
                {
                    if (!item.isDead && item.getEntityItem() != null && item.getEntityItem().getItem() != Item.getItemFromBlock(ModBlocks.livingrock))
                    {
                        addItem(null, item.getEntityItem());
                    }
                }
            }

            int newSignal = 0;
            if (manaToGet > 0)
            {
                newSignal++;
                if (mana >= manaToGet)
                    newSignal++;
            }

            if (newSignal != signal)
            {
                signal = newSignal;
                worldObj.func_147453_f(xCoord, yCoord, zCoord, getBlockType());
            }

            updateRecipe();
        }
        else
        {
            if (manaToGet > 0 && mana >= manaToGet && worldObj.rand.nextInt(20) == 0)
            {
                Vector3 vec = Vector3.fromTileEntityCenter(this);
                Vector3 endVec = vec.add(0, 2.5, 0);
                Botania.proxy.lightningFX(worldObj, vec, endVec, 2F, 0x00948B, 0x00E4D7);
            }
        }

        if (cooldown > 0)
            cooldown--;

        if (recipeKeepTicks > 0)
            recipeKeepTicks--;
        else
            lastRecipe = null;

        if (getAvailableSpaceForMana() > 0)
        {
            ISparkEntity spark = getAttachedSpark();
            if (spark != null)
            {
                for (ISparkEntity other : SparkHelper.getSparksAround(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5))
                {
                    if (spark == other)
                        continue;
                    if (other.getAttachedTile() instanceof IManaPool)
                        other.registerTransfer(spark);
                }
            }
        }
    }

    public boolean addItem(EntityPlayer player, ItemStack stack)
    {
        if (cooldown > 0 || stack == null || stack.getItem() == ModItems.twigWand || stack.getItem() == ModItems.lexicon)
            return false;

        if (stack.getItem() == Item.getItemFromBlock(ModBlocks.livingrock) && stack.getItemDamage() == 0)
        {
            if (!worldObj.isRemote)
            {
                ItemStack copy = stack.copy();
                copy.stackSize = 1;

                if (player == null || !player.capabilities.isCreativeMode)
                    stack.stackSize--;

                EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1, zCoord + 0.5, copy);
                item.delayBeforeCanPickup = 40;
                item.motionX = item.motionY = item.motionZ = 0;
                worldObj.spawnEntityInWorld(item);
            }
            return true;
        }

        if (manaToGet != 0)
            return false;

        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack slot = getStackInSlot(i);
            if (slot == null)
            {
                ItemStack toAdd = stack.copy();
                toAdd.stackSize = 1;
                setInventorySlotContents(i, toAdd);
                if (player == null || !player.capabilities.isCreativeMode)
                    stack.stackSize--;
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj, xCoord, yCoord, zCoord);
                return true;
            }
        }
        return false;
    }

    public void updateRecipe()
    {
        int prev = manaToGet;

        if (currentRecipe != null)
            manaToGet = currentRecipe.getManaUsage();
        else
        {
            manaToGet = 0;
            for (RecipeRuneAltar r : BotaniaAPI.runeAltarRecipes)
            {
                if (r.matches(this))
                {
                    currentRecipe = r;
                    manaToGet = r.getManaUsage();
                    break;
                }
            }
        }

        if (prev != manaToGet)
        {
            worldObj.playSoundEffect(xCoord, yCoord, zCoord, "botania:runeAltarStart", 1.0F, 1.0F);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj, xCoord, yCoord, zCoord);
        }
    }

    public void onWanded(EntityPlayer player, ItemStack wand)
    {
        if (worldObj.isRemote)
            return;

        if (currentRecipe == null)
        {
            for (RecipeRuneAltar recipe : BotaniaAPI.runeAltarRecipes)
                if (recipe.matches(this))
                {
                    currentRecipe = recipe;
                    break;
                }
        }

        if (currentRecipe != null && mana >= manaToGet)
        {
            List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class,
                    AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1));
            EntityItem rock = null;
            for (EntityItem item : items)
            {
                if (item.getEntityItem().getItem() == Item.getItemFromBlock(ModBlocks.livingrock))
                {
                    rock = item;
                    break;
                }
            }

            if (rock != null)
            {
                recieveMana(-manaToGet);
                EntityItem out = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5,
                        currentRecipe.getOutput().copy());
                worldObj.spawnEntityInWorld(out);
                currentRecipe = null;
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), SET_COOLDOWN_EVENT, 60);
                worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), CRAFT_EFFECT_EVENT, 0);
                saveLastRecipe();

                for (int i = 0; i < getSizeInventory(); i++)
                {
                    ItemStack stack = getStackInSlot(i);
                    if (stack != null)
                    {
                        if (stack.getItem() == ModItems.rune && (player == null || !player.capabilities.isCreativeMode))
                        {
                            EntityItem rune = new EntityItem(worldObj, xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, stack.copy());
                            worldObj.spawnEntityInWorld(rune);
                        }
                        setInventorySlotContents(i, null);
                    }
                }

                rock.getEntityItem().stackSize--;
                if (rock.getEntityItem().stackSize <= 0)
                    rock.setDead();
            }
        }
    }

    public void saveLastRecipe()
    {
        lastRecipe = new ArrayList<ItemStack>();
        for (int i = 0; i < getSizeInventory(); i++)
        {
            ItemStack stack = getStackInSlot(i);
            if (stack == null)
                break;
            lastRecipe.add(stack.copy());
        }
        recipeKeepTicks = 400;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), SET_KEEP_TICKS_EVENT, 400);
    }

    public void trySetLastRecipe(EntityPlayer player)
    {
        TileAltar.tryToSetLastRecipe(player, this, lastRecipe);
        if (!isEmpty())
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj, xCoord, yCoord, zCoord);
    }

    public boolean isEmpty()
    {
        for (int i = 0; i < getSizeInventory(); i++)
            if (getStackInSlot(i) != null)
                return false;
        return true;
    }

    @Override
    public void writeCustomNBT(NBTTagCompound cmp)
    {
        super.writeCustomNBT(cmp);
        cmp.setInteger("mana", mana);
        cmp.setInteger("manaToGet", manaToGet);
    }

    @Override
    public void readCustomNBT(NBTTagCompound cmp)
    {
        super.readCustomNBT(cmp);
        mana = cmp.getInteger("mana");
        manaToGet = cmp.getInteger("manaToGet");
    }

    @Override
    public int getSizeInventory()
    {
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
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }

    @Override
    public int getCurrentMana()
    {
        return mana;
    }

    @Override
    public boolean isFull()
    {
        return mana >= manaToGet;
    }

    @Override
    public boolean canRecieveManaFromBursts()
    {
        return !isFull();
    }

    @Override
    public void recieveMana(int mana)
    {
        int prev = this.mana;
        this.mana = Math.min(this.mana + mana, manaToGet);
        if (this.mana != prev)
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public int getAvailableSpaceForMana()
    {
        return Math.max(0, manaToGet - mana);
    }

    @Override
    public boolean areIncomingTranfersDone()
    {
        return !canRecieveManaFromBursts();
    }

    @Override
    public boolean canAttachSpark(ItemStack stack)
    {
        return true;
    }

    @Override
    public void attachSpark(ISparkEntity entity)
    {
    }

    @Override
    public ISparkEntity getAttachedSpark()
    {
        List<Entity> sparks = worldObj.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
        for (Entity e : sparks)
            if (e instanceof ISparkEntity)
                return (ISparkEntity) e;
        return null;
    }
}
