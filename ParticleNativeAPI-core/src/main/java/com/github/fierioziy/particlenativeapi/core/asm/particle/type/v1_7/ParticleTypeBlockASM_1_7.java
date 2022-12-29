package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_7.skeleton.ParticleTypeComplexSkeletonASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeBlockASM_1_7 extends ParticleTypeComplexSkeletonASM_1_7 {

    public ParticleTypeBlockASM_1_7(ContextASM context,
                                    ClassSkeleton superType, ClassSkeleton returnType) {
        super(context, superType, returnType);
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_of(cw);

        writeCommonMethods(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                OF_METHOD_NAME,
                "(" + refs.material.desc() + "B)" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_meta = 2;
        int local_particleType = 3;

        /*
        ParticleTypeImpl_X particleType = this.particleWrapper;
         */
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_WRAPPER_FIELD_NAME,
                implReturnType.desc());
        mv.visitVarInsn(ASTORE, local_particleType);

        /*
        particleType.setParticle(
            new StringBuilder(this.particle)
                .append(item.getId())
                .append("_")
                .append(meta)
                .toString()
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // new StringBuilder(this.particle)
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);

        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                "Ljava/lang/String;");

        mv.visitMethodInsn(INVOKESPECIAL,
                "java/lang/StringBuilder",
                "<init>", "(Ljava/lang/String;)V", false);

        // .append(item.getId());
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, refs.material.internalName(), "getId", "()I", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);

        // .append("_").append(meta);
        mv.visitLdcInsn("_");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ILOAD, local_meta);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);

        // .toString()
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME, "(Ljava/lang/String;)V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
