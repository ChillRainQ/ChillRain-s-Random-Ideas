package com.chillrain.chillrainrandomideas.integration.botany.helper;

import com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles.TileElvenAltar;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * InventoryHelper
 *
 * @author Chill_Rain 2025/07/10
 */
public class InventoryHelper {
    public static void withdrawInventoryToPlayer(IInventory inv, EntityPlayer player) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null) {
                EntityItem item = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, stack.copy());
                player.worldObj.spawnEntityInWorld(item);
                inv.setInventorySlotContents(i, null);
            }
        }
    }

    public static void dropInventory(IInventory inv, World world, int x, int y, int z) {
        Random rand = new Random();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (stack != null && stack.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;

                while (stack.stackSize > 0) {
                    int dropAmount = rand.nextInt(21) + 10;

                    if (dropAmount > stack.stackSize)
                        dropAmount = stack.stackSize;

                    stack.stackSize -= dropAmount;
                    EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), dropAmount, stack.getItemDamage()));

                    if (stack.hasTagCompound())
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());

                    float factor = 0.05F;
                    entityItem.motionX = (rand.nextGaussian() * factor);
                    entityItem.motionY = (rand.nextGaussian() * factor + 0.2F);
                    entityItem.motionZ = (rand.nextGaussian() * factor);
                    world.spawnEntityInWorld(entityItem);
                }
            }
        }
    }


    public static void withdrawFromInventory(IInventory inv, EntityPlayer player) {
        World world = player.worldObj;
        Random rand = world.rand;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.stackSize > 0) {
                // 复制一个物品栈准备丢出
                ItemStack dropStack = stack.copy();
                inv.setInventorySlotContents(i, null); // 清空槽位

                // 创建并生成物品实体
                float f = 0.5F;
                double dx = player.posX + (rand.nextFloat() - 0.5) * f;
                double dy = player.posY + rand.nextFloat() * f;
                double dz = player.posZ + (rand.nextFloat() - 0.5) * f;

                EntityItem item = new EntityItem(world, dx, dy, dz, dropStack);
                item.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(item);
            }
        }

        inv.markDirty();
    }
}
