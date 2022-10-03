package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeSculkChargeMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

public class ParticleTypeSculkChargeMotionImpl implements ParticleTypeSculkChargeMotion {

    @Override
    public ParticleTypeMotion roll(double roll) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
