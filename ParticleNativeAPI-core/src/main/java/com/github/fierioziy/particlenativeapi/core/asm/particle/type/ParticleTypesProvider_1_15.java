package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeBlockASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeDustASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeItemASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_15.ParticleTypeASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_15.ParticleTypeRedstoneASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypesProvider_1_15 extends ParticleTypesProvider_1_13 {

    public ParticleTypesProvider_1_15(ContextASM context) {
        super(context);
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_15(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_15(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_15(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_15(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_FLOAT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeItemASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeRedstoneASM_1_15(context).registerClass();
    }

}
