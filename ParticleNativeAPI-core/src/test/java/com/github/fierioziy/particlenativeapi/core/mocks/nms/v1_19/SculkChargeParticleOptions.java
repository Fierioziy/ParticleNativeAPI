package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

// its a record tho, but we don't access it directly so it should be a problem
public class SculkChargeParticleOptions implements ParticleParam {

    public float roll;

    public SculkChargeParticleOptions(float roll) {
        this.roll = roll;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SculkChargeParticleOptions)) return false;

        SculkChargeParticleOptions param = (SculkChargeParticleOptions) obj;

        return roll == param.roll;
    }

}
