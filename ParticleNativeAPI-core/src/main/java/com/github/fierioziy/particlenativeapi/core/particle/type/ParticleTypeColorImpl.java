package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeColor;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeColorImpl implements ParticleTypeColor {

    @Override
    public ParticleType color(Color color) {
        return color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                255
        );
    }

    @Override
    public ParticleType color(Color color, int alpha) {
        return color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha
        );
    }

    @Override
    public ParticleType color(int r, int g, int b) {
        return color(r, g, b, 255);
    }

    @Override
    public ParticleType color(int r, int g, int b, int alpha) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
