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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VibrationPath)) return false;

        VibrationPath param = (VibrationPath) obj;

        return pos.equals(param.pos) && source.equals(param.source) && ticks == param.ticks;
    }
}
