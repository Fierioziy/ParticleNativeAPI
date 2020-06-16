package com.github.fierioziy.asm;

import com.github.fierioziy.api.PlayerConnection;
import org.objectweb.asm.*;

/**
 * <p>Class responsible for providing bytecode
 * of <code>PlayerConnection</code> class.</p>
 */
public class PlayerConnectionASM extends ConnectionBaseASM {

    public PlayerConnectionASM(String version) {
        super(version);
    }

    /**
     * <p>Creates a bytecode of class implementing <code>PlayerConnection</code>
     * interface.
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>PlayerConnection</code> interface.
     * @see PlayerConnection
     */
    public byte[] generatePlayerConnectionCode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                playerConnTypeImpl.getInternalName(), null,
                "java/lang/Object", new String[] { playerConnType.getInternalName() });

        {
            FieldVisitor fv = cw.visitField(ACC_PRIVATE,
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"),
                    null, null);
            fv.visitEnd();
        }

        /*
        Generates constructor that extracts NMS PlayerConnection from player object
        and stores it in field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "<init>",
                    "(" + desc("org/bukkit/entity/Player") + ")V", null, null);
            mv.visitCode();

            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/Object",
                    "<init>",
                    "()V", false);

            // playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, OBC + "/entity/CraftPlayer");
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    OBC + "/entity/CraftPlayer",
                    "getHandle",
                    "()" + desc(NMS + "/EntityPlayer"), false);

            mv.visitFieldInsn(GETFIELD,
                    NMS + "/EntityPlayer",
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"));
            mv.visitFieldInsn(PUTFIELD,
                    playerConnTypeImpl.getInternalName(),
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"));
            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Method that casts provided object to Packet and send it
        using NMS PlayerConnection from field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();

            // playerConnection.sendPacket((Packet) packet);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    playerConnTypeImpl.getInternalName(),
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"));

            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, NMS + "/Packet");

            mv.visitMethodInsn(INVOKEVIRTUAL,
                    NMS + "/PlayerConnection",
                    "sendPacket",
                    "(" + desc(NMS + "/Packet") + ")V", false);
            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }

}
