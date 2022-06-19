package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.PositionSource;

public class VibrationParticleOption_1_19 implements ParticleParam {

    public PositionSource source;
    public int ticks;

    public VibrationParticleOption_1_19(PositionSource source, int ticks) {
        this.source = source;
        this.ticks = ticks;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VibrationParticleOption_1_19)) return false;

        VibrationParticleOption_1_19 param = (VibrationParticleOption_1_19) obj;

        return source.equals(param.source) && ticks == param.ticks;
    }

}
