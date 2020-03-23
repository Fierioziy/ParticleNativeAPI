package me.fierioziy.asm;

import me.fierioziy.api.PlayerConnection;
import me.fierioziy.api.ServerConnection;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ServerConnectionASM extends BaseASM {

    /**
     * <p>A <code>Type</code> object representing <code>ServerConnection</code> class.</p>
     */
    private Type serverConnType =       Type.getType(ServerConnection.class);

    /**
     * <p>A <code>Type</code> object representing <code>PlayerConnection</code> class.</p>
     */
    private Type playerConnType =       Type.getType(PlayerConnection.class);

    /**
     * <p>A <code>Type</code> object representing implementation
     * of <code>ServerConnection</code> class.</p>
     */
    private Type serverConnTypeImpl =   getTypeImpl(serverConnType);

    /**
     * <p>A <code>Type</code> object representing implementation
     * of <code>PlayerConnection</code> class.</p>
     */
    private Type playerConnTypeImpl =   getTypeImpl(playerConnType);

    public ServerConnectionASM(String version) {
        super(version);
    }

    /**
     * <p>Gets a <code>Type</code> object representing an implementation
     * of class represented by parameter <code>Type object</code>.</p>
     *
     * @param superType a <code>Type</code> object associated
     *                  with base class.
     * @return a <code>Type</code> object representing class
     * extending or implementing parameter's class represented
     * by <code>Type</code> object.
     */
    private Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_Impl");
    }

    /**
     * <p>Creates a bytecode of class implementing <code>ServerConnection</code>
     * interface.
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>ServerConnection</code> interface.
     * @see ServerConnection
     */
    public byte[] createServerConnection() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                serverConnTypeImpl.getInternalName(), null,
                "java/lang/Object", new String[] { serverConnType.getInternalName() });

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

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "createPlayerConnection",
                    "(Lorg/bukkit/entity/Player;)" + playerConnType.getDescriptor(), null, null);
            mv.visitCode();

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

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "sendPacket",
                    "(Lorg/bukkit/entity/Player;Ljava/lang/Object;)V", null, null);
            mv.visitCode();

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

        cw.visitEnd();

        return cw.toByteArray();
    }

    /**
     * <p>Creates a bytecode of class implementing <code>PlayerConnection</code>
     * interface.
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>PlayerConnection</code> interface.
     * @see PlayerConnection
     */
    public byte[] createPlayerConnectionClass() {
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

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "sendPacket", "(Ljava/lang/Object;)V", null, null);
            mv.visitCode();

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
