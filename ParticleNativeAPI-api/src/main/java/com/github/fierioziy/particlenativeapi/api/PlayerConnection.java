package com.github.fierioziy.particlenativeapi.api;

/**
 * <p>Interface used to handle packet sending.</p>
 *
 * <p>It it a non-reflective wrapper of NMS <code>PlayerConnection</code>
 * that is used to send Minecraft packets.</p>
 *
 * <p>Roughly speaking, it stores and uses a certain player's NMS <code>PlayerConnection</code>.</p>
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
 * <p>It is instantiated by particles lists and should
 * only be obtained from them.</p>
 *
 * @see ServerConnection
 */
@SuppressWarnings("unused")
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
