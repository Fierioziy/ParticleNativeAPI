package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.api.types.ParticleType;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.18.</p>
 */
public class ParticleTypesProvider_1_18 extends ParticleTypesProvider {

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    protected Map<String, String> currentParticlesMap;

    public ParticleTypesProvider_1_18(InternalResolver resolver) {
        this(resolver, "_1_18");
    }

    public ParticleTypesProvider_1_18(InternalResolver resolver, String suffix) {
        super(resolver, suffix);

        currentParticlesMap = resolver.getParticles_1_17();
    }

    public ParticleTypesProvider_1_18(InternalResolver resolver, String suffix,
                                      Map<String, String> currentParticlesMap) {
        super(resolver, suffix);

        this.currentParticlesMap = currentParticlesMap;
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_17(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_17(internal, suffix, refs.particleTypeNote).defineClass();

        new ParticleTypeBlockASM_1_17(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_17(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeDustASM_1_17(internal, suffix, refs.particleTypeDust, refs.particleType).defineClass();
        new ParticleTypeDustTransitionASM_1_17(internal, suffix, refs.particleTypeDustTransition, refs.particleType).defineClass();
        new ParticleTypeItemASM_1_17(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();
        new ParticleTypeVibrationASM_1_17(internal, suffix, refs.particleTypeVibration).defineClass();

        new ParticleTypeRedstoneASM_1_17(internal, suffix, refs.particleTypeRedstone).defineClass();
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
                    interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_18
            );

            // if found and it exists, then instantiate
            if (resolvedName.isPresent() && currentParticlesMap.containsKey(resolvedName.get())) {
                // get field name from Particles class associated with particle name
                String fieldName = currentParticlesMap.get(resolvedName.get());

                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = refs.particleParam_1_17.desc();
                    particlesFieldDesc = refs.particleTypeNms_1_17.desc();
                }
                else {
                    ctrParamDesc = refs.particle_1_17.desc();
                    particlesFieldDesc = refs.particle_1_17.desc();
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        refs.particles_1_17.internalName(),
                        fieldName,
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else if (interfaceVersion.equals(SpigotParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // get field name from Particles class associated with dust particle
                String fieldName = currentParticlesMap.get("dust");

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        refs.particles_1_17.internalName(),
                        fieldName,
                        refs.particle_1_17.desc());

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + refs.particle_1_17.desc() + ")V", false);
            }
            else if ((particleName.equals("BARRIER") || particleName.equals("LIGHT"))
                    && currentParticlesMap.containsKey("block_marker")) {// maintain forward compatibility
                // get field name from Particles class associated with block_marker particle
                String fieldName = currentParticlesMap.get("block_marker");

                ClassMapping blockMarkerType = refs.particleTypeBlock;
                ClassMapping blockMarkerTypeImpl = blockMarkerType.impl(suffix);

                // instantiate block_marker particle type implementation
                mv.visitTypeInsn(NEW, blockMarkerTypeImpl.internalName());
                mv.visitInsn(DUP);

                // use particle from static field as parameter
                mv.visitFieldInsn(GETSTATIC,
                        refs.particles_1_17.internalName(),
                        fieldName,
                        refs.particle_1_17.desc());

                mv.visitMethodInsn(INVOKESPECIAL,
                        blockMarkerTypeImpl.internalName(),
                        "<init>",
                        "(" + refs.particle_1_17.desc() + ")V", false);

                // get Material.<particleName> to use as block data
                mv.visitFieldInsn(GETSTATIC,
                        refs.material.internalName(),
                        particleName,
                        refs.material.desc());

                // use it to invoke ParticleTypeBlock_Impl.of(Material.<particleName>)
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        blockMarkerTypeImpl.internalName(),
                        "of",
                        "(" + refs.material.desc() + ")" + particleReturnType.desc(),false);
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
