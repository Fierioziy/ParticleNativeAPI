package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_13.ParticleTypeBlockASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_13.ParticleTypeDustASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_13.ParticleTypeItemASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_15.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypesASM_1_15 extends ParticleTypesASM_1_13 {

    private static final String SUFFIX = "_1_15";

    public ParticleTypesASM_1_15(InternalResolver resolver) {
        super(resolver, SUFFIX);
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_15(internal, SUFFIX, particleType).defineClass();
        new ParticleTypeASM_1_15(internal, SUFFIX, particleTypeColorable).defineClass();
        new ParticleTypeASM_1_15(internal, SUFFIX, particleTypeMotion).defineClass();
        new ParticleTypeASM_1_15(internal, SUFFIX, particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_13(internal, SUFFIX, particleTypeBlock, particleType).defineClass();
        new ParticleTypeBlockASM_1_13(internal, SUFFIX, particleTypeBlockMotion, particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_13(internal, SUFFIX, particleTypeDust, particleType).defineClass();
        new ParticleTypeItemASM_1_13(internal, SUFFIX, particleTypeItemMotion, particleTypeMotion).defineClass();

        new ParticleTypeRedstoneASM_1_15(internal, SUFFIX, particleTypeRedstone).defineClass();
    }

}
