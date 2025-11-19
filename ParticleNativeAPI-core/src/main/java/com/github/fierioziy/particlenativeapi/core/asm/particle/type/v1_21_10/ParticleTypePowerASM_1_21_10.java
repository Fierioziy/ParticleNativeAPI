package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_10;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypePowerASM_1_21_10 extends ParticleTypeComplexSkeletonASM_1_17 {

    private final String powerParticleOptionFactoryMethodName;

    public ParticleTypePowerASM_1_21_10(ContextASM context,
                                        ClassSkeleton superType, ClassSkeleton returnType,
                                        String powerParticleOptionFactoryMethodName) {
        super(context, superType, returnType);
        this.powerParticleOptionFactoryMethodName = powerParticleOptionFactoryMethodName;
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_power(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_power(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                POWER_METHOD_NAME,
                "(D)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_power = 1;
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
            PowerParticleOption.newByPower_obf
                this.particle,
                (float) power
            )
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // this.particle
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.particle_1_17.desc());

        // (float) power
        mv.visitVarInsn(DLOAD, local_power);mv.visitInsn(D2F);

        mv.visitMethodInsn(INVOKESTATIC,
                refs.powerParticleOption.internalName(),
                powerParticleOptionFactoryMethodName,
                "(" + refs.particle_1_17.desc() + "F)" + refs.powerParticleOption.desc(), false);

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
