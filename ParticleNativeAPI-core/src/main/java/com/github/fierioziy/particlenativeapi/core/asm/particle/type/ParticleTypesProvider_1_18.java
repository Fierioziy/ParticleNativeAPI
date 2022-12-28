package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeBlock;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.18.</p>
 */
public class ParticleTypesProvider_1_18 extends ParticleTypesProvider_1_17 {

    public ParticleTypesProvider_1_18(InternalResolver resolver,
                                      ParticlePacketProvider particlePacketProvider) {
        super(resolver, "_1_18", particlePacketProvider);
    }

    public ParticleTypesProvider_1_18(InternalResolver resolver, String suffix,
                                      ParticlePacketProvider particlePacketProvider) {
        super(resolver, suffix, particlePacketProvider);
    }

    public ParticleTypesProvider_1_18(InternalResolver resolver, String suffix,
                                      ParticlePacketProvider particlePacketProvider,
                                      Map<String, String> currentParticlesMap) {
        super(resolver, suffix, particlePacketProvider, currentParticlesMap);
    }

    @Override
    public void generateParticleFactoryMethods(ClassWriter cw, SpigotParticleVersion particleVersion,
                                               ClassSkeleton particleListSkeleton) {
        for (Method m : particleListSkeleton.getSuperClass().getSuperclass().getDeclaredMethods()) {
            String particleName = m.getName();

            ClassSkeleton returnSkeleton = ClassSkeleton.getByInterfaceClass(m.getReturnType());
            ClassMapping particleReturnType = returnSkeleton.getInterfaceType();
            ClassMapping particleReturnTypeImpl = returnSkeleton.getImpl(suffix);

            MethodVisitor mv = cw.visitMethod(ACC_PROTECTED,
                    particleName,
                    "()" + particleReturnType.desc(), null, null);
            mv.visitCode();

            int local_this = 0;

            // try to convert particle name to current server version
            Optional<String> resolvedName = particleRegistry
                    .find(particleVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_18);

            // if it is vibration in new interface, don't instantiate it
            if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_19_PART)
                    && suffix.equals("_1_18")// TODO this is bad
                    && particleName.equals("VIBRATION")
                    && currentParticlesMap.containsKey("vibration")) {
                visitInvalidType(mv, returnSkeleton);
            } // if found and it exists, then instantiate
            else if (resolvedName.isPresent() && currentParticlesMap.containsKey(resolvedName.get())) {
                // get field name from Particles class associated with particle name
                String fieldName = currentParticlesMap.get(resolvedName.get());

                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                // if it is just ParticleType, then pass it as ParticleParam directly
                // else, pass it as Particle so it can be used to make ParticleParam
                String ctrParamDesc, particlesFieldDesc;
                if (ParticleType.class.isAssignableFrom(m.getReturnType())) {
                    ctrParamDesc = refs.particleParam_1_17.desc();
                    particlesFieldDesc = refs.particleTypeNms_1_17.desc();
                }
                else {
                    ctrParamDesc = refs.particle_1_17.desc();
                    particlesFieldDesc = refs.particle_1_17.desc();
                }

                // get particle from static field
                mv.visitFieldInsn(GETSTATIC,
                        refs.particles_1_17.internalName(),
                        fieldName,
                        particlesFieldDesc);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "(" + ctrParamDesc + ")V", false);
            }
            else if (particleVersion.equals(SpigotParticleVersion.V1_8) && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {// maintain forward compatibility
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "()V", false);
            }
            else if ((particleName.equals("BARRIER") || particleName.equals("LIGHT"))
                    && currentParticlesMap.containsKey("block_marker")) {// maintain forward compatibility
                // get field name from Particles class associated with block_marker particle
                String fieldName = currentParticlesMap.get("block_marker");

                ClassSkeleton blockMarkerSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypeBlock.class);
                ClassMapping blockMarkerTypeImpl = blockMarkerSkeleton.getImpl(suffix);

                // instantiate block_marker particle type implementation
                mv.visitTypeInsn(NEW, blockMarkerTypeImpl.internalName());
                mv.visitInsn(DUP);

                // use particle from static field as parameter
                mv.visitFieldInsn(GETSTATIC,
                        refs.particles_1_17.internalName(),
                        fieldName,
                        refs.particle_1_17.desc());

                mv.visitMethodInsn(INVOKESPECIAL,
                        blockMarkerTypeImpl.internalName(),
                        "<init>",
                        "(" + refs.particle_1_17.desc() + ")V", false);

                // get Material.<particleName> to use as block data, lol
                mv.visitFieldInsn(GETSTATIC,
                        refs.material.internalName(),
                        particleName,
                        refs.material.desc());

                // use it to invoke ParticleTypeBlock_Impl.of(Material.<particleName>)
                mv.visitMethodInsn(INVOKEVIRTUAL,
                        blockMarkerTypeImpl.internalName(),
                        OF_METHOD_NAME,
                        "(" + refs.material.desc() + ")" + particleReturnType.desc(), false);
            }
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(...);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

}
