package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeShriek;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

public class ParticleTypeShriekImpl implements ParticleTypeShriek {

    @Override
    public ParticleType delay(int delay) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
