package com.github.fierioziy.particlenativeapi.core.asm.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeDustASM_1_17 extends ParticleTypeASM_1_17 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeDustASM_1_17(InternalResolver resolver, Type superType, Type returnType) {
        super(resolver, superType);
        this.implReturnType = getTypeImpl(returnType);
        this.returnType = returnType;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, "core/particles/Particle");
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, "core/particles/Particle");
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

        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        // new ParticleParamRedstone(new Vector3fa(red, green, blue), size);
        mv.visitTypeInsn(NEW, internalNMS("core/particles/ParticleParamRedstone"));
        mv.visitInsn(DUP);

        mv.visitTypeInsn(NEW, internalOther("com/mojang/math/Vector3fa"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, 1);
        mv.visitVarInsn(FLOAD, 2);
        mv.visitVarInsn(FLOAD, 3);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalOther("com/mojang/math/Vector3fa"),
                "<init>",
                "(FFF)V", false);

        mv.visitVarInsn(FLOAD, 4);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/particles/ParticleParamRedstone"),
                "<init>",
                "(" + descOther("com/mojang/math/Vector3fa") + "F)V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>",
                "(" + descNMS("core/particles/ParticleParam") + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
