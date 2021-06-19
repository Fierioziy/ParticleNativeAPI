package com.github.fierioziy.particlenativeapi.core.asm.types.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonExtend;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeASM_1_13 extends ClassSkeletonExtend {

    public ParticleTypeASM_1_13(InternalResolver resolver, Type superType) {
        super(resolver, superType);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_13");
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, "ParticleParam");
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, "ParticleParam");
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

        int local_this = 0;
        int local_far = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;
        int local_offsetX = 8;
        int local_offsetY = 10;
        int local_offsetZ = 12;
        int local_speed = 14;
        int local_count = 16;

        /*
        return new PacketPlayOutWorldParticles(particle, far,
                (float) x,          (float) y,          (float) z,
                (float) offsetX,    (float) offsetY,    (float) offsetZ,
                (float) speed, count);
         */
        mv.visitTypeInsn(NEW, internalNMS("PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("ParticleParam"));

        mv.visitVarInsn(ILOAD, local_far);
        mv.visitVarInsn(DLOAD, local_x);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_y);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_z);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetX);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetY);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetZ);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_speed);mv.visitInsn(D2F);
        mv.visitVarInsn(ILOAD, local_count);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("PacketPlayOutWorldParticles"),
                "<init>", "(" + descNMS("ParticleParam") + "ZFFFFFFFI)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void writeFields(ClassWriter cw, String fieldType) {
        cw.visitField(ACC_PRIVATE, "particle", descNMS(fieldType), null, null).visitEnd();
    }

    protected void writeConstructor(ClassWriter cw, String fieldType) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + descNMS(fieldType) + ")V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;

        /*
        Generates code that stores particle object type in field.
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_particle);
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "particle",
                descNMS(fieldType));
        mv.visitInsn(RETURN);

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
