package com.chillrain.chillrainrandomideas.integration.botany.blocks;


import com.chillrain.chillrainrandomideas.Constant;
import com.chillrain.chillrainrandomideas.integration.botany.blocks.tiles.TileElvenAltar;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.VanillaPacketDispatcher;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.api.wand.IWandHUD;
import vazkii.botania.api.wand.IWandable;
import com.chillrain.chillrainrandomideas.integration.botany.helper.InventoryHelper;
import vazkii.botania.client.core.handler.HUDHandler;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lexicon.LexiconData;

public class BlockElvenAltar extends Block implements ITileEntityProvider, IWandable, ILexiconable, IWandHUD {
    private static final AxisAlignedBB AABB = AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 0.75, 1);

    public BlockElvenAltar() {
        super(Material.rock);
        setBlockName("elvenAlter");
        setBlockTextureName(Constant.NAMESPACE + BlockName.ElvenAlter);
        setHardness(2.0F);
        setResistance(10.0F);
        setStepSound(soundTypeStone);
        BotaniaAPI.blacklistBlockFromMagnet(this, Short.MAX_VALUE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileElvenAltar();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side,
                                    float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            return true;

        TileElvenAltar altar = (TileElvenAltar) world.getTileEntity(x, y, z);
        ItemStack stack = player.getCurrentEquippedItem();

        if (player.isSneaking()) {
            if (altar.manaToGet == 0) {
                InventoryHelper.withdrawFromInventory(altar, player);
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
                return true;
            }
        } else if (altar.isEmpty() && (stack == null)) {
            altar.trySetLastRecipe(player);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
            return true;
        } else if (stack != null) {
            boolean result = altar.addItem(player, stack);
            VanillaPacketDispatcher.dispatchTEToNearbyPlayers(world, x, y, z);
            return result;
        }

        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof IInventory)
            InventoryHelper.dropInventory((IInventory) te, world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AABB;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return AABB;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileElvenAltar altar = (TileElvenAltar) world.getTileEntity(x, y, z);
        return altar.signal;
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int id, int param) {
        super.onBlockEventReceived(world, x, y, z, id, param);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public LexiconEntry getEntry(World world, int i, int i1, int i2, EntityPlayer entityPlayer, ItemStack itemStack) {
        return LexiconData.apothecary;
    }


    @Override
    public boolean onUsedByWand(EntityPlayer entityPlayer, ItemStack itemStack, World world, int i, int i1, int i2, int i3) {
        return true;
    }

    @Override
    public void renderHUD(Minecraft mc, ScaledResolution res, World world, int x, int y, int z) {
        int xc = res.getScaledWidth() / 2;
        int yc = res.getScaledHeight() / 2;
        RenderItem renderItem = new RenderItem();
        float angle = -90F;
        int radius = 24;
        int amt = 0;

        // 取 TileEntity，拿到 itemHandler 和相关数据
        TileElvenAltar tile = (TileElvenAltar) world.getTileEntity(x, y, z);
        if (tile == null) return;

        // 统计非空槽数量
        for (int i = 0; i < tile.getSizeInventory(); i++) {
            ItemStack stack = tile.getStackInSlot(i);
            if (stack == null || stack.stackSize <= 0)
                break;
            amt++;
        }

        if (amt > 0) {
            float anglePer = 360F / amt;

            RecipeRuneAltar matchingRecipe = null;
            for (RecipeRuneAltar recipe : BotaniaAPI.runeAltarRecipes) {
                if (recipe.matches(tile)) {
                    matchingRecipe = recipe;
                    break;
                }
            }

            if (matchingRecipe != null) {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                float progress = tile.manaToGet > 0 ? (float) tile.mana / (float) tile.manaToGet : 0F;

                mc.renderEngine.bindTexture(HUDHandler.manaBar);
                GL11.glColor4f(1F, 1F, 1F, 1F);

                // 1.7.10 使用不同的绘制方法
                func_146110_a(xc + radius + 9, yc - 8, 0, progress == 1F ? 0 : 22, 8, 22, 256, 256);

                RenderHelper.enableStandardItemLighting();

                if (progress == 1F) {
                    renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModBlocks.livingrock), xc + radius + 16, yc + 8);
                    GL11.glTranslatef(0F, 0F, 100F);
                    renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModItems.twigWand), xc + radius + 24, yc + 8);
                    GL11.glTranslatef(0F, 0F, -100F);
                }

                RenderHelper.disableStandardItemLighting();

                if (progress == 1F) {
                    mc.fontRenderer.drawString("+", xc + radius + 14, yc + 12, 0xFFFFFF);
                }
            }

            RenderHelper.enableStandardItemLighting();

            for (int i = 0; i < amt; i++) {
                double xPos = xc + Math.cos(Math.toRadians(angle)) * radius - 8;
                double yPos = yc + Math.sin(Math.toRadians(angle)) * radius - 8;

                GL11.glPushMatrix();
                GL11.glTranslatef((float) xPos, (float) yPos, 0F);

                ItemStack stack = tile.getStackInSlot(i);
                if (stack != null && stack.stackSize > 0)
                    renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, 0, 0);

                GL11.glPopMatrix();

                angle += anglePer;
            }

            RenderHelper.disableStandardItemLighting();

        } else if (tile.recipeKeepTicks > 0) {
            String s = StatCollector.translateToLocal("botaniamisc.altarRefill0");
            mc.fontRenderer.drawStringWithShadow(s, xc - mc.fontRenderer.getStringWidth(s) / 2, yc + 10, 0xFFFFFF);
            s = StatCollector.translateToLocal("botaniamisc.altarRefill1");
            mc.fontRenderer.drawStringWithShadow(s, xc - mc.fontRenderer.getStringWidth(s) / 2, yc + 20, 0xFFFFFF);
        }
    }

    // 1.7.10 版本的 drawTexturedModalRect 替代方法
    private void func_146110_a(int p_146110_1_, int p_146110_2_, int p_146110_3_, int p_146110_4_, int p_146110_5_, int p_146110_6_, float p_146110_7_, float p_146110_8_) {
        float f = 1.0F / p_146110_7_;
        float f1 = 1.0F / p_146110_8_;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(p_146110_1_), (double)(p_146110_2_ + p_146110_6_), 0.0D, (double)((float)(p_146110_3_) * f), (double)((float)(p_146110_4_ + p_146110_6_) * f1));
        tessellator.addVertexWithUV((double)(p_146110_1_ + p_146110_5_), (double)(p_146110_2_ + p_146110_6_), 0.0D, (double)((float)(p_146110_3_ + p_146110_5_) * f), (double)((float)(p_146110_4_ + p_146110_6_) * f1));
        tessellator.addVertexWithUV((double)(p_146110_1_ + p_146110_5_), (double)(p_146110_2_), 0.0D, (double)((float)(p_146110_3_ + p_146110_5_) * f), (double)((float)(p_146110_4_) * f1));
        tessellator.addVertexWithUV((double)(p_146110_1_), (double)(p_146110_2_), 0.0D, (double)((float)(p_146110_3_) * f), (double)((float)(p_146110_4_) * f1));
        tessellator.draw();
    }

}
