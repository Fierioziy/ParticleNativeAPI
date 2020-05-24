package com.github.fierioziy.api;

import com.github.fierioziy.ParticleNativeAPI;
import com.github.fierioziy.api.types.*;

/**
 * <p>An interface declaring getter methods for particle types.
 * It contains all particle types prior to MC 1.13.</p>
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
 * @see ParticleNativeAPI
 */
public interface Particles_1_8 {

    /*
    There is absolutely no way I would document ALL of those
     */

    // 1.7
    ParticleTypeMotion EXPLOSION_NORMAL();
    ParticleType EXPLOSION_LARGE();
    ParticleType EXPLOSION_HUGE();
    ParticleTypeMotion FIREWORKS_SPARK();
    ParticleTypeMotion WATER_BUBBLE();
    ParticleType WATER_SPLASH();
    ParticleTypeMotion WATER_WAKE();
    ParticleType SUSPENDED();
    ParticleType SUSPENDED_DEPTH();
    ParticleTypeMotion CRIT();
    ParticleTypeMotion CRIT_MAGIC();
    ParticleTypeMotion SMOKE_NORMAL();
    ParticleTypeMotion SMOKE_LARGE();
    ParticleType SPELL();
    ParticleType SPELL_INSTANT();
    ParticleTypeColorable SPELL_MOB();
    ParticleTypeColorable SPELL_MOB_AMBIENT();
    ParticleType SPELL_WITCH();
    ParticleType DRIP_WATER();
    ParticleType DRIP_LAVA();
    ParticleType VILLAGER_ANGRY();
    ParticleType VILLAGER_HAPPY();
    ParticleType TOWN_AURA();
    ParticleTypeNote NOTE();
    ParticleTypeMotion PORTAL();
    ParticleTypeMotion ENCHANTMENT_TABLE();
    ParticleTypeMotion FLAME();
    ParticleType LAVA();
    ParticleType FOOTSTEP();
    ParticleTypeMotion CLOUD();
    ParticleTypeRedstone REDSTONE();
    ParticleType SNOWBALL();
    ParticleTypeMotion SNOW_SHOVEL();
    ParticleType SLIME();
    ParticleType HEART();

    // 1.8
    ParticleType BARRIER();
    ParticleTypeItemMotion ITEM_CRACK();
    ParticleTypeBlockMotion BLOCK_CRACK();
    ParticleTypeBlockMotion BLOCK_DUST();
    ParticleType WATER_DROP();
    ParticleType ITEM_TAKE();
    ParticleType MOB_APPEARANCE();

    // 1.9
    ParticleTypeMotion DRAGON_BREATH();
    ParticleTypeMotion END_ROD();
    ParticleTypeMotion DAMAGE_INDICATOR();
    ParticleType SWEEP_ATTACK();

    // 1.10
    ParticleTypeBlock FALLING_DUST();

    // 1.11
    ParticleTypeMotion TOTEM();
    ParticleTypeMotion SPIT();

}
