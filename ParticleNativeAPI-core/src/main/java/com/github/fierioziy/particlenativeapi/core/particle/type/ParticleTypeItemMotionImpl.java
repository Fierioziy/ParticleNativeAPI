package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeItemMotion;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Material;

public class ParticleTypeItemMotionImpl implements ParticleTypeItemMotion {

    public ParticleTypeMotion of(Material item) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    public boolean isPresent() {
        return false;
    }

}
