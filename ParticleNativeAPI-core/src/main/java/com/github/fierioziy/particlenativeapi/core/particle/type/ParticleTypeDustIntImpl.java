package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDust;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeDustIntImpl implements ParticleTypeDust {

    @Override
    public ParticleType color(Color color, double size) {
        return color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                (float) size
        );
    }

    @Override
    public ParticleType color(Color color, float size) {
        return color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                size
        );
    }

    @Override
    public ParticleType color(int r, int g, int b, double size) {
        return color(r, g, b, (float) size);
    }

    @Override
    public ParticleType color(int r, int g, int b, float size) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public ParticleType color(float r, float g, float b, float size) {
        // this is awful, but we have to live with that
        return color((int) (r * 255F), (int) (g * 255F), (int) (b * 255F), size);
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
