package com.github.fierioziy.api.types;

import org.bukkit.Material;

/**
 * <p>Class used to represent block particle type that needs a block type.</p>
 *
 * <p>It provides a non-reflective <code>of</code> method overloads
 * to construct <code>ParticleTypeMotion</code> with selected block type.</p>
 *
 * <p>All <code>of</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleTypeMotion
 */
public class ParticleTypeBlockMotion {

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param block a <code>Material</code> object representing
     *              desired block type.
     * @return a valid <code>ParticleTypeMotion</code> object with selected
     * block type.
     */
    public ParticleTypeMotion of(Material block) {
        return of(block, (byte) 0);
    }

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param block a <code>Material</code> object representing
     *              desired block type.
     * @param meta a metadata used by certain blocks (it is
     *             ignored since 1.13).
     * @return a valid <code>ParticleTypeMotion</code> object with selected
     * block type.
     */
    public ParticleTypeMotion of(Material block, int meta) {
        return of(block, (byte) meta);
    }

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param block a <code>Material</code> object representing
     *              desired block type.
     * @param meta a metadata used by certain blocks (it is
     *             ignored since 1.13).
     * @return a valid <code>ParticleTypeMotion</code> object with selected
     * block type.
     */
    public ParticleTypeMotion of(Material block, byte meta) {
        throw new IllegalStateException(
                "Requested particle type is not supported by this server version!"
        );
    }

    /**
     * <p>Checks if this particle is supported by this Spigot version.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @return true if this particle is supported by
     * this Spigot version, false otherwise.
     */
    public boolean isValid() {
        return false;
    }

}
