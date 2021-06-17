package com.github.fierioziy.particlenativeapi.core.asm.types.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeRedstoneASM_1_13 extends ParticleTypeASM_1_13 {

    public ParticleTypeRedstoneASM_1_13(InternalResolver resolver, Type superType) {
        super(resolver, superType);
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + descNMS("Particle") + ")V", null, null);
        mv.visitCode();

        /*
        Generates code that stores default ParticleParamRedstone object in field.
        */
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        mv.visitVarInsn(ALOAD, 0);

        // new ParticleParamRedstone(1.0F, 0.0F, 0.0F, 1.0F);
        mv.visitTypeInsn(NEW, internalNMS("ParticleParamRedstone"));
        mv.visitInsn(DUP);

        mv.visitInsn(FCONST_1);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("ParticleParamRedstone"),
                "<init>",
                "(FFFF)V", false);

        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("ParticleParam"));
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_packet(cw);
        writeMethod_isValid(cw);
    }

    /*
    Generates method that instantiates particle packet object
    with parameters passed to a method.
    Uses ParticleParamRedstone object stored in a field
    or creates new one if count equals 0.
     */
    private void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "packet",
                "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
        mv.visitCode();

        Label zeroCountLabel = new Label();

        // if (count != 0) {
        mv.visitVarInsn(ILOAD, 16);
        mv.visitJumpInsn(IFEQ, zeroCountLabel);

        /*
        return new PacketPlayOutWorldParticles(particle, far,
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
                descNMS("ParticleParam"));

        mv.visitVarInsn(ILOAD, 1);
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
                "<init>", "(" + descNMS("ParticleParam") + "ZFFFFFFFI)V", false);
        mv.visitInsn(ARETURN);

        // }
        mv.visitLabel(zeroCountLabel);

        /*
        return new PacketPlayOutWorldParticles(
                new ParticleParamRedstone((float) offsetX, (float) offsetY, (float) offsetZ, 1.0F),
                far,
                x, y, z, 0.0F, 0.0F, 0.0F,
                0.0F, 1);
         */
        mv.visitTypeInsn(NEW, internalNMS("PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        // new ParticleParamRedstone(offsetX, offsetY, offsetZ, 1.0F);
        mv.visitTypeInsn(NEW, internalNMS("ParticleParamRedstone"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, 8);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 10);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 12);mv.visitInsn(D2F);
        mv.visitInsn(FCONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("ParticleParamRedstone"),
                "<init>",
                "(FFFF)V", false);

        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(DLOAD, 2);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 4);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, 6);mv.visitInsn(D2F);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(ICONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("PacketPlayOutWorldParticles"),
                "<init>", "(" + descNMS("ParticleParam") + "ZFFFFFFFI)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
