package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_21_10;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class PowerParticleOption implements ParticleParam {

    public Particle<?> particle;
    public float power;

    // required
    private PowerParticleOption(Particle<?> particle, float power) {
        this.particle = particle;
        this.power = power;
    }

    public static PowerParticleOption newPowerInstance_obf(Particle<?> particle, float power) {
        return new PowerParticleOption(particle, power);
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PowerParticleOption)) {
            return false;
        }

        PowerParticleOption param = (PowerParticleOption) obj;
        return power == param.power;
    }

    @Override
    public String toString() {
        return "PowerParticleOption{" +
                "power=" + power +
                '}';
    }
}
