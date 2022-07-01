package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeBlockASM_1_17 extends ParticleTypeASM_1_17 {

    private final ClassMapping implReturnType;
    private final ClassMapping returnType;

    public ParticleTypeBlockASM_1_17(InternalResolver resolver, String suffix, ClassMapping superType, ClassMapping returnType) {
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
        writeMethod_of(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "of",
                "(Lorg/bukkit/Material;B)" + returnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_meta = 2;

        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);

        /*
        new ParticleParamBlock(
                particle,
                ((CraftBlockData) material.createBlockData()).getState()
        );
         */
        mv.visitTypeInsn(NEW, refs.particleParamBlock_1_17.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                "particle",
                refs.particle_1_17.desc());


        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Material",
                "createBlockData",
                "()Lorg/bukkit/block/data/BlockData;", false);

        mv.visitTypeInsn(CHECKCAST, refs.craftBlockData.internalName());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.craftBlockData.internalName(),
                "getState",
                "()" + refs.iBlockData_1_17.desc(), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamBlock_1_17.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + refs.iBlockData_1_17.desc() + ")V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>",
                "(" + refs.particleParam_1_17.desc() + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
