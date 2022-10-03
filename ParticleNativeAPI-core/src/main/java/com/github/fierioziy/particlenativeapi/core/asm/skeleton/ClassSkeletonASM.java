package com.github.fierioziy.particlenativeapi.core.asm.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;

import java.util.Arrays;

public abstract class ClassSkeletonASM extends BaseASM {

    protected ClassMapping superType;
    protected ClassMapping interfaceType;
    protected ClassMapping implType;

    protected ClassMapping[] interfaceTypes;

    public ClassSkeletonASM(InternalResolver resolver, String suffix,
                            ClassSkeleton skeleton,
                            ClassMapping... interfaceTypes) {
        super(resolver, suffix);
        superType = skeleton.getSuperType();
        interfaceType = skeleton.getInterfaceType();
        implType = skeleton.getImpl(suffix);
        this.interfaceTypes = interfaceTypes;
    }

    public void registerClass() {
        internal.getParticleNativeClassLoader()
                .registerClass(implType.className(), generateBytecode());
    }

    public Class<?> loadClass() throws ClassNotFoundException {
        return internal.getParticleNativeClassLoader()
                .loadClass(implType.className());
    }

    private byte[] generateBytecode() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        String[] interfaceInternalNames = Arrays.stream(interfaceTypes)
                .map(ClassMapping::internalName)
                .toArray(String[]::new);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.internalName(), null,
                superType.internalName(), interfaceInternalNames);

        writeFields(cw);
        writeConstructors(cw);

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
    protected abstract void writeConstructors(ClassWriter cw);

    /**
     * <p>Writes all necessary methods to a class.</p>
     *
     * @param cw a <code>ClassWriter</code> on which constructor
     *           writing should happen.
     */
    protected abstract void writeMethods(ClassWriter cw);

    public ClassMapping getImplType() {
        return implType;
    }

    public ClassMapping getSuperType() {
        return superType;
    }

}
