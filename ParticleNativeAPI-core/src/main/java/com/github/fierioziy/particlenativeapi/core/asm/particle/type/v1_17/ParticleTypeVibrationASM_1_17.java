package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeSimpleSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeVibrationASM_1_17 extends ParticleTypeSimpleSkeletonASM_1_17 {

    public ParticleTypeVibrationASM_1_17(InternalResolver resolver, String suffix,
                                         ClassSkeleton superType,
                                         ClassMapping particlePacketImpl_X) {
        super(resolver, suffix, superType, particlePacketImpl_X);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_packet(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                PACKET_METHOD_NAME,
                "(ZDDDDDDI)" + refs.particlePacket.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_far = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;
        int local_targetX = 8;
        int local_targetY = 10;
        int local_targetZ = 12;
        int local_ticks = 14;
        int local_packet = 15;

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
            new PacketPlayOutWorldParticles(
                new VibrationParticleOption(
                    new VibrationPath(
                        new BlockPosition((int) x, (int) y, (int) z)),
                        new BlockPositionSource(
                            new BlockPosition((int) targetX, (int) targetY, (int) targetZ))
                        ),
                        ticks
                    ),
                ),
                far,
                0D, 0D, 0D,
                0F, 0F, 0F,
                0F, 1
            )
         );
         */
        mv.visitVarInsn(ALOAD, local_packet);

        mv.visitTypeInsn(NEW, refs.packetPlayOutWorldParticles_1_17.internalName());
        mv.visitInsn(DUP);

        // VibrationParticleOption start
        mv.visitTypeInsn(NEW, refs.vibrationParticleOption.internalName());
        mv.visitInsn(DUP);

        // VibrationPath start
        mv.visitTypeInsn(NEW, refs.vibrationPath.internalName());
        mv.visitInsn(DUP);

        // BlockPosition start
        mv.visitTypeInsn(NEW, refs.blockPosition.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, local_x);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_y);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_z);mv.visitInsn(D2I);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.blockPosition.internalName(),
                "<init>", "(III)V", false);
        // BlockPosition end

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
                refs.vibrationPath.internalName(),
                "<init>", "(" + refs.blockPosition.desc() + refs.positionSource.desc() + "I)V", false);
        // VibrationPath end

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vibrationParticleOption.internalName(),
                "<init>", "(" + refs.vibrationPath.desc() + ")V", false);
        // VibrationParticleOption end

        // far
        mv.visitVarInsn(ILOAD, local_far);

        mv.visitInsn(DCONST_0);
        mv.visitInsn(DCONST_0);
        mv.visitInsn(DCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(ICONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.packetPlayOutWorldParticles_1_17.internalName(),
                "<init>", "(" + refs.particleParam_1_17.desc() + "ZDDDFFFFI)V", false);

        mv.visitVarInsn(DLOAD, local_x);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitVarInsn(DLOAD, local_z);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                particlePacketImpl_X.internalName(),
                SET_PACKET_METHOD_NAME,
                "(" + refs.packet_1_17.desc() + "DDD)V", false);

        // return packet;
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
