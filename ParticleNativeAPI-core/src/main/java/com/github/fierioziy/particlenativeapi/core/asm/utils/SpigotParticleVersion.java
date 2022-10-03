package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.particle.Particles_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.Particles_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.RegisteredClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;

/**
 * <p>An enum used by <code>ParticleNode</code> class to store
 * particle version.</p>
 *
 * <p>It is used to represent a change in particle names (for ex. in field, enum
 * or string) between Minecraft updates.</p>
 *
 * <p>It also provides interface class associated with certain
 * particle version for easier generation.</p>
 */
public enum SpigotParticleVersion {
    V1_7(Particles_1_8.class, ClassSkeleton.PARTICLES_1_8),
    V1_8(Particles_1_8.class, ClassSkeleton.PARTICLES_1_8),
    V1_13(Particles_1_13.class, ClassSkeleton.PARTICLES_1_13),
    V1_18(Particles_1_13.class, ClassSkeleton.PARTICLES_1_13);

    public static final SpigotParticleVersion INITIAL_VERSION;
    public static final int VERSION_COUNT;

    static {
        SpigotParticleVersion[] values = SpigotParticleVersion.values();

        INITIAL_VERSION = values[0];
        VERSION_COUNT = values.length;
    }

    private final Class<?> particleTypesClass;
    private final Class<?> particleSupplierClass;

    private final ClassSkeleton superType;

    SpigotParticleVersion(Class<?> particleTypesClass, ClassSkeleton superType) {
        this.particleTypesClass = particleTypesClass;
        particleSupplierClass = particleTypesClass.getSuperclass();
        this.superType = superType;
    }

    public Class<?> getParticleTypesClass() {
        return particleTypesClass;
    }

    public Class<?> getParticleSupplierClass() {
        return particleSupplierClass;
    }

    public ClassSkeleton getSuperType() {
        return superType;
    }

}
