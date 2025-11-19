package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypePowerMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

public class ParticleTypePowerMotionImpl implements ParticleTypePowerMotion {

    @Override
    public ParticleTypeMotion power(double power) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
