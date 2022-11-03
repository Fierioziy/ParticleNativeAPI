package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent particle type that can construct particle packet
 * with desired initial motion.</p>
 *
 * <p>It provides a non-reflective <code>packetMotion</code>
 * and <code>packet</code> method overloads
 * to construct particle packet with desired parameters.</p>
 *
 * <p>All <code>packetMotion</code> and <code>packet</code> methods does not validate parameters in any way.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: All methods annotated with {@link Shared} annotation
 * caches and returns exactly one and the same instance with changed state between method calls.
 * For an independent copy of returned instances, check <code>detachCopy</code> methods on them.</p>
 *
 * @see ParticleType
 */
public interface ParticleTypeMotion extends ParticleType {

    /**
     * <p>Makes an independent from previously called particle type, copy of this particle type.</p>
     * <p>Returned instance can be cached and reused.</p>
     *
     * @return an independent copy of this selected particle type.
     */
    @Override
    ParticleTypeMotion detachCopy();

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param dir a <code>Vector</code> direction.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, Location loc, Vector dir);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param dir a <code>Vector</code> direction.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, Vector loc, Vector dir);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x   component of a position.
     * @param y   component of a position.
     * @param z   component of a position.
     * @param dir a <code>Vector</code> direction.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, double x, double y, double z,
                                        Vector dir);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far  if true, packets will be rendered much further
     *             than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc  a <code>Location</code> containing position.
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, Location loc,
                                        double dirX, double dirY, double dirZ);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far  if true, packets will be rendered much further
     *             than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc  a <code>Vector</code> containing position.
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, Vector loc,
                                        double dirX, double dirY, double dirZ);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far  if true, packets will be rendered much further
     *             than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x    component of a position.
     * @param y    component of a position.
     * @param z    component of a position.
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetMotion(boolean far, double x, double y, double z,
                                        double dirX, double dirY, double dirZ);


}
