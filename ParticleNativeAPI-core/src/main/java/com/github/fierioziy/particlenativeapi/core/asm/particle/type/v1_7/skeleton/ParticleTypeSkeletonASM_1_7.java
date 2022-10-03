package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton.ParticleTypeSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeSkeletonASM_1_7 extends ParticleTypeSkeletonASM {

    public ParticleTypeSkeletonASM_1_7(InternalResolver internal, String suffix,
                                       ClassSkeleton superType,
                                       ClassMapping particlePacketImpl_X) {
        super(internal, suffix, superType, particlePacketImpl_X);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PARTICLE_FIELD_NAME, "Ljava/lang/String;", null, null).visitEnd();

        writePacketField(cw);
    }

    @Override
    protected void writeConstructors(ClassWriter cw) {
        writeConstructor(cw);
        writeConstructor_String(cw);
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
                "<init>", "(Ljava/lang/String;)V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeConstructor_String(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "(Ljava/lang/String;)V",
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
                "Ljava/lang/String;");

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

    private void writeMethod_setParticle(ClassWriter cw)
    {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SET_PARTICLE_METHOD_NAME, "(Ljava/lang/String;)V",
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
                "Ljava/lang/String;");

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
