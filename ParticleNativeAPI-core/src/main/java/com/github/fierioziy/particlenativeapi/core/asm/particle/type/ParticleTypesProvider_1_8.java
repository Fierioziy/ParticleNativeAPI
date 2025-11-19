package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.ParticleTypeASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.ParticleTypeBlockASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.ParticleTypeItemASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
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

    public ParticleTypesProvider_1_8(ContextASM context) {
        super(context);

        currentParticleSet = context.internal.getParticles_1_8();
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_8(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_8(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_8(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_8(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();
        new ParticleTypeASM_1_8(context, ClassSkeleton.PARTICLE_TYPE_REDSTONE).registerClass();

        new ParticleTypeBlockASM_1_8(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_8(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeItemASM_1_8(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();
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

            // try to convert particle name to current particle version
            Optional<String> resolvedName = particleRegistry
                    .find(interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_8)
                    .map(String::toUpperCase);

            // if it is anything in 1.19 part list, visit invalid particle type
            if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_19_PART)) {
                visitInvalidType(mv, returnSkeleton);
            }
            // if found and it exists, then instantiate
            else if (resolvedName.isPresent() && currentParticleSet.contains(resolvedName.get())) {
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
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(EnumParticle.SOME_PARTICLE, new int[0]);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
