package com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeSimpleSkeletonASM extends ClassSkeletonASM {

    protected final ClassMapping particlePacketImpl_X;

    public ParticleTypeSimpleSkeletonASM(InternalResolver internal, String suffix,
                                         ClassSkeleton superType,
                                         ClassMapping particlePacketImpl_X) {
        super(internal, suffix, superType);

        this.particlePacketImpl_X = particlePacketImpl_X;
    }

    protected void writePacketField(ClassWriter cw) {
        cw.visitField(0, PACKET_WRAPPER_FIELD_NAME, particlePacketImpl_X.desc(), null, null).visitEnd();
    }

    protected void writePacketAssignment(MethodVisitor mv) {
        int local_this = 0;

        // this.packet = new ParticlePacketImpl_X();
        mv.visitVarInsn(ALOAD, local_this);

        mv.visitTypeInsn(NEW, particlePacketImpl_X.internalName());
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL,
                particlePacketImpl_X.internalName(),
                "<init>",
                "()V", false);

        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PACKET_WRAPPER_FIELD_NAME,
                particlePacketImpl_X.desc());
    }

    protected void writeCommonMethods(ClassWriter cw) {
        writeMethod_isPresent(cw);
    }

    protected void writeMethod_isPresent(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, IS_PRESENT_METHOD_NAME, "()Z", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
