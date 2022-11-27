package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class ParticleTypesProvider extends BaseASM {

    protected ParticleRegistry particleRegistry = new ParticleRegistry();

    public ParticleTypesProvider(InternalResolver resolver, String suffix) {
        super(resolver, suffix);
    }

    public abstract void registerClasses();
    public abstract void generateParticleFactoryMethods(ClassWriter cw, SpigotParticleVersion interfaceVersion,
                                                        ClassSkeleton particleListSkeleton);

    protected void visitInvalidType(MethodVisitor mv, ClassSkeleton particleTypeSkeleton) {
        ClassMapping superType = particleTypeSkeleton.getSuperType();

        mv.visitTypeInsn(NEW, superType.internalName());
        mv.visitInsn(DUP);

        mv.visitMethodInsn(INVOKESPECIAL,
                superType.internalName(),
                "<init>",
                "()V", false);
    }

}
