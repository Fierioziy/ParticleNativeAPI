package com.github.fierioziy.asm;

import com.github.fierioziy.api.PlayerConnection;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * <p>Class responsible for providing bytecode
 * of <code>PlayerConnectionArray</code> class.</p>
 */
public class PlayerConnectionArrayASM extends ConnectionBaseASM {

    public PlayerConnectionArrayASM(String version) {
        super(version);
    }

    /**
     * <p>Creates a bytecode of class implementing <code>PlayerConnectionArray</code>
     * interface.
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>PlayerConnectionArray</code> interface.
     * @see PlayerConnection
     */
    public byte[] generatePlayerConnectionArrayCode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                playerConnArrTypeImpl.getInternalName(), null,
                "java/lang/Object", new String[] { playerConnArrType.getInternalName() });

        {
            FieldVisitor fv = cw.visitField(ACC_PRIVATE,
                    "playerConnectionArr",
                    "[" + desc(NMS + "/PlayerConnection"),
                    null, null);
            fv.visitEnd();
        }

        /*
        Generates constructor that extracts NMS PlayerConnection from each player
        and stores it in array field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "<init>",
                    "(Ljava/util/Collection;)V",
                    "(Ljava/util/Collection<Lorg/bukkit/entity/Player;>;)V", null);
            mv.visitCode();

            // super();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/Object",
                    "<init>",
                    "()V", false);

            visitUpdateCode(mv);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Method that casts provided object to Packet and send it
        using NMS PlayerConnection objects from field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "sendPacket",
                    "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();

            // Packet nmsPacket = (Packet) packet;
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, NMS + "/Packet");
            mv.visitVarInsn(ASTORE, 2);

            // PlayerConnection[] pcs = this.playerConnectionArr;
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    playerConnArrTypeImpl.getInternalName(),
                    "playerConnectionArr",
                    "[" + desc(NMS + "/PlayerConnection"));
            mv.visitVarInsn(ASTORE, 3);

            // int length = pcs.length;
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInsn(ARRAYLENGTH);
            mv.visitVarInsn(ISTORE, 4);

            // while (
            Label loopBegin = new Label();
            Label loopEnd = new Label();

            // length > 0) {
            mv.visitLabel(loopBegin);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitJumpInsn(IFLE, loopEnd);

            // pcs[--length]
            mv.visitVarInsn(ALOAD, 3);
            mv.visitIincInsn(4, -1);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitInsn(AALOAD);

            // .sendPacket(nmsPacket);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    NMS + "/PlayerConnection",
                    "sendPacket",
                    "(" + desc(NMS + "/Packet") + ")V", false);

            // }
            mv.visitJumpInsn(GOTO, loopBegin);
            mv.visitLabel(loopEnd);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that extracts NMS PlayerConnection from each player
        and stores it in array field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "update",
                    "(Ljava/util/Collection;)V",
                    "(Ljava/util/Collection<Lorg/bukkit/entity/Player;>;)V", null);
            mv.visitCode();

            visitUpdateCode(mv);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }

    /**
     * <p>Visits code responsible for filling under lying array
     * with NMS <code>PlayerConnection</code> objects.</p>
     *
     * @param mv a MethodVisitor to which visiting shout occur.
     */
    private void visitUpdateCode(MethodVisitor mv) {
        // Iterator<Player> it = players.iterator();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "iterator",
                "()Ljava/util/Iterator;", true);
        mv.visitVarInsn(ASTORE, 2);

        // int length = players.size();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Collection",
                "size",
                "()I", true);
        mv.visitVarInsn(ISTORE, 3);

        // PlayerConnection[] pcs = new PlayerConnection[length];
        mv.visitVarInsn(ILOAD, 3);
        mv.visitTypeInsn(ANEWARRAY, NMS + "/PlayerConnection");
        mv.visitVarInsn(ASTORE, 4);

        // while (
        Label loopBegin = new Label();
        Label loopEnd = new Label();

        // length > 0) {
        mv.visitLabel(loopBegin);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitJumpInsn(IFLE, loopEnd);

        // pcs[--length] =
        mv.visitVarInsn(ALOAD, 4);
        mv.visitIincInsn(3, -1);
        mv.visitVarInsn(ILOAD, 3);

        // ((CraftPlayer) it.next())
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEINTERFACE,
                "java/util/Iterator",
                "next",
                "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(CHECKCAST, OBC + "/entity/CraftPlayer");

        // .getHandle()
        mv.visitMethodInsn(INVOKEVIRTUAL,
                OBC + "/entity/CraftPlayer",
                "getHandle",
                "()" + desc(NMS + "/EntityPlayer"), false);

        // .playerConnection
        mv.visitFieldInsn(GETFIELD,
                NMS + "/EntityPlayer",
                "playerConnection",
                desc(NMS + "/PlayerConnection"));

        // ;
        mv.visitInsn(AASTORE);

        //}
        mv.visitJumpInsn(GOTO, loopBegin);
        mv.visitLabel(loopEnd);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 4);
        mv.visitFieldInsn(PUTFIELD,
                playerConnArrTypeImpl.getInternalName(),
                "playerConnectionArr",
                "[" + desc(NMS + "/PlayerConnection"));
    }

}
