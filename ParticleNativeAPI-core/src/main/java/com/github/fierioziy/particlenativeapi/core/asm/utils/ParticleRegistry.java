package com.github.fierioziy.particlenativeapi.core.asm.utils;

import java.util.*;

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
        fillMap_1_18();
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

        put(particleMap.get("explode")          .follow("explosion_normal"));
        put(particleMap.get("largeexplode")     .follow("explosion_large"));
        put(particleMap.get("hugeexplosion")    .follow("explosion_huge"));
        put(particleMap.get("fireworksSpark")   .follow("fireworks_spark"));
        put(particleMap.get("bubble")           .follow("water_bubble"));
        put(particleMap.get("splash")           .follow("water_splash"));
        put(particleMap.get("wake")             .follow("water_wake"));
        put(particleMap.get("suspended")        .follow("suspended"));
        put(particleMap.get("depthsuspend")     .follow("suspended_depth"));
        put(particleMap.get("crit")             .follow("crit"));
        put(particleMap.get("magicCrit")        .follow("crit_magic"));
        put(particleMap.get("smoke")            .follow("smoke_normal"));
        put(particleMap.get("largesmoke")       .follow("smoke_large"));
        put(particleMap.get("spell")            .follow("spell"));
        put(particleMap.get("instantSpell")     .follow("spell_instant"));
        put(particleMap.get("mobSpell")         .follow("spell_mob"));
        put(particleMap.get("mobSpellAmbient")  .follow("spell_mob_ambient"));
        put(particleMap.get("witchMagic")       .follow("spell_witch"));
        put(particleMap.get("dripWater")        .follow("drip_water"));
        put(particleMap.get("dripLava")         .follow("drip_lava"));
        put(particleMap.get("angryVillager")    .follow("villager_angry"));
        put(particleMap.get("happyVillager")    .follow("villager_happy"));
        put(particleMap.get("townaura")         .follow("town_aura"));
        put(particleMap.get("note")             .follow("note"));
        put(particleMap.get("portal")           .follow("portal"));
        put(particleMap.get("enchantmenttable") .follow("enchantment_table"));
        put(particleMap.get("flame")            .follow("flame"));
        put(particleMap.get("lava")             .follow("lava"));
        put(particleMap.get("footstep")         .follow("footstep"));
        put(particleMap.get("cloud")            .follow("cloud"));
        put(particleMap.get("reddust")          .follow("redstone"));
        put(particleMap.get("snowballpoof")     .follow("snowball"));
        put(particleMap.get("snowshovel")       .follow("snow_shovel"));
        put(particleMap.get("slime")            .follow("slime"));
        put(particleMap.get("heart")            .follow("heart"));
        put(particleMap.get("iconcrack_")       .follow("item_crack"));
        put(particleMap.get("blockcrack_")      .follow("block_crack"));
        put(particleMap.get("blockdust_")       .follow("block_dust"));

        ParticleVersion v = ParticleVersion.V1_8;
        put(new ParticleNode(v, "barrier"));
        put(new ParticleNode(v, "water_drop"));
        put(new ParticleNode(v, "item_take"));
        put(new ParticleNode(v, "mob_appearance"));

        put(new ParticleNode(v, "dragon_breath"));
        put(new ParticleNode(v, "end_rod"));
        put(new ParticleNode(v, "damage_indicator"));
        put(new ParticleNode(v, "sweep_attack"));

        put(new ParticleNode(v, "falling_dust"));

        put(new ParticleNode(v, "totem"));
        put(new ParticleNode(v, "spit"));
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.13 version
     * and binds them to nodes from MC 1.8 version where possible.</p>
     */
    private void fillMap_1_13() {
        Map<String, ParticleNode> particleMap = particleMaps.get(
                ParticleVersion.V1_8.ordinal()
        );

        ParticleNode poofNode = particleMap.get("explosion_normal") .follow("poof");
        ParticleNode blockNode = particleMap.get("block_crack")     .follow("block");

        put(particleMap.get("explosion_normal")     .follow(poofNode));
        put(particleMap.get("explosion_large")      .follow("explosion"));
        put(particleMap.get("explosion_huge")       .follow("explosion_emitter"));
        put(particleMap.get("fireworks_spark")      .follow("firework"));
        put(particleMap.get("water_bubble")         .follow("bubble"));
        put(particleMap.get("water_splash")         .follow("splash"));
        put(particleMap.get("water_wake")           .follow("fishing"));
        put(particleMap.get("suspended")            .follow("underwater"));
        put(particleMap.get("suspended_depth")      .followRemoved());
        put(particleMap.get("crit")                 .follow("crit"));
        put(particleMap.get("crit_magic")           .follow("enchanted_hit"));
        put(particleMap.get("smoke_normal")         .follow("smoke"));
        put(particleMap.get("smoke_large")          .follow("large_smoke"));
        put(particleMap.get("spell")                .follow("effect"));
        put(particleMap.get("spell_instant")        .follow("instant_effect"));
        put(particleMap.get("spell_mob")            .follow("entity_effect"));
        put(particleMap.get("spell_mob_ambient")    .follow("ambient_entity_effect"));
        put(particleMap.get("spell_witch")          .follow("witch"));
        put(particleMap.get("drip_water")           .follow("dripping_water"));
        put(particleMap.get("drip_lava")            .follow("dripping_lava"));
        put(particleMap.get("villager_angry")       .follow("angry_villager"));
        put(particleMap.get("villager_happy")       .follow("happy_villager"));
        put(particleMap.get("town_aura")            .follow("mycelium"));
        put(particleMap.get("note")                 .follow("note"));
        put(particleMap.get("portal")               .follow("portal"));
        put(particleMap.get("enchantment_table")    .follow("enchant"));
        put(particleMap.get("flame")                .follow("flame"));
        put(particleMap.get("lava")                 .follow("lava"));
        put(particleMap.get("footstep")             .followRemoved());
        put(particleMap.get("cloud")                .follow("cloud"));
        put(particleMap.get("redstone")             .followRemoved());
        put(particleMap.get("snowball")             .follow("item_snowball"));
        put(particleMap.get("snow_shovel")          .follow(poofNode));
        put(particleMap.get("slime")                .follow("item_slime"));
        put(particleMap.get("heart")                .follow("heart"));

        put(particleMap.get("barrier")              .follow("barrier"));
        put(particleMap.get("item_crack")           .follow("item"));

        put(particleMap.get("block_crack")          .follow(blockNode));
        put(particleMap.get("block_dust")           .follow(blockNode));

        put(particleMap.get("water_drop")           .follow("rain"));
        put(particleMap.get("item_take")            .followRemoved());
        put(particleMap.get("mob_appearance")       .follow("elder_guardian"));

        put(particleMap.get("dragon_breath")        .follow("dragon_breath"));
        put(particleMap.get("end_rod")              .follow("end_rod"));
        put(particleMap.get("damage_indicator")     .follow("damage_indicator"));
        put(particleMap.get("sweep_attack")         .follow("sweep_attack"));

        put(particleMap.get("falling_dust")         .follow("falling_dust"));

        put(particleMap.get("totem")                .follow("totem_of_undying"));
        put(particleMap.get("spit")                 .follow("spit"));

        ParticleVersion v = ParticleVersion.V1_13;
        put(new ParticleNode(v, "dust"));
        put(new ParticleNode(v, "bubble_column_up"));
        put(new ParticleNode(v, "bubble_pop"));
        put(new ParticleNode(v, "current_down"));
        put(new ParticleNode(v, "squid_ink"));
        put(new ParticleNode(v, "nautilus"));
        put(new ParticleNode(v, "dolphin"));

        // 1.14
        put(new ParticleNode(v, "sneeze"));
        put(new ParticleNode(v, "campfire_cosy_smoke"));
        put(new ParticleNode(v, "campfire_signal_smoke"));
        put(new ParticleNode(v, "composter"));

        // 1.15
        put(new ParticleNode(v, "dripping_honey"));
        put(new ParticleNode(v, "falling_nectar"));
        put(new ParticleNode(v, "falling_honey"));
        put(new ParticleNode(v, "landing_honey"));

        // other working ones
        put(new ParticleNode(v, "flash"));
        put(new ParticleNode(v, "falling_lava"));
        put(new ParticleNode(v, "falling_water"));
        put(new ParticleNode(v, "landing_lava"));

        // 1.16
        put(new ParticleNode(v, "soul_fire_flame"));
        put(new ParticleNode(v, "soul"));
        put(new ParticleNode(v, "ash"));
        put(new ParticleNode(v, "crimson_spore"));
        put(new ParticleNode(v, "warped_spore"));
        put(new ParticleNode(v, "dripping_obsidian_tear"));
        put(new ParticleNode(v, "falling_obsidian_tear"));
        put(new ParticleNode(v, "landing_obsidian_tear"));
        put(new ParticleNode(v, "reverse_portal"));
        put(new ParticleNode(v, "white_ash"));

        // 1.17
        put(new ParticleNode(v, "light"));

        put(new ParticleNode(v, "dust_color_transition"));
        put(new ParticleNode(v, "vibration"));

        put(new ParticleNode(v, "falling_spore_blossom"));
        put(new ParticleNode(v, "spore_blossom_air"));

        put(new ParticleNode(v, "small_flame"));
        put(new ParticleNode(v, "snowflake"));

        put(new ParticleNode(v, "dripping_dripstone_lava"));
        put(new ParticleNode(v, "dripping_dripstone_water"));
        put(new ParticleNode(v, "falling_dripstone_lava"));
        put(new ParticleNode(v, "falling_dripstone_water"));

        put(new ParticleNode(v, "glow_squid_ink"));
        put(new ParticleNode(v, "glow"));

        put(new ParticleNode(v, "wax_on"));
        put(new ParticleNode(v, "wax_off"));

        put(new ParticleNode(v, "electric_spark"));
        put(new ParticleNode(v, "scrape"));
    }

    private void fillMap_1_18() {
        Map<String, ParticleNode> particleMap = particleMaps.get(
                ParticleVersion.V1_13.ordinal()
        );

        put(particleMap.get("barrier")                  .followRemoved());
        put(particleMap.get("light")                    .followRemoved());

        // forward compatibility
        // ughhhh
        // this will have to be done in different way
        // it violates the purpose of registry to contain snapshot of certain version's particles
        // and this hack will propagate further in an ugly way
        put(new ParticleNode(ParticleVersion.V1_13, "block_marker"));

        defaultFollowUnprocessedNodesTo(ParticleVersion.V1_18);
    }

    private void defaultFollowUnprocessedNodesTo(ParticleVersion currentVersion) {
        Map<String, ParticleNode> prevParticleMap = particleMaps.get(currentVersion.ordinal() - 1);
        Map<String, ParticleNode> currentParticleMap = particleMaps.get(currentVersion.ordinal());

        for (Map.Entry<String, ParticleNode> prevNodeEntry : prevParticleMap.entrySet()) {
            ParticleNode unboundNode = prevNodeEntry.getValue();

            for (Map.Entry<String, ParticleNode> currentNodeEntry : currentParticleMap.entrySet()) {
                if (unboundNode.isBound(currentNodeEntry.getValue())) {
                    unboundNode = null;
                    break;
                }
            }

            if (unboundNode != null) {
                put(unboundNode.follow());
            }
        }
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
