package me.fierioziy.api.types;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeDir extends ParticleType {

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param dir a <code>Vector</code> direction.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, Location loc, Vector dir) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param dir a <code>Vector</code> direction.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, Vector loc, Vector dir) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
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
     * @param dir a <code>Vector</code> direction.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, double x, double y, double z,
                            Vector dir) {
        return create(far,
                x,          y,          z,
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, Location loc,
                            double dirX, double dirY, double dirZ) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dirX,       dirY,       dirZ,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, Vector loc,
                            double dirX, double dirY, double dirZ) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dirX,       dirY,       dirZ,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 particle at specified position
     * with motion set to provided direction.</p>
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
     * @param dirX component of direction vector.
     * @param dirY component of direction vector.
     * @param dirZ component of direction vector.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createDir(boolean far, double x, double y, double z,
                            double dirX, double dirY, double dirZ) {
        return create(far,
                x,          y,          z,
                dirX,       dirY,       dirZ,
                1D, 0);
    }

}
