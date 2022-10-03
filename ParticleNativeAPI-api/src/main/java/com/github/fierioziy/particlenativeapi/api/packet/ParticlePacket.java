package com.github.fierioziy.particlenativeapi.api.packet;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * <p>Class used to handle packet sending.</p>
 * <p>It wraps NMS <code>Packet</code> object and sends it without Reflection usage.</p>
 *
 * <p>Each particle type caches and returns exactly one and the same instance of this class.
 * For an independent copy of this packet wrapper, check {@link ParticlePacket#detachCopy()} method.</p>
 */
@SuppressWarnings("unused")
public interface ParticlePacket {

    /**
     * <p>Makes an independent from previously called particle type, copy of this packet wrapper.</p>
     * <p>Returned instance can be cached and reused.</p>
     *
     * @return an independent copy of this packet instance.
     */
    ParticlePacket detachCopy();

    /**
     * <p>Sends packet to a <code>Player</code>.</p>
     *
     * <p><b>This method is overridden by dynamically generated
     * subclasses and depending on implementation this method
     * will look roughly like this:</b></p>
     *
     * <pre>{@code
     * public void sendPacket(Player player) {
     *     ((CraftPlayer) player).getHandle().playerConnection
     *             .sendPacket(packet);
     * }
     * }</pre>
     *
     * @param player a player to which send a packet.
     */
    void sendTo(Player player);

    /**
     * <p>Sends packet to each <code>Player</code>.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players a <code>Collection</code> of players to which send a packet.
     */
    void sendTo(Collection<Player> players);

    /**
     * <p>Sends packet to each <code>Player</code> that matches predicate.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players   a <code>Collection</code> of players to which send a packet.
     * @param predicate a {@link Predicate} used to check if packet should be send to certain player.
     */
    void sendToIf(Collection<Player> players, Predicate<Player> predicate);

    /**
     * <p>Sends a packet to every player in given particle packet radius.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players   a <code>Collection</code> of players to which send a packet.
     * @param radius   a spherical radius around which send packet to
     *                 nearby players.
     */
    void sendInRadiusTo(Collection<Player> players, double radius);

    /**
     * <p>Sends a packet to every player in given particle packet radius
     * that matches predicate.</p>
     *
     * <p>Predicate is executed <b>after</b> radius check.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players   a <code>Collection</code> of players to which send a packet.
     * @param radius    a spherical radius around which send packet to
     *                  nearby players.
     * @param predicate a {@link Predicate} used to check if packet should be send to certain player.
     */
    void sendInRadiusToIf(Collection<Player> players, double radius, Predicate<Player> predicate);

}
