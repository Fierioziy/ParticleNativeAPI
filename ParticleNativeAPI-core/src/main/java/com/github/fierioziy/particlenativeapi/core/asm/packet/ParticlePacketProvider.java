package com.github.fierioziy.particlenativeapi.core.asm.packet;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

public abstract class ParticlePacketProvider extends BaseASM {

    public ParticlePacketProvider(InternalResolver resolver, String suffix) {
        super(resolver, suffix);
    }

    public abstract void registerClasses();

}
