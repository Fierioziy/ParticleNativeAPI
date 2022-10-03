package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDustColorTransition;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeDustColorTransitionImpl implements ParticleTypeDustColorTransition {

    @Override
    public ParticleType color(Color color, Color transition, double size) {
        return color(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                transition.getRed() / 255F,
                transition.getGreen() / 255F,
                transition.getBlue() / 255F,
                (float) size
        );
    }

    @Override
    public ParticleType color(int r, int g, int b,
                              int tr, int tg, int tb,
                              double size) {
        return color(
                r / 255F, g / 255F, b / 255F,
                tr / 255F, tg / 255F, tb / 255F,
                (float) size
        );
    }

    @Override
    public ParticleType color(float r, float g, float b,
                              float tr, float tg, float tb,
                              float size) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
