package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.api.types.*;
import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * <p>A base class for all Particle ASM related classes. It holds basic fields
 * and helper methods for manipulating bytecode.</p>
 */
public abstract class ParticleBaseASM extends BaseASM {

    /**
     * <p>A <code>Type</code> object representing <code>ParticleType</code> class.</p>
     */
    protected Type particleType =               Type.getType(ParticleType.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeBlock</code> class.</p>
     */
    protected Type particleTypeBlock =          Type.getType(ParticleTypeBlock.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeBlockMotion</code> class.</p>
     */
    protected Type particleTypeBlockMotion =    Type.getType(ParticleTypeBlockMotion.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeColorable</code> class.</p>
     */
    protected Type particleTypeColorable =      Type.getType(ParticleTypeColorable.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeMotion</code> class.</p>
     */
    protected Type particleTypeMotion =         Type.getType(ParticleTypeMotion.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeDust</code> class.</p>
     */
    protected Type particleTypeDust =           Type.getType(ParticleTypeDust.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeItemMotion</code> class.</p>
     */
    protected Type particleTypeItemMotion =     Type.getType(ParticleTypeItemMotion.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeNote</code> class.</p>
     */
    protected Type particleTypeNote =           Type.getType(ParticleTypeNote.class);

    /**
     * <p>A <code>Type</code> object representing <code>ParticleTypeRedstone</code> class.</p>
     */
    protected Type particleTypeRedstone =       Type.getType(ParticleTypeRedstone.class);

    public ParticleBaseASM(InternalResolver resolver) {
        super(resolver);
    }

    /**
     * <p>Gets a <code>Type</code> object representing
     * class implementation of parameter <code>Type</code> object
     * class representation.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  certain class.
     * @return a <code>Type</code> object representing class that implements
     * class represented by parameter <code>Type</code> object.
     */
    protected abstract Type getTypeImpl(Type superType);

    /**
     * <p>Visits an <code>isValid</code> method returning <code>true</code>
     * on certain class <code>ClassWriter</code> object.</p>
     *
     * <p>It is only used by <code>ParticleTypeASM</code> related classes.</p>
     *
     * @param cw a <code>ClassWriter</code> object on which method definition
     *           should be added.
     */
    protected void addIsValid(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "isValid", "()Z", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
