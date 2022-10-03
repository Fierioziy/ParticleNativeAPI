package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeBlockMotion;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Material;

public class ParticleTypeBlockMotionImpl implements ParticleTypeBlockMotion {

    @Override
    public ParticleTypeMotion of(Material block) {
        return of(block, (byte) 0);
    }

    @Override
    public ParticleTypeMotion of(Material block, int meta) {
        return of(block, (byte) meta);
    }

    @Override
    public ParticleTypeMotion of(Material block, byte meta) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
