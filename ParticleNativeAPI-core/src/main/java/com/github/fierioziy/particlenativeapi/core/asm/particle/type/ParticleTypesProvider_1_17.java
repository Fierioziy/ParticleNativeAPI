package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.17.</p>
 */
public class ParticleTypesProvider_1_17 extends ParticleTypesProvider {

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    private final Map<String, String> currentParticlesMap;

    public ParticleTypesProvider_1_17(ContextASM context) {
        super(context);

        this.currentParticlesMap = context.internal.getParticles_1_17();
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_FLOAT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeDustTransitionASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_COLOR_TRANSITION_FLOAT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeItemASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeVibrationSingleASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_VIBRATION_SINGLE).registerClass();

        new ParticleTypeRedstoneASM_1_17(context).registerClass();
    }

    @Override
    public void generateParticleFactoryMethods(ClassWriter cw, SpigotParticleVersion interfaceVersion,
                                               ClassSkeleton particleListSkeleton) {
        for (Method m : particleListSkeleton.getSuperClass().getSuperclass().getDeclaredMethods()) {
            String particleName = m.getName();

            ClassSkeleton returnSkeleton = ClassSkeleton.getByInterfaceClass(m.getReturnType());
            ClassMapping particleReturnType = returnSkeleton.getInterfaceType();
            ClassMapping particleReturnTypeImpl = returnSkeleton.getImpl(context.suffix);

            MethodVisitor mv = cw.visitMethod(ACC_PROTECTED,
                    particleName,
                    "()" + particleReturnType.desc(), null, null);
            mv.visitCode();

            int local_this = 0;

            // try to convert particle name to current server version
            Optional<String> resolvedName = particleRegistry
                    .find(interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_13);

            // if it is anything in 1.19 part list, visit invalid particle type
            if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_19_PART)) {
                visitInvalidType(mv, returnSkeleton);
            }
            // maintain forward compatibility for redstone -> dust
            else if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8)
                    && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "()V", false);
            }
            // if found and it exists, then instantiate
            else if (resolvedName.isPresent() && currentParticlesMap.containsKey(resolvedName.get())) {
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
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(...);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
