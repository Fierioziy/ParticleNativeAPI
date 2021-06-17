package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_15.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypesASM_1_15 extends ParticleTypesASM_1_13 {

    public ParticleTypesASM_1_15(InternalResolver resolver) {
        super(resolver);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_15");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_15(internal, particleType)           .defineClass();
        new ParticleTypeASM_1_15(internal, particleTypeColorable)  .defineClass();
        new ParticleTypeASM_1_15(internal, particleTypeMotion)     .defineClass();
        new ParticleTypeASM_1_15(internal, particleTypeNote)       .defineClass();

        new ParticleTypeBlockASM_1_15(internal, particleTypeBlock,       particleType)       .defineClass();
        new ParticleTypeBlockASM_1_15(internal, particleTypeBlockMotion, particleTypeMotion) .defineClass();

        new ParticleTypeDustASM_1_15(internal, particleTypeDust, particleType).defineClass();
        new ParticleTypeItemASM_1_15(internal, particleTypeItemMotion, particleTypeMotion).defineClass();

        new ParticleTypeRedstoneASM_1_15(internal, particleTypeRedstone).defineClass();
    }

}
