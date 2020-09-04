package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;

public class PacketPlayOutWorldParticles_1_7 implements Packet {

    public String particle;
    public float x, y, z;
    public float offsetX, offsetY, offsetZ;
    public float speed;
    public int count;

    // required
    public PacketPlayOutWorldParticles_1_7(String particle, float x, float y, float z,
                                           float offsetX, float offsetY, float offsetZ,
                                           float speed, int count) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.count = count;
    }

}
