package com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8;

import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_8.skeleton.ParticleTypeComplexSkeletonASM_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeItemASM_1_8 extends ParticleTypeComplexSkeletonASM_1_8 {

    public ParticleTypeItemASM_1_8(ContextASM context,
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
                "(" + refs.material.desc() + ")" + interfaceReturnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_particleType = 2;

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
                material.getId()
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
        // dataArr[0] = material.getId();
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, refs.material.internalName(), "getId", "()I", false);
        mv.visitInsn(IASTORE);

        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);

        // dataArr[1] = 0;
        mv.visitInsn(ICONST_0);
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
