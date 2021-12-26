package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.api.types.ParticleType;
import com.github.fierioziy.particlenativeapi.api.types.ParticleTypeRedstone;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.18.</p>
 */
public class ParticleTypesASM_1_18 extends ParticleTypesASM {

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    private Map<String, String> currentParticlesMap;

    public ParticleTypesASM_1_18(InternalResolver resolver) {
        super(resolver);

        currentParticlesMap = resolver.getParticles_1_17();
    }

    // this is intentional, particle type implementation was unchanged
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_17");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_17(internal, particleType)           .defineClass();
        new ParticleTypeASM_1_17(internal, particleTypeColorable)  .defineClass();
        new ParticleTypeASM_1_17(internal, particleTypeMotion)     .defineClass();
        new ParticleTypeASM_1_17(internal, particleTypeNote)       .defineClass();

        new ParticleTypeBlockASM_1_17(internal, particleTypeBlock,       particleType)       .defineClass();
        new ParticleTypeBlockASM_1_17(internal, particleTypeBlockMotion, particleTypeMotion) .defineClass();

        new ParticleTypeDustASM_1_17(internal,              particleTypeDust, particleType)             .defineClass();
        new ParticleTypeDustTransitionASM_1_17(internal,    particleTypeDustTransition, particleType)   .defineClass();
        new ParticleTypeItemASM_1_17(internal,              particleTypeItemMotion, particleTypeMotion) .defineClass();
        new ParticleTypeVibrationASM_1_17(internal,         particleTypeVibration)                      .defineClass();

        new ParticleTypeRedstoneASM_1_17(internal, particleTypeRedstone).defineClass();
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
                    interfaceVersion, particleName.toLowerCase(), ParticleVersion.V1_18
            );

            // if found and it exists, then instantiate
            if (resolvedName != null && currentParticlesMap.containsKey(resolvedName)) {
                // get field name from Particles class associated with particle name
                String fieldName = currentParticlesMap.get(resolvedName);

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
            else if ((particleName.equals("BARRIER") || particleName.equals("LIGHT"))
                    && currentParticlesMap.containsKey("block_marker")) {// maintain forward compatibility
                // get field name from Particles class associated with block_marker particle
                String fieldName = currentParticlesMap.get("block_marker");

                Type blockMarkerType = particleTypeBlock;
                Type blockMarkerTypeImpl = getTypeImpl(blockMarkerType);

                // instantiate block_marker particle type implementation
                mv.visitTypeInsn(NEW, blockMarkerTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // use particle from static field as parameter
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("core/particles/Particles"),
                        fieldName,
                        descNMS("core/particles/Particle"));

                mv.visitMethodInsn(INVOKESPECIAL,
                        blockMarkerTypeImpl.getInternalName(),
                        "<init>",
                        "(" + descNMS("core/particles/Particle") + ")V", false);

                // get Material.<particleName> to use as block data
                mv.visitFieldInsn(GETSTATIC,
                        "org/bukkit/Material",
                        particleName,
                        "Lorg/bukkit/Material;");

                // use it to invoke ParticleTypeBlock_Impl.of(Material.<particleName>)
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        blockMarkerTypeImpl.getInternalName(),
                        "of",
                        "(Lorg/bukkit/Material;)" + particleReturnType.getDescriptor(),false);
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
