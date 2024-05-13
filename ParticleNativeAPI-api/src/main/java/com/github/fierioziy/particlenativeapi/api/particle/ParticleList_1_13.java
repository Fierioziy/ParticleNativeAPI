package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;

/**
 * <p>A class declaring fields for particle types.
 * It contains most particle types prior to MC 1.13 and all particles since MC 1.13.</p>
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
public abstract class ParticleList_1_13 extends ParticleSupplier_1_13 {

    private final ParticleNativeAPI api;

    /*
    There is absolutely no way I would document ALL of those
     */

    // 1.7
    public final ParticleTypeMotion POOF = POOF();
    public final ParticleType EXPLOSION = EXPLOSION();
    public final ParticleType EXPLOSION_EMITTER = EXPLOSION_EMITTER();
    public final ParticleTypeMotion FIREWORK = FIREWORK();
    public final ParticleTypeMotion BUBBLE = BUBBLE();
    public final ParticleType SPLASH = SPLASH();
    public final ParticleTypeMotion FISHING = FISHING();
    public final ParticleType UNDERWATER = UNDERWATER();
    public final ParticleTypeMotion CRIT = CRIT();
    public final ParticleTypeMotion ENCHANTED_HIT = ENCHANTED_HIT();
    public final ParticleTypeMotion SMOKE = SMOKE();
    public final ParticleTypeMotion LARGE_SMOKE = LARGE_SMOKE();
    public final ParticleType EFFECT = EFFECT();
    public final ParticleType INSTANT_EFFECT = INSTANT_EFFECT();
    public final ParticleTypeColorable ENTITY_EFFECT = ENTITY_EFFECT();// replaced in 1.20.5 in ParticleList_1_19_Part (mutually exclusive)
    public final ParticleTypeColorable AMBIENT_ENTITY_EFFECT = AMBIENT_ENTITY_EFFECT();// removed in 1.20.5
    public final ParticleType WITCH = WITCH();
    public final ParticleType DRIPPING_WATER = DRIPPING_WATER();
    public final ParticleType DRIPPING_LAVA = DRIPPING_LAVA();
    public final ParticleType ANGRY_VILLAGER = ANGRY_VILLAGER();
    public final ParticleType HAPPY_VILLAGER = HAPPY_VILLAGER();
    public final ParticleType MYCELIUM = MYCELIUM();
    public final ParticleTypeNote NOTE = NOTE();
    public final ParticleTypeMotion PORTAL = PORTAL();
    public final ParticleTypeMotion ENCHANT = ENCHANT();
    public final ParticleTypeMotion FLAME = FLAME();
    public final ParticleType LAVA = LAVA();
    public final ParticleTypeMotion CLOUD = CLOUD();
    public final ParticleTypeDust DUST = DUST();
    public final ParticleType ITEM_SNOWBALL = ITEM_SNOWBALL();
    public final ParticleType ITEM_SLIME = ITEM_SLIME();
    public final ParticleType HEART = HEART();

    public final ParticleTypeItemMotion ITEM = ITEM();
    public final ParticleTypeBlockMotion BLOCK = BLOCK();

    // 1.8
    public final ParticleType BARRIER = BARRIER();// removed; replaced by BLOCK_MARKER with BARRIER block data in 1.18
    public final ParticleType RAIN = RAIN();
    public final ParticleType ELDER_GUARDIAN = ELDER_GUARDIAN();

    // 1.9
    public final ParticleTypeMotion DRAGON_BREATH = DRAGON_BREATH();
    public final ParticleTypeMotion END_ROD = END_ROD();
    public final ParticleTypeMotion DAMAGE_INDICATOR = DAMAGE_INDICATOR();
    public final ParticleType SWEEP_ATTACK = SWEEP_ATTACK();

    // 1.10
    public final ParticleTypeBlock FALLING_DUST = FALLING_DUST();

    // 1.11
    public final ParticleTypeMotion SPIT = SPIT();
    public final ParticleTypeMotion TOTEM_OF_UNDYING = TOTEM_OF_UNDYING();

    // 1.13
    public final ParticleTypeMotion BUBBLE_COLUMN_UP = BUBBLE_COLUMN_UP();
    public final ParticleTypeMotion BUBBLE_POP = BUBBLE_POP();
    public final ParticleType CURRENT_DOWN = CURRENT_DOWN();
    public final ParticleTypeMotion SQUID_INK = SQUID_INK();
    public final ParticleTypeMotion NAUTILUS = NAUTILUS();
    public final ParticleType DOLPHIN = DOLPHIN();

    // 1.14
    public final ParticleTypeMotion SNEEZE = SNEEZE();
    public final ParticleTypeMotion CAMPFIRE_COSY_SMOKE = CAMPFIRE_COSY_SMOKE();
    public final ParticleTypeMotion CAMPFIRE_SIGNAL_SMOKE = CAMPFIRE_SIGNAL_SMOKE();
    public final ParticleType COMPOSTER = COMPOSTER();

    // 1.15
    public final ParticleType DRIPPING_HONEY = DRIPPING_HONEY();
    public final ParticleType FALLING_NECTAR = FALLING_NECTAR();
    public final ParticleType FALLING_HONEY = FALLING_HONEY();
    public final ParticleType LANDING_HONEY = LANDING_HONEY();

    // other working ones
    public final ParticleType FLASH = FLASH();
    public final ParticleType FALLING_LAVA = FALLING_LAVA();
    public final ParticleType FALLING_WATER = FALLING_WATER();
    public final ParticleType LANDING_LAVA = LANDING_LAVA();

    // 1.16
    public final ParticleTypeMotion SOUL_FIRE_FLAME = SOUL_FIRE_FLAME();
    public final ParticleTypeMotion SOUL = SOUL();

    public final ParticleType ASH = ASH();
    public final ParticleType WHITE_ASH = WHITE_ASH();

    public final ParticleType CRIMSON_SPORE = CRIMSON_SPORE();
    public final ParticleType WARPED_SPORE = WARPED_SPORE();

    public final ParticleType DRIPPING_OBSIDIAN_TEAR = DRIPPING_OBSIDIAN_TEAR();
    public final ParticleType FALLING_OBSIDIAN_TEAR = FALLING_OBSIDIAN_TEAR();
    public final ParticleType LANDING_OBSIDIAN_TEAR = LANDING_OBSIDIAN_TEAR();

    public final ParticleTypeMotion REVERSE_PORTAL = REVERSE_PORTAL();

    // 1.17
    public final ParticleType LIGHT = LIGHT();// removed; replaced by BLOCK_MARKER with LIGHT block data in 1.18

    public final ParticleTypeDustColorTransition DUST_COLOR_TRANSITION = DUST_COLOR_TRANSITION();
    public final ParticleTypeVibrationSingle VIBRATION = VIBRATION();

    public final ParticleType FALLING_SPORE_BLOSSOM = FALLING_SPORE_BLOSSOM();
    public final ParticleType SPORE_BLOSSOM_AIR = SPORE_BLOSSOM_AIR();

    public final ParticleTypeMotion SMALL_FLAME = SMALL_FLAME();
    public final ParticleTypeMotion SNOWFLAKE = SNOWFLAKE();

    public final ParticleType DRIPPING_DRIPSTONE_LAVA = DRIPPING_DRIPSTONE_LAVA();
    public final ParticleType DRIPPING_DRIPSTONE_WATER = DRIPPING_DRIPSTONE_WATER();
    public final ParticleType FALLING_DRIPSTONE_LAVA = FALLING_DRIPSTONE_LAVA();
    public final ParticleType FALLING_DRIPSTONE_WATER = FALLING_DRIPSTONE_WATER();

    public final ParticleTypeMotion GLOW_SQUID_INK = GLOW_SQUID_INK();
    public final ParticleType GLOW = GLOW();

    public final ParticleTypeMotion WAX_ON = WAX_ON();
    public final ParticleTypeMotion WAX_OFF = WAX_OFF();

    public final ParticleTypeMotion ELECTRIC_SPARK = ELECTRIC_SPARK();
    public final ParticleTypeMotion SCRAPE = SCRAPE();

    // 1.18
    public final ParticleTypeBlock BLOCK_MARKER = BLOCK_MARKER();

    // 1.19
    public final ParticleType SONIC_BOOM = SONIC_BOOM();

    public final ParticleTypeMotion SCULK_SOUL = SCULK_SOUL();
    public final ParticleTypeSculkChargeMotion SCULK_CHARGE = SCULK_CHARGE();
    public final ParticleTypeMotion SCULK_CHARGE_POP = SCULK_CHARGE_POP();

    public final ParticleTypeShriek SHRIEK = SHRIEK();

    // 1.20.1
    public final ParticleType CHERRY_LEAVES = CHERRY_LEAVES();

    public final ParticleType EGG_CRACK = EGG_CRACK();

    // 1.20.5
    public final ParticleType GUST = GUST();
    public final ParticleType SMALL_GUST = SMALL_GUST();
    public final ParticleType GUST_EMITTER_LARGE = GUST_EMITTER_LARGE();
    public final ParticleType GUST_EMITTER_SMALL = GUST_EMITTER_SMALL();

    public final ParticleType INFESTED = INFESTED();
    public final ParticleType ITEM_COBWEB = ITEM_COBWEB();
    public final ParticleTypeMotion WHITE_SMOKE = WHITE_SMOKE();

    public final ParticleTypeMotion DUST_PLUME = DUST_PLUME();
    public final ParticleTypeBlockMotion DUST_PILLAR = DUST_PILLAR();

    public final ParticleTypeMotion TRIAL_SPAWNER_DETECTION = TRIAL_SPAWNER_DETECTION();
    public final ParticleTypeMotion TRIAL_SPAWNER_DETECTION_OMINOUS = TRIAL_SPAWNER_DETECTION_OMINOUS();
    public final ParticleTypeMotion OMINOUS_SPAWNING = OMINOUS_SPAWNING();

    public final ParticleTypeMotion VAULT_CONNECTION = VAULT_CONNECTION();
    public final ParticleType TRIAL_OMEN = TRIAL_OMEN();

    protected ParticleList_1_13(ParticleNativeAPI api) {
        this.api = api;
    }

    public ParticleNativeAPI getAPI() {
        return api;
    }

}
