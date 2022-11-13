package com.github.fierioziy.particlenativeapi.api.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * <p>Class used to represent note particle type that can construct
 * colored particle packet.</p>
 *
 * <p>It provides a non-reflective <code>packetNote</code>
 * and <code>packet</code> method overloads
 * to construct particle packet with desired parameters.</p>
 *
 * <p>Some <code>packetNote</code> methods overloads have to validate color parameter(s)
 * to properly select certain color.</p>
 *
 * <p>All <code>packet</code> methods does not validate parameters in any way.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: All methods annotated with {@link Shared} annotation
 * caches and returns exactly one and the same instance with changed state between method calls.
 * For an independent copy of returned instances, check <code>detachCopy</code> methods on them.</p>
 *
 * @see ParticleType
 */
public interface ParticleTypeNote extends ParticleType {

    /**
     * <p>Makes an independent from previously called particle type, copy of this particle type.</p>
     * <p>Returned instance can be cached and reused.</p>
     *
     * @return an independent copy of this selected particle type.
     */
    @Override
    ParticleTypeNote detachCopy();

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Location} containing position.
     * @param color a {@link Color} object with specified color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, Location loc,
                                      Color color);

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Vector} containing position.
     * @param color a {@link Color} object with specified color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, Vector loc,
                                      Color color);

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x     component of a position.
     * @param y     component of a position.
     * @param z     component of a position.
     * @param color a {@link Color} object with specified color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, double x, double y, double z,
                                      Color color);

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a {@link Location} containing position.
     * @param r   red color value that should be between 0 and 255.
     * @param g   green color value that should be between 0 and 255.
     * @param b   blue color value that should be between 0 and 255.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, Location loc,
                                      int r, int g, int b);

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc a {@link Vector} containing position.
     * @param r   red color value that should be between 0 and 255.
     * @param g   green color value that should be between 0 and 255.
     * @param b   blue color value that should be between 0 and 255.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, Vector loc,
                                      int r, int g, int b);

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
     * <p>Otherwise, an {@link UnsupportedOperationException} will
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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far if true, packets will be rendered much further
     *            than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x   component of a position.
     * @param y   component of a position.
     * @param z   component of a position.
     * @param r   red color value that should be between 0 and 255.
     * @param g   green color value that should be between 0 and 255.
     * @param b   blue color value that should be between 0 and 255.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException             when requested particle type
     *                                       is not supported by this server version.
     * @throws UnsupportedOperationException when color does not
     *                                       have at least 1 color channel with 255 value
     *                                       or when there is more than 2 color channels active at the same time.
     */
    @Shared ParticlePacket packetNote(boolean far, double x, double y, double z,
                                      int r, int g, int b);

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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Location} containing position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetNote(boolean far, Location loc,
                                      double color);

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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param loc   a {@link Vector} containing position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetNote(boolean far, Vector loc,
                                      double color);

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
     * using {@link ParticleTypeNote#isPresent()} method.</p>
     *
     * @param far   if true, packets will be rendered much further
     *              than 16 blocks (flag is ignored prior to MC 1.8 versions).
     * @param x     component of a position.
     * @param y     component of a position.
     * @param z     component of a position.
     * @param color a proportion value representing note color.
     * @return an NMS <code>Packet</code> wrapped in shared {@link ParticlePacket} object.
     * @throws ParticleException when requested particle type
     *                           is not supported by this server version.
     */
    @Shared ParticlePacket packetNote(boolean far, double x, double y, double z,
                                      double color);

}
