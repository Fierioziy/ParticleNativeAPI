package com.github.fierioziy.particlenativeapi.core.asm.particle.types;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypesProvider extends BaseASM {

    protected ParticleRegistry particleRegistry = new ParticleRegistry();

    public ParticleTypesProvider(InternalResolver resolver, String suffix) {
        super(resolver, suffix);
    }

    /**
     * <p>Defines all classes of particle types.</p>
     */
    public abstract void defineClasses();

    /**
     * <p>Generates bytecode for instantiating all
     * particle types implementations (or default if it's impossible)
     * and storing them in instance fields.</p>
     *
     * @param mv a <code>MethodVisitor</code> on which
     *           instructions writing occurs.
     * @param interfaceVersion a <code>ParticleVersion</code> enum
     *                         providing information about processed
     *                         interface version.
     */
    public abstract void storeParticleTypesToFields(MethodVisitor mv, SpigotParticleVersion interfaceVersion);

    protected void visitInvalidType(MethodVisitor mv, ClassMapping superType) {
        mv.visitTypeInsn(NEW, superType.internalName());
        mv.visitInsn(DUP);

        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);
    }

}
