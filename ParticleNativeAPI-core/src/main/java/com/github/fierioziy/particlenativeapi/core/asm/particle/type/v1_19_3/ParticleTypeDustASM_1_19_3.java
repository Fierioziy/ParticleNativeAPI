package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19_3;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustASM_1_19_3 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeDustASM_1_19_3(ContextASM context,
                                      ClassSkeleton superType, ClassSkeleton returnType) {
        super(context, superType, returnType);
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
            new ParticleParamRedstone(new Vector3f(red, green, blue), size)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        mv.visitTypeInsn(NEW, refs.particleParamRedstone_1_17.internalName());
        mv.visitInsn(DUP);

        mv.visitTypeInsn(NEW, refs.vector3f.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(FLOAD, local_red);
        mv.visitVarInsn(FLOAD, local_green);
        mv.visitVarInsn(FLOAD, local_blue);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.vector3f.internalName(),
                "<init>",
                "(FFF)V", false);

        mv.visitVarInsn(FLOAD, local_size);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.particleParamRedstone_1_17.internalName(),
                "<init>",
                "(" + refs.vector3f.desc() + "F)V", false);

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
