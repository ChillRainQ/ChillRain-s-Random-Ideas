package com.chillrain.chillrainrandomideas.bugfix.containerfix;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

/**
 * ContainerUtil (MC 1.7.10 版本)
 *
 * 工具类：用于检测方块是否为容器以及容器是否为空。
 *
 * @author Chill_Rain
 * @date 2025/07/23
 */
public class ContainerUtil {

    /**
     * 获取方块对应的 TileEntity（如果存在）
     */
    public static TileEntity getContainerBlockTile(BlockEvent.BreakEvent event) {
        World world = event.world; // 1.7.10: 字段是 world
        int x = event.x;
        int y = event.y;
        int z = event.z;
        return world.getTileEntity(x, y, z);
    }

    /**
     * 获取方块对应的 IInventory 实例（如果存在）
     */
    public static IInventory getInventoryFromBlock(BlockEvent.BreakEvent event) {
        try {
            TileEntity tile = getContainerBlockTile(event);
            if (tile instanceof IInventory) {
                return (IInventory) tile;
            }
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * 检测容器中是否存在物品
     */
    public static boolean hasItems(IInventory inventory) {
        if (inventory == null) {
            return false;
        }

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.stackSize > 0) {
                return true;
            }
        }
        return false;
    }
}
