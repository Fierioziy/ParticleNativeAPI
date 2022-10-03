package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeBlockASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeDustASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.ParticleTypeItemASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_15.ParticleTypeASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_15.ParticleTypeRedstoneASM_1_15;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypesProvider_1_15 extends ParticleTypesProvider_1_13 {

    public ParticleTypesProvider_1_15(InternalResolver resolver,
                                      ParticlePacketProvider particlePacketProvider) {
        super(resolver, "_1_15", particlePacketProvider);
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_15(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE,
                particlePacketImpl_X)
                .registerClass();
        new ParticleTypeASM_1_15(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_MOTION,
                particlePacketImpl_X)
                .registerClass();
        new ParticleTypeASM_1_15(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_COLORABLE,
                particlePacketImpl_X)
                .registerClass();
        new ParticleTypeASM_1_15(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_NOTE,
                particlePacketImpl_X)
                .registerClass();

        new ParticleTypeBlockASM_1_13(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_13(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_13(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_DUST,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeItemASM_1_13(
                internal, suffix,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeRedstoneASM_1_15(
                internal, suffix,
                particlePacketImpl_X)
                .registerClass();
    }

}
