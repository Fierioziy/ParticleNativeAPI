package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeBlockASM_1_17 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeBlockASM_1_17(InternalResolver resolver, String suffix,
                                     ClassSkeleton superType, ClassSkeleton returnType) {
        super(resolver, suffix, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_of(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                OF_METHOD_NAME,
                "(" + refs.material.desc() + "B)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_meta = 2;
        int local_particleType = 3;

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
            new ParticleParamBlock(
                particle,
                ((CraftBlockData) material.createBlockData()).getState()
            )
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        mv.visitTypeInsn(NEW, refs.particleParamBlock_1_17.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.particle_1_17.desc());


        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.material.internalName(),
                "createBlockData",
                "()" + refs.blockData.desc(), false);

        mv.visitTypeInsn(CHECKCAST, refs.craftBlockData.internalName());
        mv.visitMethodInsn(INVOKEVIRTUAL,
                refs.craftBlockData.internalName(),
                "getState",
                "()" + refs.iBlockData_1_17.desc(), false);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamBlock_1_17.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + refs.iBlockData_1_17.desc() + ")V", false);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME,
                "(" + refs.particleParam_1_17.desc() + ")V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
