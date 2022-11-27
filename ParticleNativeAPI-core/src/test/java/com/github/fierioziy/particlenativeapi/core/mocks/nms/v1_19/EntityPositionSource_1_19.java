package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Entity;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.PositionSource;

public class EntityPositionSource_1_19 implements PositionSource {

    public Entity entity;
    public float yOffset;

    public EntityPositionSource_1_19(Entity entity, float yOffset) {
        this.entity = entity;
        this.yOffset = yOffset;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntityPositionSource_1_19)) return false;

        EntityPositionSource_1_19 param = (EntityPositionSource_1_19) obj;

        return entity == param.entity && yOffset == param.yOffset;
    }

}
