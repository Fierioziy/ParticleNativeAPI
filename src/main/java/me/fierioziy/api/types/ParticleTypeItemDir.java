package me.fierioziy.api.types;

import org.bukkit.Material;

public class ParticleTypeItemDir {

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
     * @return a valid <code>ParticleTypeDir</code> object with selected
     * item type.
     */
    public ParticleTypeDir of(Material item) {
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
