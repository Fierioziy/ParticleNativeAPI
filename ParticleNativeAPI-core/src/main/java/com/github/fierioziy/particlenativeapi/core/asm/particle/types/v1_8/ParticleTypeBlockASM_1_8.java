package com.github.fierioziy.particlenativeapi.core.asm.particle.types.v1_8;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class ParticleTypeBlockASM_1_8 extends ParticleTypeASM_1_8 {

    private final ClassMapping implReturnType;
    private final ClassMapping returnType;

    public ParticleTypeBlockASM_1_8(InternalResolver resolver, String suffix, ClassMapping superType, ClassMapping returnType) {
        super(resolver, suffix, superType);
        this.implReturnType = returnType.impl(suffix);
        this.returnType = returnType;
    }

    @Override
    protected void writeMethods(ClassWriter cw) {
        writeMethod_of(cw);
        writeMethod_isValid(cw);
    }

    private void writeMethod_of(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
                "of",
                "(Lorg/bukkit/Material;B)" + returnType.desc(), null, null);
        mv.visitCode();

        int local_this = 0;
        int local_material = 1;
        int local_meta = 2;

        mv.visitTypeInsn(NEW, implReturnType.internalName());
        mv.visitInsn(DUP);

        // get particle from field
        mv.visitVarInsn(ALOAD, local_this);
        mv.visitFieldInsn(GETFIELD,
                implType.internalName(),
                "particle",
                refs.enumParticle.desc());

        // new int[2]
        mv.visitInsn(ICONST_2);
        mv.visitIntInsn(NEWARRAY, T_INT);

        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);

        // operating on above array
        // dataArr[0] = material.getId();
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
        mv.visitInsn(IASTORE);

        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);

        // dataArr[1] = material.getId() | (meta << 12);
        mv.visitVarInsn(ALOAD, local_material);
        mv.visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/Material", "getId", "()I", false);
        mv.visitVarInsn(ILOAD, local_meta);
        mv.visitLdcInsn(12);
        mv.visitInsn(ISHL);
        mv.visitInsn(IOR);
        mv.visitInsn(IASTORE);

        mv.visitMethodInsn(INVOKESPECIAL,
                implReturnType.internalName(),
                "<init>", "(" + refs.enumParticle.desc() + "[I)V", false);
        mv.visitInsn(ARETURN);

        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
