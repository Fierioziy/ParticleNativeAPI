package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleVersion;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public abstract class ParticleTypesASM extends BaseASM {

    protected ParticleRegistry particleRegistry = new ParticleRegistry();

    public ParticleTypesASM(InternalResolver resolver) {
        super(resolver);
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
    public abstract void storeParticleTypesToFields(MethodVisitor mv, ParticleVersion interfaceVersion);

    /**
     * <p>Visits a default <code>ParticleType</code> related constructor.</p>
     *
     * @param mv a <code>MethodVisitor</code> on which visiting occurs.
     * @param superType a <code>Type</code> object representing
     *                  <code>ParticleType</code> related class.
     */
    protected void visitInvalidType(MethodVisitor mv, Type superType) {
        mv.visitTypeInsn(NEW, superType.getInternalName());
        mv.visitInsn(DUP);

        mv.visitMethodInsn(INVOKESPECIAL,
                superType.getInternalName(),
                "<init>",
                "()V", false);
    }

}
