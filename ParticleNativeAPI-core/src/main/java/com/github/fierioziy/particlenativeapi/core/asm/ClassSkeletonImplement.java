package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

/**
 * <p>Represents a class generation, where a class implements interface.</p>
 */
public abstract class ClassSkeletonImplement extends BaseASM {

    protected Type implType;
    protected Type superType;
    protected Type interfaceType;

    private final String suffix;

    public ClassSkeletonImplement(InternalResolver resolver, Type interfaceType, String suffix) {
        super(resolver);
        this.suffix = suffix;
        this.implType = getTypeImpl(interfaceType);
        this.superType = Type.getObjectType("java/lang/Object");
        this.interfaceType = interfaceType;
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
    protected final Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, suffix);
    }

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
                superType.getInternalName(), new String[] { interfaceType.getInternalName() });

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
     *
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

    public Type getInterfaceType() {
        return interfaceType;
    }

}
