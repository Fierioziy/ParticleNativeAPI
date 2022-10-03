package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.packet.v1_17.ParticlePacketASM_1_17;

public class ParticlePacketProvider_1_17 extends ParticlePacketProvider {

    private final String playerConnectionFieldName;
    private final String sendPacketMethodName;

    public ParticlePacketProvider_1_17(InternalResolver resolver) {
        super(resolver, "_1_17");

        playerConnectionFieldName = resolver.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = resolver.getSendPacketMethodName_1_18();
    }

    @Override
    public void registerClasses() {
        new ParticlePacketASM_1_17(internal, suffix,
                ClassSkeleton.PARTICLE_PACKET,
                playerConnectionFieldName, sendPacketMethodName)
                .registerClass();
    }

}
