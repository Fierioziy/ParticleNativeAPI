package com.github.fierioziy.api.types;

import org.bukkit.Material;

/**
 * <p>Class used to represent item particle type that needs an item type.</p>
 *
 * <p>It provides a non-reflective <code>of</code> method overloads
 * to construct <code>ParticleTypeMotion</code> with selected item type.</p>
 *
 * <p>All <code>of</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleTypeMotion
 */
public class ParticleTypeItemMotion {

    /**
     * <p>Selects an item this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param item a <code>Material</code> object representing
     *              desired item type.
     * @return a valid <code>ParticleTypeMotion</code> object with selected
     * item type.
     */
    public ParticleTypeMotion of(Material item) {
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
