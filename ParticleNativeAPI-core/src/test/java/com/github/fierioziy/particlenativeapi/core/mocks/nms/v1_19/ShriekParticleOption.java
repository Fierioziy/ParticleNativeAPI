package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class ShriekParticleOption implements ParticleParam {

    public int delay;

    public ShriekParticleOption(int delay) {
        this.delay = delay;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShriekParticleOption)) return false;

        ShriekParticleOption param = (ShriekParticleOption) obj;

        return delay == param.delay;
    }

}
