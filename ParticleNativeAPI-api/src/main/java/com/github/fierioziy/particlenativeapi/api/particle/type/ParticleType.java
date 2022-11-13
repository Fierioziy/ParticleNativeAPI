package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent particle type with no additional features.</p>
 *
 * <p>It provides a non-reflective <code>packet</code> method overloads
 * to construct particle packet with desired parameters.</p>
 *
 * <p>All <code>packet</code> methods does not validate parameters in any way.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: All methods annotated with {@link Shared} annotation
 * caches and returns exactly one and the same instance with changed state between method calls.
 * For an independent copy of returned instances, check <code>detachCopy</code> methods on them.</p>
 */
public interface ParticleType {

    /**
     * <p>Makes an independent from previously called particle type, copy of this particle type.</p>
     * <p>Returned instance can be cached and reused.</p>
     *
     * @return an independent copy of this selected particle type.
     */
    ParticleType detachCopy();

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a {@link Location} containing position.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Location loc);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a {@link Vector} containing position.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Vector loc);

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x   component of a position.
     * @param y   component of a position.
     * @param z   component of a position.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, double x, double y, double z);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Location} containing position.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Location loc, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Vector} containing position.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Vector loc, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x     component of a position.
     * @param y     component of a position.
     * @param z     component of a position.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, double x, double y, double z, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Location} containing position.
     * @param speed parameter used in various contexts.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Location loc, double speed, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Vector} containing position.
     * @param speed parameter used in various contexts.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Vector loc, double speed, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x     component of a position.
     * @param y     component of a position.
     * @param z     component of a position.
     * @param speed parameter used in various contexts.
     * @param count amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, double x, double y, double z,
                                  double speed, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc     a {@link Location} containing position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Location loc,
                                  double offsetX, double offsetY, double offsetZ,
                                  int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc     a {@link Vector} containing position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Vector loc,
                                  double offsetX, double offsetY, double offsetZ,
                                  int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x       component of a position.
     * @param y       component of a position.
     * @param z       component of a position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, double x, double y, double z,
                                  double offsetX, double offsetY, double offsetZ,
                                  int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters
     * and <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc     a {@link Location} containing position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param speed   parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Location loc,
                                  double offsetX, double offsetY, double offsetZ,
                                  double speed, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters
     * and <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc     a {@link Vector} containing position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param speed   parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, Vector loc,
                                  double offsetX, double offsetY, double offsetZ,
                                  double speed, int count);

    /**
     * <p>Construct particle packet that will
     * spawn <code>count</code> particles at specified position
     * using provided offset parameters
     * and <code>speed</code> parameter.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses and depending on implementation this method
     * will look roughly like this:</b></p>
     * <pre>{@code
     * public Packet packet(boolean far, double x, double y, double z,
     *                      double offsetX, double offsetY, double offsetZ,
     *                      double speed, int count) {
     *     sharedPacket.setPacket(new PacketPlayOutWorldParticles(parameters...));
     *     return sharedPacket;
     * }
     * }</pre>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using {@link ParticleType#isPresent()} method.</p>
     *
     * @param far     if true, packets will be rendered much further
     *                than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x       component of a position.
     * @param y       component of a position.
     * @param z       component of a position.
     * @param offsetX parameter used in various contexts.
     * @param offsetY parameter used in various contexts.
     * @param offsetZ parameter used in various contexts.
     * @param speed   parameter used in various contexts.
     * @param count   amount of particles to spawn.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packet(boolean far, double x, double y, double z,
                                  double offsetX, double offsetY, double offsetZ,
                                  double speed, int count);

    /**
     * <p>Checks if this particle is supported by this Spigot version.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses.</b></p>
     *
     * @return true if this particle is supported by
     * this server version, false otherwise.
     */
    boolean isPresent();

}
