package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;

public class PacketPlayOutWorldParticles_1_8 implements Packet {

    public EnumParticle particle;
    public boolean far;
    public float x, y, z;
    public float offsetX, offsetY, offsetZ;
    public float speed;
    public int count;
    public int[] data;

    // required
    public PacketPlayOutWorldParticles_1_8(EnumParticle particle, boolean far,
                                           float x, float y, float z,
                                           float offsetX, float offsetY, float offsetZ,
                                           float speed, int count, int... data) {
        this.particle = particle;
        this.far = far;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.count = count;
        this.data = data;
    }
}
