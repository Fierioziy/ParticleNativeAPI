package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13;

public class ParticleParamRedstone_1_13 implements ParticleParam {

    public float r, g, b, size;

    // required
    public ParticleParamRedstone_1_13(float r, float g, float b, float size) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamRedstone_1_13)) {
            return false;
        }

        ParticleParamRedstone_1_13 param = (ParticleParamRedstone_1_13) obj;
        return eq(r, param.r) && eq(g, param.g) && eq(b, param.b)
                && eq(size, param.size);
    }

    private boolean eq(float value1, float value2) {
        return Math.abs(value1 - value2) <= 0.001F;
    }

}
