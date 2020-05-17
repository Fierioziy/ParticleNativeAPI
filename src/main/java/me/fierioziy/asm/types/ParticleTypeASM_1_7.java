package me.fierioziy.asm.types;

import me.fierioziy.asm.utils.ClassImplProvider;
import me.fierioziy.asm.utils.ParticleRegistry;
import me.fierioziy.asm.utils.ParticleVersion;
import me.fierioziy.utils.TempClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.7.</p>
 */
public class ParticleTypeASM_1_7 extends ParticleBaseASM implements ClassImplProvider {

    private ParticleRegistry particleRegistry = new ParticleRegistry();

    public ParticleTypeASM_1_7(String version) {
        super(version);
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_7");
    }

    @Override
    public void defineImplementation(TempClassLoader cl) {
        defineBase(cl, particleType);
        defineBase(cl, particleTypeColorable);
        defineBase(cl, particleTypeMotion);
        defineBase(cl, particleTypeNote);
        defineBase(cl, particleTypeRedstone);

        cl.defineClass(
                getTypeImpl(particleTypeBlock).getClassName(),
                createParticleTypeBlockBase(particleTypeBlock, particleType)
        );
        cl.defineClass(
                getTypeImpl(particleTypeBlockMotion).getClassName(),
                createParticleTypeBlockBase(particleTypeBlockMotion, particleTypeMotion)
        );
        cl.defineClass(
                getTypeImpl(particleTypeItemMotion).getClassName(),
                createParticleTypeItemBase(particleTypeItemMotion, particleTypeMotion)
        );
    }

    private void defineBase(TempClassLoader cl, Type superType) {
        cl.defineClass(
                getTypeImpl(superType).getClassName(),
                createParticleTypeBase(superType)
        );
    }

    @Override
    public void visitParticleTypes(MethodVisitor mv, ParticleVersion interfaceVersion) {
        for (Method m : interfaceVersion.getParticleTypesClass().getMethods()) {
            String particleName = m.getName();

            Type particleReturnType = Type.getReturnType(m);
            Type particleReturnTypeImpl = getTypeImpl(particleReturnType);

            /*
            Instantiates certain particle type and put it in proper field.
             */
            mv.visitVarInsn(ALOAD, 0);

            String resolvedName = particleRegistry.find(
                    interfaceVersion, particleName, ParticleVersion.V1_7
            );

            if (resolvedName != null) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                mv.visitLdcInsn(resolvedName);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(Ljava/lang/String;)V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().getInternalName(),
                    particleName,
                    particleReturnType.getDescriptor());
        }
    }

    /**
     * <p>Visits a default <code>ParticleType</code> related constructor.</p>
     *
     * @param mv a <code>MethodVisitor</code> on which visiting occurs.
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleType</code> related class.
     */
    private void visitInvalidType(MethodVisitor mv, Type superType) {
        mv.visitTypeInsn(NEW, superType.getInternalName());
        mv.visitInsn(DUP);

        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);
    }

    /**
     * <p>Creates a bytecode of class extending <code>ParticleType</code>
     * related class represented by parameter <code>Type</code> object.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleType</code> related class.
     * @return a {@code byte[]} array containing bytecode of class
     * extending <code>ParticleType</code> related class.
     */
    private byte[] createParticleTypeBase(Type superType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw);
        visitConstructor(cw, implType, superType);
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
                    "Ljava/lang/String;");
            mv.visitVarInsn(FLOAD, 2);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitVarInsn(FLOAD, 8);
            mv.visitVarInsn(ILOAD, 9);
            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(Ljava/lang/String;FFFFFFFI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Creates a bytecode of class extending <code>ParticleTypeBlock</code>
     * related class represented by parameter <code>Type</code> object.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleTypeBlock</code> related class.
     * @param returnType a <code>Type</code> object representing
     *                   <code>ParticleType</code> related class.
     * @return a {@code byte[]} array containing bytecode of class
     * extending <code>ParticleTypeBlock</code> related class.
     */
    private byte[] createParticleTypeBlockBase(Type superType, Type returnType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);
        Type implReturnType = getTypeImpl(returnType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw);
        visitConstructor(cw, implType, superType);
        addIsValid(cw);

        /*
        Generates a method that return particle type with selected block.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "of",
                    "(Lorg/bukkit/Material;B)" + returnType.getDescriptor(), null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, implReturnType.getInternalName());
            mv.visitInsn(DUP);

            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    "Ljava/lang/String;");

            // StringBuilder builder = new StringBuilder(particle);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/StringBuilder",
                    "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(ASTORE, 3);

            // builder.append(item.getId());
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);

            // if (byte >= 0) builder.append("_").append(meta);
            Label label = new Label();
            mv.visitVarInsn(ILOAD, 2);
            mv.visitJumpInsn(IFLT, label);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitLdcInsn("_");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);

            // builder.toString();
            mv.visitLabel(label);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);


            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>", "(Ljava/lang/String;)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Creates a bytecode of class extending <code>ParticleTypeItem</code>
     * related class represented by parameter <code>Type</code> object.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleTypeItem</code> related class.
     * @param returnType a <code>Type</code> object representing
     *                   <code>ParticleType</code> related class.
     * @return a {@code byte[]} array containing bytecode of class
     * extending <code>ParticleTypeItem</code> related class.
     */
    private byte[] createParticleTypeItemBase(Type superType, Type returnType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);
        Type implReturnType = getTypeImpl(returnType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw);
        visitConstructor(cw, implType, superType);
        addIsValid(cw);

        /*
        Generates a method that return particle type with selected item.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "of",
                    "(Lorg/bukkit/Material;)" + returnType.getDescriptor(), null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, implReturnType.getInternalName());
            mv.visitInsn(DUP);

            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    "Ljava/lang/String;");

            // StringBuilder builder = new StringBuilder(particle);
            mv.visitMethodInsn(INVOKESPECIAL,
                    "java/lang/StringBuilder",
                    "<init>", "(Ljava/lang/String;)V", false);
            mv.visitVarInsn(ASTORE, 3);

            // builder.append(item.getId());
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);

            // builder.toString();
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>", "(Ljava/lang/String;)V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Visits fields necessary to properly represent certain particle.</p>
     *
     * @param cw a <code>ClassWriter</code> on which fields visiting should happen.
     */
    private void visitFields(ClassWriter cw) {
        cw.visitField(ACC_PRIVATE, "particle", "Ljava/lang/String;", null, null).visitEnd();
    }

    /**
     * <p>Visits constructor on class represented by <code>Type implType</code> parameter
     * extending class represented by <code>Type superType</code> parameter</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor visiting
     *           should happen.
     * @param implType a <code>Type</code> object representing implementation class.
     * @param superType a <code>Type</code> object representing super class.
     */
    private void visitConstructor(ClassWriter cw, Type implType, Type superType) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/String;)V", null, null);
        mv.visitCode();

        /*
        Generates code that stores particle name in field.
         */
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "particle",
                "Ljava/lang/String;");
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
