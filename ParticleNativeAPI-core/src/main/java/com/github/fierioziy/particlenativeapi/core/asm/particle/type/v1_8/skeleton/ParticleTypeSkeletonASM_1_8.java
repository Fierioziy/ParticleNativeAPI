package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton.ParticleTypeSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeSkeletonASM_1_8 extends ParticleTypeSkeletonASM {

    public ParticleTypeSkeletonASM_1_8(InternalResolver internal, String suffix,
                                       ClassSkeleton superType,
                                       ClassMapping particlePacketImpl_X) {
        super(internal, suffix, superType, particlePacketImpl_X);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PARTICLE_FIELD_NAME, refs.enumParticle.desc(), null, null).visitEnd();
        cw.visitField(ACC_PRIVATE, PARTICLE_DATA_FIELD_NAME, "[I", null, null).visitEnd();

        writePacketField(cw);
    }

    @Override
    protected void writeConstructors(ClassWriter cw) {
        writeConstructor(cw);
        writeConstructor_EnumParticle_IntArray(cw);
    }

    private void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "()V",
                null, null);
        mv.visitCode();

        int local_this = 0;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitInsn(ACONST_NULL);
        mv.visitInsn(ACONST_NULL);
        mv.visitMethodInsn(INVOKESPECIAL,
                implType.internalName(),
                "<init>", "(" + refs.enumParticle.desc() + "[I)V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeConstructor_EnumParticle_IntArray(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "(" + refs.enumParticle.desc() + "[I)V",
                null, null);
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
                SET_PARTICLE_METHOD_NAME, "(" + refs.enumParticle.desc() + "[I)V",
                null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;
        int local_arr = 2;

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

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
