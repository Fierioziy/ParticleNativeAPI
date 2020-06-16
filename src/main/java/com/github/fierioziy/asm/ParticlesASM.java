package com.github.fierioziy.asm;

import com.github.fierioziy.api.Particles_1_13;
import com.github.fierioziy.api.Particles_1_8;
import com.github.fierioziy.asm.types.ParticleTypeASM_1_13;
import com.github.fierioziy.asm.types.ParticleTypeASM_1_15;
import com.github.fierioziy.asm.types.ParticleTypeASM_1_7;
import com.github.fierioziy.asm.types.ParticleTypeASM_1_8;
import com.github.fierioziy.asm.utils.ParticleTypesImplProvider;
import com.github.fierioziy.asm.utils.ParticleVersion;
import com.github.fierioziy.utils.TempClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * <p>A class used to create bytecode for <code>Particles</code> related
 * interface implementations.</p>
 *
 * <p>It is responsible for providing proper bytecode for all methods
 * in certain <code>Particles</code> interface.</p>
 *
 * <p>Bytecode generation consist of generating field for every method
 * in certain's <code>Particles</code> interface with same name
 * as the getter method in interface.</p>
 *
 * <p>Next, a constructor bytecode is generated to instantiate every field
 * with an implementation of certain version <code>ParticleType</code> related
 * class or default <code>ParticleType</code> related class if certain
 * particle does not exist in current Spigot version.</p>
 */
public class ParticlesASM extends ConnectionBaseASM {

    /**
     * <p>A class implementation provider used to define necessary
     * classes in <code>TempClassLoader</code> class loader.</p>
     *
     * <p>It is also used to construct <code>ParticleType</code> related
     * implementations and storing them in interface's implementation class fields.</p>
     */
    private ParticleTypesImplProvider implProvider;

    /**
     * <p>Chooses proper <code>ClassImplProvider</code> provider based
     * on current Spigot version and defines necessary classes for
     * proper class generation.</p>
     *
     * @param version a package version string.
     * @param cl a <code>TempClassLoader</code> class loader on which
     *           class definitions should occur.
     * @see ParticleTypesImplProvider
     */
    public ParticlesASM(String version, TempClassLoader cl) {
        super(version);

        if      (isPacketConstructor_1_7())     implProvider = new ParticleTypeASM_1_7(version);
        else if (isPacketConstructor_1_8())     implProvider = new ParticleTypeASM_1_8(version);
        else if (isPacketConstructor_1_13())    implProvider = new ParticleTypeASM_1_13(version);
        else if (isPacketConstructor_1_15())    implProvider = new ParticleTypeASM_1_15(version);
        else throw new IllegalStateException("Error: this server version is not supported!");

        implProvider.defineImplementation(cl);
    }

    /**
     * <p>Generates a bytecode of class implementing <code>Particles_1_8</code> interface.</p>
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>Particles_1_8</code> interface
     * @see Particles_1_8
     */
    public byte[] generateParticles_1_8() {
        return generateParticlesList(ParticleVersion.V1_8);
    }

    /**
     * <p>Generates a bytecode of class implementing <code>Particles_1_13</code> interface.</p>
     *
     * @return a {@code byte[]} array containing bytecode of class
     * implementing <code>Particles_1_13</code> interface
     * @see Particles_1_13
     */
    public byte[] generateParticles_1_13() {
        return generateParticlesList(ParticleVersion.V1_13);
    }

    /**
     * <p>Generates a bytecode of class implementing interface associated
     * with parameter <code>ParameterVersion</code> enum.</p>
     *
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with target interface class.
     * @return a {@code byte[]} array containing bytecode of class
     * implementing interface associated with
     * parameter <code>ParticleVersion</code> enum.
     */
    private byte[] generateParticlesList(ParticleVersion interfaceVersion) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        Type superType = interfaceVersion.getSuperType();
        Type implType = interfaceVersion.getImplType();

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(),
                null,
                serverConnTypeImpl.getInternalName(),
                new String[] { superType.getInternalName() });

        visitFields(cw, interfaceVersion);
        visitConstructor(cw, interfaceVersion);

        /*
        Creates getter for every particle type (from field).
         */
        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    particleName, Type.getMethodDescriptor(m), null, null);
            mv.visitCode();

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD,
                    implType.getInternalName(),
                    particleName,
                    Type.getReturnType(m).getDescriptor());

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        cw.visitEnd();
        return cw.toByteArray();
    }

    /**
     * <p>Visits all fields of interface associated
     * with parameter <code>ParticleVersion</code> enum.</p>
     *
     * @param cw a <code>ClassWriter</code> on which fields visiting should happen.
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with target interface class.
     */
    private void visitFields(ClassWriter cw, ParticleVersion interfaceVersion) {
        /*
        Creates fields of the same type as method return type
        with same name as the method.
         */
        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            cw.visitField(ACC_PRIVATE,
                    m.getName(),
                    Type.getReturnType(m).getDescriptor(), null, null
            ).visitEnd();
        }
    }

    /**
     * <p>Visits constructor of class implementing interface associated
     * with parameter <code>ParticleVersion</code> enum.</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor visiting
     *           should happen.
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with target interface class.
     */
    private void visitConstructor(ClassWriter cw, ParticleVersion interfaceVersion) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();

        /*
        Initiates constructor and instantiate all particle types in fields
        using class implementation provider.
         */
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL,
                serverConnTypeImpl.getInternalName(),
                "<init>",
                "()V", false);

        implProvider.visitParticleTypes(mv, interfaceVersion);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.7 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.7 version, false otherwise.
     */
    private boolean isPacketConstructor_1_7() {
        try {
            Class.forName(classNMS + ".PacketPlayOutWorldParticles").getConstructor(
                    String.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.8 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.8 version, false otherwise.
     */
    private boolean isPacketConstructor_1_8() {
        try {
            Class.forName(classNMS + ".PacketPlayOutWorldParticles").getConstructor(
                    Class.forName(classNMS + ".EnumParticle"), boolean.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class, int[].class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.13 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.13 version, false otherwise.
     */
    private boolean isPacketConstructor_1_13() {
        try {
            Class.forName(classNMS + ".PacketPlayOutWorldParticles").getConstructor(
                    Class.forName(classNMS + ".ParticleParam"), boolean.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.15 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.15 version, false otherwise.
     */
    private boolean isPacketConstructor_1_15() {
        try {
            Class.forName(classNMS + ".PacketPlayOutWorldParticles").getConstructor(
                    Class.forName(classNMS + ".ParticleParam"), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

}
