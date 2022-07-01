package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeASM_1_17 extends ClassSkeletonASM {

    public ParticleTypeASM_1_17(InternalResolver resolver, String suffix, ClassMapping superType) {
        super(resolver, suffix, superType);
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, refs.particleParam_1_17);
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, refs.particleParam_1_17);
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
                x,                  y,                  z,
                (float) offsetX,    (float) offsetY,    (float) offsetZ,
                (float) speed, count);
         */
        mv.visitTypeInsn(NEW, refs.packetPlayOutWorldParticles_1_17.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                "particle",
                refs.particleParam_1_17.desc());

        mv.visitVarInsn(ILOAD, local_far);
        mv.visitVarInsn(DLOAD, local_x);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitVarInsn(DLOAD, local_z);
        mv.visitVarInsn(DLOAD, local_offsetX);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetY);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_offsetZ);mv.visitInsn(D2F);
        mv.visitVarInsn(DLOAD, local_speed);mv.visitInsn(D2F);
        mv.visitVarInsn(ILOAD, local_count);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.packetPlayOutWorldParticles_1_17.internalName(),
                "<init>", "(" + refs.particleParam_1_17.desc() + "ZDDDFFFFI)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void writeFields(ClassWriter cw, ClassMapping fieldType) {
        cw.visitField(ACC_PRIVATE, "particle", fieldType.desc(), null, null).visitEnd();
    }

    protected void writeConstructor(ClassWriter cw, ClassMapping fieldType) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + fieldType.desc() + ")V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_particle = 1;

        /*
        Generates code that stores particle object type in field.
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_particle);
        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                "particle",
                fieldType.desc());
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
