package com.chillrain.chillrainrandomideas.bugfix.containerfix;

import com.chillrain.chillrainrandomideas.utils.ClientServerUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

/**
 * ContainerEventHandler
 * 在玩家破坏方块时检测容器是否为空，
 * 若容器中仍有物品则阻止破坏并提示玩家。
 * @author Chill_Rain
 * @date 2025/07/23
 */
public class ContainerEventHandler {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.world; // 1.7.10 中字段名为 world
        if (ClientServerUtil.isClient(world))
            return;
        TileEntity tile = ContainerUtil.getContainerBlockTile(event);
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory) tile;
            if (ContainerUtil.hasItems(inventory)) {
                event.setCanceled(true);
                if (event.getPlayer() != null) {
                    event.getPlayer().addChatMessage(
                            new ChatComponentTranslation("This container is not empty!")
                    );
                }
            }
        }
    }
}
