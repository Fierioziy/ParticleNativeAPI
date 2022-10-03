package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeShriekASM_1_19 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeShriekASM_1_19(InternalResolver resolver, String suffix,
                                      ClassSkeleton superType, ClassSkeleton returnType) {
        super(resolver, suffix, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_delay(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_delay(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                DELAY_METHOD_NAME,
                "(I)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_delay = 1;
        int local_particleType = 2;

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
            new ShriekParticleOption(delay)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // ShriekParticleOption start
        mv.visitTypeInsn(NEW, refs.shriekParticleOption.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ILOAD, local_delay);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.shriekParticleOption.internalName(),
                "<init>", "(I)V", false);
        // ShriekParticleOption end

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
