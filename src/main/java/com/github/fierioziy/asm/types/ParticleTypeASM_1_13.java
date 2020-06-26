package com.github.fierioziy.asm.types;

import com.github.fierioziy.api.types.ParticleTypeRedstone;
import com.github.fierioziy.asm.utils.ParticleTypesImplProvider;
import com.github.fierioziy.asm.utils.ParticleRegistry;
import com.github.fierioziy.asm.utils.ParticleVersion;
import com.github.fierioziy.utils.TempClassLoader;
import com.github.fierioziy.api.types.ParticleType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.13.</p>
 */
public class ParticleTypeASM_1_13 extends ParticleBaseASM
        implements ParticleTypesImplProvider {

    private ParticleRegistry particleRegistry = new ParticleRegistry();

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private Set<String> currentParticleSet = new HashSet<>();

    public ParticleTypeASM_1_13(String version) {
        super(version);

        Class<?> particleClass;
        Class<?> particlesClass;
        try {
            particleClass = Class.forName(classNMS + ".Particle");
            particlesClass = Class.forName(classNMS + ".Particles");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Error: couldn't find "
                    + classNMS + ".Particle and "
                    + classNMS + ".Particles" + "classes!");
        }

        // cache all particles that exists in current version
        for (Field f : particlesClass.getFields()) {
            if (particleClass.isAssignableFrom(f.getType())) {
                currentParticleSet.add(f.getName());
            }
        }
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_13");
    }

    @Override
    public void defineImplementation(TempClassLoader cl) {
        defineBase(cl, particleType);
        defineBase(cl, particleTypeColorable);
        defineBase(cl, particleTypeMotion);
        defineBase(cl, particleTypeNote);

        cl.defineClass(
                getTypeImpl(particleTypeBlock).getClassName(),
                createParticleTypeBlockBase(particleTypeBlock, particleType)
        );
        cl.defineClass(
                getTypeImpl(particleTypeBlockMotion).getClassName(),
                createParticleTypeBlockBase(particleTypeBlockMotion, particleTypeMotion)
        );

        cl.defineClass(
                getTypeImpl(particleTypeDust).getClassName(),
                createParticleTypeDustBase(particleTypeDust, particleType)
        );
        cl.defineClass(
                getTypeImpl(particleTypeItemMotion).getClassName(),
                createParticleTypeItemBase(particleTypeItemMotion, particleTypeMotion)
        );
        cl.defineClass(
                getTypeImpl(particleTypeRedstone).getClassName(),
                createParticleTypeRedstone(particleTypeRedstone)
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
                    interfaceVersion, particleName, ParticleVersion.V1_13
            );

            // if found and it exists, then instantiate
            if (resolvedName != null && currentParticleSet.contains(resolvedName)) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                // if it is ParticleTypeRedstone, handle it to accept Particle
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleTypeRedstone.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = desc(NMS + "/Particle");
                    particlesFieldDesc = desc(NMS + "/Particle");
                }
                else if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = desc(NMS + "/ParticleParam");
                    particlesFieldDesc = desc(NMS + "/ParticleType");
                }
                else {
                    ctrParamDesc = desc(NMS + "/Particle");
                    particlesFieldDesc = desc(NMS + "/Particle");
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        NMS + "/Particles",
                        resolvedName,
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(particle);
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

            /*
            return new PacketPlayOutWorldParticles(particle, far,
                    x, y, z, offsetX, offsetY, offsetZ,
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
            mv.visitVarInsn(FLOAD, 3);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitVarInsn(FLOAD, 8);
            mv.visitVarInsn(ILOAD, 9);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZFFFFFFFI)V", false);
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

        visitFields(cw, "Particle");
        visitConstructor(cw, implType, superType, "Particle");
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

            /*
            new ParticleParamBlock(
                    particle,
                    ((CraftBlockData) material.createBlockData()).getState()
            );
             */
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamBlock");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/Particle"));


            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    "org/bukkit/Material",
                    "createBlockData",
                    "()Lorg/bukkit/block/data/BlockData;", false);

            mv.visitTypeInsn(CHECKCAST, OBC + "/block/data/CraftBlockData");
            mv.visitMethodInsn(INVOKEVIRTUAL,
                    OBC + "/block/data/CraftBlockData",
                    "getState",
                    "()" + desc(NMS + "/IBlockData"), false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamBlock",
                    "<init>",
                    "(" + desc(NMS + "/Particle") + desc(NMS + "/IBlockData") + ")V", false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>",
                    "(" + desc(NMS + "/ParticleParam") + ")V", false);
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

        visitFields(cw, "Particle");
        visitConstructor(cw, implType, superType, "Particle");
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

            /*
            new ParticleParamItem(
                    particle,
                    CraftItemStack.asNMSCopy(new ItemStack(material, 1))
            );
             */
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamItem");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    "particle",
                    desc(NMS + "/Particle"));

            mv.visitTypeInsn(NEW, "org/bukkit/inventory/ItemStack");
            mv.visitInsn(DUP);

            mv.visitVarInsn(ALOAD, 1);
            mv.visitInsn(ICONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    "org/bukkit/inventory/ItemStack",
                    "<init>",
                    "(Lorg/bukkit/Material;I)V", false);

            mv.visitMethodInsn(INVOKESTATIC,
                    OBC + "/inventory/CraftItemStack",
                    "asNMSCopy",
                    "(Lorg/bukkit/inventory/ItemStack;)" + desc(NMS + "/ItemStack"), false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamItem",
                    "<init>",
                    "(" + desc(NMS + "/Particle") + desc(NMS + "/ItemStack") + ")V", false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>", "(" + desc(NMS + "/ParticleParam") + ")V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Creates a bytecode of class extending <code>ParticleTypeDust</code>
     * related class represented by parameter <code>Type</code> object.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleTypeDust</code> related class.
     * @param returnType a <code>Type</code> object representing
     *                   <code>ParticleType</code> related class.
     * @return a {@code byte[]} array containing bytecode of class
     * extending <code>ParticleTypeDust</code> related class.
     */
    private byte[] createParticleTypeDustBase(Type superType, Type returnType) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type implType = getTypeImpl(superType);
        Type implReturnType = getTypeImpl(returnType);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(), null,
                superType.getInternalName(), null);

        visitFields(cw, "Particle");
        visitConstructor(cw, implType, superType, "Particle");
        addIsValid(cw);

        /*
        Generates a method that return particle type with selected dust color and size.
         */
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    "color",
                    "(FFFF)" + returnType.getDescriptor(), null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, implReturnType.getInternalName());
            mv.visitInsn(DUP);

            // new ParticleParamRedstone(red, green, blue, size);
            mv.visitTypeInsn(NEW, NMS + "/ParticleParamRedstone");
            mv.visitInsn(DUP);

            mv.visitVarInsn(FLOAD, 1);
            mv.visitVarInsn(FLOAD, 2);
            mv.visitVarInsn(FLOAD, 3);
            mv.visitVarInsn(FLOAD, 4);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/ParticleParamRedstone",
                    "<init>",
                    "(FFFF)V", false);

            mv.visitMethodInsn(INVOKESPECIAL,
                    implReturnType.getInternalName(),
                    "<init>",
                    "(" + desc(NMS + "/ParticleParam") + ")V", false);
            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Creates a bytecode of class extending <code>ParticleTypeRedstone</code>
     * related class represented by parameter <code>Type</code> object.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleType</code> related class.
     * @return a {@code byte[]} array containing bytecode of class
     * extending <code>ParticleType</code> related class.
     */
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
                    x, y, z, offsetX, offsetY, offsetZ,
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
            mv.visitVarInsn(FLOAD, 3);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitVarInsn(FLOAD, 5);
            mv.visitVarInsn(FLOAD, 6);
            mv.visitVarInsn(FLOAD, 7);
            mv.visitVarInsn(FLOAD, 8);
            mv.visitVarInsn(ILOAD, 9);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZFFFFFFFI)V", false);
            mv.visitInsn(ARETURN);

            // }
            mv.visitLabel(zeroCountLabel);

            /*
            return new PacketPlayOutWorldParticles(
                    new ParticleParamRedstone(offsetX, offsetY, offsetZ, 1.0F), far,
                    x, y, z, 0.0F, 0.0F, 0.0F,
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
            mv.visitVarInsn(FLOAD, 3);
            mv.visitVarInsn(FLOAD, 4);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(FCONST_0);
            mv.visitInsn(ICONST_1);

            mv.visitMethodInsn(INVOKESPECIAL,
                    NMS + "/PacketPlayOutWorldParticles",
                    "<init>", "(" + desc(NMS + "/ParticleParam") + "ZFFFFFFFI)V", false);
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
     * @param fieldType a NMS string field name.
     */
    protected void visitFields(ClassWriter cw, String fieldType) {
        cw.visitField(ACC_PRIVATE, "particle", desc(NMS + "/" + fieldType), null, null).visitEnd();
    }

    /**
     * <p>Visits constructor on class represented by <code>Type implType</code> parameter
     * extending class represented by <code>Type superType</code> parameter</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor visiting
     *           should happen.
     * @param implType a <code>Type</code> object representing implementation class.
     * @param superType a <code>Type</code> object representing super class.
     * @param fieldType a NMS string field name.
     */
    protected void visitConstructor(ClassWriter cw, Type implType, Type superType, String fieldType) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "(" + desc(NMS + "/" + fieldType) + ")V", null, null);
        mv.visitCode();

        /*
        Generates code that stores particle object type in field.
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
                desc(NMS + "/" + fieldType));
        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
