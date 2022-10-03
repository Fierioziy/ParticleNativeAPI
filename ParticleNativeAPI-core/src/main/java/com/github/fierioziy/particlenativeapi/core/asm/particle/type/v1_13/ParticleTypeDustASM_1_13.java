package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13;

import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.skeleton.ParticleTypeComplexSkeletonASM_1_13;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustASM_1_13 extends ParticleTypeComplexSkeletonASM_1_13 {


    public ParticleTypeDustASM_1_13(InternalResolver resolver, String suffix,
                                    ClassSkeleton superType, ClassSkeleton returnType) {
        super(resolver, suffix, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_color(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_color(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                COLOR_METHOD_NAME,
                "(FFFF)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_red = 1;
        int local_green = 2;
        int local_blue = 3;
        int local_size = 4;
        int local_particleType = 5;

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
            new ParticleParamRedstone(red, green, blue, size)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        mv.visitTypeInsn(NEW, refs.particleParamRedstone_1_7.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_red);
        mv.visitVarInsn(FLOAD, local_green);
        mv.visitVarInsn(FLOAD, local_blue);
        mv.visitVarInsn(FLOAD, local_size);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamRedstone_1_7.internalName(),
                "<init>",
                "(FFFF)V", false);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME,
                "(" + refs.particleParam_1_7.desc() + ")V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
