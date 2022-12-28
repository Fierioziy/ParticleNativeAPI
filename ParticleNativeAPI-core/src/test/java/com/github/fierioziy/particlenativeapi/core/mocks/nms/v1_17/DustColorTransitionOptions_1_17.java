package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class DustColorTransitionOptions_1_17 implements ParticleParam {

    public float r, g, b, size;
    public float tr, tg, tb;

    // required
    public DustColorTransitionOptions_1_17(Vector3fa vector, Vector3fa target, float size) {
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
        if (!(obj instanceof DustColorTransitionOptions_1_17)) {
            return false;
        }

        DustColorTransitionOptions_1_17 param = (DustColorTransitionOptions_1_17) obj;
        return r == param.r && g == param.g && b == param.b
                && size == param.size
                && tr == param.tr && tg == param.tg && tb == param.tb;
    }

}
