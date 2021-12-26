package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7;

public class EntityPlayer_1_7 {

    public double x, y, z;

    // required
    public PlayerConnection_1_7 playerConnection;

    public EntityPlayer_1_7(PlayerConnection_1_7 pc, double x, double y, double z) {
        this.playerConnection = pc;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
