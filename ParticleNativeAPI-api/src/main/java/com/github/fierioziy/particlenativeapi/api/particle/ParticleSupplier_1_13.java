package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.particle.type.*;

abstract class ParticleSupplier_1_13 {

    /*
    Methods below are overridden by dynamically generated subclasses.
    Their purpose is to be "factory" for fields with either particle type valid implementation
    or it's invalid dummy variant.
     */

    // 1.7
    protected abstract ParticleTypeMotion POOF();
    protected abstract ParticleType EXPLOSION();
    protected abstract ParticleType EXPLOSION_EMITTER();
    protected abstract ParticleTypeMotion FIREWORK();
    protected abstract ParticleTypeMotion BUBBLE();
    protected abstract ParticleType SPLASH();
    protected abstract ParticleTypeMotion FISHING();
    protected abstract ParticleType UNDERWATER();
    protected abstract ParticleTypeMotion CRIT();
    protected abstract ParticleTypeMotion ENCHANTED_HIT();
    protected abstract ParticleTypeMotion SMOKE();
    protected abstract ParticleTypeMotion LARGE_SMOKE();
    protected abstract ParticleType EFFECT();
    protected abstract ParticleType INSTANT_EFFECT();
    protected abstract ParticleTypeColorable ENTITY_EFFECT();
    protected abstract ParticleTypeColorable AMBIENT_ENTITY_EFFECT();
    protected abstract ParticleType WITCH();
    protected abstract ParticleType DRIPPING_WATER();
    protected abstract ParticleType DRIPPING_LAVA();
    protected abstract ParticleType ANGRY_VILLAGER();
    protected abstract ParticleType HAPPY_VILLAGER();
    protected abstract ParticleType MYCELIUM();
    protected abstract ParticleTypeNote NOTE();
    protected abstract ParticleTypeMotion PORTAL();
    protected abstract ParticleTypeMotion ENCHANT();
    protected abstract ParticleTypeMotion FLAME();
    protected abstract ParticleType LAVA();
    protected abstract ParticleTypeMotion CLOUD();
    protected abstract ParticleTypeDust DUST();
    protected abstract ParticleType ITEM_SNOWBALL();
    protected abstract ParticleType ITEM_SLIME();
    protected abstract ParticleType HEART();

    protected abstract ParticleTypeItemMotion ITEM();
    protected abstract ParticleTypeBlockMotion BLOCK();

    // 1.8
    protected abstract ParticleType BARRIER();// removed; replaced by BLOCK_MARKER with BARRIER block data in 1.18
    protected abstract ParticleType RAIN();
    protected abstract ParticleType ELDER_GUARDIAN();

    // 1.9
    protected abstract ParticleTypeMotion DRAGON_BREATH();
    protected abstract ParticleTypeMotion END_ROD();
    protected abstract ParticleTypeMotion DAMAGE_INDICATOR();
    protected abstract ParticleType SWEEP_ATTACK();

    // 1.10
    protected abstract ParticleTypeBlock FALLING_DUST();

    // 1.11
    protected abstract ParticleTypeMotion SPIT();
    protected abstract ParticleTypeMotion TOTEM_OF_UNDYING();

    // 1.13
    protected abstract ParticleTypeMotion BUBBLE_COLUMN_UP();
    protected abstract ParticleTypeMotion BUBBLE_POP();
    protected abstract ParticleType CURRENT_DOWN();
    protected abstract ParticleTypeMotion SQUID_INK();
    protected abstract ParticleTypeMotion NAUTILUS();
    protected abstract ParticleType DOLPHIN();

    // 1.14
    protected abstract ParticleTypeMotion SNEEZE();
    protected abstract ParticleTypeMotion CAMPFIRE_COSY_SMOKE();
    protected abstract ParticleTypeMotion CAMPFIRE_SIGNAL_SMOKE();
    protected abstract ParticleType COMPOSTER();

    // 1.15
    protected abstract ParticleType DRIPPING_HONEY();
    protected abstract ParticleType FALLING_NECTAR();
    protected abstract ParticleType FALLING_HONEY();
    protected abstract ParticleType LANDING_HONEY();

    // other working ones
    protected abstract ParticleType FLASH();
    protected abstract ParticleType FALLING_LAVA();
    protected abstract ParticleType FALLING_WATER();
    protected abstract ParticleType LANDING_LAVA();

    // 1.16
    protected abstract ParticleTypeMotion SOUL_FIRE_FLAME();
    protected abstract ParticleTypeMotion SOUL();

    protected abstract ParticleType ASH();
    protected abstract ParticleType WHITE_ASH();

    protected abstract ParticleType CRIMSON_SPORE();
    protected abstract ParticleType WARPED_SPORE();

    protected abstract ParticleType DRIPPING_OBSIDIAN_TEAR();
    protected abstract ParticleType FALLING_OBSIDIAN_TEAR();
    protected abstract ParticleType LANDING_OBSIDIAN_TEAR();

    protected abstract ParticleTypeMotion REVERSE_PORTAL();

    // 1.17
    protected abstract ParticleType LIGHT();// removed; replaced by BLOCK_MARKER with LIGHT block data in 1.18

    protected abstract ParticleTypeDustColorTransition DUST_COLOR_TRANSITION();
    protected abstract ParticleTypeVibrationSingle VIBRATION();

    protected abstract ParticleType FALLING_SPORE_BLOSSOM();
    protected abstract ParticleType SPORE_BLOSSOM_AIR();

    protected abstract ParticleTypeMotion SMALL_FLAME();
    protected abstract ParticleTypeMotion SNOWFLAKE();

    protected abstract ParticleType DRIPPING_DRIPSTONE_LAVA();
    protected abstract ParticleType DRIPPING_DRIPSTONE_WATER();
    protected abstract ParticleType FALLING_DRIPSTONE_LAVA();
    protected abstract ParticleType FALLING_DRIPSTONE_WATER();

    protected abstract ParticleTypeMotion GLOW_SQUID_INK();
    protected abstract ParticleType GLOW();

    protected abstract ParticleTypeMotion WAX_ON();
    protected abstract ParticleTypeMotion WAX_OFF();

    protected abstract ParticleTypeMotion ELECTRIC_SPARK();
    protected abstract ParticleTypeMotion SCRAPE();

    // 1.18
    protected abstract ParticleTypeBlock BLOCK_MARKER();

    // 1.19
    protected abstract ParticleType SONIC_BOOM();

    protected abstract ParticleTypeMotion SCULK_SOUL();
    protected abstract ParticleTypeSculkChargeMotion SCULK_CHARGE();
    protected abstract ParticleTypeMotion SCULK_CHARGE_POP();

    protected abstract ParticleTypeShriek SHRIEK();

}
