package me.fierioziy.api;

/**
 * <p>Interface used to handle packet sending.</p>
 *
 * <p>It it a non-reflective wrapper of NMS <code>PlayerConnection</code>
 * that is used to send Minecraft packets.</p>
 *
 * <p>Roughly speaking, it stores and uses a certain player's NMS <code>PlayerConnection</code>.</p>
 *
 * <p>If you plan to send more than 4-5 packets to one
 * of Players somewhere, then using this wrapper will be
 * more beneficial (faster) than using <code>ServerConnection</code> due
 * to caching NMS PlayerConnection directly in field.</p>
 *
 * <p>It is better <b>not to</b> cache it long-term and any complications to do it
 * anyways <b>will be</b> significantly slower than <code>ServerConnection</code>.</p>
 *
 * <p>It is instantiated by <code>ServerConnection</code> instance and should
 * only be obtained from it.</p>
 * @see ServerConnection
 */
public interface PlayerConnection {

    /**
     * <p>Sends packet to a Player using its NMS <code>PlayerConnection</code>.</p>
     *
     * <p>A generated code for this method looks roughly like this:</p>
     * <pre>{@code
     * void sendPacket(Object packet) {
     *     playerConnection.sendPacket((Packet) packet);
     * }
     * }</pre>
     *
     * <p>If you plan to send more than 4-5 packets to this player, then
     * using this method will be more beneficial (faster) than
     * using <code>ServerConnection</code> due to caching NMS PlayerConnection
     * directly in field.</p>
     *
     * <p>It is better <b>not to</b> use this wrapper long-term and any complications to do it
     * anyways <b>will be</b> significantly slower than <code>ServerConnection</code>.</p>
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
}
