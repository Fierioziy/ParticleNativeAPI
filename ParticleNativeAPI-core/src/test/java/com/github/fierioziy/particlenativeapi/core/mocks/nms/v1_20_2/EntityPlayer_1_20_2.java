package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_2;

public class EntityPlayer_1_20_2 {

    public double x, y, z;

    // required
    public PlayerConnection_1_20_2 playerConnection_obf;

    public EntityPlayer_1_20_2(PlayerConnection_1_20_2 pc, double x, double y, double z) {
        this.playerConnection_obf = pc;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
