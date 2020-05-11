package me.fierioziy.asm.types;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

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

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "create",
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

        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "create",
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
}
