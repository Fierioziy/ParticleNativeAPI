package com.github.fierioziy.api;

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * <p>Interface used to handle packet sending.</p>
 *
 * <p>It it a non-reflective wrapper of NMS <code>PlayerConnection</code> objects
 * that are used to send Minecraft packets.</p>
 *
 * <p>Roughly speaking, it stores and uses a certain group of player's NMS <code>PlayerConnection</code>.</p>
 *
 * <p>If you plan to send more than 4-5 packets to players
 * somewhere, then using this wrapper will be
 * more beneficial (faster) than using <code>ServerConnection</code> due
 * to caching array of NMS PlayerConnection directly in field.</p>
 *
 * <p>It is better <b>not to</b> cache it long-term, however if you
 * really want to do so, you <b>ought to</b> update underlying array content
 * using <code>update</code> method at least on player disconnect to make
 * sure all NMS <code>PlayerConnection</code> objects are valid inside.</p>
 *
 * <p>It is instantiated by <code>ServerConnection</code> instance and should
 * only be obtained from it.</p>
 * @see ServerConnection
 */
public interface PlayerConnectionArray {

    /**
     * <p>Sends packet to players using their NMS <code>PlayerConnection</code>.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void sendPacket(Object packet) {
     *     PlayerConnection[] pcs = this.playerConnectionArr;
     *     int length = pcs.length;
     *     while (length > 0) {
     *        pcs[--length].sendPacket((Packet) packet);
     *     }
     * }
     * }</pre>
     *
     * <p>If you plan to send more than 4-5 packets players
     * somewhere, then using this wrapper will be
     * more beneficial (faster) than using <code>ServerConnection</code> due
     * to caching array of NMS PlayerConnection directly in field.</p>
     *
     * <p>It is better <b>not to</b> cache it long-term, however if you
     * really want to do so, you <b>ought to</b> update underlying array content
     * using <code>update</code> method at least on player disconnect to make
     * sure all NMS <code>PlayerConnection</code> objects are valid inside.</p>
     *
     * <p>A packet parameter must be an instance of Minecraft packet interface.
     * Otherwise, you might get <code>ClassCastException</code> on packet parameter.</p>
     *
     * <p>You can use this method to send other packet than instances created using
     * this API. Any valid Minecraft packet can be used by this method.</p>
     *
     * @param packet a valid Minecraft packet created either by this API or
     *               via reflections.
     * @throws ClassCastException when provided packet object is not
     * an instance of Minecraft packet interface
     */
    void sendPacket(Object packet);

    /**
     * <p>Updates underlying <code>PlayerConnection</code> array
     * with NMS <code>PlayerConnection</code> objects acquired
     * from Player collection.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void update(Collection<Player> players) {
     *     int length = players.size();
     *     PlayerConnection[] pcs = new PlayerConnection[length];
     *
     *     Iterator<Player> it = players.iterator();
     *     while (length > 0) {
     *        pcs[--length] = ((CraftPlayer) it.next()).getHandle()
     *                                .playerConnection;
     *     }
     *     this.playerConnectionArr = pcs;
     * }
     * }</pre>
     *
     * <p>You usually don't have to use this method if you don't cache this wrapper anywhere.</p>
     *
     * <p>However, if you store this wrapper long-term, you <b>ought to</b> update
     * underlying array content using this method at least on player disconnect to make
     * sure all NMS <code>PlayerConnection</code> objects are valid inside.</p>
     *
     * @param players a collection of players which should be used
     *                to update underlying array.
     */
    void updateArray(Collection<Player> players);
}
