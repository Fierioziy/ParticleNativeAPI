package com.github.fierioziy.asm.types;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.15.</p>
 */
public class ParticleTypeASM_1_15 extends ParticleTypeASM_1_13 {

    public ParticleTypeASM_1_15(String version) {
        super(version);
    }

    @Override
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_15");
    }

    @Override
    protected byte[] createParticleTypeBase(Type superType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw, "ParticleParam");
        visitConstructor(cw, implType, superType, "ParticleParam");
        addIsValid(cw);

        /*
        Generates method that instantiates particle packet object
        with parameters passed to a method.
        Uses particle type stored in a field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "packet",
                    "(ZFFFFFFFI)Ljava/lang/Object;", null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/ParticleParam"));

            mv.visitVarInsn(ILOAD, 1);

            // cast float to double for constructor invocation
            // temporary fix, but still fast (added 3 fast instructions)
            // and compatible with other API methods
            mv.visitVarInsn(FLOAD, 2);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitInsn(F2D);

            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitVarInsn(FLOAD, 8);
            mv.visitVarInsn(ILOAD, 9);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        /*
        Generates method that instantiates particle packet object
        with parameters passed to a method.
        Uses particle type stored in a field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "packet",
                    "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/ParticleParam"));

            mv.visitVarInsn(ILOAD, 1);

            mv.visitVarInsn(DLOAD, 2);
            mv.visitVarInsn(DLOAD, 4);
            mv.visitVarInsn(DLOAD, 6);

            mv.visitVarInsn(DLOAD, 8);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 10);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 12);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 14);
            mv.visitInsn(D2F);
            mv.visitVarInsn(ILOAD, 16);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /*
    This one is quite messy (coz of many double/float casts), marked method with float parameters
    as deprecated (for removal) to simplify entire code (above method as well).
     */
    @Override
    protected byte[] createParticleTypeRedstone(Type superType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw, "ParticleParam");

        /*
        Generates constructor that creates default redstone color (red)
        and stores it in a field.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                    "(" + desc(NMS + "/Particle") + ")V", null, null);
            mv.visitCode();

            /*
            Generates code that stores default ParticleParamRedstone object in field.
            */
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL,
                    superType.getInternalName(),
                    "<init>",
                    "()V", false);

            mv.visitVarInsn(ALOAD, 0);

            // new ParticleParamRedstone(1.0F, 0.0F, 0.0F, 1.0F);
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamRedstone");
            mv.visitInsn(DUP);

            mv.visitInsn(FCONST_1);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamRedstone",
                    "<init>",
                    "(FFFF)V", false);

            mv.visitFieldInsn(PUTFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/ParticleParam"));
            mv.visitInsn(RETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        addIsValid(cw);

        /*
        Generates method that instantiates particle packet object
        with parameters passed to a method.
        Uses ParticleParamRedstone object stored in a field
        or creates new one if count equals 0.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "packet",
                    "(ZFFFFFFFI)Ljava/lang/Object;", null, null);
            mv.visitCode();

            Label zeroCountLabel = new Label();

            // if (count != 0) {
            mv.visitVarInsn(ILOAD, 9);
            mv.visitJumpInsn(IFEQ, zeroCountLabel);

            /*
            return new PacketPlayOutWorldParticles(particle, far,
                    (double) x, (double) y, (double) z, offsetX, offsetY, offsetZ,
                    speed, count);
             */
            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/ParticleParam"));

            mv.visitVarInsn(ILOAD, 1);

            mv.visitVarInsn(FLOAD, 2);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitInsn(F2D);

            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitVarInsn(FLOAD, 8);
            mv.visitVarInsn(ILOAD, 9);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            // }
            mv.visitLabel(zeroCountLabel);

            /*
            return new PacketPlayOutWorldParticles(
                    new ParticleParamRedstone(offsetX, offsetY, offsetZ, 1.0F), far,
                    (double) x, (double) y, (double) z, 0.0F, 0.0F, 0.0F,
                    0.0F, 1);
             */
            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            // new ParticleParamRedstone(offsetX, offsetY, offsetZ, 1.0F);
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamRedstone");
            mv.visitInsn(DUP);

            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitInsn(FCONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamRedstone",
                    "<init>",
                    "(FFFF)V", false);

            mv.visitVarInsn(ILOAD, 1);

            mv.visitVarInsn(FLOAD, 2);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitInsn(F2D);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitInsn(F2D);

            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(ICONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

               /*
        Generates method that instantiates particle packet object
        with parameters passed to a method.
        Uses ParticleParamRedstone object stored in a field
        or creates new one if count equals 0.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "packet",
                    "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
            mv.visitCode();

            Label zeroCountLabel = new Label();

            // if (count != 0) {
            mv.visitVarInsn(ILOAD, 16);
            mv.visitJumpInsn(IFEQ, zeroCountLabel);

            /*
            return new PacketPlayOutWorldParticles(particle, far,
                    x, y, z, (float) offsetX, (float) offsetY, (float) offsetZ,
                    (float) speed, count);
             */
            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/ParticleParam"));

            mv.visitVarInsn(ILOAD, 1);

            mv.visitVarInsn(DLOAD, 2);
            mv.visitVarInsn(DLOAD, 4);
            mv.visitVarInsn(DLOAD, 6);

            mv.visitVarInsn(DLOAD, 8);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 10);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 12);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 14);
            mv.visitInsn(D2F);
            mv.visitVarInsn(ILOAD, 16);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            // }
            mv.visitLabel(zeroCountLabel);

            /*
            return new PacketPlayOutWorldParticles(
                    new ParticleParamRedstone((float) offsetX, (float) offsetY, (float) offsetZ, 1.0F),
                    far,
                    x, y, z, 0.0F, 0.0F, 0.0F,
                    0.0F, 1);
             */
            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            // new ParticleParamRedstone((float) offsetX, (float) offsetY, (float) offsetZ, 1.0F);
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamRedstone");
            mv.visitInsn(DUP);

            mv.visitVarInsn(DLOAD, 8);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 10);
            mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 12);
            mv.visitInsn(D2F);
            mv.visitInsn(FCONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamRedstone",
                    "<init>",
                    "(FFFF)V", false);

            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(DLOAD, 2);
            mv.visitVarInsn(DLOAD, 4);
            mv.visitVarInsn(DLOAD, 6);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(ICONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZDDDFFFFI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }
}
