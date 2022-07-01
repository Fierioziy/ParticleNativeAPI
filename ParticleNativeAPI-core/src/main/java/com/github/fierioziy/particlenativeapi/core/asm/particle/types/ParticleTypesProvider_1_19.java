package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_19.ParticleTypeShriekASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_19.ParticleTypeVibrationASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_19.ParticleTypeSculkChargeASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.19.</p>
 */
public class ParticleTypesProvider_1_19 extends ParticleTypesProvider_1_18 {

    public ParticleTypesProvider_1_19(InternalResolver resolver) {
        super(resolver, "_1_19");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_17(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_17(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_17(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_17(internal, suffix, refs.particleTypeDust, refs.particleType).defineClass();
        new ParticleTypeDustTransitionASM_1_17(internal, suffix, refs.particleTypeDustTransition, refs.particleType).defineClass();
        new ParticleTypeItemASM_1_17(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();
        new ParticleTypeVibrationASM_1_19(internal, suffix, refs.particleTypeVibration).defineClass();

        new ParticleTypeRedstoneASM_1_17(internal, suffix, refs.particleTypeRedstone).defineClass();

        new ParticleTypeSculkChargeASM_1_19(internal, suffix, refs.particleTypeSculkChargeMotion, refs.particleTypeMotion).defineClass();
        new ParticleTypeShriekASM_1_19(internal, suffix, refs.particleTypeShriek, refs.particleType).defineClass();
    }

}
