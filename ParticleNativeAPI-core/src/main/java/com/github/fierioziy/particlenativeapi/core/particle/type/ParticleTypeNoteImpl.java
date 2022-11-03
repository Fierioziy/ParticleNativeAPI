package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeNote;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeNoteImpl extends ParticleTypeImpl implements ParticleTypeNote {

    @Override
    public ParticleTypeNote detachCopy() {
        try {
            // this will also copy any fields created via ASM
            // so, it will copy cached NMS particle type related object
            return (ParticleTypeNote) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ParticleException(
                    "Tried to copy ParticleTypeNote, but for some reason it is not possible", e
            );
        }
    }

    @Override
    public ParticlePacket packetNote(boolean far, Location loc,
                                     Color color) {
        return packetNote(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public ParticlePacket packetNote(boolean far, Vector loc,
                                     Color color) {
        return packetNote(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public ParticlePacket packetNote(boolean far, double x, double y, double z,
                                     Color color) {
        return packetNote(far, x, y, z,
                color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public ParticlePacket packetNote(boolean far, Location loc,
                                     int r, int g, int b) {
        return packetNote(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r, g, b);
    }

    @Override
    public ParticlePacket packetNote(boolean far, Vector loc,
                                     int r, int g, int b) {
        return packetNote(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r, g, b);
    }

    @Override
    public ParticlePacket packetNote(boolean far, double x, double y, double z,
                                     int r, int g, int b) {
        r = Math.min(Math.max(r, 0), 255);
        g = Math.min(Math.max(g, 0), 255);
        b = Math.min(Math.max(b, 0), 255);

        if (r != 255 && g != 255 && b != 255) {
            throw new UnsupportedOperationException(
                    "At least one color channel in ("
                            + r + ", " + g + ", " + b +
                            ") must have value of 255 to make note color!"
            );
        }

        double rr = 4D * r / 255D;
        double gg = 4D * g / 255D;
        double bb = 4D * b / 255D;

        if (b == 0) {
            rr = 2D + rr - gg;

            // rr can be lower than 0.0 so
            // shift it between 0.0 and 24.0
            rr = rr < 0.0 ? rr + 24.0 : rr;
        }
        else if (r == 0) rr = 18D + gg - bb;
        else if (g == 0) rr = 10D - rr + bb;
        else throw new UnsupportedOperationException(
                    "All color channels in ("
                            + r + ", " + g + ", " + b +
                            ") can't be used to make note color!"
            );

        return packet(far,
                x, y, z,
                rr / 24D, 0D, 0D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>Note particle can't be dimmed and brighten.</p>
     *
     * <p>To color this particle, you have to use scale
     * between 0.0 and 1.0. An valid proportion can be made by taking an index
     * of certain note's color divided by 24 (amount of color notes).</p>
     *
     * <p>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a <code>Location</code> containing position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    public ParticlePacket packetNote(boolean far, Location loc,
                                     double color) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color, 0D, 0D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>Note particle can't be dimmed and brighten.</p>
     *
     * <p>To color this particle, you have to use scale
     * between 0.0 and 1.0. An valid proportion can be made by taking an index
     * of certain note's color divided by 24 (amount of color notes).</p>
     *
     * <p>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a <code>Vector</code> containing position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    public ParticlePacket packetNote(boolean far, Vector loc,
                                     double color) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color, 0D, 0D,
                1D, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>Note particle can't be dimmed and brighten.</p>
     *
     * <p>To color this particle, you have to use scale
     * between 0.0 and 1.0. An valid proportion can be made by taking an index
     * of certain note's color divided by 24 (amount of color notes).</p>
     *
     * <p>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isPresent</code> method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x     component of a position.
     * @param y     component of a position.
     * @param z     component of a position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in internal {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    public ParticlePacket packetNote(boolean far, double x, double y, double z,
                                     double color) {
        return packet(far,
                x, y, z,
                color, 0D, 0D,
                1D, 0);
    }

}
