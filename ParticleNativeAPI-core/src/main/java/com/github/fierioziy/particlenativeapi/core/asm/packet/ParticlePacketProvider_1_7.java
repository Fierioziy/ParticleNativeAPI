package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.packet.v1_7.ParticlePacketASM_1_7;

public class ParticlePacketProvider_1_7 extends ParticlePacketProvider {

    public ParticlePacketProvider_1_7(InternalResolver resolver) {
        super(resolver, "_1_7");
    }

    @Override
    public void registerClasses() {
        new ParticlePacketASM_1_7(internal, suffix,
                ClassSkeleton.PARTICLE_PACKET)
                .registerClass();
    }

}
