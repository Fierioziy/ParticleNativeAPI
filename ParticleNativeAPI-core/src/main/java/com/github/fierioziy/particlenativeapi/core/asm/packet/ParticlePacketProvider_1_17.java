package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.packet.v1_17.ParticlePacketASM_1_17;

public class ParticlePacketProvider_1_17 extends ParticlePacketProvider {

    private final String playerConnectionFieldName;
    private final String sendPacketMethodName;

    public ParticlePacketProvider_1_17(ContextASM context) {
        super(context);

        playerConnectionFieldName = context.internal.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = context.internal.getSendPacketMethodName_1_18();
    }

    @Override
    public void registerClasses() {
        new ParticlePacketASM_1_17(context,
                ClassSkeleton.PARTICLE_PACKET,
                playerConnectionFieldName, sendPacketMethodName)
                .registerClass();
    }

}
