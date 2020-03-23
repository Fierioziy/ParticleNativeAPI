package me.fierioziy.api.types;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeNote extends ParticleType {

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param color a <code>Color</code> object with specified color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, Location loc,
                             Color color) {
        return createNote(far,
                (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                color.getRed(), color.getGreen(), color .getBlue());
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param color a <code>Color</code> object with specified color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, Vector loc,
                             Color color) {
        return createNote(far,
                (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                color.getRed(), color.getGreen(), color .getBlue());
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x component of a position.
     * @param y component of a position.
     * @param z component of a position.
     * @param color a <code>Color</code> object with specified color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, double x, double y, double z,
                             Color color) {
        return createNote(far,
                (float) x, (float) y, (float) z,
                color.getRed(), color.getGreen(), color .getBlue());
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
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
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, Location loc,
                             int r, int g, int b) {
        return createNote(far,
                (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                r, g, b);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
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
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, Vector loc,
                             int r, int g, int b) {
        return createNote(far,
                (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                r, g, b);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks have to be performed:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
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
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, double x, double y, double z,
                             int r, int g, int b) {
        return createNote(far, (float) x, (float) y,   (float) z,
                r, g, b);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     * <p>This method may not work correctly due to
     * bug - MC-80658. It affects MC versions
     * between 1.8 and 1.13 where most of the colors are gray-ish.</p>
     * <p>It has been fixed in MC 1.14 where normal colors
     * spectrum works again.</p>
     *
     * <p>Note particle can't be dimmed and brighten so certain
     * checks <b>have to be performed</b>:</p>
     * <ul>
     *     <li>at least one color channel must have value of 255,</li>
     *     <li>there is no more than 2 channels active at the same time.</li>
     * </ul>
     *
     * <p>Otherwise, an <code>UnsupportedOperationException</code> will
     * be thrown.</p>
     *
     * <p>Examples of valid colors:</p>
     * <ul>
     *     <li>(255, 180, 0)</li>
     *     <li>(255, 0, 255)</li>
     *     <li>(0, 255, 0)</li>
     * </ul>
     * <p>Examples of colors that will throw an exception:</p>
     * <ul>
     *     <li>(0, 180, 0) - must be at least 1 color channel with 255 value,</li>
     *     <li>(255, 30, 255) - all color channels can't be active at the same time.</li>
     * </ul>
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
     * @throws UnsupportedOperationException when color does not
     * have at least 1 color channel with 255 value
     * or when there is more than 2 color channels active at the same time.
     */
    public Object createNote(boolean far, float x, float y, float z,
                             int r, int g, int b) {
        if (r != 255 && g != 255 && b != 255) {
            throw new UnsupportedOperationException(
                    "At least one color channel in ("
                            + r + ", " + g + ", " + b +
                            ") must have value of 255 to make note color!"
            );
        }

        float rr = 4F * r / 255F;
        float gg = 4F * g / 255F;
        float bb = 4F * b / 255F;

        if      (b == 0) rr =  2F + rr - gg;
        else if (r == 0) rr = 18F + gg - bb;
        else if (g == 0) rr = 10F - rr + bb;
        else throw new UnsupportedOperationException(
                    "All color channels in ("
                            + r + ", " + g + ", " + b +
                            ") can't be used to make note color!"
            );

        return create(far,
                x,          y,          z,
                rr / 24F,   0F,         0F,
                1F, 0);
    }

    /**
     * <p>Construct particle packet that will
     * spawn 1 colored note particle at specified position
     * with provided color.</p>
     *
     *
     *
     * <p>Parameters are not validated in any way.</p>
     *
     * <p>Note particle can't be dimmed and brighten.</p>
     *
     * <p>To color this particle, you have to use scale
     * between 0.0 and 1.0. An valid proportion can be made by taking an index
     * of certain note's color divided by 24 (amount of color notes).</p>
     *
     * <p>>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Location</code> containing position.
     * @param color a proportion value representing note color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createNote(boolean far, Location loc,
                             double color) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color,      0D,         0D,
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
     * <p>>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a <code>Vector</code> containing position.
     * @param color a proportion value representing note color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createNote(boolean far, Vector loc,
                             double color) {
        return create(far,
                loc.getX(), loc.getY(), loc.getZ(),
                color,      0D,         0D,
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
     * <p>>Those note colors can be found on the wiki page about Note Block here:
     * <a href="https://minecraft.gamepedia.com/Note_Block#Notes">NoteBlock#Notes</a></p>
     *
     * <p>It is wise to check, if particle is supported by current Spigot version
     * using <code>isValid</code> method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x component of a position.
     * @param y component of a position.
     * @param z component of a position.
     * @param color a proportion value representing note color.
     * @return a valid NMS <code>Packet</code> object.
     * @throws IllegalStateException when requested particle type
     * is not supported by this server version.
     */
    public Object createNote(boolean far, double x, double y, double z,
                             double color) {
        return create(far,
                x,      y,      z,
                color,  0D,     0D,
                1D, 0);
    }

}
