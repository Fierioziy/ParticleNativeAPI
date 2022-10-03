package com.github.fierioziy.particlenativeapi.core.asm.packet.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticlePacketASM_1_7 extends ClassSkeletonASM {

    public ParticlePacketASM_1_7(InternalResolver resolver, String suffix,
                                 ClassSkeleton skeleton) {
        super(resolver, suffix, skeleton);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PACKET_FIELD_NAME, refs.packet_1_7.desc(), null, null);
    }

    @Override
    protected void writeConstructors(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>",
                "()V", null, null);
        mv.visitCode();

        int local_this = 0;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_sendPacket_Player_Object(cw);
        writeMethod_setPacket_Packet_double_double_double(cw);
    }

    private void writeMethod_sendPacket_Player_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SEND_TO_METHOD_NAME,
                "(Lorg/bukkit/entity/Player;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_player = 1;

        /*
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket(packet);
         */
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitTypeInsn(CHECKCAST, refs.craftPlayer.internalName());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.craftPlayer.internalName(),
                "getHandle",
                "()" + refs.entityPlayer_1_7.desc(), false);

        mv.visitFieldInsn(GETFIELD,
                refs.entityPlayer_1_7.internalName(),
                "playerConnection",
                refs.playerConnection_1_7.desc());

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PACKET_FIELD_NAME,
                refs.packet_1_7.desc());

        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.playerConnection_1_7.internalName(),
                "sendPacket",
                "(" + refs.packet_1_7.desc() + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_setPacket_Packet_double_double_double(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SET_PACKET_METHOD_NAME,
                "(" + refs.packet_1_7.desc() + "DDD)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_packet = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;

        // this.packet = packet;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PACKET_FIELD_NAME,
                refs.packet_1_7.desc());

        // this.x = x;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(DLOAD, local_x);
        mv.visitFieldInsn(PUTFIELD, implType.internalName(), "x", "D");

        // this.y = y;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitFieldInsn(PUTFIELD, implType.internalName(), "y", "D");

        // this.z = z;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(DLOAD, local_z);
        mv.visitFieldInsn(PUTFIELD, implType.internalName(), "z", "D");

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
