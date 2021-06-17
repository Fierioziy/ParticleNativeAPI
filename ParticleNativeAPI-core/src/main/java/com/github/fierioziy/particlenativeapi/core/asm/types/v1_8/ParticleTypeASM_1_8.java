package com.github.fierioziy.particlenativeapi.core.asm.types.v1_8;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonExtend;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeASM_1_8 extends ClassSkeletonExtend {

    public ParticleTypeASM_1_8(InternalResolver resolver, Type superType) {
        super(resolver, superType);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_8");
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, "particle", descNMS("EnumParticle"), null, null).visitEnd();
        cw.visitField(ACC_PRIVATE, "data", "[I", null, null).visitEnd();
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + descNMS("EnumParticle") + "[I)V", null, null);
        mv.visitCode();

        /*
        Generates code that stores particle enum and data in field.
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
                descNMS("EnumParticle"));

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "data",
                "[I");

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_packet(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "packet",
                "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
        mv.visitCode();

        /*
        return new PacketPlayOutWorldParticles(particle, far,
                (float) x,          (float) y,          (float) z,
                (float) offsetX,    (float) offsetY,    (float) offsetZ,
                (float) speed, count, data);
         */
        mv.visitTypeInsn(NEW, internalNMS("PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("EnumParticle"));

        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(DLOAD, 2);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 4);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 6);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 8);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 10);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 12);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 14);mv.visitInsn(D2F);
        mv.visitVarInsn(ILOAD, 16);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "data",
                "[I");
        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("PacketPlayOutWorldParticles"),
                "<init>", "(" + descNMS("EnumParticle") + "ZFFFFFFFI[I)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void writeMethod_isValid(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "isValid", "()Z", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
