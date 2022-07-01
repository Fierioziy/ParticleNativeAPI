package com.github.fierioziy.particlenativeapi.core.asm.particle;

import com.github.fierioziy.particlenativeapi.core.asm.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.ParticleTypesProvider;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
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
public class ParticlesASM extends ClassSkeletonASM {

    private final SpigotParticleVersion particleVersion;
    private final ParticleTypesProvider particleTypesProvider;

    public ParticlesASM(InternalResolver resolver, SpigotParticleVersion particleVersion,
                        ParticleTypesProvider particleTypesProvider, ClassMapping serverConnTypeImpl) {
        super(resolver, "_Impl", true, serverConnTypeImpl, particleVersion.getType());
        this.particleVersion = particleVersion;
        this.particleTypesProvider = particleTypesProvider;
    }

    @Override
    public void writeFields(ClassWriter cw) {
        /*
        Creates fields of the same type as method return type
        with same name as the method.
         */
        for (Method m : particleVersion.getParticleTypesClass().getDeclaredMethods()) {
            cw.visitField(ACC_PRIVATE,
                    m.getName(),
                    refs.of(m.getReturnType()).desc(), null, null
            ).visitEnd();
        }
    }

    @Override
    public void writeConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();

        int local_this = 0;

        /*
        Initiates constructor and instantiate all particle types in fields
        using class implementation provider.
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);

        particleTypesProvider.storeParticleTypesToFields(mv, particleVersion);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    public void writeMethods(ClassWriter cw) {
        int local_this = 0;

        /*
        Creates getter for every particle type (from field).
         */
        for (Method m : particleVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                    particleName, Type.getMethodDescriptor(m), null, null);
            mv.visitCode();

            mv.visitVarInsn(ALOAD, local_this);
            mv.visitFieldInsn(GETFIELD,
                    implType.internalName(),
                    particleName,
                    refs.of(m.getReturnType()).desc());

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
