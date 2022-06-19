package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

/**
 * <p>Class used to represent sculk charge particle type that takes a roll parameter.</p>
 *
 * <p>It provides a non-reflective <code>of</code> method overloads
 * to construct <code>ParticleTypeMotion</code> with selected item type.</p>
 *
 * <p>All <code>of</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleTypeMotion
 */
public class ParticleTypeSculkChargeMotion {

    /**
     * <p>Selects roll angle this particle should be rotated.
     * An angle is in radians and will rotate particle clockwise</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param roll angle in radians by which to rotate particle clockwise.
     *
     * @return a valid <code>ParticleTypeMotion</code> object with selected
     * roll angle.
     */
    public ParticleTypeMotion roll(double roll) {
        throw new ParticleException(
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
