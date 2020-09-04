package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13;

public class ParticleParamRedstone implements ParticleParam {

    public float r, g, b, size;

    // required
    public ParticleParamRedstone(float r, float g, float b, float size) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ParticleParamRedstone)) {
            return false;
        }

        ParticleParamRedstone param = (ParticleParamRedstone) obj;
        return r == param.r && g == param.g && b == param.b
                && size == param.size;
    }

}
