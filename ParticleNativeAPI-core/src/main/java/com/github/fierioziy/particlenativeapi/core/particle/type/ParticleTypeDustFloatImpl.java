package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDust;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;

public class ParticleTypeDustFloatImpl implements ParticleTypeDust {

    @Override
    public ParticleType color(Color color, double size) {
        return color(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                (float) size
        );
    }

    @Override
    public ParticleType color(Color color, float size) {
        return color(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                size
        );
    }

    @Override
    public ParticleType color(int r, int g, int b, double size) {
        return color(r / 255F, g / 255F, b / 255F, (float) size);
    }

    @Override
    public ParticleType color(int r, int g, int b, float size) {
        return color(r / 255F, g / 255F, b / 255F, size);
    }

    @Override
    public ParticleType color(float r, float g, float b, float size) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
