package com.github.fierioziy.particlenativeapi.core.asm.types.v1_19;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_17.ParticleTypeASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeVibrationASM_1_19 extends ParticleTypeASM_1_17 {

    public ParticleTypeVibrationASM_1_19(InternalResolver resolver, String suffix, Type superType) {
        super(resolver, suffix, superType);
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
        writeMethod_packet(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_packet(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "packet",
                "(ZDDDDDDI)Ljava/lang/Object;", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_far = 1;
        int local_x = 2;
        int local_y = 4;
        int local_z = 6;
        int local_targetX = 8;
        int local_targetY = 10;
        int local_targetZ = 12;
        int local_ticks = 14;

        /*
        return new PacketPlayOutWorldParticles(
                new VibrationParticleOption(
                    new BlockPositionSource(
                        new BlockPosition((int) targetX, (int) targetY, (int) targetZ))
                    ),
                    ticks
                ),
                far,
                x, y, z,
                0F, 0F, 0F,
                0F, 1);
         */
        mv.visitTypeInsn(NEW, internalNMS("network/protocol/game/PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        // VibrationParticleOption start
        mv.visitTypeInsn(NEW, internalNMS("core/particles/VibrationParticleOption"));
        mv.visitInsn(DUP);

        // BlockPositionSource start
        mv.visitTypeInsn(NEW, internalNMS("world/level/gameevent/BlockPositionSource"));
        mv.visitInsn(DUP);

        // BlockPosition start
        mv.visitTypeInsn(NEW, internalNMS("core/BlockPosition"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, local_targetX);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_targetY);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, local_targetZ);mv.visitInsn(D2I);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/BlockPosition"),
                "<init>", "(III)V", false);
        // BlockPosition end

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("world/level/gameevent/BlockPositionSource"),
                "<init>", "(" + descNMS("core/BlockPosition") + ")V", false);
        // BlockPositionSource end

        mv.visitTypeInsn(CHECKCAST, internalNMS("world/level/gameevent/PositionSource"));

        // ticks
        mv.visitVarInsn(ILOAD, local_ticks);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/particles/VibrationParticleOption"),
                "<init>", "(" + descNMS("world/level/gameevent/PositionSource") + "I)V", false);
        // VibrationParticleOption end

        // far
        mv.visitVarInsn(ILOAD, local_far);

        // x, y, z
        mv.visitVarInsn(DLOAD, local_x);
        mv.visitVarInsn(DLOAD, local_y);
        mv.visitVarInsn(DLOAD, local_z);

        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(FCONST_0);
        mv.visitInsn(ICONST_1);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("network/protocol/game/PacketPlayOutWorldParticles"),
                "<init>", "(" + descNMS("core/particles/ParticleParam") + "ZDDDFFFFI)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
