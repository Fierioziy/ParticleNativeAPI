package com.github.fierioziy.particlenativeapi.core.asm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>A registry representing all particle name changes between
 * various Spigot versions.</p>
 *
 * <p>It consist of list containing a maps of <code>ParticleNode</code> nodes
 * for each significant Spigot version change.</p>
 *
 * <p>This approach allows for fast access to <code>ParticleNode</code> nodes of
 * current particle name in running Spigot version using <code>ParticleVersion</code> enum
 * as index in the list. Next, it uses particle name as a key for returned map that
 * returns <code>ParticleNode</code> associated with current particle.</p>
 *
 * <p>This node is then used to find certain's particle in desired Spigot version.</p>
 *
 * <p>This registry only exists during class generation and will be
 * garbage collected after implementations of interface instantiation
 * finishes.</p>
 */
public class ParticleRegistry {

    private List<Map<String, ParticleNode>> particleMaps = new ArrayList<>();

    public ParticleRegistry() {
        ParticleVersion[] versions = ParticleVersion.values();
        for (int i = 0; i < versions.length; ++i) {
            particleMaps.add(new HashMap<String, ParticleNode>());
        }

        fillMap_1_7();
        fillMap_1_8();
        fillMap_1_13();
        fillMap_1_17();
    }

    /**
     * <p>Attempts to convert particle name from provided Spigot version
     * to target Spigot version.</p>
     *
     * @param from a <code>ParticleVersion</code> enum representing Spigot
     *             version of parameter particle name.
     * @param name a particle name in parameter <code>from</code> Spigot version.
     * @param to a <code>ParticleVersion</code> enum representing Spigot version
     *           to which attempt of conversion should happen.
     * @return a converted particle name or null if particle has been removed
     * or non-existent in target Spigot version.
     */
    public String find(ParticleVersion from, String name,
                       ParticleVersion to) {
        ParticleNode node = particleMaps.get(from.ordinal()).get(name);
        if (node == null) return null;

        ParticleNode resolvedNode = node.find(to);
        if (resolvedNode == null || resolvedNode.isRemoved()) return null;

        return resolvedNode.getName();
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.7 version.</p>
     */
    private void fillMap_1_7() {
        ParticleVersion v = ParticleVersion.V1_7;
        put(new ParticleNode(v, "explode"));
        put(new ParticleNode(v, "largeexplode"));
        put(new ParticleNode(v, "hugeexplosion"));
        put(new ParticleNode(v, "fireworksSpark"));
        put(new ParticleNode(v, "bubble"));
        put(new ParticleNode(v, "splash"));
        put(new ParticleNode(v, "wake"));
        put(new ParticleNode(v, "suspended"));
        put(new ParticleNode(v, "depthsuspend"));
        put(new ParticleNode(v, "crit"));
        put(new ParticleNode(v, "magicCrit"));
        put(new ParticleNode(v, "smoke"));
        put(new ParticleNode(v, "largesmoke"));
        put(new ParticleNode(v, "spell"));
        put(new ParticleNode(v, "instantSpell"));
        put(new ParticleNode(v, "mobSpell"));
        put(new ParticleNode(v, "mobSpellAmbient"));
        put(new ParticleNode(v, "witchMagic"));
        put(new ParticleNode(v, "dripWater"));
        put(new ParticleNode(v, "dripLava"));
        put(new ParticleNode(v, "angryVillager"));
        put(new ParticleNode(v, "happyVillager"));
        put(new ParticleNode(v, "townaura"));
        put(new ParticleNode(v, "note"));
        put(new ParticleNode(v, "portal"));
        put(new ParticleNode(v, "enchantmenttable"));
        put(new ParticleNode(v, "flame"));
        put(new ParticleNode(v, "lava"));
        put(new ParticleNode(v, "footstep"));
        put(new ParticleNode(v, "cloud"));
        put(new ParticleNode(v, "reddust"));
        put(new ParticleNode(v, "snowballpoof"));
        put(new ParticleNode(v, "snowshovel"));
        put(new ParticleNode(v, "slime"));
        put(new ParticleNode(v, "heart"));
        put(new ParticleNode(v, "iconcrack_"));
        put(new ParticleNode(v, "blockcrack_"));
        put(new ParticleNode(v, "blockdust_"));
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.8 version
     * and binds them to nodes from MC 1.7 version where possible.</p>
     */
    private void fillMap_1_8() {
        Map<String, ParticleNode> particleMap = particleMaps.get(
                ParticleVersion.V1_7.ordinal()
        );

        put(particleMap.get("explode")          .follow("EXPLOSION_NORMAL"));
        put(particleMap.get("largeexplode")     .follow("EXPLOSION_LARGE"));
        put(particleMap.get("hugeexplosion")    .follow("EXPLOSION_HUGE"));
        put(particleMap.get("fireworksSpark")   .follow("FIREWORKS_SPARK"));
        put(particleMap.get("bubble")           .follow("WATER_BUBBLE"));
        put(particleMap.get("splash")           .follow("WATER_SPLASH"));
        put(particleMap.get("wake")             .follow("WATER_WAKE"));
        put(particleMap.get("suspended")        .follow("SUSPENDED"));
        put(particleMap.get("depthsuspend")     .follow("SUSPENDED_DEPTH"));
        put(particleMap.get("crit")             .follow("CRIT"));
        put(particleMap.get("magicCrit")        .follow("CRIT_MAGIC"));
        put(particleMap.get("smoke")            .follow("SMOKE_NORMAL"));
        put(particleMap.get("largesmoke")       .follow("SMOKE_LARGE"));
        put(particleMap.get("spell")            .follow("SPELL"));
        put(particleMap.get("instantSpell")     .follow("SPELL_INSTANT"));
        put(particleMap.get("mobSpell")         .follow("SPELL_MOB"));
        put(particleMap.get("mobSpellAmbient")  .follow("SPELL_MOB_AMBIENT"));
        put(particleMap.get("witchMagic")       .follow("SPELL_WITCH"));
        put(particleMap.get("dripWater")        .follow("DRIP_WATER"));
        put(particleMap.get("dripLava")         .follow("DRIP_LAVA"));
        put(particleMap.get("angryVillager")    .follow("VILLAGER_ANGRY"));
        put(particleMap.get("happyVillager")    .follow("VILLAGER_HAPPY"));
        put(particleMap.get("townaura")         .follow("TOWN_AURA"));
        put(particleMap.get("note")             .follow("NOTE"));
        put(particleMap.get("portal")           .follow("PORTAL"));
        put(particleMap.get("enchantmenttable") .follow("ENCHANTMENT_TABLE"));
        put(particleMap.get("flame")            .follow("FLAME"));
        put(particleMap.get("lava")             .follow("LAVA"));
        put(particleMap.get("footstep")         .follow("FOOTSTEP"));
        put(particleMap.get("cloud")            .follow("CLOUD"));
        put(particleMap.get("reddust")          .follow("REDSTONE"));
        put(particleMap.get("snowballpoof")     .follow("SNOWBALL"));
        put(particleMap.get("snowshovel")       .follow("SNOW_SHOVEL"));
        put(particleMap.get("slime")            .follow("SLIME"));
        put(particleMap.get("heart")            .follow("HEART"));
        put(particleMap.get("iconcrack_")       .follow("ITEM_CRACK"));
        put(particleMap.get("blockcrack_")      .follow("BLOCK_CRACK"));
        put(particleMap.get("blockdust_")       .follow("BLOCK_DUST"));

        ParticleVersion v = ParticleVersion.V1_8;
        put(new ParticleNode(v, "BARRIER"));
        put(new ParticleNode(v, "WATER_DROP"));
        put(new ParticleNode(v, "ITEM_TAKE"));
        put(new ParticleNode(v, "MOB_APPEARANCE"));

        put(new ParticleNode(v, "DRAGON_BREATH"));
        put(new ParticleNode(v, "END_ROD"));
        put(new ParticleNode(v, "DAMAGE_INDICATOR"));
        put(new ParticleNode(v, "SWEEP_ATTACK"));

        put(new ParticleNode(v, "FALLING_DUST"));

        put(new ParticleNode(v, "TOTEM"));
        put(new ParticleNode(v, "SPIT"));
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.13 version
     * and binds them to nodes from MC 1.8 version where possible.</p>
     */
    private void fillMap_1_13() {
        Map<String, ParticleNode> particleMap = particleMaps.get(
                ParticleVersion.V1_8.ordinal()
        );

        ParticleNode poofNode = particleMap.get("EXPLOSION_NORMAL") .follow("POOF");
        ParticleNode blockNode = particleMap.get("BLOCK_CRACK")     .follow("BLOCK");

        put(particleMap.get("EXPLOSION_NORMAL")     .follow(poofNode));
        put(particleMap.get("EXPLOSION_LARGE")      .follow("EXPLOSION"));
        put(particleMap.get("EXPLOSION_HUGE")       .follow("EXPLOSION_EMITTER"));
        put(particleMap.get("FIREWORKS_SPARK")      .follow("FIREWORK"));
        put(particleMap.get("WATER_BUBBLE")         .follow("BUBBLE"));
        put(particleMap.get("WATER_SPLASH")         .follow("SPLASH"));
        put(particleMap.get("WATER_WAKE")           .follow("FISHING"));
        put(particleMap.get("SUSPENDED")            .follow("UNDERWATER"));
        put(particleMap.get("SUSPENDED_DEPTH")      .followRemoved());
        put(particleMap.get("CRIT")                 .follow());
        put(particleMap.get("CRIT_MAGIC")           .follow("ENCHANTED_HIT"));
        put(particleMap.get("SMOKE_NORMAL")         .follow("SMOKE"));
        put(particleMap.get("SMOKE_LARGE")          .follow("LARGE_SMOKE"));
        put(particleMap.get("SPELL")                .follow("EFFECT"));
        put(particleMap.get("SPELL_INSTANT")        .follow("INSTANT_EFFECT"));
        put(particleMap.get("SPELL_MOB")            .follow("ENTITY_EFFECT"));
        put(particleMap.get("SPELL_MOB_AMBIENT")    .follow("AMBIENT_ENTITY_EFFECT"));
        put(particleMap.get("SPELL_WITCH")          .follow("WITCH"));
        put(particleMap.get("DRIP_WATER")           .follow("DRIPPING_WATER"));
        put(particleMap.get("DRIP_LAVA")            .follow("DRIPPING_LAVA"));
        put(particleMap.get("VILLAGER_ANGRY")       .follow("ANGRY_VILLAGER"));
        put(particleMap.get("VILLAGER_HAPPY")       .follow("HAPPY_VILLAGER"));
        put(particleMap.get("TOWN_AURA")            .follow("MYCELIUM"));
        put(particleMap.get("NOTE")                 .follow());
        put(particleMap.get("PORTAL")               .follow());
        put(particleMap.get("ENCHANTMENT_TABLE")    .follow("ENCHANT"));
        put(particleMap.get("FLAME")                .follow());
        put(particleMap.get("LAVA")                 .follow());
        put(particleMap.get("FOOTSTEP")             .followRemoved());
        put(particleMap.get("CLOUD")                .follow());
        put(particleMap.get("REDSTONE")             .followForward("DUST"));
        put(particleMap.get("SNOWBALL")             .follow("ITEM_SNOWBALL"));
        put(particleMap.get("SNOW_SHOVEL")          .follow(poofNode));
        put(particleMap.get("SLIME")                .follow("ITEM_SLIME"));
        put(particleMap.get("HEART")                .follow());

        put(particleMap.get("BARRIER")              .follow());
        put(particleMap.get("ITEM_CRACK")           .follow("ITEM"));

        put(particleMap.get("BLOCK_CRACK")          .follow(blockNode));
        put(particleMap.get("BLOCK_DUST")           .follow(blockNode));

        put(particleMap.get("WATER_DROP")           .follow("RAIN"));
        put(particleMap.get("ITEM_TAKE")            .followRemoved());
        put(particleMap.get("MOB_APPEARANCE")       .follow("ELDER_GUARDIAN"));

        put(particleMap.get("DRAGON_BREATH")        .follow());
        put(particleMap.get("END_ROD")              .follow());
        put(particleMap.get("DAMAGE_INDICATOR")     .follow());
        put(particleMap.get("SWEEP_ATTACK")         .follow());

        put(particleMap.get("FALLING_DUST")         .follow());

        put(particleMap.get("TOTEM")                .follow("TOTEM_OF_UNDYING"));
        put(particleMap.get("SPIT")                 .follow());

        ParticleVersion v = ParticleVersion.V1_13;
        put(new ParticleNode(v, "BUBBLE_COLUMN_UP"));
        put(new ParticleNode(v, "BUBBLE_POP"));
        put(new ParticleNode(v, "CURRENT_DOWN"));
        put(new ParticleNode(v, "SQUID_INK"));
        put(new ParticleNode(v, "NAUTILUS"));
        put(new ParticleNode(v, "DOLPHIN"));
        put(new ParticleNode(v, "SNEEZE"));
        put(new ParticleNode(v, "CAMPFIRE_COSY_SMOKE"));
        put(new ParticleNode(v, "CAMPFIRE_SIGNAL_SMOKE"));

        // other working ones
        put(new ParticleNode(v, "COMPOSTER"));
        put(new ParticleNode(v, "FLASH"));
        put(new ParticleNode(v, "DRIPPING_HONEY"));

        put(new ParticleNode(v, "FALLING_NECTAR"));
        put(new ParticleNode(v, "FALLING_HONEY"));
        put(new ParticleNode(v, "FALLING_LAVA"));
        put(new ParticleNode(v, "FALLING_WATER"));

        put(new ParticleNode(v, "LANDING_HONEY"));
        put(new ParticleNode(v, "LANDING_LAVA"));

        // 1.16
        put(new ParticleNode(v, "SOUL_FIRE_FLAME"));
        put(new ParticleNode(v, "SOUL"));
        put(new ParticleNode(v, "ASH"));
        put(new ParticleNode(v, "CRIMSON_SPORE"));
        put(new ParticleNode(v, "WARPED_SPORE"));
        put(new ParticleNode(v, "DRIPPING_OBSIDIAN_TEAR"));
        put(new ParticleNode(v, "FALLING_OBSIDIAN_TEAR"));
        put(new ParticleNode(v, "LANDING_OBSIDIAN_TEAR"));
        put(new ParticleNode(v, "REVERSE_PORTAL"));
        put(new ParticleNode(v, "WHITE_ASH"));
    }

    private void fillMap_1_17() {
        Map<String, ParticleNode> particleMap = particleMaps.get(
                ParticleVersion.V1_13.ordinal()
        );

        put(particleMap.get("POOF")                     .follow("poof"));
        put(particleMap.get("EXPLOSION")                .follow("explosion"));
        put(particleMap.get("EXPLOSION_EMITTER")        .follow("explosion_emitter"));
        put(particleMap.get("FIREWORK")                 .follow("firework"));
        put(particleMap.get("BUBBLE")                   .follow("bubble"));
        put(particleMap.get("SPLASH")                   .follow("splash"));
        put(particleMap.get("FISHING")                  .follow("fishing"));
        put(particleMap.get("UNDERWATER")               .follow("underwater"));
        put(particleMap.get("SUSPENDED_DEPTH")          .followRemoved());
        put(particleMap.get("CRIT")                     .follow("crit"));
        put(particleMap.get("ENCHANTED_HIT")            .follow("enchanted_hit"));
        put(particleMap.get("SMOKE")                    .follow("smoke"));
        put(particleMap.get("LARGE_SMOKE")              .follow("large_smoke"));
        put(particleMap.get("EFFECT")                   .follow("effect"));
        put(particleMap.get("INSTANT_EFFECT")           .follow("instant_effect"));
        put(particleMap.get("ENTITY_EFFECT")            .follow("entity_effect"));
        put(particleMap.get("AMBIENT_ENTITY_EFFECT")    .follow("ambient_entity_effect"));
        put(particleMap.get("WITCH")                    .follow("witch"));
        put(particleMap.get("DRIPPING_WATER")           .follow("dripping_water"));
        put(particleMap.get("DRIPPING_LAVA")            .follow("dripping_lava"));
        put(particleMap.get("ANGRY_VILLAGER")           .follow("angry_villager"));
        put(particleMap.get("HAPPY_VILLAGER")           .follow("happy_villager"));
        put(particleMap.get("MYCELIUM")                 .follow("mycelium"));
        put(particleMap.get("NOTE")                     .follow("note"));
        put(particleMap.get("PORTAL")                   .follow("portal"));
        put(particleMap.get("ENCHANT")                  .follow("enchant"));
        put(particleMap.get("FLAME")                    .follow("flame"));
        put(particleMap.get("LAVA")                     .follow("lava"));
        put(particleMap.get("FOOTSTEP")                 .followRemoved());
        put(particleMap.get("CLOUD")                    .follow("cloud"));
        put(particleMap.get("DUST")                     .follow("dust"));
        put(particleMap.get("ITEM_SNOWBALL")            .follow("item_snowball"));
        put(particleMap.get("ITEM_SLIME")               .follow("item_slime"));
        put(particleMap.get("HEART")                    .follow("heart"));

        put(particleMap.get("BARRIER")                  .follow("barrier"));
        put(particleMap.get("ITEM")                     .follow("item"));

        put(particleMap.get("BLOCK")                    .follow("block"));

        put(particleMap.get("RAIN")                     .follow("rain"));
        put(particleMap.get("ITEM_TAKE")                .followRemoved());
        put(particleMap.get("ELDER_GUARDIAN")           .follow("elder_guardian"));

        put(particleMap.get("DRAGON_BREATH")            .follow("dragon_breath"));
        put(particleMap.get("END_ROD")                  .follow("end_rod"));
        put(particleMap.get("DAMAGE_INDICATOR")         .follow("damage_indicator"));
        put(particleMap.get("SWEEP_ATTACK")             .follow("sweep_attack"));

        put(particleMap.get("FALLING_DUST")             .follow("falling_dust"));

        put(particleMap.get("TOTEM_OF_UNDYING")         .follow("totem_of_undying"));
        put(particleMap.get("SPIT")                     .follow("spit"));

        put(particleMap.get("BUBBLE_COLUMN_UP")         .follow("bubble_column_up"));
        put(particleMap.get("BUBBLE_POP")               .follow("bubble_pop"));
        put(particleMap.get("CURRENT_DOWN")             .follow("current_down"));
        put(particleMap.get("SQUID_INK")                .follow("squid_ink"));
        put(particleMap.get("NAUTILUS")                 .follow("nautilus"));
        put(particleMap.get("DOLPHIN")                  .follow("dolphin"));
        put(particleMap.get("SNEEZE")                   .follow("sneeze"));
        put(particleMap.get("CAMPFIRE_COSY_SMOKE")      .follow("campfire_cosy_smoke"));
        put(particleMap.get("CAMPFIRE_SIGNAL_SMOKE")    .follow("campfire_signal_smoke"));

        // other working ones
        put(particleMap.get("COMPOSTER")                .follow("composter"));
        put(particleMap.get("FLASH")                    .follow("flash"));
        put(particleMap.get("DRIPPING_HONEY")           .follow("dripping_honey"));

        put(particleMap.get("FALLING_NECTAR")           .follow("falling_nectar"));
        put(particleMap.get("FALLING_HONEY")            .follow("falling_honey"));
        put(particleMap.get("FALLING_LAVA")             .follow("falling_lava"));
        put(particleMap.get("FALLING_WATER")            .follow("falling_water"));

        put(particleMap.get("LANDING_HONEY")            .follow("landing_honey"));
        put(particleMap.get("LANDING_LAVA")             .follow("landing_lava"));

        // 1.16
        put(particleMap.get("SOUL_FIRE_FLAME")          .follow("soul_fire_flame"));
        put(particleMap.get("SOUL")                     .follow("soul"));
        put(particleMap.get("ASH")                      .follow("ash"));
        put(particleMap.get("CRIMSON_SPORE")            .follow("crimson_spore"));
        put(particleMap.get("WARPED_SPORE")             .follow("warped_spore"));
        put(particleMap.get("DRIPPING_OBSIDIAN_TEAR")   .follow("dripping_obsidian_tear"));
        put(particleMap.get("FALLING_OBSIDIAN_TEAR")    .follow("falling_obsidian_tear"));
        put(particleMap.get("LANDING_OBSIDIAN_TEAR")    .follow("landing_obsidian_tear"));
        put(particleMap.get("REVERSE_PORTAL")           .follow("reverse_portal"));
        put(particleMap.get("WHITE_ASH")                .follow("white_ash"));

        // for forward compatibility
        ParticleVersion v = ParticleVersion.V1_13;
        put(new ParticleNode(v, "LIGHT"));
        put(new ParticleNode(v, "DUST_COLOR_TRANSITION"));
        put(new ParticleNode(v, "VIBRATION"));
        put(new ParticleNode(v, "FALLING_SPORE_BLOSSOM"));
        put(new ParticleNode(v, "SPORE_BLOSSOM_AIR"));
        put(new ParticleNode(v, "SMALL_FLAME"));
        put(new ParticleNode(v, "SNOWFLAKE"));
        put(new ParticleNode(v, "DRIPPING_DRIPSTONE_LAVA"));
        put(new ParticleNode(v, "DRIPPING_DRIPSTONE_WATER"));
        put(new ParticleNode(v, "FALLING_DRIPSTONE_LAVA"));
        put(new ParticleNode(v, "FALLING_DRIPSTONE_WATER"));
        put(new ParticleNode(v, "GLOW_SQUID_INK"));
        put(new ParticleNode(v, "GLOW"));
        put(new ParticleNode(v, "WAX_ON"));
        put(new ParticleNode(v, "WAX_OFF"));
        put(new ParticleNode(v, "ELECTRIC_SPARK"));
        put(new ParticleNode(v, "SCRAPE"));

        put(particleMap.get("LIGHT")                        .followForward("light"));
        put(particleMap.get("DUST_COLOR_TRANSITION")        .followForward("dust_color_transition"));
        put(particleMap.get("VIBRATION")                    .followForward("vibration"));
        put(particleMap.get("FALLING_SPORE_BLOSSOM")        .followForward("falling_spore_blossom"));
        put(particleMap.get("SPORE_BLOSSOM_AIR")            .followForward("spore_blossom_air"));
        put(particleMap.get("SMALL_FLAME")                  .followForward("small_flame"));
        put(particleMap.get("SNOWFLAKE")                    .followForward("snowflake"));
        put(particleMap.get("DRIPPING_DRIPSTONE_LAVA")      .followForward("dripping_dripstone_lava"));
        put(particleMap.get("DRIPPING_DRIPSTONE_WATER")     .followForward("dripping_dripstone_water"));
        put(particleMap.get("FALLING_DRIPSTONE_LAVA")       .followForward("falling_dripstone_lava"));
        put(particleMap.get("FALLING_DRIPSTONE_WATER")      .followForward("falling_dripstone_water"));
        put(particleMap.get("GLOW_SQUID_INK")               .followForward("glow_squid_ink"));
        put(particleMap.get("GLOW")                         .followForward("glow"));
        put(particleMap.get("WAX_ON")                       .followForward("wax_on"));
        put(particleMap.get("WAX_OFF")                      .followForward("wax_off"));
        put(particleMap.get("ELECTRIC_SPARK")               .followForward("electric_spark"));
        put(particleMap.get("SCRAPE")                       .followForward("scrape"));
    }

    /**
     * <p>Puts parameter node in appropriate map.</p>
     * @param node a node to put in a map.
     */
    private void put(ParticleNode node) {
        particleMaps.get(node.getVersion().ordinal()).put(
                node.getName(), node
        );
    }

}
