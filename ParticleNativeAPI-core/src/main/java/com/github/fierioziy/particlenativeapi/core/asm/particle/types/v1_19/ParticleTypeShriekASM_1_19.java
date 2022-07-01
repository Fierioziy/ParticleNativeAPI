package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17.ParticleTypeASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeShriekASM_1_19 extends ParticleTypeASM_1_17 {

    private final ClassMapping implReturnType;
    private final ClassMapping returnType;

    public ParticleTypeShriekASM_1_19(InternalResolver resolver, String suffix, ClassMapping superType, ClassMapping returnType) {
        super(resolver, suffix, superType);
        this.implReturnType = returnType.impl(suffix);
        this.returnType = returnType;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, refs.particle_1_17);
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, refs.particle_1_17);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_roll(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_roll(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "delay",
                "(I)" + returnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_roll = 1;

        /*
        return new ParticleType_someImpl(
            new ShriekParticleOption(delay)
        );
         */

        // ParticleType_someImpl start
        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);

        // ShriekParticleOption start
        mv.visitTypeInsn(NEW, refs.shriekParticleOption.internalName());
        mv.visitInsn(DUP);

        mv.visitVarInsn(ILOAD, local_roll);

        mv.visitMethodInsn(INVOKESPECIAL,
                refs.shriekParticleOption.internalName(),
                "<init>", "(I)V", false);
        // ShriekParticleOption end

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>",
                "(" + refs.particleParam_1_17.desc() + ")V", false);
        // ParticleType_someImpl end

        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
