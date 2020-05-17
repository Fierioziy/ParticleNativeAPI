package me.fierioziy.api.types;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent particle type that can construct
 * colored particle packet.</p>
 *
 * <p>It provides a non-reflective <code>packetColored</code>
 * and <code>packet</code> method overloads
 * to construct particle packet with desired parameters.</p>
 *
 * <p>All <code>packetColored</code> and <code>packet</code> methods does not validate parameters in any way.</p>
 *
 * @see ParticleType
 */
public class ParticleTypeColorable extends ParticleType {

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param color a <code>Color</code> object with color parameters.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, Location loc,
                                Color color) {
        return packet(far,
                loc.getX(),             loc.getY(),                 loc.getZ(),
                color.getRed() / 255D,  color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param color a <code>Color</code> object with color parameters.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, Vector loc,
                                Color color) {
        return packet(far,
                loc.getX(),             loc.getY(),                 loc.getZ(),
                color.getRed() / 255D,  color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x component of a position.
     * @param y component of a position.
     * @param z component of a position.
     * @param color a <code>Color</code> object with color parameters.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, double x, double y, double z,
                                Color color) {
        return packet(far,
                x,                      y,                          z,
                color.getRed() / 255D,  color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param r red color value that should be between 0 and 255.
     * @param g green color value that should be between 0 and 255.
     * @param b blue color value that should be between 0 and 255.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, Location loc,
                                int r, int g, int b) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param r red color value that should be between 0 and 255.
     * @param g green color value that should be between 0 and 255.
     * @param b blue color value that should be between 0 and 255.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, Vector loc,
                                int r, int g, int b) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored particle at specified position.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x component of a position.
     * @param y component of a position.
     * @param z component of a position.
     * @param r red color value that should be between 0 and 255.
     * @param g green color value that should be between 0 and 255.
     * @param b blue color value that should be between 0 and 255.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object packetColored(boolean far, double x, double y, double z,
                                int r, int g, int b) {
        return packet(far,
                x,          y,          z,
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

}
