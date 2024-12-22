package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_21_3;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class DustColorTransitionOptions_1_21_3 implements ParticleParam {

    public int color;
    public int targetColor;
    public float size;

    // required
    public DustColorTransitionOptions_1_21_3(int color, int targetColor, float size) {
        this.color = color;
        this.targetColor = targetColor;
        this.size = size;
    }

    // simple value compare
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DustColorTransitionOptions_1_21_3)) {
            return false;
        }

        DustColorTransitionOptions_1_21_3 param = (DustColorTransitionOptions_1_21_3) obj;
        return color == param.color
                && targetColor == param.targetColor
                && eq(size, param.size);
    }

    private boolean eq(float value1, float value2) {
        return Math.abs(value1 - value2) <= 0.001F;
    }

}
