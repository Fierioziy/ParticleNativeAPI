package com.github.fierioziy.particlenativeapi.core.asm.types.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeBlockASM_1_13 extends ParticleTypeASM_1_13 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeBlockASM_1_13(InternalResolver resolver, Type superType, Type returnType) {
        super(resolver, superType);
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
        writeMethod_of(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "of",
                "(Lorg/bukkit/Material;B)" + returnType.getDescriptor(), null, null);
        mv.visitCode();

        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        /*
        new ParticleParamBlock(
                particle,
                ((CraftBlockData) material.createBlockData()).getState()
        );
         */
        mv.visitTypeInsn(NEW, internalNMS("ParticleParamBlock"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("Particle"));


        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Material",
                "createBlockData",
                "()Lorg/bukkit/block/data/BlockData;", false);

        mv.visitTypeInsn(CHECKCAST, internalOBC("block/data/CraftBlockData"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("block/data/CraftBlockData"),
                "getState",
                "()" + descNMS("IBlockData"), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("ParticleParamBlock"),
                "<init>",
                "(" + descNMS("Particle") + descNMS("IBlockData") + ")V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>",
                "(" + descNMS("ParticleParam") + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
