package com.github.fierioziy.asm.types;

import com.github.fierioziy.asm.utils.ParticleTypesImplProvider;
import com.github.fierioziy.asm.utils.ParticleRegistry;
import com.github.fierioziy.asm.utils.ParticleVersion;
import com.github.fierioziy.utils.TempClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.8.</p>
 */
public class ParticleTypeASM_1_8 extends ParticleBaseASM
        implements ParticleTypesImplProvider {

    private ParticleRegistry particleRegistry = new ParticleRegistry();

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private Set<String> currentParticleSet = new HashSet<>();

    public ParticleTypeASM_1_8(String version) {
        super(version);

        Class<?> enumClass;
        try {
            enumClass = Class.forName(classNMS + ".EnumParticle");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error: couldn't find " + classNMS + ".EnumParticle class!");
        }

        // cache all particles that exists in current version
        for (Object o : enumClass.getEnumConstants()) {
            currentParticleSet.add(((Enum<?>) o).name());
        }
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_8");
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
        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            Type particleReturnType = Type.getReturnType(m);
            Type particleReturnTypeImpl = getTypeImpl(particleReturnType);

            /*
            Instantiates certain particle type and put it in proper field.
             */
            mv.visitVarInsn(ALOAD, 0);

            // try to convert particle name to current server version
            String resolvedName = particleRegistry.find(
                    interfaceVersion, particleName, ParticleVersion.V1_8
            );

            // if found and it exists, then instantiate
            if (resolvedName != null && currentParticleSet.contains(resolvedName)) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // get enum directly
                mv.visitFieldInsn(GETSTATIC,
                        NMS + "/EnumParticle",
                        resolvedName,
                        desc(NMS + "/EnumParticle"));

                // new int[0]
                mv.visitInsn(ICONST_0);
                mv.visitIntInsn(NEWARRAY, T_INT);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + desc(NMS + "/EnumParticle") + "[I)V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(enum, new int[0]);
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
                    "(ZDDDDDDDI)Ljava/lang/Object;", null, null);
            mv.visitCode();

            /*
            return new PacketPlayOutWorldParticles(particle, far,
                    (float) x,          (float) y,          (float) z,
                    (float) offsetX,    (float) offsetY,    (float) offsetZ,
                    (float) speed, count, data);
             */
            mv.visitTypeInsn(NEW, NMS + "/PacketPlayOutWorldParticles");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/EnumParticle"));

            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(DLOAD, 2);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 4);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 6);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 8);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 10);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 12);mv.visitInsn(D2F);
            mv.visitVarInsn(DLOAD, 14);mv.visitInsn(D2F);
            mv.visitVarInsn(ILOAD, 16);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "data",
                    "[I");
            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/EnumParticle") + "ZFFFFFFFI[I)V", false);
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

            // get particle from field
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/EnumParticle"));

            // new int[2]
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);

            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);

            // operating on above array
            // dataArr[0] = material.getId();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
            mv.visitInsn(IASTORE);

            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);

            // dataArr[1] = material.getId() | (meta << 12);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitLdcInsn(12);
            mv.visitInsn(ISHL);
            mv.visitInsn(IOR);
            mv.visitInsn(IASTORE);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>", "(" + desc(NMS + "/EnumParticle") + "[I)V", false);
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

            // get particle from field
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/EnumParticle"));

            // new int[2]
            mv.visitInsn(ICONST_2);
            mv.visitIntInsn(NEWARRAY, T_INT);

            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_0);

            // operating on above array
            // dataArr[0] = material.getId();
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
            mv.visitInsn(IASTORE);

            mv.visitInsn(DUP);
            mv.visitInsn(ICONST_1);

            // dataArr[1] = 0;
            mv.visitInsn(ICONST_0);
            mv.visitInsn(IASTORE);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>", "(" + desc(NMS + "/EnumParticle") + "[I)V", false);
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
        cw.visitField(ACC_PRIVATE, "particle", desc(NMS + "/EnumParticle"), null, null).visitEnd();
        cw.visitField(ACC_PRIVATE, "data", "[I", null, null).visitEnd();
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
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + desc(NMS + "/EnumParticle") + "[I)V", null, null);
        mv.visitCode();

        /*
        Generates code that stores particle enum and data in field.
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
                desc(NMS + "/EnumParticle"));

        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitFieldInsn(PUTFIELD,
                implType.getInternalName(),
                "data",
                "[I");

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
