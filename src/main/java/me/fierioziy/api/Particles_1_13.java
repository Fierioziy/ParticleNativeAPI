package me.fierioziy.api;

import me.fierioziy.api.types.*;

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
 *@see me.fierioziy.ParticleNativeAPI
 */
public interface Particles_1_13 {

    /*
    There is absolutely no way i would document ALL of those
     */

    // 1.7
    ParticleTypeDir POOF();
    ParticleType EXPLOSION();
    ParticleType EXPLOSION_EMITTER();
    ParticleTypeDir FIREWORK();
    ParticleTypeDir BUBBLE();
    ParticleType SPLASH();
    ParticleTypeDir FISHING();
    ParticleType UNDERWATER();
    ParticleTypeDir CRIT();
    ParticleTypeDir ENCHANTED_HIT();
    ParticleTypeDir SMOKE();
    ParticleTypeDir LARGE_SMOKE();
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
    ParticleTypeDir PORTAL();
    ParticleTypeDir ENCHANT();
    ParticleTypeDir FLAME();
    ParticleType LAVA();
    ParticleTypeDir CLOUD();
    ParticleTypeDust DUST();
    ParticleType ITEM_SNOWBALL();
    ParticleType ITEM_SLIME();
    ParticleType HEART();

    // 1.8
    ParticleType BARRIER();
    ParticleTypeItemDir ITEM();
    ParticleTypeBlockDir BLOCK();
    ParticleType RAIN();
    ParticleType ELDER_GUARDIAN();

    // 1.9
    ParticleTypeDir DRAGON_BREATH();
    ParticleTypeDir END_ROD();
    ParticleTypeDir DAMAGE_INDICATOR();
    ParticleType SWEEP_ATTACK();

    // 1.10
    ParticleTypeBlock FALLING_DUST();

    // 1.11
    ParticleTypeDir SPIT();
    ParticleTypeDir TOTEM_OF_UNDYING();

    // 1.13
    ParticleTypeDir BUBBLE_COLUMN_UP();
    ParticleTypeDir BUBBLE_POP();
    ParticleType CURRENT_DOWN();
    ParticleTypeDir SQUID_INK();
    ParticleTypeDir NAUTILUS();
    ParticleType DOLPHIN();
    ParticleTypeDir SNEEZE();
    ParticleTypeDir CAMPFIRE_COSY_SMOKE();
    ParticleTypeDir CAMPFIRE_SIGNAL_SMOKE();

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
