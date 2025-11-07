package com.chillrain.chillrainrandomideas.api;

import net.minecraft.util.EnumFacing;

/**
 * BlockPos
 *
 * @author Chill_Rain 2025/07/18
 */
public class BlockPos {
    public final int x;
    public final int y;
    public final int z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public BlockPos add(int dx, int dy, int dz) {
        return new BlockPos(x + dx, y + dy, z + dz);
    }

    public BlockPos offset(EnumFacing facing) {
        return new BlockPos(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BlockPos)) return false;
        BlockPos other = (BlockPos) obj;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode() {
        return x * 31 * 31 + y * 31 + z;
    }

    @Override
    public String toString() {
        return "BlockPos{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
