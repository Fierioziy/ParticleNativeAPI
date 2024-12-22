package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeSculkChargeASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeShriekASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeVibrationASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeVibrationSingleASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19_3.ParticleTypeDustASM_1_19_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19_3.ParticleTypeDustTransitionASM_1_19_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19_3.ParticleTypeRedstoneASM_1_19_3;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.19.3.</p>
 */
public class ParticleTypesProvider_1_19_3 extends ParticleTypesProvider_1_18 {

    public ParticleTypesProvider_1_19_3(ContextASM context) {
        super(context, context.internal.getParticles_1_19_3());
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_19_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_FLOAT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeDustTransitionASM_1_19_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_COLOR_TRANSITION_FLOAT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeItemASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeVibrationSingleASM_1_19(context, ClassSkeleton.PARTICLE_TYPE_VIBRATION_SINGLE).registerClass();

        new ParticleTypeRedstoneASM_1_19_3(context).registerClass();

        new ParticleTypeSculkChargeASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SCULK_CHARGE_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeShriekASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SHRIEK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeVibrationASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_VIBRATION,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
    }

}
