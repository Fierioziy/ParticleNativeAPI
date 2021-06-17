package com.github.fierioziy.particlenativeapi.core.asm.types.v1_15;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_13.ParticleTypeDustASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

public class ParticleTypeDustASM_1_15 extends ParticleTypeDustASM_1_13 {

    public ParticleTypeDustASM_1_15(InternalResolver resolver, Type superType, Type returnType) {
        super(resolver, superType, returnType);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_15");
    }

}
