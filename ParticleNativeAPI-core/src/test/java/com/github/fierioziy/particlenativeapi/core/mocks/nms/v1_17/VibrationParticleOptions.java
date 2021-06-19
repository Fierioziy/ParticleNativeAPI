package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class VibrationParticleOptions implements ParticleParam {

    private VibrationPath path;

    public VibrationParticleOptions(VibrationPath path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VibrationParticleOptions)) {
            return false;
        }

        VibrationParticleOptions param = (VibrationParticleOptions) obj;
        return path.equals(param.path);
    }
}
