package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class BlockPositionSource implements PositionSource {

    public BlockPosition pos;

    public BlockPositionSource(BlockPosition pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockPositionSource)) return false;

        BlockPositionSource param = (BlockPositionSource) obj;

        return pos.equals(param.pos);
    }
}
