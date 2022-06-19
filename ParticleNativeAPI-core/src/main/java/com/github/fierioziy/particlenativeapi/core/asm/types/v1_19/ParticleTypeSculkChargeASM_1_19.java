package com.github.fierioziy.particlenativeapi.core.asm.types.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_17.ParticleTypeASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeSculkChargeASM_1_19 extends ParticleTypeASM_1_17 {

    private Type implReturnType;
    private Type returnType;

    public ParticleTypeSculkChargeASM_1_19(InternalResolver resolver, String suffix, Type superType, Type returnType) {
        super(resolver, suffix, superType);
        this.implReturnType = getTypeImpl(returnType);
        this.returnType = returnType;
    }

    @Override
    protected void writeFields(ClassWriter cw) {
        writeFields(cw, "core/particles/Particle");
    }

    @Override
    protected void writeConstructor(ClassWriter cw) {
        writeConstructor(cw, "core/particles/Particle");
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_roll(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_roll(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "roll",
                "(D)" + returnType.getDescriptor(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_roll = 1;

        /*
        return new ParticleType_someMotionImpl(
            new SculkChargeParticleOptions((float) roll)
        );
         */

        // ParticleType_someMotionImpl start
        mv.visitTypeInsn(NEW, implReturnType.getInternalName());
        mv.visitInsn(DUP);

        // SculkChargeParticleOptions start
        mv.visitTypeInsn(NEW, internalNMS("core/particles/SculkChargeParticleOptions"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, local_roll);mv.visitInsn(D2F);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/particles/SculkChargeParticleOptions"),
                "<init>", "(F)V", false);
        // SculkChargeParticleOptions end

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.getInternalName(),
                "<init>",
                "(" + descNMS("core/particles/ParticleParam") + ")V", false);
        // ParticleType_someMotionImpl end

        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
