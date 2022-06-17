package com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonImplement;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class PlayerConnectionASM_1_17 extends ClassSkeletonImplement {

    protected String playerConnectionFieldName;
    protected String sendPacketMethodName;

    public PlayerConnectionASM_1_17(InternalResolver resolver,
                                    String suffix,
                                    String playerConnectionFieldName,
                                    String sendPacketMethodName) {
        super(resolver, playerConnType, suffix);
        this.playerConnectionFieldName = playerConnectionFieldName;
        this.sendPacketMethodName = sendPacketMethodName;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        FieldVisitor fv = cw.visitField(ACC_PRIVATE,
                "playerConnection",
                descNMS("server/network/PlayerConnection"),
                null, null);
        fv.visitEnd();
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>",
                "(Lorg/bukkit/entity/Player;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_player = 1;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        // playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_player);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("server/level/EntityPlayer"), false);

        mv.visitFieldInsn(GETFIELD,
                internalNMS("server/level/EntityPlayer"),
                playerConnectionFieldName,
                descNMS("server/network/PlayerConnection"));
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "playerConnection",
                descNMS("server/network/PlayerConnection"));
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_sendPacket_Object(cw);
    }

    private void writeMethod_sendPacket_Object(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_packet = 1;

        // playerConnection.sendPacket((Packet) packet);
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "playerConnection",
                descNMS("server/network/PlayerConnection"));

        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, internalNMS("network/protocol/Packet"));

        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("server/network/PlayerConnection"),
                sendPacketMethodName,
                "(" + descNMS("network/protocol/Packet") + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}