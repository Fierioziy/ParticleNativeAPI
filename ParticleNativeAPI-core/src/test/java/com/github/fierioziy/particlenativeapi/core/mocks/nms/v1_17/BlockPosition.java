package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class BlockPosition {

    public int x, y, z;

    public BlockPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockPosition)) {
            return false;
        }

        BlockPosition param = (BlockPosition) obj;

        return x == param.x && y == param.y && z == param.z;
    }
}
