package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDustColorTransition;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeDustColorTransitionIntImpl
        implements ParticleTypeDustColorTransition {

    @Override
    public ParticleType color(Color color, Color transition, double size) {
        return color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                transition.getRed(),
                transition.getGreen(),
                transition.getBlue(),
                size
        );
    }

    @Override
    public ParticleType color(int r, int g, int b,
                              int tr, int tg, int tb,
                              double size) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public ParticleType color(float r, float g, float b,
                              float tr, float tg, float tb,
                              float size) {
        // this is awful, but we have to live with that
        return color(
                (int) (r * 255F), (int) (g * 255F), (int) (b * 255F),
                (int) (tr * 255F), (int) (tg * 255F), (int) (tb * 255F),
                (double) size
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
