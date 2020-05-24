package com.github.fierioziy.api;

import com.github.fierioziy.ParticleNativeAPI;
import com.github.fierioziy.api.types.*;

/**
 * <p>An interface declaring getter methods for particle types.
 * It contains most particle types prior to MC 1.13 and all
 * particles since MC 1.13.</p>
 *
 * <p>To obtain a valid instance of this interface, you should access
 * a revelant getter using <code>ParticleNativeAPI</code> instance.</p>
 *
 * <p>All particle list interfaces holds same particle types
 * where possible (for ex. FLAME particle from this instance should also be present
 * in other particle list version if it is same particle type or if particle type
 * handling haven't changed significantly.</p>
 *
 * <p>Before accessing any particle type, you should check if its valid by
 * an <code>isValid</code> defined by all particle types in this interface.</p>
 *@see ParticleNativeAPI
 */
public interface Particles_1_13 {

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

}
