package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.skeleton.ParticleTypeComplexSkeletonASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeItemASM_1_13 extends ParticleTypeComplexSkeletonASM_1_13 {

    public ParticleTypeItemASM_1_13(ContextASM context,
                                    ClassSkeleton superType, ClassSkeleton returnType) {
        super(context, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_of(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                OF_METHOD_NAME,
                "(" + refs.material.desc() + ")" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_particleType = 2;

        /*
        ParticleTypeImpl_X particleType = this.particleWrapper;
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_WRAPPER_FIELD_NAME,
                implReturnType.desc());
        mv.visitVarInsn(ASTORE, local_particleType);

        /*
        particleType.setParticle(
            new ParticleParamItem(
                particle,
                CraftItemStack.asNMSCopy(new ItemStack(material, 1))
            )
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        mv.visitTypeInsn(NEW, refs.particleParamItem_1_7.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.particle_1_7.desc());

        mv.visitTypeInsn(NEW, refs.itemStackBukkit.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_material);
        mv.visitInsn(ICONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.itemStackBukkit.internalName(),
                "<init>",
                "(" + refs.material.desc() + "I)V", false);

        mv.visitMethodInsn(INVOKESTATIC,
                refs.craftItemStack.internalName(),
                "asNMSCopy",
                "(" + refs.itemStackBukkit.desc() + ")" + refs.itemStackNms_1_7.desc(), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamItem_1_7.internalName(),
                "<init>",
                "(" + refs.particle_1_7.desc() + refs.itemStackNms_1_7.desc() + ")V", false);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME, "(" + refs.particleParam_1_7.desc() + ")V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
