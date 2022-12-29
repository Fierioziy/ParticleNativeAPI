package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeSculkChargeASM_1_19 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeSculkChargeASM_1_19(ContextASM context,
                                           ClassSkeleton superType, ClassSkeleton returnType) {
        super(context, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_roll(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_roll(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                ROLL_METHOD_NAME,
                "(D)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_roll = 1;
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
            new SculkChargeParticleOptions((float) roll)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // SculkChargeParticleOptions start
        mv.visitTypeInsn(NEW, refs.sculkChargeParticleOptions.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, local_roll);mv.visitInsn(D2F);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.sculkChargeParticleOptions.internalName(),
                "<init>", "(F)V", false);
        // SculkChargeParticleOptions end

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
