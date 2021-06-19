package com.github.fierioziy.particlenativeapi.core.asm.types.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeItemASM_1_13 extends ParticleTypeASM_1_13 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeItemASM_1_13(InternalResolver resolver, Type superType, Type returnType) {
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
                "(Lorg/bukkit/Material;)" + returnType.getDescriptor(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;

        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        /*
        new ParticleParamItem(
                particle,
                CraftItemStack.asNMSCopy(new ItemStack(material, 1))
        );
         */
        mv.visitTypeInsn(NEW, internalNMS("ParticleParamItem"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.getInternalName(),
                "particle",
                descNMS("Particle"));

        mv.visitTypeInsn(NEW, "org/bukkit/inventory/ItemStack");
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_material);
        mv.visitInsn(ICONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                "org/bukkit/inventory/ItemStack",
                "<init>",
                "(Lorg/bukkit/Material;I)V", false);

        mv.visitMethodInsn(INVOKESTATIC,
                internalOBC("inventory/CraftItemStack"),
                "asNMSCopy",
                "(Lorg/bukkit/inventory/ItemStack;)" + descNMS("ItemStack"), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("ParticleParamItem"),
                "<init>",
                "(" + descNMS("Particle") + descNMS("ItemStack") + ")V", false);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>", "(" + descNMS("ParticleParam") + ")V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
