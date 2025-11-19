package com.github.fierioziy.particlenativeapi.core.asm.skeleton;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_19_Part;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_8;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.RegisteredClassMapping;
import com.github.fierioziy.particlenativeapi.core.packet.ParticlePacketImpl;
import com.github.fierioziy.particlenativeapi.core.particle.type.*;

import java.util.HashMap;
import java.util.Map;

public enum ClassSkeleton {
    PARTICLE_LIST_1_8(
            ParticleList_1_8.class
    ),
    PARTICLE_LIST_1_13(
            ParticleList_1_13.class
    ),
    PARTICLE_LIST_1_19_PART(
            ParticleList_1_19_Part.class
    ),
    PARTICLE_PACKET(
            ParticlePacketImpl.class,
            ParticlePacket.class
    ),
    PARTICLE_TYPE(
            ParticleTypeImpl.class,
            ParticleType.class
    ),
    PARTICLE_TYPE_MOTION(
            ParticleTypeMotionImpl.class,
            ParticleTypeMotion.class
    ),
    PARTICLE_TYPE_COLORABLE(
            ParticleTypeColorableImpl.class,
            ParticleTypeColorable.class
    ),
    PARTICLE_TYPE_NOTE(
            ParticleTypeNoteImpl.class,
            ParticleTypeNote.class
    ),
    PARTICLE_TYPE_REDSTONE(
            ParticleTypeRedstoneImpl.class,
            ParticleTypeRedstone.class
    ),
    PARTICLE_TYPE_BLOCK(
            ParticleTypeBlockImpl.class,
            ParticleTypeBlock.class
    ),
    PARTICLE_TYPE_BLOCK_MOTION(
            ParticleTypeBlockMotionImpl.class,
            ParticleTypeBlockMotion.class
    ),
    PARTICLE_TYPE_DUST_FLOAT(
            ParticleTypeDustFloatImpl.class,
            ParticleTypeDust.class
    ),
    PARTICLE_TYPE_DUST_INT(
            ParticleTypeDustIntImpl.class,
            ParticleTypeDust.class
    ),
    PARTICLE_TYPE_DUST_COLOR_TRANSITION_FLOAT(
            ParticleTypeDustColorTransitionFloatImpl.class,
            ParticleTypeDustColorTransition.class
    ),
    PARTICLE_TYPE_DUST_COLOR_TRANSITION_INT(
            ParticleTypeDustColorTransitionIntImpl.class,
            ParticleTypeDustColorTransition.class
    ),
    PARTICLE_TYPE_ITEM_MOTION(
            ParticleTypeItemMotionImpl.class,
            ParticleTypeItemMotion.class
    ),
    PARTICLE_TYPE_SCULK_CHARGE_MOTION(
            ParticleTypeSculkChargeMotionImpl.class,
            ParticleTypeSculkChargeMotion.class
    ),
    PARTICLE_TYPE_SHRIEK(
            ParticleTypeShriekImpl.class,
            ParticleTypeShriek.class
    ),
    PARTICLE_TYPE_VIBRATION_SINGLE(
            ParticleTypeVibrationSingleImpl.class,
            ParticleTypeVibrationSingle.class
    ),
    PARTICLE_TYPE_VIBRATION(
            ParticleTypeVibrationImpl.class,
            ParticleTypeVibration.class
    ),
    PARTICLE_TYPE_COLOR(
            ParticleTypeColorImpl.class,
            ParticleTypeColor.class
    ),
    PARTICLE_TYPE_SPELL(
            ParticleTypeSpellImpl.class,
            ParticleTypeSpell.class
    ),
    PARTICLE_TYPE_POWER_MOTION(
            ParticleTypePowerMotionImpl.class,
            ParticleTypePowerMotion.class
    ),
    ;

    private static final Map<Class<?>, ClassSkeleton> INTERFACE_TO_SKELETON_MAP;
    static {
        ClassSkeleton[] values = values();
        INTERFACE_TO_SKELETON_MAP = new HashMap<>(values.length);

        for (ClassSkeleton value : values) {
            INTERFACE_TO_SKELETON_MAP.put(value.interfaceClass, value);
        }
    }

    private final ClassMapping superType;
    private final ClassMapping interfaceType;

    private final Class<?> superClass;
    private final Class<?> interfaceClass;

    ClassSkeleton(Class<?> superClass) {
        this(superClass, superClass);
    }

    ClassSkeleton(Class<?> superClass, Class<?> interfaceClass) {
        superType = new RegisteredClassMapping(superClass);
        interfaceType = new RegisteredClassMapping(interfaceClass);
        this.superClass = superClass;
        this.interfaceClass = interfaceClass;
    }

    public ClassMapping getSuperType() {
        return superType;
    }

    public ClassMapping getInterfaceType() {
        return interfaceType;
    }

    public Class<?> getSuperClass() {
        return superClass;
    }

    public ClassMapping getImpl(String suffix) {
        return new RegisteredClassMapping(interfaceType.internalName() + suffix);
    }

    public static ClassSkeleton getByInterfaceClass(Class<?> interfaceClass) {
        ClassSkeleton classSkeleton = INTERFACE_TO_SKELETON_MAP.get(interfaceClass);

        if (classSkeleton == null) {
            throw new ParticleException(String.format(
                    "Could not get class skeleton by class \"%s\"",
                    interfaceClass.getName()
            ));
        }

        return classSkeleton;
    }

}
