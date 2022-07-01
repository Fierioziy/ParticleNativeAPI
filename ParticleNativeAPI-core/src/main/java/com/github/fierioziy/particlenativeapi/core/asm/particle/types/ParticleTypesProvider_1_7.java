package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_7.ParticleTypeASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_7.ParticleTypeBlockASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_7.ParticleTypeItemASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.7.</p>
 */
public class ParticleTypesProvider_1_7 extends ParticleTypesProvider {

    public ParticleTypesProvider_1_7(InternalResolver resolver) {
        super(resolver, "_1_7");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_7(internal, suffix, refs.particleType).defineClass();
        new ParticleTypeASM_1_7(internal, suffix, refs.particleTypeColorable).defineClass();
        new ParticleTypeASM_1_7(internal, suffix, refs.particleTypeMotion).defineClass();
        new ParticleTypeASM_1_7(internal, suffix, refs.particleTypeNote).defineClass();
        new ParticleTypeASM_1_7(internal, suffix, refs.particleTypeRedstone).defineClass();

        new ParticleTypeBlockASM_1_7(internal, suffix, refs.particleTypeBlock, refs.particleType).defineClass();
        new ParticleTypeBlockASM_1_7(internal, suffix, refs.particleTypeBlockMotion, refs.particleTypeMotion).defineClass();

        new ParticleTypeItemASM_1_7(internal, suffix, refs.particleTypeItemMotion, refs.particleTypeMotion).defineClass();
    }

    @Override
    public void storeParticleTypesToFields(MethodVisitor mv, SpigotParticleVersion interfaceVersion) {
        int local_this = 0;

        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            ClassMapping particleReturnType = refs.of(m.getReturnType());
            ClassMapping particleReturnTypeImpl = particleReturnType.impl(suffix);

            mv.visitVarInsn(ALOAD, local_this);

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
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl("particle_name");
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().internalName(),
                    particleName,
                    particleReturnType.desc());
        }
    }

}
