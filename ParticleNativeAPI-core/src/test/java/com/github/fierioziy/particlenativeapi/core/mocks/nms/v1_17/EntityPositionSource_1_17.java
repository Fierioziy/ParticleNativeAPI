package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class EntityPositionSource_1_17 implements PositionSource {

    public int entityId;

    public EntityPositionSource_1_17(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EntityPositionSource_1_17)) return false;

        EntityPositionSource_1_17 param = (EntityPositionSource_1_17) obj;

        return entityId == param.entityId;
    }

}
