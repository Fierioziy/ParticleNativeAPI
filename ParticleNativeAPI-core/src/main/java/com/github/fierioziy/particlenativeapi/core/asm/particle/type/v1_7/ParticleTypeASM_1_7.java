package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.skeleton.ParticleTypeSkeletonASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeASM_1_7 extends ParticleTypeSkeletonASM_1_7 {

    public ParticleTypeASM_1_7(InternalResolver internal, String suffix,
                               ClassSkeleton superType,
                               ClassMapping particlePacketImpl_X) {
        super(internal, suffix, superType, particlePacketImpl_X);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_packet(cw);

        writeCommonMethods(cw);
    }

    protected void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                PACKET_METHOD_NAME,
                "(ZDDDDDDDI)" + refs.particlePacket.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_far = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;
        int local_offsetX = 8;
        int local_offsetY = 10;
        int local_offsetZ = 12;
        int local_speed = 14;
        int local_count = 16;
        int local_packet = 17;

        /*
        ParticlePacketImpl_X packet = this.packetWrapper;
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PACKET_WRAPPER_FIELD_NAME,
                particlePacketImpl_X.desc());
        mv.visitVarInsn(ASTORE, local_packet);

        /*
        packetWrapper.setPacket(
            new PacketPlayOutWorldParticles(particle,
                (float) x,       (float) y,       (float) z,
                (float) offsetX, (float) offsetY, (float) offsetZ,
                (float) speed, count),
                x, y, z
            )
        );
         */
        mv.visitVarInsn(ALOAD, local_packet);

        mv.visitTypeInsn(NEW, refs.packetPlayOutWorldParticles_1_7.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                "Ljava/lang/String;");

        mv.visitVarInsn(DLOAD, local_x);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_y);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_z);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetX);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetY);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetZ);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_speed);mv.visitInsn(D2F);
        mv.visitVarInsn(ILOAD, local_count);
        mv.visitMethodInsn(INVOKESPECIAL,
                refs.packetPlayOutWorldParticles_1_7.internalName(),
                "<init>", "(Ljava/lang/String;FFFFFFFI)V", false);

        mv.visitVarInsn(DLOAD, local_x);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitVarInsn(DLOAD, local_z);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                particlePacketImpl_X.internalName(),
                SET_PACKET_METHOD_NAME,
                "(" + refs.packet_1_7.desc() + "DDD)V", false);

        // return packetWrapper;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
