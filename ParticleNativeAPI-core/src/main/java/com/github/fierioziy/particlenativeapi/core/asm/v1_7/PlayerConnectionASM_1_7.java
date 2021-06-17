package com.github.fierioziy.particlenativeapi.core.asm.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonImplement;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * <p>Class responsible for providing bytecode
 * of <code>PlayerConnection</code> class.</p>
 */
public class PlayerConnectionASM_1_7 extends ClassSkeletonImplement {

    public PlayerConnectionASM_1_7(InternalResolver resolver) {
        super(resolver, playerConnType);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_7");
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        FieldVisitor fv = cw.visitField(ACC_PRIVATE,
                "playerConnection",
                descNMS("PlayerConnection"),
                null, null);
        fv.visitEnd();
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>",
                "(Lorg/bukkit/entity/Player;)V", null, null);
        mv.visitCode();

        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        // playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitTypeInsn(CHECKCAST, internalOBC("entity/CraftPlayer"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("entity/CraftPlayer"),
                "getHandle",
                "()" + descNMS("EntityPlayer"), false);

        mv.visitFieldInsn(GETFIELD,
                internalNMS("EntityPlayer"),
                "playerConnection",
                descNMS("PlayerConnection"));
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "playerConnection",
                descNMS("PlayerConnection"));
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

        // playerConnection.sendPacket((Packet) packet);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "playerConnection",
                descNMS("PlayerConnection"));

        mv.visitVarInsn(ALOAD, 1);
        mv.visitTypeInsn(CHECKCAST, internalNMS("Packet"));

        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalNMS("PlayerConnection"),
                "sendPacket",
                "(" + descNMS("Packet") + ")V", false);
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
