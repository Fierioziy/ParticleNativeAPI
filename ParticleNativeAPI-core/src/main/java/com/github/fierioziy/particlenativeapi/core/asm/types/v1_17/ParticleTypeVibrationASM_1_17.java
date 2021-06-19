package com.github.fierioziy.particlenativeapi.core.asm.types.v1_17;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ParticleTypeVibrationASM_1_17 extends ParticleTypeASM_1_17 {


    public ParticleTypeVibrationASM_1_17(InternalResolver resolver, Type superType) {
        super(resolver, superType);
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

        /*
        return new PacketPlayOutWorldParticles(
                new VibrationParticleOption(
                    new VibrationPath(
                        new BlockPosition((int) x, (int) y, (int) z)),
                        new BlockPositionSource(
                            new BlockPosition((int) targetX, (int) targetY, (int) targetZ))
                        ),
                        ticks
                    ),
                ),
                far,
                0D, 0D, 0D,
                0F, 0F, 0F,
                0F, 1);
         */
        mv.visitTypeInsn(NEW, internalNMS("network/protocol/game/PacketPlayOutWorldParticles"));
        mv.visitInsn(DUP);

        // VibrationParticleOption start
        mv.visitTypeInsn(NEW, internalNMS("core/particles/VibrationParticleOption"));
        mv.visitInsn(DUP);

        // VibrationPath start
        mv.visitTypeInsn(NEW, internalNMS("world/level/gameevent/vibrations/VibrationPath"));
        mv.visitInsn(DUP);

        // BlockPosition start
        mv.visitTypeInsn(NEW, internalNMS("core/BlockPosition"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, 2);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, 4);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, 6);mv.visitInsn(D2I);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/BlockPosition"),
                "<init>", "(III)V", false);
        // BlockPosition end

        // BlockPositionSource start
        mv.visitTypeInsn(NEW, internalNMS("world/level/gameevent/BlockPositionSource"));
        mv.visitInsn(DUP);

        // BlockPosition start
        mv.visitTypeInsn(NEW, internalNMS("core/BlockPosition"));
        mv.visitInsn(DUP);

        mv.visitVarInsn(DLOAD, 8);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, 10);mv.visitInsn(D2I);
        mv.visitVarInsn(DLOAD, 12);mv.visitInsn(D2I);

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
        mv.visitVarInsn(ILOAD, 14);

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("world/level/gameevent/vibrations/VibrationPath"),
                "<init>", "(" + descNMS("core/BlockPosition") + descNMS("world/level/gameevent/PositionSource") + "I)V", false);
        // VibrationPath end

        mv.visitMethodInsn(INVOKESPECIAL,
                internalNMS("core/particles/VibrationParticleOption"),
                "<init>", "(" + descNMS("world/level/gameevent/vibrations/VibrationPath") + ")V", false);
        // VibrationParticleOption end

        // far
        mv.visitVarInsn(ILOAD, 1);

        mv.visitInsn(DCONST_0);
        mv.visitInsn(DCONST_0);
        mv.visitInsn(DCONST_0);
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
