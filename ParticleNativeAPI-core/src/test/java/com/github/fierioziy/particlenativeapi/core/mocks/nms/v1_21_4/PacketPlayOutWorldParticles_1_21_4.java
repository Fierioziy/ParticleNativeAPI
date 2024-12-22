package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_21_4;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;

public class PacketPlayOutWorldParticles_1_21_4 implements Packet {

    public ParticleParam particle;
    public boolean far;
    public boolean alwaysShow;
    public double x, y, z;
    public float offsetX, offsetY, offsetZ;
    public float speed;
    public int count;

    // required
    public PacketPlayOutWorldParticles_1_21_4(ParticleParam particle,
                                              boolean far, boolean alwaysShow,
                                              double x, double y, double z,
                                              float offsetX, float offsetY, float offsetZ,
                                              float speed, int count) {
        this.particle = particle;
        this.far = far;
        this.alwaysShow = alwaysShow;
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
