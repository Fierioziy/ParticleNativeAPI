package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.api.Particles_1_13;
import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.types.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
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
public class ParticlesASM extends BaseASM {

    /**
     * <p>A class implementation provider used to define necessary
     * classes in <code>TempClassLoader</code> class loader.</p>
     *
     * <p>It is also used to construct <code>ParticleType</code> related
     * implementations and storing them in interface's implementation class fields.</p>
     */
    private ConnectionsASM connectionsProvider;

    /**
     * <p>A class implementation provider used to define necessary
     * classes in <code>TempClassLoader</code> class loader.</p>
     *
     * <p>It is also used to construct <code>ParticleType</code> related
     * implementations and storing them in interface's implementation class fields.</p>
     */
    private ParticleTypesASM particleTypesProvider;

    private Type serverConnTypeImpl;

    /**
     * <p>Chooses proper <code>ParticleTypesASM</code> based
     * on current Spigot version and defines necessary classes for
     * proper class generation.</p>
     *
     * @param resolver an internal class data resolver.
     * @see ParticleTypesASM
     */
    public ParticlesASM(InternalResolver resolver) {
        super(resolver);

        if (internal.isVersion_1_7()) {
            connectionsProvider = new ConnectionsASM_1_7(resolver);
            particleTypesProvider = new ParticleTypesASM_1_7(resolver);
        }
        else if (internal.isVersion_1_8()) {
            connectionsProvider = new ConnectionsASM_1_7(resolver);
            particleTypesProvider = new ParticleTypesASM_1_8(resolver);
        }
        else if (internal.isVersion_1_13()) {
            connectionsProvider = new ConnectionsASM_1_7(resolver);
            particleTypesProvider = new ParticleTypesASM_1_13(resolver);
        }
        else if (internal.isVersion_1_15()) {
            connectionsProvider = new ConnectionsASM_1_7(resolver);
            particleTypesProvider = new ParticleTypesASM_1_15(resolver);
        }
        else throw new ParticleException("Error: this server version is not supported!");

        connectionsProvider.defineClasses();
        particleTypesProvider.defineClasses();

        serverConnTypeImpl = connectionsProvider.getTypeImpl(serverConnType);
    }

    /**
     * <p>Writes all fields for implementing interface.</p>
     *
     * @param cw a <code>ClassWriter</code> on which fields writing should happen.
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with implementing interface class.
     */
    private void writeFields(ClassWriter cw, ParticleVersion interfaceVersion) {
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
     * <p>Write constructor of class implementing interface.</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor writing
     *           should happen.
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with implementing interface class.
     */
    private void writeConstructor(ClassWriter cw, ParticleVersion interfaceVersion) {
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

        particleTypesProvider.storeParticleTypesToFields(mv, interfaceVersion);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
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
     * implementing <code>Particles_1_13</code> interface.
     * @see Particles_1_13
     */
    public byte[] generateParticles_1_13() {
        return generateParticlesList(ParticleVersion.V1_13);
    }

    /**
     * <p>Generates a bytecode Particles class implementing interface.</p>
     *
     * @param interfaceVersion a <code>ParticleVersion</code> enum associated
     *                         with implementing interface class.
     * @return a {@code byte[]} array containing bytecode of class
     * implementing interface.
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

        writeFields(cw, interfaceVersion);
        writeConstructor(cw, interfaceVersion);

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

}
