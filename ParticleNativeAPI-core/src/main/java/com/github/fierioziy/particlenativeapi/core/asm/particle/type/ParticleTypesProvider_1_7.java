package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.ParticleTypeASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.ParticleTypeBlockASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.ParticleTypeItemASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.7.</p>
 */
public class ParticleTypesProvider_1_7 extends ParticleTypesProvider {

    public ParticleTypesProvider_1_7(ContextASM context) {
        super(context);
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_7(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_7(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_7(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_7(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();
        new ParticleTypeASM_1_7(context, ClassSkeleton.PARTICLE_TYPE_REDSTONE).registerClass();

        new ParticleTypeBlockASM_1_7(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_7(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeItemASM_1_7(context,
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

            // try to convert particle name to current server version
            Optional<String> resolvedName = particleRegistry.find(
                    interfaceVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_7
            );

            // if found, it exists at least in 1.7.10
            if (resolvedName.isPresent()) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitLdcInsn(resolvedName.get());

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(Ljava/lang/String;)V", false);
            }
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl("particle_name");

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
