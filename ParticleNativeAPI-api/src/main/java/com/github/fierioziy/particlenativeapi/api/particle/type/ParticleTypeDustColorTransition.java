package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Color;

/**
 * <p>Class used to represent dust particle type that needs
 * a starting color, transition color and a size.</p>
 *
 * <p>It provides a non-reflective <code>color</code> method overloads
 * to construct <code>ParticleType</code> with selected colors and size.</p>
 *
 * <p>All <code>color</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleType
 */
public interface ParticleTypeDustColorTransition {

    /**
     * <p>Selects a color this particle should get
     * and to what color it should transition on fading away.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param color      a <code>Color</code> object representing
     *                   desired particle color.
     * @param transition a <code>Color</code> object representing
     *                   desired color on fade.
     * @param size       size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * colors and size.
     */
    @Shared ParticleType color(Color color, Color transition, double size);

    /**
     * <p>Selects a color this particle should get
     * and to what color it should transition on fading away.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param r    red color value that should be between 0 and 255.
     * @param g    green color value that should be between 0 and 255.
     * @param b    blue color value that should be between 0 and 255.
     * @param tr   red fade color value that should be between 0 and 255.
     * @param tg   green fade color value that should be between 0 and 255.
     * @param tb   blue fade color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * colors and size.
     */
    @Shared ParticleType color(int r, int g, int b,
                               int tr, int tg, int tb,
                               double size);

    /**
     * <p>Selects a color this particle should get
     * and to what color it should transition on fading away.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param r    red color value that should be between 0.0 and 1.0.
     * @param g    green color value that should be between 0.0 and 1.0.
     * @param b    blue color value that should be between 0.0 and 1.0.
     * @param tr   red fade color value that should be between 0 and 255.
     * @param tg   green fade color value that should be between 0 and 255.
     * @param tb   blue fade color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * colors and size.
     */
    @Shared ParticleType color(float r, float g, float b,
                               float tr, float tg, float tb,
                               float size);

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
