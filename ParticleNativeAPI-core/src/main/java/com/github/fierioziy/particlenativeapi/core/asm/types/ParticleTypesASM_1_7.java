package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.types.v1_7.ParticleTypeASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_7.ParticleTypeBlockASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_7.ParticleTypeItemASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.7.</p>
 */
public class ParticleTypesASM_1_7 extends ParticleTypesASM {

    public ParticleTypesASM_1_7(InternalResolver resolver) {
        super(resolver);
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_7");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_7(internal, particleType)           .defineClass();
        new ParticleTypeASM_1_7(internal, particleTypeColorable)  .defineClass();
        new ParticleTypeASM_1_7(internal, particleTypeMotion)     .defineClass();
        new ParticleTypeASM_1_7(internal, particleTypeNote)       .defineClass();
        new ParticleTypeASM_1_7(internal, particleTypeRedstone)   .defineClass();

        new ParticleTypeBlockASM_1_7(internal, particleTypeBlock,       particleType)       .defineClass();
        new ParticleTypeBlockASM_1_7(internal, particleTypeBlockMotion, particleTypeMotion) .defineClass();

        new ParticleTypeItemASM_1_7(internal, particleTypeItemMotion, particleTypeMotion).defineClass();
    }

    @Override
    public void storeParticleTypesToFields(MethodVisitor mv, ParticleVersion interfaceVersion) {
        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            Type particleReturnType = Type.getReturnType(m);
            Type particleReturnTypeImpl = getTypeImpl(particleReturnType);

            mv.visitVarInsn(ALOAD, 0);

            // try to convert particle name to current server version
            String resolvedName = particleRegistry.find(
                    interfaceVersion, particleName, ParticleVersion.V1_7
            );

            // if found, it exists at least in 1.7.10
            if (resolvedName != null) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                mv.visitLdcInsn(resolvedName);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(Ljava/lang/String;)V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl("particle_name");
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().getInternalName(),
                    particleName,
                    particleReturnType.getDescriptor());
        }
    }

}
