package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_13.ParticleTypeBlockASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_13.ParticleTypeDustASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_13.ParticleTypeItemASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_15.ParticleTypeASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_15.ParticleTypeRedstoneASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypesProvider_1_15 extends ParticleTypesProvider_1_13 {

    public ParticleTypesProvider_1_15(InternalResolver resolver) {
        super(resolver, "_1_15");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_15(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_15(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_15(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_15(internal, suffix, refs.particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_13(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_13(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_13(internal, suffix, refs.particleTypeDust, refs.particleType).defineClass();
        new ParticleTypeItemASM_1_13(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeRedstoneASM_1_15(internal, suffix, refs.particleTypeRedstone).defineClass();
    }

}
