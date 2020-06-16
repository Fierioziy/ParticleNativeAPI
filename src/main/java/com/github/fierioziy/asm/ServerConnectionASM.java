package com.github.fierioziy.asm;

import com.github.fierioziy.api.ServerConnection;
import org.objectweb.asm.*;

/**
 * <p>Class responsible for providing bytecode of <code>ServerConnection</code>
 * class.</p>
 */
public class ServerConnectionASM extends ConnectionBaseASM {

    public ServerConnectionASM(String version) {
        super(version);
    }

    /**
     * <p>Creates a bytecode of class implementing <code>ServerConnection</code>
     * interface.
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>ServerConnection</code> interface.
     * @see ServerConnection
     */
    public byte[] generateServerConnectionCode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                serverConnTypeImpl.getInternalName(), null,
                "java/lang/Object", new String[] { serverConnType.getInternalName() });

        /*
        Generates default constructor.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "<init>",
                    "()V", null, null);
            mv.visitCode();

            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/Object",
                    "<init>",
                    "()V", false);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that instantiates PlayerConnection interface implementation.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "createPlayerConnection",
                    "(Lorg/bukkit/entity/Player;)" + playerConnType.getDescriptor(), null, null);
            mv.visitCode();

            // return new PlayerConnection_Impl(player);
            mv.visitTypeInsn(NEW, playerConnTypeImpl.getInternalName());
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESPECIAL,
                    playerConnTypeImpl.getInternalName(),
                    "<init>",
                    "(Lorg/bukkit/entity/Player;)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that extracts NMS PlayerConnection from Player and
        sends packet object (cast from Object to Packet is performed).
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "sendPacket",
                    "(Lorg/bukkit/entity/Player;Ljava/lang/Object;)V", null, null);
            mv.visitCode();

            /*
            ((CraftPlayer) player).getHandle().playerConnection
                    .sendPacket((Packet) packet);
             */
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

            mv.visitVarInsn(ALOAD, 2);
            mv.visitTypeInsn(CHECKCAST, NMS + "/Packet");

            mv.visitMethodInsn(INVOKEVIRTUAL,
                    NMS + "/PlayerConnection",
                    "sendPacket",
                    "(" + desc(NMS + "/Packet") + ")V", false);
            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that extracts NMS PlayerConnection from each Player and
        sends packet object (cast from Object to Packet is performed before loop).
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "sendPacket",
                    "(Ljava/util/Collection;Ljava/lang/Object;)V",
                    "(Ljava/util/Collection<Lorg/bukkit/entity/Player;>;Ljava/lang/Object;)V", null);
            mv.visitCode();

            // Packet nmsPacket = (Packet) packet;
            mv.visitVarInsn(ALOAD, 2);
            mv.visitTypeInsn(CHECKCAST, NMS + "/Packet");
            mv.visitVarInsn(ASTORE, 3);

            // int length = player.size();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Collection",
                    "size",
                    "()I", true);
            mv.visitVarInsn(ISTORE, 4);

            // Iterator it = players.iterator();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Collection",
                    "iterator",
                    "()Ljava/util/Iterator;", true);
            mv.visitVarInsn(ASTORE, 5);

            // while (
            Label loopBegin = new Label();
            Label loopEnd = new Label();

            // length > 0) {
            mv.visitLabel(loopBegin);
            mv.visitVarInsn(ILOAD, 4);
            mv.visitJumpInsn(IFLE, loopEnd);

            // ((CraftPlayer) it.next()).getHandle()
            mv.visitVarInsn(ALOAD, 5);
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Iterator",
                    "next",
                    "()Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, OBC + "/entity/CraftPlayer");
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    OBC + "/entity/CraftPlayer",
                    "getHandle",
                    "()" + desc(NMS + "/EntityPlayer"), false);

            // .playerConnection
            mv.visitFieldInsn(GETFIELD,
                    NMS + "/EntityPlayer",
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"));

            // .sendPacket(nmsPacket);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    NMS + "/PlayerConnection",
                    "sendPacket",
                    "(" + desc(NMS + "/Packet") + ")V", false);

            // --length;
            mv.visitIincInsn(4, -1);

            // }
            mv.visitJumpInsn(GOTO, loopBegin);
            mv.visitLabel(loopEnd);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that iterates over players
        from Location's world and checks, if player is within range.
        A NMS PlayerConnection is then extracted and used
        to send packet object (casted from Object to Packet before entering loop).
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "sendPacket",
                    "(Lorg/bukkit/Location;DLjava/lang/Object;)V", null, null);
            mv.visitCode();

            // radius *= radius;
            mv.visitVarInsn(DLOAD, 2);
            mv.visitVarInsn(DLOAD, 2);
            mv.visitInsn(DMUL);
            mv.visitVarInsn(DSTORE, 2);

            // Packet nmsPacket = (Packet) packet;
            mv.visitVarInsn(ALOAD, 4);
            mv.visitTypeInsn(CHECKCAST, NMS + "/Packet");
            mv.visitVarInsn(ASTORE, 5);

            // double x = loc.getX();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getX",
                    "()D", false);
            mv.visitVarInsn(DSTORE, 6);

            // double y = loc.getY();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getY",
                    "()D", false);
            mv.visitVarInsn(DSTORE, 8);

            // double z = loc.getZ();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getZ",
                    "()D", false);
            mv.visitVarInsn(DSTORE, 10);

            // 2x loc.getWorld().getPlayers()
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getWorld",
                    "()Lorg/bukkit/World;", false);
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "org/bukkit/World",
                    "getPlayers",
                    "()Ljava/util/List;", true);
            mv.visitInsn(DUP);

            // int length = players.size();
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/List",
                    "size",
                    "()I", true);
            mv.visitVarInsn(ISTORE, 12);

            // Iterator it = players.iterator();
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/List",
                    "iterator",
                    "()Ljava/util/Iterator;", true);
            mv.visitVarInsn(ASTORE, 13);

            // while )
            Label loopBegin = new Label();
            Label loopEnd = new Label();

            // length > 0) {
            mv.visitLabel(loopBegin);
            mv.visitVarInsn(ILOAD, 12);
            mv.visitJumpInsn(IFLE, loopEnd);

            // CraftPlayer p = (CraftPlayer) it.next();
            mv.visitVarInsn(ALOAD, 13);
            mv.visitMethodInsn(INVOKEINTERFACE,
                    "java/util/Iterator",
                    "next",
                    "()Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, OBC + "/entity/CraftPlayer");
            mv.visitVarInsn(ASTORE, 14);

            // Location pLoc = p.getLocation();
            mv.visitVarInsn(ALOAD, 14);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    OBC + "/entity/CraftPlayer",
                    "getLocation",
                    "()Lorg/bukkit/Location;", false);
            mv.visitVarInsn(ASTORE, 15);

            // radius if statement
            // (pLoc.getX() - x)^2
            mv.visitVarInsn(ALOAD, 15);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getX",
                    "()D", false);
            mv.visitVarInsn(DLOAD, 6);
            mv.visitInsn(DSUB);
            mv.visitInsn(DUP2);
            mv.visitInsn(DMUL);

            // + (pLoc.getY() - y)^2
            mv.visitVarInsn(ALOAD, 15);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getY",
                    "()D", false);
            mv.visitVarInsn(DLOAD, 8);
            mv.visitInsn(DSUB);
            mv.visitInsn(DUP2);
            mv.visitInsn(DMUL);

            mv.visitInsn(DADD);

            // + (pLoc.getZ() - z)^2)
            mv.visitVarInsn(ALOAD, 15);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Location",
                    "getZ",
                    "()D", false);
            mv.visitVarInsn(DLOAD, 10);
            mv.visitInsn(DSUB);
            mv.visitInsn(DUP2);
            mv.visitInsn(DMUL);

            mv.visitInsn(DADD);

            // if (distSquared <= radius) {
            mv.visitVarInsn(DLOAD, 2);
            Label notSendLabel = new Label();
            mv.visitInsn(DCMPL);
            mv.visitJumpInsn(IFGT, notSendLabel);

            // p.getHandle().playerConnection
            mv.visitVarInsn(ALOAD, 14);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    OBC + "/entity/CraftPlayer",
                    "getHandle",
                    "()" + desc(NMS + "/EntityPlayer"), false);
            mv.visitFieldInsn(GETFIELD,
                    NMS + "/EntityPlayer",
                    "playerConnection",
                    desc(NMS + "/PlayerConnection"));

            // .sendPacket(packet);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    NMS + "/PlayerConnection",
                    "sendPacket",
                    "(" + desc(NMS + "/Packet") + ")V", false);

            // }
            mv.visitLabel(notSendLabel);

            // --length;
            mv.visitIincInsn(12, -1);

            // }
            mv.visitJumpInsn(GOTO, loopBegin);
            mv.visitLabel(loopEnd);

            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();

        return cw.toByteArray();
    }

}
