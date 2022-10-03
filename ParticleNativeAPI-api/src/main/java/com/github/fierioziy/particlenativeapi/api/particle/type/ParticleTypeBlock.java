package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Material;

/**
 * <p>Class used to represent block particle type that needs a block type.</p>
 *
 * <p>It provides a non-reflective <code>of</code> method overloads
 * to construct <code>ParticleType</code> with selected block type.</p>
 *
 * <p>All <code>of</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleType
 */
public interface ParticleTypeBlock {

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param block a {@link Material} object representing
     *              desired block type.
     * @return a valid <code>ParticleType</code> object with selected
     * block type.
     */
    @Shared ParticleType of(Material block);

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param block a {@link Material} object representing
     *              desired block type.
     * @param meta  a metadata used by certain blocks (it is
     *              ignored since 1.13).
     * @return a valid <code>ParticleType</code> object with selected
     * block type.
     */
    @Shared ParticleType of(Material block, int meta);

    /**
     * <p>Selects a block this particle should represents.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param block a {@link Material} object representing
     *              desired block type.
     * @param meta  a metadata used by certain blocks (it is
     *              ignored since 1.13).
     * @return a valid <code>ParticleType</code> object with selected
     * block type.
     */
    @Shared ParticleType of(Material block, byte meta);

    /**
     * <p>Checks if this particle is supported by this Spigot version.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @return true if this particle is supported by
     * this Spigot version, false otherwise.
     */
    boolean isValid();

}
