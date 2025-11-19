package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_10;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.skeleton.ParticleTypeComplexSkeletonASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeSpellASM_1_21_10 extends ParticleTypeComplexSkeletonASM_1_17 {

    private final String spellParticleOptionFactoryMethodName;

    public ParticleTypeSpellASM_1_21_10(ContextASM context,
                                        ClassSkeleton superType, ClassSkeleton returnType,
                                        String spellParticleOptionFactoryMethodName) {
        super(context, superType, returnType);
        this.spellParticleOptionFactoryMethodName = spellParticleOptionFactoryMethodName;
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_spell(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_spell(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                SPELL_METHOD_NAME,
                "(IIIID)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_red = 1;
        int local_green = 2;
        int local_blue = 3;
        int local_alpha = 4;
        int local_power = 5;
        int local_particleType = 7;

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
            SpellParticleOption.newByColorPower_obf
                this.particle,
                alpha << 24 | red << 16 | green << 8 | blue,
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

        // alpha << 24
        mv.visitVarInsn(ILOAD, local_alpha);
        mv.visitIntInsn(BIPUSH, 24);
        mv.visitInsn(ISHL);

        // ... | red << 16
        mv.visitVarInsn(ILOAD, local_red);
        mv.visitIntInsn(BIPUSH, 16);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // ... | green << 8
        mv.visitVarInsn(ILOAD, local_green);
        mv.visitIntInsn(BIPUSH, 8);
        mv.visitInsn(ISHL);

        mv.visitInsn(IOR);

        // .. | blue,
        mv.visitVarInsn(ILOAD, local_blue);
        mv.visitInsn(IOR);

        // (float) power
        mv.visitVarInsn(DLOAD, local_power);mv.visitInsn(D2F);

        mv.visitMethodInsn(INVOKESTATIC,
                refs.spellParticleOption.internalName(),
                spellParticleOptionFactoryMethodName,
                "(" + refs.particle_1_17.desc() + "IF)" + refs.spellParticleOption.desc(), false);

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
