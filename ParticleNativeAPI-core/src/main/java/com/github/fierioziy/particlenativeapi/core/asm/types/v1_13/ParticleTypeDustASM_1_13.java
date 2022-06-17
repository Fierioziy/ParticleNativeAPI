package com.github.fierioziy.particlenativeapi.core.asm.types.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeDustASM_1_13 extends ParticleTypeASM_1_13 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeDustASM_1_13(InternalResolver resolver, String suffix, Type superType, Type returnType) {
        super(resolver, suffix, superType);
        this.implReturnType = getTypeImpl(returnType);
        this.returnType = returnType;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, "Particle");
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, "Particle");
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_color(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_color(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "color",
                "(FFFF)" + returnType.getDescriptor(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_red = 1;
        int local_green = 2;
        int local_blue = 3;
        int local_size = 4;

        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        // new ParticleParamRedstone(red, green, blue, size);
        mv.visitTypeInsn(NEW, internalNMS("ParticleParamRedstone"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_red);
        mv.visitVarInsn(FLOAD, local_green);
        mv.visitVarInsn(FLOAD, local_blue);
        mv.visitVarInsn(FLOAD, local_size);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("ParticleParamRedstone"),
                "<init>",
                "(FFFF)V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>",
                "(" + descNMS("ParticleParam") + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
