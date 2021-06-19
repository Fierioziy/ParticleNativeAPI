package com.github.fierioziy.particlenativeapi.api.utils;

import org.bukkit.entity.Player;

/**
 * <p>Interface used to match players that meets criteria.</p>
 *
 * <p>It does not declare <code>FunctionalInterface</code> annotation, however
 * it can be used as a lambda expression.</p>
 */
public interface PlayerPredicate {

    /**
     * Returns <code>true</code> if a packet should be send to a player.
     *
     * @param player a <code>Player</code> being processed by this predicate.
     *
     * @return <code>true</code> if a packet should be send to a player, <code>false</code> otherwise.
     */
    boolean shouldSend(Player player);
}
