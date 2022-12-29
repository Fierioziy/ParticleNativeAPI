package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.packet.v1_7.ParticlePacketASM_1_7;

public class ParticlePacketProvider_1_7 extends ParticlePacketProvider {

    public ParticlePacketProvider_1_7(ContextASM context) {
        super(context);
    }

    @Override
    public void registerClasses() {
        new ParticlePacketASM_1_7(context, ClassSkeleton.PARTICLE_PACKET).registerClass();
    }

}
