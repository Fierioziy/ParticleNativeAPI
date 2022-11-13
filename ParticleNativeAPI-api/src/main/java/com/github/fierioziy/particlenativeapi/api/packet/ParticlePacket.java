package com.github.fierioziy.particlenativeapi.api.packet;

import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * <p>Class used to handle packet sending.</p>
 * <p>It wraps NMS <code>Packet</code> object and sends it without Reflection usage.</p>
 *
 * <p><b>IMPORTANT NOTE</b>: Each particle type that constructs packets
 * caches and returns exactly one and the same instance of this class.</p>
 *
 * <p>Any shared usage of this class is annotated with {@link Shared} annotation in appropriate context.</p>
 *
 * <p>For an independent copy of this packet wrapper, check {@link ParticlePacket#detachCopy()} method.</p>
 */
public interface ParticlePacket {

    /**
     * <p>Makes an independent from previously called particle type, copy of this packet wrapper.</p>
     * <p>Returned instance can be cached and reused.</p>
     *
     * @return an independent copy of this packet instance.
     */
    ParticlePacket detachCopy();

    /**
     * <p>Sends packet to a {@link Player}.</p>
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
     * @param player a {@link Player} to which send a packet.
     */
    void sendTo(Player player);

    /**
     * <p>Sends packet to a {@link Player} if in given particle packet radius.</p>
     *
     * @param player a {@link Player} to which send a packet.
     * @param radius a spherical radius around which send packet to.
     */
    void sendInRadiusTo(Player player, double radius);

    /**
     * <p>Sends packet to each {@link Player}.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players a {@link Collection} of {@link Player} to which send a packet.
     */
    void sendTo(Collection<? extends Player> players);

    /**
     * <p>Sends packet to each {@link Player} that matches predicate.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players   a {@link Collection} of {@link Player} to which send a packet.
     * @param predicate a {@link Predicate} used to check if packet should be send to certain player.
     */
    void sendTo(Collection<? extends Player> players,
                Predicate<? super Player> predicate);

    /**
     * <p>Sends a packet to every player in given particle packet radius.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players a {@link Collection} of {@link Player} to which send a packet.
     * @param radius  a spherical radius around which send packet to.
     */
    void sendInRadiusTo(Collection<? extends Player> players, double radius);

    /**
     * <p>Sends a packet to every player in given particle packet radius
     * that matches predicate.</p>
     *
     * <p>Predicate is executed <b>after</b> radius check.</p>
     *
     * <p>NOTE: Bukkit internally creates a new copy of player list on {@link World#getPlayers()} method.
     * Keep it in mind if you operate on {@link Location} or {@link World} objects.</p>
     *
     * @param players   a {@link Collection} of {@link Player} to which send a packet.
     * @param radius    a spherical radius around which send packet to
     *                  nearby players.
     * @param predicate a {@link Predicate} used to check if packet should be send to certain player.
     */
    void sendInRadiusTo(Collection<? extends Player> players, double radius,
                        Predicate<? super Player> predicate);

}
