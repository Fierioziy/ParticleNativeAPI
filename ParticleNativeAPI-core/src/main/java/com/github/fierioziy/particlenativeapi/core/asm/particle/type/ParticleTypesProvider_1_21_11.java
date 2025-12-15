package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.ParticleTypeBlockASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_17.ParticleTypeItemASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeSculkChargeASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeShriekASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_19.ParticleTypeVibrationASM_1_19;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_20_5.ParticleTypeColorASM_1_20_5;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_10.ParticleTypePowerASM_1_21_10;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_10.ParticleTypeSpellASM_1_21_10;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3.ParticleTypeDustASM_1_21_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_3.ParticleTypeDustTransitionASM_1_21_3;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_4.ParticleTypeASM_1_21_4;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_4.ParticleTypeRedstoneASM_1_21_4;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.v1_21_4.ParticleTypeVibrationSingleASM_1_21_4;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>Class responsible for providing version-dependent code of
 * particle types in MC 1.21.11.</p>
 */
public class ParticleTypesProvider_1_21_11 extends ParticleTypesProvider {

    /**
     * <p>Map containing all available particles in current Spigot version.</p>
     */
    private final Map<String, String> currentParticlesMap;

    private final String colorParticleOptionFactoryMethodName;

    private final String spellParticleOptionFactoryMethodName;

    private final String powerParticleOptionFactoryMethodName;

    public ParticleTypesProvider_1_21_11(ContextASM context) {
        super(context);

        this.currentParticlesMap = context.internal.getParticles_1_21_11();
        this.colorParticleOptionFactoryMethodName = context.internal.getColorParticleOptionFactoryMethodName_1_20_5();
        this.spellParticleOptionFactoryMethodName = context.internal.getSpellParticleOptionFactoryMethodName_1_21_10();
        this.powerParticleOptionFactoryMethodName = context.internal.getPowerParticleOptionFactoryMethodName_1_21_10();
    }

    @Override
    public void registerClasses() {
        new ParticleTypeASM_1_21_4(context, ClassSkeleton.PARTICLE_TYPE).registerClass();
        new ParticleTypeASM_1_21_4(context, ClassSkeleton.PARTICLE_TYPE_COLORABLE).registerClass();
        new ParticleTypeASM_1_21_4(context, ClassSkeleton.PARTICLE_TYPE_MOTION).registerClass();
        new ParticleTypeASM_1_21_4(context, ClassSkeleton.PARTICLE_TYPE_NOTE).registerClass();

        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeBlockASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_BLOCK_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeDustASM_1_21_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_INT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();
        new ParticleTypeDustTransitionASM_1_21_3(context,
                ClassSkeleton.PARTICLE_TYPE_DUST_COLOR_TRANSITION_INT,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeItemASM_1_17(context,
                ClassSkeleton.PARTICLE_TYPE_ITEM_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeVibrationSingleASM_1_21_4(context, ClassSkeleton.PARTICLE_TYPE_VIBRATION_SINGLE).registerClass();

        new ParticleTypeRedstoneASM_1_21_4(context).registerClass();

        new ParticleTypeSculkChargeASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SCULK_CHARGE_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION)
                .registerClass();

        new ParticleTypeShriekASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_SHRIEK,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeVibrationASM_1_19(context,
                ClassSkeleton.PARTICLE_TYPE_VIBRATION,
                ClassSkeleton.PARTICLE_TYPE)
                .registerClass();

        new ParticleTypeColorASM_1_20_5(context,
                ClassSkeleton.PARTICLE_TYPE_COLOR,
                ClassSkeleton.PARTICLE_TYPE,
                colorParticleOptionFactoryMethodName)
                .registerClass();

        new ParticleTypeSpellASM_1_21_10(context,
                ClassSkeleton.PARTICLE_TYPE_SPELL,
                ClassSkeleton.PARTICLE_TYPE,
                spellParticleOptionFactoryMethodName)
                .registerClass();

        new ParticleTypePowerASM_1_21_10(context,
                ClassSkeleton.PARTICLE_TYPE_POWER_MOTION,
                ClassSkeleton.PARTICLE_TYPE_MOTION,
                powerParticleOptionFactoryMethodName)
                .registerClass();
    }

    @Override
    public void generateParticleFactoryMethods(ClassWriter cw, SpigotParticleVersion particleVersion,
                                               ClassSkeleton particleListSkeleton) {
        Set<String> allowedParticlesFromList_1_19_Part = new HashSet<>(Arrays.asList(
                "VIBRATION",
                "ENTITY_EFFECT",
                "FLASH",
                "DRAGON_BREATH",
                "EFFECT",
                "INSTANT_EFFECT"
        ));

        for (Method m : particleListSkeleton.getSuperClass().getSuperclass().getDeclaredMethods()) {
            String particleName = m.getName();

            ClassSkeleton returnSkeleton = ClassSkeleton.getByInterfaceClass(m.getReturnType());
            ClassMapping particleReturnType = returnSkeleton.getInterfaceType();
            ClassMapping particleReturnTypeImpl = returnSkeleton.getImpl(context.suffix);

            MethodVisitor mv = cw.visitMethod(ACC_PROTECTED,
                    particleName,
                    "()" + particleReturnType.desc(), null, null);
            mv.visitCode();

            int local_this = 0;

            // try to convert particle name to current server version
            Optional<String> resolvedName = particleRegistry
                    .find(particleVersion, particleName.toLowerCase(), SpigotParticleVersion.V1_20_5);

            // if it is SPELL_MOB particle in 1.8 list or ENTITY_EFFECT in 1.13 list, then visit invalid type
            // because it is not backward compatible
            // their "default color" was previously random, now I cannot guarantee same behavior
            // because color must now be defined explicitly
            if (is_SPELL_MOB_1_8_or_ENTITY_EFFECT_1_13(particleListSkeleton, particleName)
                    && currentParticlesMap.containsKey("entity_effect")) {
                visitInvalidType(mv, returnSkeleton);
            }
            // if it is SPELL particle in 1.8 list or EFFECT in 1.13 list,
            // then visit ParticleTypeSpell with white color and power equal 1
            // because that's how they have behaved previously
            else if (is_SPELL_1_8_or_EFFECT_1_13(particleListSkeleton, particleName)
                    && currentParticlesMap.containsKey("effect")) {
                String particleNmsFieldName = currentParticlesMap.get("effect");
                visitSpellParticleImplConstructorWithWhiteColorAndDefaultPower(
                        mv, particleReturnType, particleNmsFieldName
                );
            }
            // if it is SPELL_INSTANT particle in 1.8 list or INSTANT_EFFECT in 1.13 list,
            // then visit ParticleTypeSpell with white color and power equal 1
            // because that's how they have behaved previously
            else if (is_SPELL_INSTANT_1_8_or_INSTANT_EFFECT_1_13(particleListSkeleton, particleName)
                    && currentParticlesMap.containsKey("instant_effect")) {
                String particleNmsFieldName = currentParticlesMap.get("instant_effect");
                visitSpellParticleImplConstructorWithWhiteColorAndDefaultPower(
                        mv, particleReturnType, particleNmsFieldName
                );
            }
            // if it is FLASH in 1.13 list, then visit ParticleTypeColor with white color
            // because that's how it has behaved previously
            else if (is_FLASH_1_13(particleName, particleListSkeleton)
                    && currentParticlesMap.containsKey("flash")) {
                String particleNmsFieldName = currentParticlesMap.get("flash");
                visitColorParticleImplConstructorWithWhiteColor(
                        mv, particleReturnType, particleNmsFieldName
                );
            }
            // if it is DRAGON_BREATH in 1.8 or 1.13 list, then visit ParticleTypePower with power 1
            // because that's how it has behaved previously
            else if (is_DRAGON_BREATH_1_8_or_1_13(particleName, particleListSkeleton)
                    && currentParticlesMap.containsKey("dragon_breath")) {
                String particleNmsFieldName = currentParticlesMap.get("dragon_breath");
                visitPowerParticleImplConstructorWithDefaultPower(
                        mv, particleReturnType, particleNmsFieldName
                );
            }
            // if it is anything in 1.19 list part that isn't allowed, visit invalid particle type
            else if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_19_PART)
                    && !allowedParticlesFromList_1_19_Part.contains(particleName)) {
                visitInvalidType(mv, returnSkeleton);
            }
            // maintain forward compatibility for redstone -> dust
            else if (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8)
                    && particleName.equals("REDSTONE")
                    && currentParticlesMap.containsKey("dust")) {
                mv.visitTypeInsn(NEW, particleReturnTypeImpl.internalName());
                mv.visitInsn(DUP);

                mv.visitMethodInsn(INVOKESPECIAL,
                        particleReturnTypeImpl.internalName(),
                        "<init>",
                        "()V", false);
            }
            // maintain forward compatibility for barrier/light -> block_marker
            else if ((particleName.equals("BARRIER") || particleName.equals("LIGHT"))
                    && currentParticlesMap.containsKey("block_marker")) {// maintain forward compatibility
                visitParticleTypeBlockImplConstructorWithMaterial(mv, particleName, particleReturnType);
            }
            // if found and it exists, then instantiate
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
            else visitInvalidType(mv, returnSkeleton);

            // return new SomeParticleType_Impl(...);

            mv.visitInsn(ARETURN);

            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
    }

    private boolean is_SPELL_MOB_1_8_or_ENTITY_EFFECT_1_13(ClassSkeleton particleListSkeleton, String particleName) {
        return (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8) && particleName.equals("SPELL_MOB")) ||
                (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13) && particleName.equals("ENTITY_EFFECT"));
    }

    private boolean is_SPELL_1_8_or_EFFECT_1_13(ClassSkeleton particleListSkeleton, String particleName) {
        return (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8) && particleName.equals("SPELL")) ||
                (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13) && particleName.equals("EFFECT"));
    }

    private boolean is_SPELL_INSTANT_1_8_or_INSTANT_EFFECT_1_13(ClassSkeleton particleListSkeleton, String particleName) {
        return (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8) && particleName.equals("SPELL_INSTANT")) ||
                (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13) && particleName.equals("INSTANT_EFFECT"));
    }

    private boolean is_FLASH_1_13(String particleName, ClassSkeleton particleListSkeleton) {
        return particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13) && particleName.equals("FLASH");
    }

    private boolean is_DRAGON_BREATH_1_8_or_1_13(String particleName, ClassSkeleton particleListSkeleton) {
        return (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_8) && particleName.equals("DRAGON_BREATH")) ||
                (particleListSkeleton.equals(ClassSkeleton.PARTICLE_LIST_1_13) && particleName.equals("DRAGON_BREATH"));
    }

    private void visitSpellParticleImplConstructorWithWhiteColorAndDefaultPower(
            MethodVisitor mv,
            ClassMapping particleReturnType,
            String particleNmsFieldName
    ) {
        ClassSkeleton spellParticleTypeSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypeSpell.class);
        ClassMapping spellParticleTypeImpl = spellParticleTypeSkeleton.getImpl(context.suffix);

        // instantiate spell particle type implementation
        mv.visitTypeInsn(NEW, spellParticleTypeImpl.internalName());
        mv.visitInsn(DUP);

        // use particle from static field as parameter
        mv.visitFieldInsn(GETSTATIC,
                refs.particles_1_17.internalName(),
                particleNmsFieldName,
                refs.particle_1_17.desc());

        mv.visitMethodInsn(INVOKESPECIAL,
                spellParticleTypeImpl.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + ")V", false);

        // we want to invoke spell with RGBA(255, 255, 255, 255) and power 1D
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitInsn(DCONST_1);

        // invoke ParticleTypeSpell_Impl.spell(255, 255, 255, 255, 1D)
        mv.visitMethodInsn(INVOKEVIRTUAL,
                spellParticleTypeImpl.internalName(),
                SPELL_METHOD_NAME,
                "(IIIID)" + particleReturnType.desc(), false);
    }

    private void visitColorParticleImplConstructorWithWhiteColor(
            MethodVisitor mv, ClassMapping particleReturnType, String particleNmsFieldName
    ) {
        ClassSkeleton colorParticleTypeSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypeColor.class);
        ClassMapping colorParticleTypeImpl = colorParticleTypeSkeleton.getImpl(context.suffix);

        // instantiate color particle type implementation
        mv.visitTypeInsn(NEW, colorParticleTypeImpl.internalName());
        mv.visitInsn(DUP);

        // use particle from static field as parameter
        mv.visitFieldInsn(GETSTATIC,
                refs.particles_1_17.internalName(),
                particleNmsFieldName,
                refs.particle_1_17.desc());

        mv.visitMethodInsn(INVOKESPECIAL,
                colorParticleTypeImpl.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + ")V", false);

        // we want to invoke color with RGBA(255, 255, 255, 255)
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);
        mv.visitIntInsn(SIPUSH, 255);

        // invoke ParticleTypeColor_Impl.color(255, 255, 255, 255)
        mv.visitMethodInsn(INVOKEVIRTUAL,
                colorParticleTypeImpl.internalName(),
                COLOR_METHOD_NAME,
                "(IIII)" + particleReturnType.desc(), false);
    }

    private void visitPowerParticleImplConstructorWithDefaultPower(
            MethodVisitor mv, ClassMapping particleReturnType, String particleNmsFieldName
    ) {
        ClassSkeleton powerParticleTypeMotionSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypePowerMotion.class);
        ClassMapping powerParticleTypeMotionImpl = powerParticleTypeMotionSkeleton.getImpl(context.suffix);

        // instantiate power particle type implementation
        mv.visitTypeInsn(NEW, powerParticleTypeMotionImpl.internalName());
        mv.visitInsn(DUP);

        // use particle from static field as parameter
        mv.visitFieldInsn(GETSTATIC,
                refs.particles_1_17.internalName(),
                particleNmsFieldName,
                refs.particle_1_17.desc());

        mv.visitMethodInsn(INVOKESPECIAL,
                powerParticleTypeMotionImpl.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + ")V", false);

        // we want to invoke power with 1D
        mv.visitInsn(DCONST_1);

        // invoke ParticleTypeColor_Impl.power(1D)
        mv.visitMethodInsn(INVOKEVIRTUAL,
                powerParticleTypeMotionImpl.internalName(),
                POWER_METHOD_NAME,
                "(D)" + particleReturnType.desc(), false);
    }

    private void visitParticleTypeBlockImplConstructorWithMaterial(
            MethodVisitor mv, String particleName, ClassMapping particleReturnType
    ) {
        // get field name from Particles class associated with block_marker particle
        String fieldName = currentParticlesMap.get("block_marker");

        ClassSkeleton particleTypeBlockSkeleton = ClassSkeleton.getByInterfaceClass(ParticleTypeBlock.class);
        ClassMapping particleTypeBlockImpl = particleTypeBlockSkeleton.getImpl(context.suffix);

        // instantiate particle type block implementation
        mv.visitTypeInsn(NEW, particleTypeBlockImpl.internalName());
        mv.visitInsn(DUP);

        // use particle from static field as parameter
        mv.visitFieldInsn(GETSTATIC,
                refs.particles_1_17.internalName(),
                fieldName,
                refs.particle_1_17.desc());

        mv.visitMethodInsn(INVOKESPECIAL,
                particleTypeBlockImpl.internalName(),
                "<init>",
                "(" + refs.particle_1_17.desc() + ")V", false);

        // get Material.<particleName> to use as block data, lol
        mv.visitFieldInsn(GETSTATIC,
                refs.material.internalName(),
                particleName,
                refs.material.desc());

        // use it to invoke ParticleTypeBlock_Impl.of(Material.<particleName>)
        mv.visitMethodInsn(INVOKEVIRTUAL,
                particleTypeBlockImpl.internalName(),
                OF_METHOD_NAME,
                "(" + refs.material.desc() + ")" + particleReturnType.desc(), false);
    }

}
