package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.types.*;

/**
 * <p>An interface declaring getter methods for particle types.
 * It contains all particle types prior to MC 1.13.</p>
 *
 * <p>It is also used to send packets.</p>
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
 *
 * @see ServerConnection
 */
@SuppressWarnings("unused")
public interface Particles_1_8 extends ServerConnection {

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
