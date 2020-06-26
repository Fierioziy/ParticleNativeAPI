package com.github.fierioziy.api;

import com.github.fierioziy.ParticleNativeAPI;
import com.github.fierioziy.api.types.*;

/**
 * <p>An interface declaring getter methods for particle types.
 * It contains most particle types prior to MC 1.13 and all
 * particles since MC 1.13.</p>
 *
 * <p>It is also used to send packets.</p>
 *
 * <p>To obtain a valid instance of this interface, you should access
 * a relevant getter using <code>ParticleNativeAPI</code> instance.</p>
 *
 * <p>All particle lists attempt to provide same particle types between
 * renames or merges. They also attempt to provide cross-version
 * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
 * from <code>Particles_1_13</code> should work on MC 1.8), however this is
 * not always possible.</p>
 *
 * <p>Use <code>isValid</code> method on particle type to handle such cases.</p>
 *
 * <p>Before accessing any particle type, you should check if it exists on server by
 * an <code>isValid</code> defined by all particle types in this interface.</p>
 * @see ParticleNativeAPI
 * @see ServerConnection
 */
@SuppressWarnings("unused")
public interface Particles_1_13 extends ServerConnection {

    /*
    There is absolutely no way I would document ALL of those
     */

    // 1.7
    ParticleTypeMotion POOF();
    ParticleType EXPLOSION();
    ParticleType EXPLOSION_EMITTER();
    ParticleTypeMotion FIREWORK();
    ParticleTypeMotion BUBBLE();
    ParticleType SPLASH();
    ParticleTypeMotion FISHING();
    ParticleType UNDERWATER();
    ParticleTypeMotion CRIT();
    ParticleTypeMotion ENCHANTED_HIT();
    ParticleTypeMotion SMOKE();
    ParticleTypeMotion LARGE_SMOKE();
    ParticleType EFFECT();
    ParticleType INSTANT_EFFECT();
    ParticleTypeColorable ENTITY_EFFECT();
    ParticleTypeColorable AMBIENT_ENTITY_EFFECT();
    ParticleType WITCH();
    ParticleType DRIPPING_WATER();
    ParticleType DRIPPING_LAVA();
    ParticleType ANGRY_VILLAGER();
    ParticleType HAPPY_VILLAGER();
    ParticleType MYCELIUM();
    ParticleTypeNote NOTE();
    ParticleTypeMotion PORTAL();
    ParticleTypeMotion ENCHANT();
    ParticleTypeMotion FLAME();
    ParticleType LAVA();
    ParticleTypeMotion CLOUD();
    ParticleTypeDust DUST();
    ParticleType ITEM_SNOWBALL();
    ParticleType ITEM_SLIME();
    ParticleType HEART();

    // 1.8
    ParticleType BARRIER();
    ParticleTypeItemMotion ITEM();
    ParticleTypeBlockMotion BLOCK();
    ParticleType RAIN();
    ParticleType ELDER_GUARDIAN();

    // 1.9
    ParticleTypeMotion DRAGON_BREATH();
    ParticleTypeMotion END_ROD();
    ParticleTypeMotion DAMAGE_INDICATOR();
    ParticleType SWEEP_ATTACK();

    // 1.10
    ParticleTypeBlock FALLING_DUST();

    // 1.11
    ParticleTypeMotion SPIT();
    ParticleTypeMotion TOTEM_OF_UNDYING();

    // 1.13
    ParticleTypeMotion BUBBLE_COLUMN_UP();
    ParticleTypeMotion BUBBLE_POP();
    ParticleType CURRENT_DOWN();
    ParticleTypeMotion SQUID_INK();
    ParticleTypeMotion NAUTILUS();
    ParticleType DOLPHIN();
    ParticleTypeMotion SNEEZE();
    ParticleTypeMotion CAMPFIRE_COSY_SMOKE();
    ParticleTypeMotion CAMPFIRE_SIGNAL_SMOKE();

    // other working ones
    ParticleType COMPOSTER();
    ParticleType FLASH();
    ParticleType DRIPPING_HONEY();

    ParticleType FALLING_NECTAR();
    ParticleType FALLING_HONEY();
    ParticleType FALLING_LAVA();
    ParticleType FALLING_WATER();

    ParticleType LANDING_HONEY();
    ParticleType LANDING_LAVA();

    // 1.16
    ParticleTypeMotion SOUL_FIRE_FLAME();
    ParticleTypeMotion SOUL();

    ParticleType ASH();
    ParticleType WHITE_ASH();

    ParticleType CRIMSON_SPORE();
    ParticleType WARPED_SPORE();

    ParticleType DRIPPING_OBSIDIAN_TEAR();
    ParticleType FALLING_OBSIDIAN_TEAR();
    ParticleType LANDING_OBSIDIAN_TEAR();

    ParticleTypeMotion REVERSE_PORTAL();

}
