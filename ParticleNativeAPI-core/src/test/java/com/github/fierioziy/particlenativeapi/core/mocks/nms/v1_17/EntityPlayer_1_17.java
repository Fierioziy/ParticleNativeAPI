package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class EntityPlayer_1_17 {

    public double x, y, z;

    // required
    public PlayerConnection_1_17 playerConnection;

    public EntityPlayer_1_17(PlayerConnection_1_17 pc, double x, double y, double z) {
        this.playerConnection = pc;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
