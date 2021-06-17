package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public abstract class ConnectionASM extends BaseASM {

    protected Type implType;
    protected Type interfaceType;

    public ConnectionASM(InternalResolver resolver, Type interfaceType) {
        super(resolver);
        this.implType = getTypeImpl(interfaceType);
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
                "java/lang/Object", new String[] { interfaceType.getInternalName() });

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

}
