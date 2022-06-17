package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class VibrationParticleOption_1_17 implements ParticleParam {

    private VibrationPath path;

    public VibrationParticleOption_1_17(VibrationPath path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VibrationParticleOption_1_17)) {
            return false;
        }

        VibrationParticleOption_1_17 param = (VibrationParticleOption_1_17) obj;
        return path.equals(param.path);
    }
}
