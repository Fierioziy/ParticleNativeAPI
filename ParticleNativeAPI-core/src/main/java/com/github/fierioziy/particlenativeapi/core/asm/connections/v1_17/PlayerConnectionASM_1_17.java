package com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public class PlayerConnectionASM_1_17 extends ClassSkeletonASM {

    protected String playerConnectionFieldName;
    protected String sendPacketMethodName;

    public PlayerConnectionASM_1_17(InternalResolver resolver,
                                    String suffix,
                                    String playerConnectionFieldName,
                                    String sendPacketMethodName) {
        super(resolver, suffix, resolver.refs.OBJECT, resolver.refs.playerConnType);
        this.playerConnectionFieldName = playerConnectionFieldName;
        this.sendPacketMethodName = sendPacketMethodName;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        FieldVisitor fv = cw.visitField(ACC_PRIVATE,
                "playerConnection",
                refs.playerConnection_1_17.desc(),
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
                superType.internalName(),
                "<init>",
                "()V", false);

        // playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        mv.visitVarInsn(ALOAD, local_this);
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
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                "playerConnection",
                refs.playerConnection_1_17.desc());
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
                implType.internalName(),
                "playerConnection",
                refs.playerConnection_1_17.desc());

        mv.visitVarInsn(ALOAD, local_packet);
        mv.visitTypeInsn(CHECKCAST, refs.packet_1_17.internalName());

        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.playerConnection_1_17.internalName(),
                sendPacketMethodName,
                "(" + refs.packet_1_17.desc() + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
