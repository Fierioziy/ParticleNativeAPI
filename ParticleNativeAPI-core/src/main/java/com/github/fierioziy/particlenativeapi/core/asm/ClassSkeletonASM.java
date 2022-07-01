package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;

import java.util.Arrays;

public abstract class ClassSkeletonASM extends BaseASM {

    protected ClassMapping superType;
    protected ClassMapping[] interfaceTypes;
    protected ClassMapping implType;

    public ClassSkeletonASM(InternalResolver resolver, String suffix,
                            ClassMapping superType, ClassMapping... interfaceTypes) {
        this(resolver, suffix, false, superType, interfaceTypes);
    }

    public ClassSkeletonASM(InternalResolver resolver, String suffix, boolean forceSuffixOnFirstInterface,
                            ClassMapping superType, ClassMapping... interfaceTypes) {
        super(resolver, suffix);

        if (superType.equals(refs.OBJECT) || forceSuffixOnFirstInterface){
            if (interfaceTypes.length == 0) {
                throw new ParticleException("Can't infer class to base name on");
            }

            this.superType = superType;
            this.interfaceTypes = interfaceTypes;
            this.implType = this.interfaceTypes[0].impl(suffix);
        }
        else {
            this.superType = superType;
            this.interfaceTypes = interfaceTypes;
            this.implType = this.superType.impl(suffix);
        }
    }

    /**
     * Generates and defines class to classloader.
     *
     * @return a generated {@link Class} object.
     */
    public Class<?> defineClass() {
        return internal.getParticleNativeClassLoader().defineClass(
                implType.className(),
                generate()
        );
    }

    private byte[] generate() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        String[] interfaceInternalNames = Arrays.stream(interfaceTypes)
                .map(ClassMapping::internalName)
                .toArray(String[]::new);

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER,
                implType.internalName(), null,
                superType.internalName(), interfaceInternalNames);

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

    public ClassMapping getImplType() {
        return implType;
    }

    public ClassMapping getSuperType() {
        return superType;
    }

}
