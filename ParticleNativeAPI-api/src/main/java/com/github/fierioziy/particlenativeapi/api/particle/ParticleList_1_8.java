package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;

/**
 * <p>A class declaring fields for particle types.
 * It contains all particle types prior to MC 1.13.</p>
 *
 * <p>All particle lists attempt to provide same particle types between
 * renames or merges. They also attempt to provide cross-version
 * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
 * from {@link ParticleList_1_13} should work on MC 1.8), however this is
 * not always possible.</p>
 *
 * <p>Use <code>isPresent</code> method on particle type to handle such cases.</p>
 *
 * <p>Before accessing any particle type, you should check if it exists on server by
 * an <code>isPresent</code> defined by all particle types in this interface.</p>
 */
@SuppressWarnings("unused")
public abstract class ParticleList_1_8 extends ParticleSupplier_1_8 {

    private final ParticleNativeAPI api;

    /*
    There is absolutely no way I would document ALL of those
     */

    // 1.7
    public final ParticleTypeMotion EXPLOSION_NORMAL = EXPLOSION_NORMAL();// merged into POOF in 1.13 (bidirectional compatible)
    public final ParticleType EXPLOSION_LARGE = EXPLOSION_LARGE();
    public final ParticleType EXPLOSION_HUGE = EXPLOSION_HUGE();
    public final ParticleTypeMotion FIREWORKS_SPARK = FIREWORKS_SPARK();
    public final ParticleTypeMotion WATER_BUBBLE = WATER_BUBBLE();
    public final ParticleType WATER_SPLASH = WATER_SPLASH();
    public final ParticleTypeMotion WATER_WAKE = WATER_WAKE();
    public final ParticleType SUSPENDED = SUSPENDED();
    public final ParticleType SUSPENDED_DEPTH = SUSPENDED_DEPTH();// removed in 1.13
    public final ParticleTypeMotion CRIT = CRIT();
    public final ParticleTypeMotion CRIT_MAGIC = CRIT_MAGIC();
    public final ParticleTypeMotion SMOKE_NORMAL = SMOKE_NORMAL();
    public final ParticleTypeMotion SMOKE_LARGE = SMOKE_LARGE();
    public final ParticleType SPELL = SPELL();
    public final ParticleType SPELL_INSTANT = SPELL_INSTANT();
    public final ParticleTypeColorable SPELL_MOB = SPELL_MOB();
    public final ParticleTypeColorable SPELL_MOB_AMBIENT = SPELL_MOB_AMBIENT();
    public final ParticleType SPELL_WITCH = SPELL_WITCH();
    public final ParticleType DRIP_WATER = DRIP_WATER();
    public final ParticleType DRIP_LAVA = DRIP_LAVA();
    public final ParticleType VILLAGER_ANGRY = VILLAGER_ANGRY();
    public final ParticleType VILLAGER_HAPPY = VILLAGER_HAPPY();
    public final ParticleType TOWN_AURA = TOWN_AURA();
    public final ParticleTypeNote NOTE = NOTE();
    public final ParticleTypeMotion PORTAL = PORTAL();
    public final ParticleTypeMotion ENCHANTMENT_TABLE = ENCHANTMENT_TABLE();
    public final ParticleTypeMotion FLAME = FLAME();
    public final ParticleType LAVA = LAVA();
    public final ParticleType FOOTSTEP = FOOTSTEP();// removed in 1.13
    public final ParticleTypeMotion CLOUD = CLOUD();
    public final ParticleTypeRedstone REDSTONE = REDSTONE();
    public final ParticleType SNOWBALL = SNOWBALL();
    public final ParticleTypeMotion SNOW_SHOVEL = SNOW_SHOVEL();// merged into POOF in 1.13 (unidirectional compatible)
    public final ParticleType SLIME = SLIME();
    public final ParticleType HEART = HEART();

    public final ParticleTypeItemMotion ITEM_CRACK = ITEM_CRACK();
    public final ParticleTypeBlockMotion BLOCK_CRACK = BLOCK_CRACK();// merged into BLOCK in 1.13 (bidirectional compatible)
    public final ParticleTypeBlockMotion BLOCK_DUST = BLOCK_DUST();// merged into BLOCK in 1.13 (unidirectional compatible)

    // 1.8
    public final ParticleType BARRIER = BARRIER();
    public final ParticleType WATER_DROP = WATER_DROP();
    public final ParticleType ITEM_TAKE = ITEM_TAKE();// i have no clue what it does, but was removed in 1.13
    public final ParticleType MOB_APPEARANCE = MOB_APPEARANCE();

    // 1.9
    public final ParticleTypeMotion DRAGON_BREATH = DRAGON_BREATH();
    public final ParticleTypeMotion END_ROD = END_ROD();
    public final ParticleTypeMotion DAMAGE_INDICATOR = DAMAGE_INDICATOR();
    public final ParticleType SWEEP_ATTACK = SWEEP_ATTACK();

    // 1.10
    public final ParticleTypeBlock FALLING_DUST = FALLING_DUST();

    // 1.11
    public final ParticleTypeMotion TOTEM = TOTEM();
    public final ParticleTypeMotion SPIT = SPIT();

    protected ParticleList_1_8(ParticleNativeAPI api) {
        this.api = api;
    }

    public ParticleNativeAPI getAPI() {
        return api;
    }

}
