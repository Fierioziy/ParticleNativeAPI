package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_8.ParticleTypeASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_8.ParticleTypeBlockASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_8.ParticleTypeItemASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.8.</p>
 */
public class ParticleTypesProvider_1_8 extends ParticleTypesProvider {

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private final Set<String> currentParticleSet;

    public ParticleTypesProvider_1_8(InternalResolver resolver) {
        super(resolver, "_1_8");

        currentParticleSet = resolver.getParticles_1_8();
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_8(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_8(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_8(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_8(internal, suffix, refs.particleTypeNote).defineClass();
        new ParticleTypeASM_1_8(internal, suffix, refs.particleTypeRedstone).defineClass();

        new ParticleTypeBlockASM_1_8(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_8(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeItemASM_1_8(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();
    }

    @Override
    public void storeParticleTypesToFields(MethodVisitor mv, SpigotParticleVersion interfaceVersion) {
        int local_this = 0;

        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            ClassMapping particleReturnType = refs.of(m.getReturnType());
            ClassMapping particleReturnTypeImpl = particleReturnType.impl(suffix);

            /*
            Instantiates certain particle type and put it in proper field.
             */
            mv.visitVarInsn(ALOAD, local_this);

            // try to convert particle name to current server version
            Optional<String> resolvedName = particleRegistry.find(
                    interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_8
            ).map(String::toUpperCase);

            // if found and it exists, then instantiate
            if (resolvedName.isPresent() && currentParticleSet.contains(resolvedName.get())) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // get enum directly
                mv.visitFieldInsn(GETSTATIC,
                        refs.enumParticle.internalName(),
                        resolvedName.get(),
                        refs.enumParticle.desc());

                // new int[0]
                mv.visitInsn(ICONST_0);
                mv.visitIntInsn(NEWARRAY, T_INT);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + refs.enumParticle.desc() + "[I)V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(enum, new int[0]);
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().internalName(),
                    particleName,
                    particleReturnType.desc());
        }
    }

}
