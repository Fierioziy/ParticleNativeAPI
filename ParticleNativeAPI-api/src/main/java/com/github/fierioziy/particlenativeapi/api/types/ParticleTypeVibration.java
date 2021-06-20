package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent vibration particle type.</p>
 *
 * <p>It provides a non-reflective <code>packet</code> method overloads
 * to construct particle packet with desired parameters.</p>
 *
 * <p>All <code>packet</code> methods does not validate parameters in any way.</p>
 */
public class ParticleTypeVibration {

    /**
     * <p>Construct particle packet that will
     * spawn 1 vibration particle at specified position
     * flying toward target position.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param target a <code>Location</code> containing target position.
     * @param ticks flight duration (in ticks).
     *
     * @return a valid NMS <code>Packet</code> object.
     *
     * @throws ParticleException when requested particle type
     * is not supported by this server version.
     */
    public Object packet(boolean far, Location loc, Location target, int ticks) {
        return packet(far,
                loc.getX(),     loc.getY(),     loc.getZ(),
                target.getX(),  target.getY(),  target.getZ(),
                ticks);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 vibration particle at specified position
     * flying toward target position.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param target a <code>Vector</code> containing target position.
     * @param ticks flight duration (in ticks).
     *
     * @return a valid NMS <code>Packet</code> object.
     *
     * @throws ParticleException when requested particle type
     * is not supported by this server version.
     */
    public Object packet(boolean far, Location loc, Vector target, int ticks) {
        return packet(far,
                loc.getX(),     loc.getY(),     loc.getZ(),
                target.getX(),  target.getY(),  target.getZ(),
                ticks);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 vibration particle at specified position
     * flying toward target position.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param target a <code>Location</code> containing target position.
     * @param ticks flight duration (in ticks).
     *
     * @return a valid NMS <code>Packet</code> object.
     *
     * @throws ParticleException when requested particle type
     * is not supported by this server version.
     */
    public Object packet(boolean far, Vector loc, Location target, int ticks) {
        return packet(far,
                loc.getX(),     loc.getY(),     loc.getZ(),
                target.getX(),  target.getY(),  target.getZ(),
                ticks);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 vibration particle at specified position
     * flying toward target position.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param target a <code>Vector</code> containing target position.
     * @param ticks flight duration (in ticks).
     *
     * @return a valid NMS <code>Packet</code> object.
     *
     * @throws ParticleException when requested particle type
     * is not supported by this server version.
     */
    public Object packet(boolean far, Vector loc, Vector target, int ticks) {
        return packet(far,
                loc.getX(),     loc.getY(),     loc.getZ(),
                target.getX(),  target.getY(),  target.getZ(),
                ticks);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 vibration particle at specified position
     * flying toward target position.</p>
     *
     * <p>Currently, positions are aligned to block centers.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x component of a position.
     * @param y component of a position.
     * @param z component of a position.
     * @param targetX component of a target position.
     * @param targetY component of a target position.
     * @param targetZ component of a target position.
     * @param ticks flight duration (in ticks).
     *
     * @return a valid NMS <code>Packet</code> object.
     *
     * @throws ParticleException when requested particle type
     * is not supported by this server version.
     */
    public Object packet(boolean far, double x, double y, double z,
                         double targetX, double targetY, double targetZ,
                         int ticks) {
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
