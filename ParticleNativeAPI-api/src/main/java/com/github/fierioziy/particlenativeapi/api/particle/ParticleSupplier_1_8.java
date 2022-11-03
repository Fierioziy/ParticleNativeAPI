package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.particle.type.*;

abstract class ParticleSupplier_1_8 {

    /*
    Methods below are overridden by dynamically generated subclasses.
    Their purpose is to be "factory" for fields with either particle type valid implementation
    or it's invalid dummy variant.
     */

    // 1.7
    protected abstract ParticleTypeMotion EXPLOSION_NORMAL();
    protected abstract ParticleType EXPLOSION_LARGE();
    protected abstract ParticleType EXPLOSION_HUGE();
    protected abstract ParticleTypeMotion FIREWORKS_SPARK();
    protected abstract ParticleTypeMotion WATER_BUBBLE();
    protected abstract ParticleType WATER_SPLASH();
    protected abstract ParticleTypeMotion WATER_WAKE();
    protected abstract ParticleType SUSPENDED();
    protected abstract ParticleType SUSPENDED_DEPTH();
    protected abstract ParticleTypeMotion CRIT();
    protected abstract ParticleTypeMotion CRIT_MAGIC();
    protected abstract ParticleTypeMotion SMOKE_NORMAL();
    protected abstract ParticleTypeMotion SMOKE_LARGE();
    protected abstract ParticleType SPELL();
    protected abstract ParticleType SPELL_INSTANT();
    protected abstract ParticleTypeColorable SPELL_MOB();
    protected abstract ParticleTypeColorable SPELL_MOB_AMBIENT();
    protected abstract ParticleType SPELL_WITCH();
    protected abstract ParticleType DRIP_WATER();
    protected abstract ParticleType DRIP_LAVA();
    protected abstract ParticleType VILLAGER_ANGRY();
    protected abstract ParticleType VILLAGER_HAPPY();
    protected abstract ParticleType TOWN_AURA();
    protected abstract ParticleTypeNote NOTE();
    protected abstract ParticleTypeMotion PORTAL();
    protected abstract ParticleTypeMotion ENCHANTMENT_TABLE();
    protected abstract ParticleTypeMotion FLAME();
    protected abstract ParticleType LAVA();
    protected abstract ParticleType FOOTSTEP();
    protected abstract ParticleTypeMotion CLOUD();
    protected abstract ParticleTypeRedstone REDSTONE();
    protected abstract ParticleType SNOWBALL();
    protected abstract ParticleTypeMotion SNOW_SHOVEL();
    protected abstract ParticleType SLIME();
    protected abstract ParticleType HEART();

    protected abstract ParticleTypeItemMotion ITEM_CRACK();
    protected abstract ParticleTypeBlockMotion BLOCK_CRACK();
    protected abstract ParticleTypeBlockMotion BLOCK_DUST();

    // 1.8
    protected abstract ParticleType BARRIER();
    protected abstract ParticleType WATER_DROP();
    protected abstract ParticleType ITEM_TAKE();
    protected abstract ParticleType MOB_APPEARANCE();

    // 1.9
    protected abstract ParticleTypeMotion DRAGON_BREATH();
    protected abstract ParticleTypeMotion END_ROD();
    protected abstract ParticleTypeMotion DAMAGE_INDICATOR();
    protected abstract ParticleType SWEEP_ATTACK();

    // 1.10
    protected abstract ParticleTypeBlock FALLING_DUST();

    // 1.11
    protected abstract ParticleTypeMotion TOTEM();
    protected abstract ParticleTypeMotion SPIT();

}
