package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class ParticleParamRedstone_1_17 implements ParticleParam {

    public float r, g, b, size;

    // required
    public ParticleParamRedstone_1_17(Vector3fa vector, float size) {
        this.r = vector.x;
        this.g = vector.y;
        this.b = vector.z;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamRedstone_1_17)) {
            return false;
        }

        ParticleParamRedstone_1_17 param = (ParticleParamRedstone_1_17) obj;
        return r == param.r && g == param.g && b == param.b
                && size == param.size;
    }

}
