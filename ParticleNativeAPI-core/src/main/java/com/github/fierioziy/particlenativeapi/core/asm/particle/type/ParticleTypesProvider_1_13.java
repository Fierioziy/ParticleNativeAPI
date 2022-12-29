package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
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

    public ParticleTypesProvider_1_13(ContextASM context) {
        super(context);

        currentParticleSet = context.internal.getParticles_1_13();
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_13(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_13(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_13(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_13(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_DUST,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeItemASM_1_13(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeRedstoneASM_1_13(context).registerClass();
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
                    .find(interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_13)
                    .map(String::toUpperCase);

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
            else if (interfaceVersion.equals(SpigotParticleVersion.V1_8)
                    && particleName.equals("REDSTONE")
                    && currentParticleSet.contains("DUST")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "()V", false);
            }
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(...);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
