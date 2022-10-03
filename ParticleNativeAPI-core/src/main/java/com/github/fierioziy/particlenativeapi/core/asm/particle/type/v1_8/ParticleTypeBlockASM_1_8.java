package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8;

import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.skeleton.ParticleTypeComplexSkeletonASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeBlockASM_1_8 extends ParticleTypeComplexSkeletonASM_1_8 {

    public ParticleTypeBlockASM_1_8(InternalResolver resolver, String suffix,
                                    ClassSkeleton superType, ClassSkeleton returnType) {
        super(resolver, suffix, superType, returnType);
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
            this.particle,
            new int[] {
                material.getId(),
                material.getId() | (meta << 12)
            }
        );
         */
        mv.visitVarInsn(ALOAD, local_particleType);

        // get particle from field
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                PARTICLE_FIELD_NAME,
                refs.enumParticle.desc());

        // new int[2]
        mv.visitInsn(ICONST_2);
        mv.visitIntInsn(NEWARRAY, T_INT);

        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);

        // operating on above array
        // dataArr[0] = material.getId()
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, refs.material.internalName(), "getId", "()I", false);
        mv.visitInsn(IASTORE);

        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);

        // dataArr[1] = material.getId() | (meta << 12);
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, refs.material.internalName(), "getId", "()I", false);
        mv.visitVarInsn(ILOAD, local_meta);
        mv.visitLdcInsn(12);
        mv.visitInsn(ISHL);
        mv.visitInsn(IOR);
        mv.visitInsn(IASTORE);

        mv.visitMethodInsn(INVOKEVIRTUAL,
                implReturnType.internalName(),
                SET_PARTICLE_METHOD_NAME, "(" + refs.enumParticle.desc() + "[I)V", false);

        // return particleType;
        mv.visitVarInsn(ALOAD, local_particleType);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
