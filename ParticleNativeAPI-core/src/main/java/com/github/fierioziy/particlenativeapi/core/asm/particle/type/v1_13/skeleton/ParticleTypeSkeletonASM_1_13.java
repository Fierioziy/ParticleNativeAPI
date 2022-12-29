package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton.ParticleTypeSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeSkeletonASM_1_13 extends ParticleTypeSkeletonASM {

    public ParticleTypeSkeletonASM_1_13(ContextASM context,
                                        ClassSkeleton superType) {
        super(context, superType);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PARTICLE_FIELD_NAME, refs.particleParam_1_7.desc(), null, null).visitEnd();

        writePacketField(cw);
    }

    @Override
    protected void writeConstructors(ClassWriter cw) {
        writeConstructor(cw);
        writeConstructor_ParticleParam(cw);
    }

    private void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "()V",
                null, null);
        mv.visitCode();

        int local_this = 0;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitInsn(ACONST_NULL);
        mv.visitMethodInsn(INVOKESPECIAL,
                implType.internalName(),
                "<init>", "(" + refs.particleParam_1_7.desc() + ")V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeConstructor_ParticleParam(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "(" + refs.particleParam_1_7.desc() + ")V",
                null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;

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
                refs.particleParam_1_7.desc());

        // this.packet = new ParticlePacketImpl_X();
        writePacketAssignment(mv);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeCommonMethods(ClassWriter cw) {
        super.writeCommonMethods(cw);

        writeMethod_setParticle(cw);
    }

    private void writeMethod_setParticle(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SET_PARTICLE_METHOD_NAME, "(" + refs.particleParam_1_7.desc() + ")V",
                null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;

        // this.particle = particle;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_particle);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.particleParam_1_7.desc());

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
