package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_21_3;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class ParticleParamRedstone_1_21_3 implements ParticleParam {

    public int color;
    public float size;

    // required
    public ParticleParamRedstone_1_21_3(int color, float size) {
        this.color = color;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamRedstone_1_21_3)) {
            return false;
        }

        // make delta check
        ParticleParamRedstone_1_21_3 param = (ParticleParamRedstone_1_21_3) obj;
        return color == param.color && eq(size, param.size);
    }

    private boolean eq(float value1, float value2) {
        return Math.abs(value1 - value2) <= 0.001F;
    }

}
