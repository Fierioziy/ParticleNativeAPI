package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_8.ParticleTypeASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_8.ParticleTypeBlockASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.types.v1_8.ParticleTypeItemASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.8.</p>
 */
public class ParticleTypesASM_1_8 extends ParticleTypesASM {

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private Set<String> currentParticleSet;

    public ParticleTypesASM_1_8(InternalResolver resolver) {
        super(resolver);

        try {
            currentParticleSet = resolver.getParticles_1_8();
        } catch (ClassNotFoundException e) {
            throw new ParticleException("Error: couldn't find "
                    + classNameNMS("EnumParticle") + " class!", e);
        }
    }

    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_1_8");
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_8(internal, particleType)           .defineClass();
        new ParticleTypeASM_1_8(internal, particleTypeColorable)  .defineClass();
        new ParticleTypeASM_1_8(internal, particleTypeMotion)     .defineClass();
        new ParticleTypeASM_1_8(internal, particleTypeNote)       .defineClass();
        new ParticleTypeASM_1_8(internal, particleTypeRedstone)   .defineClass();

        new ParticleTypeBlockASM_1_8(internal, particleTypeBlock,       particleType)       .defineClass();
        new ParticleTypeBlockASM_1_8(internal, particleTypeBlockMotion, particleTypeMotion) .defineClass();

        new ParticleTypeItemASM_1_8(internal, particleTypeItemMotion, particleTypeMotion).defineClass();
    }

    @Override
    public void storeParticleTypesToFields(MethodVisitor mv, ParticleVersion interfaceVersion) {
        for (Method m : interfaceVersion.getParticleTypesClass().getDeclaredMethods()) {
            String particleName = m.getName();

            Type particleReturnType = Type.getReturnType(m);
            Type particleReturnTypeImpl = getTypeImpl(particleReturnType);

            /*
            Instantiates certain particle type and put it in proper field.
             */
            mv.visitVarInsn(ALOAD, 0);

            // try to convert particle name to current server version
            String resolvedName = particleRegistry.find(
                    interfaceVersion, particleName, ParticleVersion.V1_8
            );

            // if found and it exists, then instantiate
            if (resolvedName != null && currentParticleSet.contains(resolvedName)) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.getInternalName());
                mv.visitInsn(DUP);

                // get enum directly
                mv.visitFieldInsn(GETSTATIC,
                        internalNMS("EnumParticle"),
                        resolvedName,
                        descNMS("EnumParticle"));

                // new int[0]
                mv.visitInsn(ICONST_0);
                mv.visitIntInsn(NEWARRAY, T_INT);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.getInternalName(),
                        "<init>",
                        "(" + descNMS("EnumParticle") + "[I)V", false);
            }
            else visitInvalidType(mv, particleReturnType);

            // PARTICLE_NAME = new SomeParticleType_Impl(enum, new int[0]);
            mv.visitFieldInsn(PUTFIELD,
                    interfaceVersion.getImplType().getInternalName(),
                    particleName,
                    particleReturnType.getDescriptor());
        }
    }

}
