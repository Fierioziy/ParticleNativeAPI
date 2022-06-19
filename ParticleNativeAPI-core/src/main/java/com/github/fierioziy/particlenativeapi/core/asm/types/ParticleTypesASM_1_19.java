package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_19.ParticleTypeSculkChargeASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_19.ParticleTypeShriekASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_19.ParticleTypeVibrationASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.19.</p>
 */
public class ParticleTypesASM_1_19 extends ParticleTypesASM_1_18 {

    private static final String SUFFIX = "_1_19";

    public ParticleTypesASM_1_19(InternalResolver resolver) {
        super(resolver, SUFFIX);
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_17(internal, SUFFIX, particleType).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeColorable).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeMotion).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_17(internal, SUFFIX, particleTypeBlock, particleType).defineClass();
        new ParticleTypeBlockASM_1_17(internal, SUFFIX, particleTypeBlockMotion, particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_17(internal, SUFFIX, particleTypeDust, particleType).defineClass();
        new ParticleTypeDustTransitionASM_1_17(internal, SUFFIX, particleTypeDustTransition, particleType).defineClass();
        new ParticleTypeItemASM_1_17(internal, SUFFIX, particleTypeItemMotion, particleTypeMotion).defineClass();
        new ParticleTypeVibrationASM_1_19(internal, SUFFIX, particleTypeVibration).defineClass();

        new ParticleTypeRedstoneASM_1_17(internal, SUFFIX, particleTypeRedstone).defineClass();

        new ParticleTypeSculkChargeASM_1_19(internal, SUFFIX, particleTypeSculkChargeMotion, particleTypeMotion).defineClass();
        new ParticleTypeShriekASM_1_19(internal, SUFFIX, particleTypeShriek, particleType).defineClass();
    }

}
