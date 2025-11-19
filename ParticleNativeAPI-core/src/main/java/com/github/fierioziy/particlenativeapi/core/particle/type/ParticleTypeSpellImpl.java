package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeSpell;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeSpellImpl implements ParticleTypeSpell {

    @Override
    public ParticleType spell(Color color, int alpha, double power) {
        return spell(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha,
                power
        );
    }

    @Override
    public ParticleType spell(int r, int g, int b, int alpha, double power) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
