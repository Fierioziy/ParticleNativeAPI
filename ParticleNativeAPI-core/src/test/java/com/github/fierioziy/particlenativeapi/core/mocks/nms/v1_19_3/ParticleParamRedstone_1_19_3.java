package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3;

import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3f;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class ParticleParamRedstone_1_19_3 implements ParticleParam {

    public float r, g, b, size;

    // required
    public ParticleParamRedstone_1_19_3(Vector3f vector, float size) {
        this.r = vector.x;
        this.g = vector.y;
        this.b = vector.z;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamRedstone_1_19_3)) {
            return false;
        }

        // make delta check

        ParticleParamRedstone_1_19_3 param = (ParticleParamRedstone_1_19_3) obj;
        return eq(r, param.r) && eq(g, param.g) && eq(b, param.b)
                && eq(size, param.size);
    }

    private boolean eq(float value1, float value2) {
        return Math.abs(value1 - value2) <= 0.001F;
    }

}
