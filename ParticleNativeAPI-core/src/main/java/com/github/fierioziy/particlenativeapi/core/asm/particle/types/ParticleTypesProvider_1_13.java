package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.api.types.ParticleType;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.13.</p>
 */
public class ParticleTypesProvider_1_13 extends ParticleTypesProvider {

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private final Set<String> currentParticleSet;

    public ParticleTypesProvider_1_13(InternalResolver resolver) {
        this(resolver, "_1_13");
    }

    public ParticleTypesProvider_1_13(InternalResolver resolver, String suffix) {
        super(resolver, suffix);

        currentParticleSet = resolver.getParticles_1_13();
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_13(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_13(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_13(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_13(internal, suffix, refs.particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_13(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_13(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_13(internal, suffix, refs.particleTypeDust, refs.particleType).defineClass();
        new ParticleTypeItemASM_1_13(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeRedstoneASM_1_13(internal, suffix, refs.particleTypeRedstone).defineClass();
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
                    interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_13
            ).map(String::toUpperCase);

            // if found and it exists, then instantiate
            if (resolvedName.isPresent() && currentParticleSet.contains(resolvedName.get())) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = refs.particleParam_1_7.desc();
                    particlesFieldDesc = refs.particleTypeNms_1_7.desc();
                }
                else {
                    ctrParamDesc = refs.particle_1_7.desc();
                    particlesFieldDesc = refs.particle_1_7.desc();
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        refs.particlesNms_1_7.internalName(),
                        resolvedName.get(),
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else if (interfaceVersion.equals(SpigotParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticleSet.contains("DUST")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        refs.particlesNms_1_7.internalName(),
                        "DUST",
                        refs.particle_1_7.desc());

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + refs.particle_1_7.desc() + ")V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(particle);
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().internalName(),
                    particleName,
                    particleReturnType.desc());
        }
    }

}
