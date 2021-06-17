package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class VibrationPath {

    public BlockPosition pos;
    public PositionSource source;
    public int ticks;

    public VibrationPath(BlockPosition pos, PositionSource source, int ticks) {
        this.pos = pos;
        this.source = source;
        this.ticks = ticks;
    }

}
