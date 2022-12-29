package com.github.fierioziy.particlenativeapi.core.asm.packet.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticlePacketASM_1_17 extends ClassSkeletonASM {

    protected String playerConnectionFieldName;
    protected String sendPacketMethodName;

    public ParticlePacketASM_1_17(ContextASM context, ClassSkeleton skeleton,
                                  String playerConnectionFieldName,
                                  String sendPacketMethodName) {
        super(context, skeleton);

        this.playerConnectionFieldName = playerConnectionFieldName;
        this.sendPacketMethodName = sendPacketMethodName;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, PACKET_FIELD_NAME, refs.packet_1_17.desc(), null, null);
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
        writeMethod_sendTo_Player_Object(cw);
        writeMethod_setPacket_Packet(cw);
    }

    private void writeMethod_sendTo_Player_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SEND_TO_METHOD_NAME,
                "(Lorg/bukkit/entity/Player;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_player = 1;

        /*
        ((CraftPlayer) player).getHandle().playerConnection
                .sendPacket( packet);
         */
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitTypeInsn(CHECKCAST, refs.craftPlayer.internalName());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.craftPlayer.internalName(),
                "getHandle",
                "()" + refs.entityPlayer_1_17.desc(), false);

        mv.visitFieldInsn(GETFIELD,
                refs.entityPlayer_1_17.internalName(),
                playerConnectionFieldName,
                refs.playerConnection_1_17.desc());

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PACKET_FIELD_NAME,
                refs.packet_1_17.desc());

        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.playerConnection_1_17.internalName(),
                sendPacketMethodName,
                "(" + refs.packet_1_17.desc() + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void writeMethod_setPacket_Packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SET_PACKET_METHOD_NAME,
                "(" + refs.packet_1_17.desc() + "DDD)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_packet = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PACKET_FIELD_NAME,
                refs.packet_1_17.desc());

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
