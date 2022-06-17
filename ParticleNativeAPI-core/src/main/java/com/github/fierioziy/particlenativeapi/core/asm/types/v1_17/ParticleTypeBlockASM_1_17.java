package com.github.fierioziy.particlenativeapi.core.asm.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeBlockASM_1_17 extends ParticleTypeASM_1_17 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeBlockASM_1_17(InternalResolver resolver, String suffix, Type superType, Type returnType) {
        super(resolver, suffix, superType);
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
        writeMethod_of(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "of",
                "(Lorg/bukkit/Material;B)" + returnType.getDescriptor(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_meta = 2;

        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        /*
        new ParticleParamBlock(
                particle,
                ((CraftBlockData) material.createBlockData()).getState()
        );
         */
        mv.visitTypeInsn(NEW, internalNMS("core/particles/ParticleParamBlock"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("core/particles/Particle"));


        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "org/bukkit/Material",
                "createBlockData",
                "()Lorg/bukkit/block/data/BlockData;", false);

        mv.visitTypeInsn(CHECKCAST, internalOBC("block/data/CraftBlockData"));
        mv.visitMethodInsn(INVOKEVIRTUAL,
                internalOBC("block/data/CraftBlockData"),
                "getState",
                "()" + descNMS("world/level/block/state/IBlockData"), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/particles/ParticleParamBlock"),
                "<init>",
                "(" + descNMS("core/particles/Particle") + descNMS("world/level/block/state/IBlockData") + ")V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>",
                "(" + descNMS("core/particles/ParticleParam") + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
