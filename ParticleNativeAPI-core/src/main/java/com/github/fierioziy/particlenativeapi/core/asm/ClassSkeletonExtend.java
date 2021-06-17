package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

public abstract class ClassSkeletonExtend extends BaseASM {

    protected Type implType;
    protected Type superType;

    public ClassSkeletonExtend(InternalResolver resolver, Type superType) {
        super(resolver);
        this.implType = getTypeImpl(superType);
        this.superType = superType != null ? superType : Type.getObjectType("java/lang/Object");
    }

    /**
     * <p>Gets a <code>Type</code> object representing an implementation
     * of class represented by parameter <code>Type object</code>.</p>
     *
     * @param superType a <code>Type</code> object associated
     *                  with base class.
     * @return a <code>Type</code> object representing class
     * extending or implementing parameter's class represented
     * by <code>Type</code> object.
     */
    protected abstract Type getTypeImpl(Type superType);

    /**
     * <p>Generates class and defines it to class loader.</p>
     */
    public void defineClass() {
        internal.getTempClassLoader().defineClass(
                implType.getClassName(),
                generate()
        );
    }

    private byte[] generate() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.getInternalName(), null,
                superType.getInternalName(), null);

        writeFields(cw);
        writeConstructor(cw);

        writeMethods(cw);

        cw.visitEnd();
        return cw.toByteArray();
    }


    /**
     * <p>Writes fields necessary to properly represent certain particle.</p>
     *
     * @param cw a <code>ClassWriter</code> on which fields writing should happen.
     */
    protected abstract void writeFields(ClassWriter cw);

    /**
     * <p>Writes constructor code to a class.</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor
     *           writing should happen.
     */
    protected abstract void writeConstructor(ClassWriter cw);

    /**
     * <p>Writes all necessary methods to a class.</p>
     * @param cw a <code>ClassWriter</code> on which constructor
     *           writing should happen.
     */
    protected abstract void writeMethods(ClassWriter cw);

    public Type getImplType() {
        return implType;
    }

    public Type getSuperType() {
        return superType;
    }

}
