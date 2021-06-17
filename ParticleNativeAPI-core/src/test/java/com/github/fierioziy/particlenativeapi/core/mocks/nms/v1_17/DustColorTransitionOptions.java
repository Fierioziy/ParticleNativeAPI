package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class DustColorTransitionOptions implements ParticleParam {

    public float r, g, b, size;
    public float tr, tg, tb;

    // required
    public DustColorTransitionOptions(Vector3fa vector, Vector3fa target, float size) {
        this.r = vector.x;
        this.g = vector.y;
        this.b = vector.z;
        this.size = size;

        this.tr = target.x;
        this.tg = target.y;
        this.tb = target.z;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DustColorTransitionOptions)) {
            return false;
        }

        DustColorTransitionOptions param = (DustColorTransitionOptions) obj;
        return r == param.r && g == param.g && b == param.b
                && size == param.size
                && tr == param.tr && tg == param.tg && tb == param.tb;
    }

}
