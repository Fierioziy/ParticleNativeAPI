package com.github.fierioziy.particlenativeapi.core.mocks.obc.common;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Entity;

/*
It has to be abstract, we don't want to implement tons of Player's methods.
 */
public abstract class CraftEntity implements org.bukkit.entity.Entity {

    public Entity entity;

    public CraftEntity() {}

    // required
    public Entity getHandle() {
        return entity;
    }

}
