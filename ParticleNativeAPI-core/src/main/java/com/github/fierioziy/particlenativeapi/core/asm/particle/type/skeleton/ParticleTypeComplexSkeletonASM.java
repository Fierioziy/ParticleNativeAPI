package com.github.fierioziy.particlenativeapi.core.asm.particle.type.skeleton;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeletonASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypeComplexSkeletonASM extends ClassSkeletonASM {

    protected final ClassMapping superReturnType;
    protected final ClassMapping interfaceReturnType;
    protected final ClassMapping implReturnType;

    public ParticleTypeComplexSkeletonASM(InternalResolver internal, String suffix,
                                          ClassSkeleton superType, ClassSkeleton returnType) {
        super(internal, suffix, superType);

        this.superReturnType = returnType.getSuperType();
        this.interfaceReturnType = returnType.getInterfaceType();
        this.implReturnType = returnType.getImpl(suffix);
    }

    protected void writeParticleTypeField(ClassWriter cw) {
        cw.visitField(0, PARTICLE_WRAPPER_FIELD_NAME, implReturnType.desc(), null, null).visitEnd();
    }

    protected void writeParticleTypeAssignment(MethodVisitor mv) {
        // this will be constant in all constructors
        int local_this = 0;

        // this.particleType = new ParticleTypeImpl_X();
        mv.visitVarInsn(ALOAD, local_this);

        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>",
                "()V", false);

        mv.visitFieldInsn(PUTFIELD,
                implType.internalName(),
                PARTICLE_WRAPPER_FIELD_NAME,
                implReturnType.desc());
    }

    protected void writeCommonMethods(ClassWriter cw) {
        writeMethod_isValid(cw);
    }

    private void writeMethod_isValid(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, IS_VALID_METHOD_NAME, "()Z", null, null);
        mv.visitCode();

        mv.visitInsn(ICONST_1);
        mv.visitInsn(IRETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
