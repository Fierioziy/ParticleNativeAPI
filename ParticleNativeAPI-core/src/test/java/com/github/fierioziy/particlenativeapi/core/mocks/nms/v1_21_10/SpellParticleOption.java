package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_21_10;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class SpellParticleOption implements ParticleParam {

    public Particle<?> particle;
    public int color;
    public float power;

    // required
    private SpellParticleOption(Particle<?> particle, int color, float power) {
        this.particle = particle;
        this.color = color;
        this.power = power;
    }

    public static SpellParticleOption newSpellInstance_obf(Particle<?> particle, int color, float power) {
        return new SpellParticleOption(particle, color, power);
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SpellParticleOption)) {
            return false;
        }

        SpellParticleOption param = (SpellParticleOption) obj;
        return particle == param.particle && color == param.color && power == param.power;
    }

    @Override
    public String toString() {
        return "SpellParticleOption{" +
                "particle=" + particle +
                ", color=" + color +
                ", power=" + power +
                '}';
    }
}
