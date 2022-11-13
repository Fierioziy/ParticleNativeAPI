package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Color;

/**
 * <p>Class used to represent dust particle type that needs a color and size.</p>
 *
 * <p>It provides a non-reflective <code>color</code> method overloads
 * to construct <code>ParticleType</code> with selected color and size.</p>
 *
 * <p>All <code>color</code> methods does not validate parameters in any way.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: All methods annotated with {@link Shared} annotation
 * caches and returns exactly one and the same instance with changed state between method calls.
 * For an independent copy of returned instances, check <code>detachCopy</code> methods on them.</p>
 *
 * @see ParticleType
 */
public interface ParticleTypeDust {

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param color a {@link Color} object representing
     *              desired particle color.
     * @param size  size of a particle.
     * @return a valid shared {@link ParticleType} object with selected
     * color and size.
     */
    @Shared ParticleType color(Color color, double size);

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param color a {@link Color} object representing
     *              desired particle color.
     * @param size  size of a particle.
     * @return a valid shared {@link ParticleType} object with selected
     * color and size.
     */
    @Shared ParticleType color(Color color, float size);

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param r    red color value that should be between 0 and 255.
     * @param g    green color value that should be between 0 and 255.
     * @param b    blue color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid shared {@link ParticleType} object with selected
     * color and size.
     */
    @Shared ParticleType color(int r, int g, int b, double size);

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param r    red color value that should be between 0 and 255.
     * @param g    green color value that should be between 0 and 255.
     * @param b    blue color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid shared {@link ParticleType} object with selected
     * color and size.
     */
    @Shared ParticleType color(int r, int g, int b, float size);

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param r    red color value that should be between 0.0 and 1.0.
     * @param g    green color value that should be between 0.0 and 1.0.
     * @param b    blue color value that should be between 0.0 and 1.0.
     * @param size size of a particle.
     * @return a valid shared {@link ParticleType} object with selected
     * color and size.
     */
    @Shared ParticleType color(float r, float g, float b, float size);

    /**
     * <p>Checks if this particle is supported by this Spigot version.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @return true if this particle is supported by
     * this Spigot version, false otherwise.
     */
    boolean isPresent();

}
