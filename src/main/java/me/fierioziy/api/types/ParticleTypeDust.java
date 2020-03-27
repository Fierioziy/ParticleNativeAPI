package me.fierioziy.api.types;

import org.bukkit.Color;

/**
 * <p>Class used to represent dust particle type that needs a color and size.</p>
 *
 * <p>It provides a non-reflective <code>color</code> method overloads
 * to construct <code>ParticleType</code> with selected color and size.</p>
 *
 * <p>All <code>color</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleType
 */
public class ParticleTypeDust {

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param color a <code>Color</code> object representing
     *              desired particle color.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * color and size.
     */
    public ParticleType color(Color color, double size) {
        return color(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                (float) size
        );
    }

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param color a <code>Color</code> object representing
     *              desired particle color.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * color and size.
     */
    public ParticleType color(Color color, float size) {
        return color(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                size
        );
    }

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param r red color value that should be between 0 and 255.
     * @param g green color value that should be between 0 and 255.
     * @param b blue color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * color and size.
     */
    public ParticleType color(int r, int g, int b, double size) {
        return color(r / 255F, g / 255F, b / 255F, (float) size);
    }

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * @param r red color value that should be between 0 and 255.
     * @param g green color value that should be between 0 and 255.
     * @param b blue color value that should be between 0 and 255.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * color and size.
     */
    public ParticleType color(int r, int g, int b, float size) {
        return color(r / 255F, g / 255F, b / 255F, size);
    }

    /**
     * <p>Selects a color this particle should get.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @param r red color value that should be between 0.0 and 1.0.
     * @param g green color value that should be between 0.0 and 1.0.
     * @param b blue color value that should be between 0.0 and 1.0.
     * @param size size of a particle.
     * @return a valid <code>ParticleType</code> object with selected
     * color and size.
     */
    public ParticleType color(float r, float g, float b, float size) {
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
