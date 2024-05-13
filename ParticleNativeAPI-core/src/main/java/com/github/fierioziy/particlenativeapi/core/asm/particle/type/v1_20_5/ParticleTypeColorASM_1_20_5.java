package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_20_5;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeColorASM_1_20_5 extends ParticleTypeComplexSkeletonASM_1_17 {

    private final String colorParticleOptionFactoryMethodName;

    public ParticleTypeColorASM_1_20_5(ContextASM context,
                                       ClassSkeleton superType, ClassSkeleton returnType,
                                       String colorParticleOptionFactoryMethodName) {
        super(context, superType, returnType);
        this.colorParticleOptionFactoryMethodName = colorParticleOptionFactoryMethodName;
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_color(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_color(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                COLOR_METHOD_NAME,
                "(III)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_red = 1;
        int local_green = 2;
        int local_blue = 3;
        int local_particleType = 4;

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
            ColorParticleOption.newByInt_obf(this.particle, 0xFF000000 | red << 16 | green << 8 | blue)
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // this.particle
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.particle_1_17.desc());

        // 0xFF000000 | red << 16
        mv.visitLdcInsn(0xFF000000);

        mv.visitVarInsn(ILOAD, local_red);
        mv.visitIntInsn(BIPUSH, 16);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // ... | green << 8
        mv.visitVarInsn(ILOAD, local_green);
        mv.visitIntInsn(BIPUSH, 8);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // .. | blue
        mv.visitVarInsn(ILOAD, local_blue);
        mv.visitInsn(IOR);

        mv.visitMethodInsn(INVOKESTATIC,
                refs.colorParticleOption.internalName(),
                colorParticleOptionFactoryMethodName,
                "(" + refs.particle_1_17.desc() + "I)" + refs.colorParticleOption.desc(), false);

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
