package com.github.fierioziy.particlenativeapi.core.asm.utils;

/**
 * <p>An enum used by {@link ParticleNode} class to store
 * particle version.</p>
 *
 * <p>It is used to represent a change in particle names (for ex. in field, enum
 * or string) between Minecraft updates.</p>
 *
 * <p>It also provides interface class associated with certain
 * particle version for easier generation.</p>
 */
public enum SpigotParticleVersion {

    V1_7, V1_8, V1_13, V1_18;

    public static final SpigotParticleVersion INITIAL_VERSION;
    public static final int VERSION_COUNT;

    static {
        SpigotParticleVersion[] values = SpigotParticleVersion.values();

        INITIAL_VERSION = values[0];
        VERSION_COUNT = values.length;
    }

}
