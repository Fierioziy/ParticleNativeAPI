package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeVibrationASM_1_19 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeVibrationASM_1_19(InternalResolver resolver, String suffix,
                                         ClassSkeleton superType, ClassSkeleton returnType) {
        super(resolver, suffix, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_flyingTo_pos(cw);
        writeMethod_flyingTo_entity(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_flyingTo_pos(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                FLYING_TO_METHOD_NAME,
                "(DDDI)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_targetX = 1;
        int local_targetY = 3;
        int local_targetZ = 5;
        int local_ticks = 7;
        int local_particleType = 8;

        /*
        ParticleTypeImpl_X particleType = this.particleWrapper;
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_WRAPPER_FIELD_NAME,
                implReturnType.desc());
        mv.visitVarInsn(ASTORE, local_particleType);

        /*
        particleType.setParticle(
            new VibrationParticleOption(
                new BlockPositionSource(
                    new BlockPosition((int) targetX, (int) targetY, (int) targetZ))
                ),
                ticks
            ),
         );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // VibrationParticleOption start
        mv.visitTypeInsn(NEW, refs.vibrationParticleOption.internalName());
        mv.visitInsn(DUP);

        // BlockPositionSource start
        mv.visitTypeInsn(NEW, refs.blockPositionSource.internalName());
        mv.visitInsn(DUP);

        // BlockPosition start
        mv.visitTypeInsn(NEW, refs.blockPosition.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, local_targetX);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_targetY);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_targetZ);mv.visitInsn(D2I);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.blockPosition.internalName(),
                "<init>", "(III)V", false);
        // BlockPosition end

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.blockPositionSource.internalName(),
                "<init>", "(" + refs.blockPosition.desc() + ")V", false);
        // BlockPositionSource end

        mv.visitTypeInsn(CHECKCAST, refs.positionSource.internalName());

        // ticks
        mv.visitVarInsn(ILOAD, local_ticks);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vibrationParticleOption.internalName(),
                "<init>", "(" + refs.positionSource.desc() + "I)V", false);
        // VibrationParticleOption end

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME,
                "(" + refs.particleParam_1_17.desc() + ")V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_flyingTo_entity(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                FLYING_TO_METHOD_NAME,
                "(Lorg/bukkit/entity/Entity;I)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_entity = 1;
        int local_ticks = 2;
        int local_particleType = 3;

        /*
        ParticleTypeImpl_X particleType = this.particleWrapper;
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_WRAPPER_FIELD_NAME,
                implReturnType.desc());
        mv.visitVarInsn(ASTORE, local_particleType);

        /*
        particleType.setParticle(
            new VibrationParticleOption(
                new EntityPositionSource(
                    ((CraftEntity) entity).getHandle(), 0F
                ),
                ticks
            )
         );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // VibrationParticleOption start
        mv.visitTypeInsn(NEW, refs.vibrationParticleOption.internalName());
        mv.visitInsn(DUP);

        // EntityPositionSource start
        mv.visitTypeInsn(NEW, refs.entityPositionSource.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_entity);
        mv.visitTypeInsn(CHECKCAST, refs.craftEntity.internalName());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.craftEntity.internalName(),
                "getHandle", "()" + refs.entity.desc(), false);

        mv.visitInsn(FCONST_0);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.entityPositionSource.internalName(),
                "<init>", "(" + refs.entity.desc() + "F)V", false);
        // EntityPositionSource end

        mv.visitTypeInsn(CHECKCAST, refs.positionSource.internalName());

        // ticks
        mv.visitVarInsn(ILOAD, local_ticks);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vibrationParticleOption.internalName(),
                "<init>", "(" + refs.positionSource.desc() + "I)V", false);
        // VibrationParticleOption end

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME,
                "(" + refs.particleParam_1_17.desc() + ")V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
