package com.github.fierioziy.particlenativeapi.core.asm.particle;

import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.ParticleTypesProvider;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

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
                        ParticleTypesProvider particleTypesProvider) {
        super(resolver, "_Impl", particleVersion.getSuperType());
        this.particleVersion = particleVersion;
        this.particleTypesProvider = particleTypesProvider;
    }

    @Override
    public void writeFields(ClassWriter cw) {

    }

    @Override
    public void writeConstructors(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "<init>", "(" + refs.particleNativeAPI.desc() +  ")V", null, null);
        mv.visitCode();

        int local_this = 0;
        int local_api = 1;

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitVarInsn(ALOAD, local_api);
        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "(" + refs.particleNativeAPI.desc() + ")V", false);

        mv.visitInsn(RETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    public void writeMethods(ClassWriter cw) {
        particleTypesProvider.generateParticleFactoryMethods(cw, particleVersion);
    }

}
