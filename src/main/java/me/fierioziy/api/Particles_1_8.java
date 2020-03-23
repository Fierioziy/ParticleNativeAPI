package me.fierioziy.api;

import me.fierioziy.api.types.*;

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
 * @see me.fierioziy.ParticleNativeAPI
 */
public interface Particles_1_8 {

    /*
    There is absolutely no way i would document ALL of those
     */

    // 1.7
    ParticleTypeDir EXPLOSION_NORMAL();
    ParticleType EXPLOSION_LARGE();
    ParticleType EXPLOSION_HUGE();
    ParticleTypeDir FIREWORKS_SPARK();
    ParticleTypeDir WATER_BUBBLE();
    ParticleType WATER_SPLASH();
    ParticleTypeDir WATER_WAKE();
    ParticleType SUSPENDED();
    ParticleType SUSPENDED_DEPTH();
    ParticleTypeDir CRIT();
    ParticleTypeDir CRIT_MAGIC();
    ParticleTypeDir SMOKE_NORMAL();
    ParticleTypeDir SMOKE_LARGE();
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
    ParticleTypeDir PORTAL();
    ParticleTypeDir ENCHANTMENT_TABLE();
    ParticleTypeDir FLAME();
    ParticleType LAVA();
    ParticleType FOOTSTEP();
    ParticleTypeDir CLOUD();
    ParticleTypeRedstone REDSTONE();
    ParticleType SNOWBALL();
    ParticleTypeDir SNOW_SHOVEL();
    ParticleType SLIME();
    ParticleType HEART();

    // 1.8
    ParticleType BARRIER();
    ParticleTypeItemDir ITEM_CRACK();
    ParticleTypeBlockDir BLOCK_CRACK();
    ParticleTypeBlockDir BLOCK_DUST();
    ParticleType WATER_DROP();
    ParticleType ITEM_TAKE();
    ParticleType MOB_APPEARANCE();

    // 1.9
    ParticleTypeDir DRAGON_BREATH();
    ParticleTypeDir END_ROD();
    ParticleTypeDir DAMAGE_INDICATOR();
    ParticleType SWEEP_ATTACK();

    // 1.10
    ParticleTypeBlock FALLING_DUST();

    // 1.11
    ParticleTypeDir TOTEM();
    ParticleTypeDir SPIT();

}
