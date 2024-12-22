package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeBlock;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.ParticleTypeASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.ParticleTypeBlockASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.ParticleTypeItemASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeSculkChargeASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeShriekASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeVibrationASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeVibrationSingleASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_20_5.ParticleTypeColorASM_1_20_5;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3.ParticleTypeDustASM_1_21_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3.ParticleTypeDustTransitionASM_1_21_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3.ParticleTypeRedstoneASM_1_21_3;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.21.3.</p>
 */
public class ParticleTypesProvider_1_21_3 extends ParticleTypesProvider {

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    protected final Map<String, String> currentParticlesMap;
    protected final String colorParticleOptionFactoryMethodName;

    public ParticleTypesProvider_1_21_3(ContextASM context) {
        this(context, context.internal.getParticles_1_19_3());
    }

    public ParticleTypesProvider_1_21_3(ContextASM context, Map<String, String> currentParticlesMap) {
        super(context);
        this.currentParticlesMap = currentParticlesMap;

        this.colorParticleOptionFactoryMethodName = context.internal.getColorParticleOptionFactoryMethodName_1_20_5();
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_17(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_21_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_INT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeDustTransitionASM_1_21_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_COLOR_TRANSITION_INT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeItemASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeVibrationSingleASM_1_19(context, ClassSkeleton.PARTICLE_TYPE_VIBRATION_SINGLE).registerClass();

        new ParticleTypeRedstoneASM_1_21_3(context).registerClass();

        new ParticleTypeSculkChargeASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SCULK_CHARGE_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeShriekASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SHRIEK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeVibrationASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_VIBRATION,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeColorASM_1_20_5(context,
                ClassSkeleton.PARTICLE_TYPE_COLOR,
                ClassSkeleton.PARTICLE_TYPE,
                colorParticleOptionFactoryMethodName)
                .registerClass();
    }

    @Override
    public void generateParticleFactoryMethods(ClassWriter cw, SpigotParticleVersion particleVersion,
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
                    .find(particleVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_20_5);

            // if it is SPELL_MOB particle in 1.8 list or ENTITY_EFFECT in 1.13 list, then visit invalid type
            // because it is not backward compatible
            // their "default color" was previously random, now I cannot guarantee same behavior
            // because color must now be defined explicitly
            if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8)
                    && particleName.equals("SPELL_MOB")) {
                visitInvalidType(mv, returnSkeleton);
            }
            else if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13)
                    && particleName.equals("ENTITY_EFFECT")) {
                visitInvalidType(mv, returnSkeleton);
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
            else if (particleVersion.equals(SpigotParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "()V", false);
            }
            else if ((particleName.equals("BARRIER") || particleName.equals("LIGHT"))
                    && currentParticlesMap.containsKey("block_marker")) {// maintain forward compatibility
                // get field name from Particles class associated with block_marker particle
                String fieldName = currentParticlesMap.get("block_marker");

                ClassSkeleton blockMarkerSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypeBlock.class);
                ClassMapping blockMarkerTypeImpl = blockMarkerSkeleton.getImpl(context.suffix);

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

                // get Material.<particleName> to use as block data, lol
                mv.visitFieldInsn(GETSTATIC,
                        refs.material.internalName(),
                        particleName,
                        refs.material.desc());

                // use it to invoke ParticleTypeBlock_Impl.of(Material.<particleName>)
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        blockMarkerTypeImpl.internalName(),
                        OF_METHOD_NAME,
                        "(" + refs.material.desc() + ")" + particleReturnType.desc(), false);
            }
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(...);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
