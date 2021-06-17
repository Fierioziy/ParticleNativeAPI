package com.github.fierioziy.particlenativeapi.core.asm.types;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public abstract class ParticleTypeASM extends BaseASM {

    protected Type implType;
    protected Type superType;

    public ParticleTypeASM(InternalResolver resolver, Type superType) {
        super(resolver);
        this.implType = getTypeImpl(superType);
        this.superType = superType;
    }

    /**
     * <p>Gets a <code>Type</code> object representing
     * class implementation of parameter <code>Type</code> object
     * class representation.</p>
     *
     * @param superType a <code>Type</code> object representing
     *                  certain class.
     *
     * @return a <code>Type</code> object representing class that implements
     *         class represented by parameter <code>Type</code> object.
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
        writeIsValidMethod(cw);

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

    /**
     * <p>Writes an <code>isValid</code> method returning <code>true</code>
     * to a class.</p>
     *
     * @param cw a <code>ClassWriter</code> object on which method writing
     *           should happen.
     */
    protected void writeIsValidMethod(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "isValid", "()Z", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
