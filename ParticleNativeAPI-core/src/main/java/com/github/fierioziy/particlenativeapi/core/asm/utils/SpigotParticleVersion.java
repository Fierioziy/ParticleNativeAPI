package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.Particles_1_13;
import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistry;

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
    V1_7(Particles_1_8.class),
    V1_8(Particles_1_8.class),
    V1_13(Particles_1_13.class),
    V1_18(Particles_1_13.class);

    public static final SpigotParticleVersion INITIAL_VERSION;
    public static final int VERSION_COUNT;

    static {
        SpigotParticleVersion[] values = SpigotParticleVersion.values();

        INITIAL_VERSION = values[0];
        VERSION_COUNT = values.length;
    }

    private final Class<?> particleTypesClass;
    private final ClassMapping type;
    private final ClassMapping implType;

    SpigotParticleVersion(Class<?> particleTypesClass) {
        this.particleTypesClass = particleTypesClass;

        type = new ClassRegistry.RegisteredClassMapping(particleTypesClass);
        implType = type.impl("_Impl");
    }

    public Class<?> getParticleTypesClass() {
        return particleTypesClass;
    }

    public ClassMapping getType() {
        return type;
    }

    public ClassMapping getImplType() {
        return implType;
    }
}
