package com.github.fierioziy.particlenativeapi.core.asm.types.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.types.ParticleTypeASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeASM_1_7 extends ParticleTypeASM {

    public ParticleTypeASM_1_7(InternalResolver internal, Type superType) {
        super(internal, superType);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_7");
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, "particle", "Ljava/lang/String;", null, null).visitEnd();
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();

        /*
        Generates code that stores particle name in field.
         */
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "particle",
                "Ljava/lang/String;");
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void writeMethods(ClassWriter cw) {
        writeMethod_packet(cw);
    }

    /**
     * <p>Generates method that instantiates particle packet object
     * with parameters passed to a method.</p>
     *
     * <p>Uses particle type stored in a field.</p>
     *
     * @param cw a ClassWriter to which write a packet method.
     */
    protected void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "packet",
                "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
        mv.visitCode();

        /*
        return new PacketPlayOutWorldParticles(particle,
                (float) x,          (float) y,          (float) z,
                (float) offsetX,    (float) offsetY,    (float) offsetZ,
                (float) speed, count);
         */
        mv.visitTypeInsn(NEW, internalNMS("PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                "Ljava/lang/String;");
        mv.visitVarInsn(DLOAD, 2);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 4);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 6);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 8);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 10);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 12);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 14);mv.visitInsn(D2F);
        mv.visitVarInsn(ILOAD, 16);
        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("PacketPlayOutWorldParticles"),
                "<init>", "(Ljava/lang/String;FFFFFFFI)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
