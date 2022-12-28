package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3;

import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3f;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class DustColorTransitionOptions_1_19_3 implements ParticleParam {

    public float r, g, b, size;
    public float tr, tg, tb;

    // required
    public DustColorTransitionOptions_1_19_3(Vector3f vector, Vector3f target, float size) {
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
        if (!(obj instanceof DustColorTransitionOptions_1_19_3)) {
            return false;
        }

        DustColorTransitionOptions_1_19_3 param = (DustColorTransitionOptions_1_19_3) obj;
        return r == param.r && g == param.g && b == param.b
                && size == param.size
                && tr == param.tr && tg == param.tg && tb == param.tb;
    }

}
