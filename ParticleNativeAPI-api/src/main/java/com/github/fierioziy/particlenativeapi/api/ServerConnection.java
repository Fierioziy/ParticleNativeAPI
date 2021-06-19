package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.utils.PlayerPredicate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * <p>Interface used to handle packet sending. All particles lists extends it
 * to provide sending packets functionality on top of creating particle packets.</p>
 *
 * <p>It provides a non-reflective methods that uses NMS <code>PlayerConnection</code>
 * to send them.</p>
 */
@SuppressWarnings("unused")
public interface ServerConnection {

    /**
     * <p>Creates a non-reflective wrapper of
     * player's NMS <code>PlayerConnection</code> instance.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * PlayerConnection createPlayerConnection(Player player) {
     *     return new PlayerConnection_Impl(player);
     * }
     * }</pre>
     *
     * <p>If you plan to send more than 4-5 packets to one
     * of <code>Player</code> somewhere, then using this wrapper slightly faster
     * than using particles lists due to
     * caching NMS <code>PlayerConnection</code> directly in field.</p>
     *
     * <p>It is better <b>not to</b> cache it long-term (for ex.
     * in <code>HashMap/ArrayList</code> etc.) and any complications to do it
     * anyways <b>will be</b> significantly slower than particles lists.</p>
     *
     * Obtaining <code>PlayerConnection</code> from particles lists is fast, really.
     *
     * @param player a player from which <code>PlayerConnection</code> should be obtained.
     *
     * @return a non-reflective <code>PlayerConnection</code> wrapper of
     *         this player's NMS <code>PlayerConnection</code>
     * @see PlayerConnection
     */
    PlayerConnection createPlayerConnection(Player player);

    /**
     * <p>Sends packet to a <code>Player</code>.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void sendPacket(Player player, Object packet) {
     *     ((CraftPlayer) player).getHandle().playerConnection
     *             .sendPacket((Packet) packet);
     * }
     * }</pre>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param player a player to which send a packet object.
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     *
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface.
     */
    void sendPacket(Player player, Object packet);

    /**
     * <p>Sends packet to each <code>Player</code>.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void sendPacket(Collection<Player> players, Object packet) {
     *     Packet nmsPacket = (Packet) packet;
     *
     *     int length = players.size();
     *     Iterator it = players.iterator();
     *
     *     while (length > 0) {
     *         ((CraftPlayer) it.next()).getHandle().playerConnection
     *                 .sendPacket(nmsPacket);
     *         --length;
     *     }
     * }
     * }</pre>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param players a <code>Collection</code> of players to which send a packet object.
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     *
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface.
     */
    void sendPacket(Collection<Player> players, Object packet);

    /**
     * <p>Sends packet to each <code>Player</code> that matches predicate.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void sendPacketIf(Collection<Player> players, Object packet, PlayerPredicate predicate) {
     *     Packet nmsPacket = (Packet) packet;
     *
     *     int length = players.size();
     *     Iterator it = players.iterator();
     *
     *     while (length > 0) {
     *         CraftPlayer p = (CraftPlayer) it.next();
     *         if (predicate.shouldSend(p)) {
     *             p.getHandle().playerConnection
     *                     .sendPacket(nmsPacket);
     *         }
     *         --length;
     *     }
     * }
     * }</pre>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param players a <code>Collection</code> of players to which send a packet object.
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     * @param predicate a PlayerPredicate used if packet should be send.
     *
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface.
     */
    void sendPacketIf(Collection<Player> players, Object packet, PlayerPredicate predicate);

    /**
     * <p>Sends a packet to every player in given radius.</p>
     *
     * <p>Technically speaking, gets all players from <code>loc</code> parameter's world
     * and send packet to every player in radius.</p>
     *
     * <p>A generated code for this method looks (roughly) like this:</p>
     * <pre>{@code
     * void sendPacket(Location loc, double radius, Object packet) {
     *     radius *= radius;
     *     Packet nmsPacket = (Packet) packet;
     *
     *     double x = loc.getX();
     *     double y = loc.getY();
     *     double z = loc.getZ();
     *
     *     // this initializations are optimized
     *     int length = loc.getWorld().getPlayers().size();
     *     Iterator it = loc.getWorld().getPlayers().iterator();
     *
     *     while (length > 0) {
     *         CraftPlayer p = (CraftPlayer) it.next();
     *         Location pLoc = p.getLocation();
     *
     *         // pseudo code if statement is optimized
     *         if (( (pLoc.getX() - x)^2
     *             + (pLoc.getY() - y)^2
     *             + (pLoc.getZ() - z)^2) <= radius) {
     *             p.getHandle().playerConnection.sendPacket(nmsPacket);
     *         }
     *
     *         --length;
     *     }
     * }
     * }</pre>
     *
     * <p>This method should be a little faster than normal for-each loop
     * with radius check due to few bytecode optimizations:</p>
     * <ul>
     *     <li>one packet cast per entire loop,</li>
     *     <li>duplicating list reference around
     *     <code>length</code> and <code>it</code> variables,</li>
     *     <li>duplicating subtraction results around radius check,</li>
     *     <li>using traditional <code>for</code> loop with
     *     cached list size instead of <code>hasNext</code> interface
     *     method check on each iteration.</li>
     * </ul>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param loc a <code>Location</code> containing position.
     * @param radius a spherical radius around which send packet to
     *               nearby players.
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface.
     */
    void sendPacket(Location loc, double radius, Object packet);

    /**
     * <p>Sends a packet to every player in given radius
     * that matches predicate.</p>
     *
     * <p>Technically speaking, gets all players from <code>loc</code> parameter's world
     * and send packet to every player in radius that matches predicate.</p>
     *
     * <p>Predicate is executed <b>after</b> radius check.</p>
     *
     * <p>A generated code for this method looks (roughly) like this:</p>
     * <pre>{@code
     * void sendPacketIf(Location loc, double radius, Object packet, PlayerPredicate predicate) {
     *     radius *= radius;
     *     Packet nmsPacket = (Packet) packet;
     *
     *     double x = loc.getX();
     *     double y = loc.getY();
     *     double z = loc.getZ();
     *
     *     // this initializations are optimized
     *     int length = loc.getWorld().getPlayers().size();
     *     Iterator it = loc.getWorld().getPlayers().iterator();
     *
     *     while (length > 0) {
     *         CraftPlayer p = (CraftPlayer) it.next();
     *         Location pLoc = p.getLocation();
     *
     *         // pseudo code if statement is optimized
     *         if (( (pLoc.getX() - x)^2
     *             + (pLoc.getY() - y)^2
     *             + (pLoc.getZ() - z)^2) <= radius
     *                 && predicate.shouldSend(p)) {
     *             p.getHandle().playerConnection.sendPacket(nmsPacket);
     *         }
     *
     *         --length;
     *     }
     * }
     * }</pre>
     *
     * <p>This method should be a little faster than normal for-each loop
     * with radius check due to few bytecode optimizations:</p>
     * <ul>
     *     <li>one packet cast per entire loop,</li>
     *     <li>duplicating list reference around
     *     <code>length</code> and <code>it</code> variables,</li>
     *     <li>duplicating subtraction results around radius check,</li>
     *     <li>using traditional <code>for</code> loop with
     *     cached list size instead of <code>hasNext</code> interface
     *     method check on each iteration.</li>
     * </ul>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param loc a <code>Location</code> containing position.
     * @param radius a spherical radius around which send packet to
     *               nearby players.
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     * @param predicate a PlayerPredicate used if packet should be send.
     *
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface.
     */
    void sendPacketIf(Location loc, double radius, Object packet, PlayerPredicate predicate);

}
