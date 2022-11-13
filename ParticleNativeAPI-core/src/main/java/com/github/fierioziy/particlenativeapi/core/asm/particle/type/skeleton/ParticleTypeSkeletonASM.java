package com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeSkeletonASM extends ClassSkeletonASM {

    protected final ClassMapping particlePacketImpl_X;
    protected final boolean shouldGenerateBridge;

    public ParticleTypeSkeletonASM(InternalResolver internal, String suffix,
                                   ClassSkeleton superType,
                                   ClassMapping particlePacketImpl_X) {
        super(internal, suffix, superType);

        this.particlePacketImpl_X = particlePacketImpl_X;
        this.shouldGenerateBridge = superType != ClassSkeleton.PARTICLE_TYPE;
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
        if (shouldGenerateBridge) {
            writeBridgeMethod_detachCopy(cw);
        }

        writeOverridingMethod_detachCopy(cw);
        writeMethod_isPresent(cw);
    }

    private void writeBridgeMethod_detachCopy(ClassWriter cw) {
        // important: mark as synthetic, so JVM won't complain about almost same signature
        // normally this wouldn't be allowed, but this is how covariance is handled
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_SYNTHETIC,
                DETACH_COPY_METHOD_NAME,
                "()" + refs.particleType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;

        // return this.detachCopy() but with bridged returned type
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                implType.internalName(),
                DETACH_COPY_METHOD_NAME,
                "()" + interfaceType.desc(), false);

        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeOverridingMethod_detachCopy(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                DETACH_COPY_METHOD_NAME,
                "()" + interfaceType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particleType = 1;

        // ParticleTypeImpl_X particleType = (ParticleTypeImpl_X) super.detachCopy();
        // it may be other extending types as well
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                DETACH_COPY_METHOD_NAME,
                "()" + interfaceType.desc(), false);
        mv.visitTypeInsn(CHECKCAST, implType.internalName());
        mv.visitVarInsn(ASTORE, local_particleType);

        // we have to do this, because particle type copies shouldn't share mutable packet wrapper
        // particleType.packet = (ParticlePacketImpl_X) this.packet.detachCopy();
        mv.visitVarInsn(ALOAD, local_particleType);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PACKET_WRAPPER_FIELD_NAME,
                particlePacketImpl_X.desc());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                particlePacketImpl_X.internalName(),
                DETACH_COPY_METHOD_NAME,
                "()" + refs.particlePacket.desc(), false);
        mv.visitTypeInsn(CHECKCAST, particlePacketImpl_X.internalName());

        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PACKET_WRAPPER_FIELD_NAME,
                particlePacketImpl_X.desc());

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
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
