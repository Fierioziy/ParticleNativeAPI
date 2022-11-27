package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent vibration particle type since MC 1.19.</p>
 *
 * <p>It provides a non-reflective <code>flyingTo</code> method overloads
 * to construct {@link ParticleType} with selected target and flying time.</p>
 *
 * <p>All <code>flyingTo</code> methods does not validate parameters in any way.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: All methods annotated with {@link Shared} annotation
 * caches and returns exactly one and the same instance with changed state between method calls.
 * For an independent copy of returned instances, check <code>detachCopy</code> methods on them.</p>
 */
public interface ParticleTypeVibration {

    /**
     * <p>Selects target position and flying time this particle should get.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleTypeVibration#isPresent()} method.</p>
     *
     * @param target a {@link Location} containing target position.
     * @param ticks  flight duration (in ticks).
     * @return a valid shared {@link ParticleType} object with selected
     * target and flying time.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticleType flyingTo(Location target, int ticks);

    /**
     * <p>Selects target position and flying time this particle should get.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleTypeVibration#isPresent()} method.</p>
     *
     * @param target a {@link Vector} containing target position.
     * @param ticks  flight duration (in ticks).
     * @return a valid shared {@link ParticleType} object with selected
     * target and flying time.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticleType flyingTo(Vector target, int ticks);

    /**
     * <p>Selects target position and flying time this particle should get.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleTypeVibration#isPresent()} method.</p>
     *
     * @param targetX component of a target position.
     * @param targetY component of a target position.
     * @param targetZ component of a target position.
     * @param ticks   flight duration (in ticks).
     * @return a valid shared {@link ParticleType} object with selected
     * target and flying time.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticleType flyingTo(double targetX, double targetY, double targetZ,
                                  int ticks);

    /**
     * <p>Selects target position and flying time this particle should get.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleTypeVibration#isPresent()} method.</p>
     *
     * @param targetEntity an {@link Entity} this particle should fly to.
     * @param ticks        flight duration (in ticks).
     * @return a valid shared {@link ParticleType} object with selected
     * target entity and flying time.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticleType flyingTo(Entity targetEntity, int ticks);

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
