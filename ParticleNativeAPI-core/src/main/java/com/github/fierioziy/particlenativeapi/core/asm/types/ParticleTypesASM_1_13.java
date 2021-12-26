package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.api.types.ParticleType;
import com.github.fierioziy.particlenativeapi.api.types.ParticleTypeRedstone;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.objectweb.asm.*;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.13.</p>
 */
public class ParticleTypesASM_1_13 extends ParticleTypesASM {

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private Set<String> currentParticleSet;

    public ParticleTypesASM_1_13(InternalResolver resolver) {
        super(resolver);

        currentParticleSet = resolver.getParticles_1_13();
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_13");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_13(internal, particleType)           .defineClass();
        new ParticleTypeASM_1_13(internal, particleTypeColorable)  .defineClass();
        new ParticleTypeASM_1_13(internal, particleTypeMotion)     .defineClass();
        new ParticleTypeASM_1_13(internal, particleTypeNote)       .defineClass();

        new ParticleTypeBlockASM_1_13(internal, particleTypeBlock,       particleType)       .defineClass();
        new ParticleTypeBlockASM_1_13(internal, particleTypeBlockMotion, particleTypeMotion) .defineClass();

        new ParticleTypeDustASM_1_13(internal, particleTypeDust, particleType).defineClass();
        new ParticleTypeItemASM_1_13(internal, particleTypeItemMotion, particleTypeMotion).defineClass();

        new ParticleTypeRedstoneASM_1_13(internal, particleTypeRedstone).defineClass();
    }

    @Override
    public void storeParticleTypesToFields(MethodVisitor mv, ParticleVersion interfaceVersion) {
        int local_this = 0;

        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            Type particleReturnType = Type.getReturnType(m);
            Type particleReturnTypeImpl = getTypeImpl(particleReturnType);

            /*
            Instantiates certain particle type and put it in proper field.
             */
            mv.visitVarInsn(ALOAD, local_this);

            // try to convert particle name to current server version
            String resolvedName = particleRegistry.find(
                    interfaceVersion, particleName.toLowerCase(), ParticleVersion.V1_13
            );
            resolvedName = resolvedName != null ? resolvedName.toUpperCase() : null;

            // if found and it exists, then instantiate
            if (resolvedName != null && currentParticleSet.contains(resolvedName)) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = descNMS("ParticleParam");
                    particlesFieldDesc = descNMS("ParticleType");
                }
                else {
                    ctrParamDesc = descNMS("Particle");
                    particlesFieldDesc = descNMS("Particle");
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("Particles"),
                        resolvedName,
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else if (interfaceVersion.equals(ParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticleSet.contains("DUST")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("Particles"),
                        "DUST",
                        descNMS("Particle"));

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + descNMS("Particle") + ")V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(particle);
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().getInternalName(),
                    particleName,
                    particleReturnType.getDescriptor());
        }
    }

}
