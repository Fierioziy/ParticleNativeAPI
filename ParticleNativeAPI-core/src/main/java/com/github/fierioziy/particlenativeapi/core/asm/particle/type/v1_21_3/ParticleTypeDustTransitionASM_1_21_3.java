package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeDustTransitionASM_1_21_3 extends ParticleTypeComplexSkeletonASM_1_17 {

    public ParticleTypeDustTransitionASM_1_21_3(ContextASM context,
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
                "(IIIIIID)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_r = 1;
        int local_g = 2;
        int local_b = 3;
        int local_tr = 4;
        int local_tg = 5;
        int local_tb = 6;
        int local_size = 7;
        int local_particleType = 9;

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
            new DustColorTransitionOptions(
            0xFF000000 | r << 16 | g << 8 | b,
            0xFF000000 | tr << 16 | tg << 8 | tb,
            (float) size)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // new DustColorTransitionOptions(...);
        mv.visitTypeInsn(NEW, refs.dustColorTransitionOptions.internalName());
        mv.visitInsn(DUP);

        // 0xFF000000
        mv.visitLdcInsn(0xFF000000);

        // ... | r << 16
        mv.visitVarInsn(ILOAD, local_r);
        mv.visitIntInsn(BIPUSH, 16);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // ... | g << 8
        mv.visitVarInsn(ILOAD, local_g);
        mv.visitIntInsn(BIPUSH, 8);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // .. | b,
        mv.visitVarInsn(ILOAD, local_b);
        mv.visitInsn(IOR);

        // 0xFF000000
        mv.visitLdcInsn(0xFF000000);

        // ... | tr << 16
        mv.visitVarInsn(ILOAD, local_tr);
        mv.visitIntInsn(BIPUSH, 16);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // ... | tg << 8
        mv.visitVarInsn(ILOAD, local_tg);
        mv.visitIntInsn(BIPUSH, 8);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // .. | tb,
        mv.visitVarInsn(ILOAD, local_tb);
        mv.visitInsn(IOR);

        // (float) size
        mv.visitVarInsn(DLOAD, local_size);
        mv.visitInsn(D2F);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.dustColorTransitionOptions.internalName(),
                "<init>",
                "(IIF)V", false);

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
