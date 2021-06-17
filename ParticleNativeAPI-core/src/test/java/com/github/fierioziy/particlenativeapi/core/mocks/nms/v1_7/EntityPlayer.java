package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.PlayerConnection;

public class EntityPlayer {

    public double x, y, z;

    // required
    public PlayerConnection playerConnection;

    public EntityPlayer(PlayerConnection pc, double x, double y, double z) {
        this.playerConnection = pc;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
