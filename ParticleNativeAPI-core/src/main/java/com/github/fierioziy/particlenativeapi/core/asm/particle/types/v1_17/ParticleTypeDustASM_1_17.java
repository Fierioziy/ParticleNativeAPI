package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustASM_1_17 extends ParticleTypeASM_1_17 {

    private final ClassMapping implReturnType;
    private final ClassMapping returnType;

    public ParticleTypeDustASM_1_17(InternalResolver resolver, String suffix, ClassMapping superType, ClassMapping returnType) {
        super(resolver, suffix, superType);
        this.implReturnType = returnType.impl(suffix);
        this.returnType = returnType;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, refs.particle_1_17);
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, refs.particle_1_17);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_color(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_color(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "color",
                "(FFFF)" + returnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_red = 1;
        int local_green = 2;
        int local_blue = 3;
        int local_size = 4;

        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);

        // new ParticleParamRedstone(new Vector3fa(red, green, blue), size);
        mv.visitTypeInsn(NEW, refs.particleParamRedstone_1_17.internalName());
        mv.visitInsn(DUP);

        mv.visitTypeInsn(NEW, refs.vector3fa.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_red);
        mv.visitVarInsn(FLOAD, local_green);
        mv.visitVarInsn(FLOAD, local_blue);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vector3fa.internalName(),
                "<init>",
                "(FFF)V", false);

        mv.visitVarInsn(FLOAD, local_size);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamRedstone_1_17.internalName(),
                "<init>",
                "(" + refs.vector3fa.desc() + "F)V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>",
                "(" + refs.particleParam_1_17.desc() + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
