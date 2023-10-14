package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.packet.v1_17.ParticlePacketASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;

public class ParticlePacketProvider_1_20_2 extends ParticlePacketProvider {

    private final String playerConnectionFieldName;
    private final String sendPacketMethodName;

    public ParticlePacketProvider_1_20_2(ContextASM context) {
        super(context);

        playerConnectionFieldName = context.internal.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = context.internal.getSendPacketMethodName_1_20_2();
    }

    @Override
    public void registerClasses() {
        new ParticlePacketASM_1_17(context,
                ClassSkeleton.PARTICLE_PACKET,
                playerConnectionFieldName, sendPacketMethodName)
                .registerClass();
    }

}
