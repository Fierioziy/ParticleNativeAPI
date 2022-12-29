package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton.ParticleTypeComplexSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeComplexSkeletonASM_1_8 extends ParticleTypeComplexSkeletonASM {

    public ParticleTypeComplexSkeletonASM_1_8(ContextASM context,
                                              ClassSkeleton superType, ClassSkeleton returnType) {
        super(context, superType, returnType);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PARTICLE_FIELD_NAME, refs.enumParticle.desc(), null, null).visitEnd();
        cw.visitField(ACC_PRIVATE, PARTICLE_DATA_FIELD_NAME, "[I", null, null).visitEnd();

        writeParticleTypeField(cw);
    }

    @Override
    protected void writeConstructors(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + refs.enumParticle.desc() + "[I)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;
        int local_arr = 2;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);

        // this.particle = particle;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_particle);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.enumParticle.desc());

        // this.data = arr;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_arr);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PARTICLE_DATA_FIELD_NAME,
                "[I");

        // this.particleWrapper = new ParticleTypeImpl_X();
        writeParticleTypeAssignment(mv);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
