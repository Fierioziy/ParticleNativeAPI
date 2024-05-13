package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_5;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class ColorParticleOption implements ParticleParam {

    public Particle<?> particle;
    public int color;

    // required
    private ColorParticleOption(Particle<?> particle, int color) {
        this.particle = particle;
        this.color = color;
    }

    public static ColorParticleOption newByInt_obf(Particle<?> particle, int color) {
        return new ColorParticleOption(particle, color);
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ColorParticleOption)) {
            return false;
        }

        ColorParticleOption param = (ColorParticleOption) obj;
        return particle == param.particle && color == param.color;
    }

    @Override
    public String toString() {
        return "ColorParticleOption{" +
                "particle=" + particle +
                ", color=" + color +
                '}';
    }
}
