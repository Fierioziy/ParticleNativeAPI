package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustTransitionASM_1_17 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeDustTransitionASM_1_17(ContextASM context,
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
                "(FFFFFFF)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_r = 1;
        int local_g = 2;
        int local_b = 3;
        int local_tr = 4;
        int local_tg = 5;
        int local_tb = 6;
        int local_size = 7;
        int local_particleType = 8;

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
            new DustColorTransitionOptions(new Vector3fa(r, g, b), new Vector3fa(tr, tg, tb), size)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // new DustColorTransitionOptions(...);
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
