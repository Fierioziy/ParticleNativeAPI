package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustTransitionASM_1_17 extends ParticleTypeASM_1_17 {

    private final ClassMapping implReturnType;
    private final ClassMapping returnType;

    public ParticleTypeDustTransitionASM_1_17(InternalResolver resolver, String suffix, ClassMapping superType, ClassMapping returnType) {
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
                "(FFFFFFF)" + returnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_r = 1;
        int local_g = 2;
        int local_b = 3;
        int local_tr = 4;
        int local_tg = 5;
        int local_tb = 6;
        int local_size = 7;

        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);

        // new DustColorTransitionOptions(new Vector3fa(r, g, b), new Vector3fa(tr, tg, tb), size);
        mv.visitTypeInsn(NEW, refs.dustColorTransitionOptions.internalName());
        mv.visitInsn(DUP);

        // new Vector3fa(r, g, b)
        mv.visitTypeInsn(NEW, refs.vector3fa.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_r);
        mv.visitVarInsn(FLOAD, local_g);
        mv.visitVarInsn(FLOAD, local_b);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vector3fa.internalName(),
                "<init>",
                "(FFF)V", false);

        // new Vector3fa(tr, tg, tb)
        mv.visitTypeInsn(NEW, refs.vector3fa.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_tr);
        mv.visitVarInsn(FLOAD, local_tg);
        mv.visitVarInsn(FLOAD, local_tb);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vector3fa.internalName(),
                "<init>",
                "(FFF)V", false);

        // size
        mv.visitVarInsn(FLOAD, local_size);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.dustColorTransitionOptions.internalName(),
                "<init>",
                "(" + refs.vector3fa.desc() + refs.vector3fa.desc() + "F)V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>",
                "(" + refs.particleParam_1_17.desc() + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
