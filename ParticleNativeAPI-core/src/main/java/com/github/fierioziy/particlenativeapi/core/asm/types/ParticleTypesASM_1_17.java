package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.api.types.ParticleType;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.17.</p>
 */
public class ParticleTypesASM_1_17 extends ParticleTypesASM {

    private static final String SUFFIX = "_1_17";

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    private Map<String, String> currentParticlesMap;

    public ParticleTypesASM_1_17(InternalResolver resolver) {
        super(resolver, SUFFIX);

        currentParticlesMap = resolver.getParticles_1_17();
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_17(internal, SUFFIX, particleType).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeColorable).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeMotion).defineClass();
        new ParticleTypeASM_1_17(internal, SUFFIX, particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_17(internal, SUFFIX, particleTypeBlock, particleType).defineClass();
        new ParticleTypeBlockASM_1_17(internal, SUFFIX, particleTypeBlockMotion, particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_17(internal, SUFFIX, particleTypeDust, particleType).defineClass();
        new ParticleTypeDustTransitionASM_1_17(internal, SUFFIX, particleTypeDustTransition, particleType).defineClass();
        new ParticleTypeItemASM_1_17(internal, SUFFIX, particleTypeItemMotion, particleTypeMotion).defineClass();
        new ParticleTypeVibrationASM_1_17(internal, SUFFIX, particleTypeVibration).defineClass();

        new ParticleTypeRedstoneASM_1_17(internal, SUFFIX, particleTypeRedstone).defineClass();
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
            Optional<String> resolvedName = particleRegistry.find(
                    interfaceVersion, particleName.toLowerCase(), ParticleVersion.V1_13
            );

            // if found and it exists, then instantiate
            if (resolvedName.isPresent() && currentParticlesMap.containsKey(resolvedName.get())) {
                // get field name from Particles class associated with particle name
                String fieldName = currentParticlesMap.get(resolvedName.get());

                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = descNMS("core/particles/ParticleParam");
                    particlesFieldDesc = descNMS("core/particles/ParticleType");
                }
                else {
                    ctrParamDesc = descNMS("core/particles/Particle");
                    particlesFieldDesc = descNMS("core/particles/Particle");
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("core/particles/Particles"),
                        fieldName,
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else if (interfaceVersion.equals(ParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // get field name from Particles class associated with dust particle
                String fieldName = currentParticlesMap.get("dust");

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("core/particles/Particles"),
                        fieldName,
                        descNMS("core/particles/Particle"));

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + descNMS("core/particles/Particle") + ")V", false);
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
