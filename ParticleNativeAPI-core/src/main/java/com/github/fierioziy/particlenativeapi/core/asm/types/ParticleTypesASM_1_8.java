package com.github.fierioziy.particlenativeapi.core.asm.types;

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

    private static final String SUFFIX = "_1_8";

    /**
     * <p>Set containing all available particles in current Spigot version.</p>
     */
    private Set<String> currentParticleSet;

    public ParticleTypesASM_1_8(InternalResolver resolver) {
        super(resolver, SUFFIX);

        currentParticleSet = resolver.getParticles_1_8();
    }

    @Override
    public void defineClasses() {
        new ParticleTypeASM_1_8(internal, SUFFIX, particleType).defineClass();
        new ParticleTypeASM_1_8(internal, SUFFIX, particleTypeColorable).defineClass();
        new ParticleTypeASM_1_8(internal, SUFFIX, particleTypeMotion).defineClass();
        new ParticleTypeASM_1_8(internal, SUFFIX, particleTypeNote).defineClass();
        new ParticleTypeASM_1_8(internal, SUFFIX, particleTypeRedstone).defineClass();

        new ParticleTypeBlockASM_1_8(internal, SUFFIX, particleTypeBlock, particleType).defineClass();
        new ParticleTypeBlockASM_1_8(internal, SUFFIX, particleTypeBlockMotion, particleTypeMotion).defineClass();

        new ParticleTypeItemASM_1_8(internal, SUFFIX, particleTypeItemMotion, particleTypeMotion).defineClass();
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
                    interfaceVersion, particleName.toLowerCase(), ParticleVersion.V1_8
            );
            resolvedName = resolvedName != null ? resolvedName.toUpperCase() : null;

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
