package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

import java.util.*;

/**
 * <p>A registry representing all particle name changes between
 * various Spigot versions.</p>
 *
 * <p>It consist of list containing a maps of {@link ParticleNode} nodes
 * for each significant Spigot version change.</p>
 *
 * <p>This approach allows for fast access to {@link ParticleNode} nodes of
 * current particle name in running Spigot version using {@link SpigotParticleVersion} enum
 * as index in the list. Next, it uses particle name as a key for returned map that
 * returns {@link ParticleNode} associated with current particle.</p>
 *
 * <p>This node is then used to find certain's particle in desired Spigot version.</p>
 *
 * <p>This registry takes some memory, but it only exists during class generation and will be
 * garbage collected after implementations of interface instantiation
 * finishes.</p>
 */
public class ParticleRegistry {

    private List<Map<String, ParticleNode>> particleMaps = new ArrayList<>(SpigotParticleVersion.VERSION_COUNT);

    public ParticleRegistry() {
        SpigotParticleVersion[] versions = SpigotParticleVersion.values();
        for (int i = 0; i < versions.length; ++i) {
            particleMaps.add(new HashMap<>(110));// slightly more for future particles to minimize resizing
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
     * @param from a {@link SpigotParticleVersion} enum representing Spigot
     *             version of parameter particle name.
     * @param name a particle name in parameter <code>from</code> Spigot version.
     * @param to a {@link SpigotParticleVersion} enum representing Spigot version
     *           to which attempt of conversion should happen.
     * @return an {@link Optional} containing converted particle name or empty if particle has been removed
     * or non-existent in target Spigot version.
     */
    public Optional<String> find(SpigotParticleVersion from, String name,
                                 SpigotParticleVersion to) {
        ParticleNode node = particleMaps.get(from.ordinal()).get(name);

        if (node == null) {
            throw new ParticleException(String.format(
                    "Could not find particle node with name %s in particle version %s",
                    name, from.name()
            ));
        }

        ParticleNode resolvedNode = node.find(to);

        return !resolvedNode.isRemoved() ? Optional.of(resolvedNode.getName()) : Optional.empty();
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.7 version.</p>
     */
    private void fillMap_1_7() {
        ParticleNodeRegistrar registrar = new ParticleNodeRegistrar(SpigotParticleVersion.V1_7);

        registrar.followNew("explode");
        registrar.followNew("largeexplode");
        registrar.followNew("hugeexplosion");
        registrar.followNew("fireworksSpark");
        registrar.followNew("bubble");
        registrar.followNew("splash");
        registrar.followNew("wake");
        registrar.followNew("suspended");
        registrar.followNew("depthsuspend");
        registrar.followNew("crit");
        registrar.followNew("magicCrit");
        registrar.followNew("smoke");
        registrar.followNew("largesmoke");
        registrar.followNew("spell");
        registrar.followNew("instantSpell");
        registrar.followNew("mobSpell");
        registrar.followNew("mobSpellAmbient");
        registrar.followNew("witchMagic");
        registrar.followNew("dripWater");
        registrar.followNew("dripLava");
        registrar.followNew("angryVillager");
        registrar.followNew("happyVillager");
        registrar.followNew("townaura");
        registrar.followNew("note");
        registrar.followNew("portal");
        registrar.followNew("enchantmenttable");
        registrar.followNew("flame");
        registrar.followNew("lava");
        registrar.followNew("footstep");
        registrar.followNew("cloud");
        registrar.followNew("reddust");
        registrar.followNew("snowballpoof");
        registrar.followNew("snowshovel");
        registrar.followNew("slime");
        registrar.followNew("heart");
        registrar.followNew("iconcrack_");
        registrar.followNew("blockcrack_");
        registrar.followNew("blockdust_");
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.8 version
     * and binds them to nodes from MC 1.7 version where possible.</p>
     */
    private void fillMap_1_8() {
        ParticleNodeRegistrar registrar = new ParticleNodeRegistrar(SpigotParticleVersion.V1_8);

        registrar.follow("explode",          "explosion_normal");
        registrar.follow("largeexplode",     "explosion_large");
        registrar.follow("hugeexplosion",    "explosion_huge");
        registrar.follow("fireworksSpark",   "fireworks_spark");
        registrar.follow("bubble",           "water_bubble");
        registrar.follow("splash",           "water_splash");
        registrar.follow("wake",             "water_wake");
        registrar.follow("suspended",        "suspended");
        registrar.follow("depthsuspend",     "suspended_depth");
        registrar.follow("crit",             "crit");
        registrar.follow("magicCrit",        "crit_magic");
        registrar.follow("smoke",            "smoke_normal");
        registrar.follow("largesmoke",       "smoke_large");
        registrar.follow("spell",            "spell");
        registrar.follow("instantSpell",     "spell_instant");
        registrar.follow("mobSpell",         "spell_mob");
        registrar.follow("mobSpellAmbient",  "spell_mob_ambient");
        registrar.follow("witchMagic",       "spell_witch");
        registrar.follow("dripWater",        "drip_water");
        registrar.follow("dripLava",         "drip_lava");
        registrar.follow("angryVillager",    "villager_angry");
        registrar.follow("happyVillager",    "villager_happy");
        registrar.follow("townaura",         "town_aura");
        registrar.follow("note",             "note");
        registrar.follow("portal",           "portal");
        registrar.follow("enchantmenttable", "enchantment_table");
        registrar.follow("flame",            "flame");
        registrar.follow("lava",             "lava");
        registrar.follow("footstep",         "footstep");
        registrar.follow("cloud",            "cloud");
        registrar.follow("reddust",          "redstone");
        registrar.follow("snowballpoof",     "snowball");
        registrar.follow("snowshovel",       "snow_shovel");
        registrar.follow("slime",            "slime");
        registrar.follow("heart",            "heart");
        registrar.follow("iconcrack_",       "item_crack");
        registrar.follow("blockcrack_",      "block_crack");
        registrar.follow("blockdust_",       "block_dust");

        registrar.followNew("barrier");
        registrar.followNew("water_drop");
        registrar.followNew("item_take");
        registrar.followNew("mob_appearance");

        registrar.followNew("dragon_breath");
        registrar.followNew("end_rod");
        registrar.followNew("damage_indicator");
        registrar.followNew("sweep_attack");

        registrar.followNew("falling_dust");

        registrar.followNew("totem");
        registrar.followNew("spit");
    }

    /**
     * <p>Fills map with nodes of particles in MC 1.13 version
     * and binds them to nodes from MC 1.8 version where possible.</p>
     */
    private void fillMap_1_13() {
        ParticleNodeRegistrar registrar = new ParticleNodeRegistrar(SpigotParticleVersion.V1_13);

        // pre register for merge
        registrar.follow(           "explosion_normal",     "poof");
        registrar.follow(           "block_crack",          "block");

        registrar.follow(           "explosion_large",      "explosion");
        registrar.follow(           "explosion_huge",       "explosion_emitter");
        registrar.follow(           "fireworks_spark",      "firework");
        registrar.follow(           "water_bubble",         "bubble");
        registrar.follow(           "water_splash",         "splash");
        registrar.follow(           "water_wake",           "fishing");
        registrar.follow(           "suspended",            "underwater");
        registrar.followRemoved(    "suspended_depth");
        registrar.follow(           "crit",                 "crit");
        registrar.follow(           "crit_magic",           "enchanted_hit");
        registrar.follow(           "smoke_normal",         "smoke");
        registrar.follow(           "smoke_large",          "large_smoke");
        registrar.follow(           "spell",                "effect");
        registrar.follow(           "spell_instant",        "instant_effect");
        registrar.follow(           "spell_mob",            "entity_effect");
        registrar.follow(           "spell_mob_ambient",    "ambient_entity_effect");
        registrar.follow(           "spell_witch",          "witch");
        registrar.follow(           "drip_water",           "dripping_water");
        registrar.follow(           "drip_lava",            "dripping_lava");
        registrar.follow(           "villager_angry",       "angry_villager");
        registrar.follow(           "villager_happy",       "happy_villager");
        registrar.follow(           "town_aura",            "mycelium");
        registrar.follow(           "note",                 "note");
        registrar.follow(           "portal",               "portal");
        registrar.follow(           "enchantment_table",    "enchant");
        registrar.follow(           "flame",                "flame");
        registrar.follow(           "lava",                 "lava");
        registrar.followRemoved(    "footstep");
        registrar.follow(           "cloud",                "cloud");
        registrar.followRemoved(    "redstone");
        registrar.follow(           "snowball",             "item_snowball");
        registrar.followMerge(      "snow_shovel",          "poof");
        registrar.follow(           "slime",                "item_slime");
        registrar.follow(           "heart",                "heart");

        registrar.follow(           "barrier",              "barrier");
        registrar.follow(           "item_crack",           "item");

        registrar.followMerge(      "block_dust",           "block");

        registrar.follow(           "water_drop",           "rain");
        registrar.followRemoved(    "item_take");

        registrar.follow(           "mob_appearance",       "elder_guardian");

        registrar.follow(           "dragon_breath",        "dragon_breath");
        registrar.follow(           "end_rod",              "end_rod");
        registrar.follow(           "damage_indicator",     "damage_indicator");
        registrar.follow(           "sweep_attack",         "sweep_attack");

        registrar.follow(           "falling_dust",         "falling_dust");

        registrar.follow(           "totem",                "totem_of_undying");
        registrar.follow(           "spit",                 "spit");

        registrar.followNew("dust");
        registrar.followNew("bubble_column_up");
        registrar.followNew("bubble_pop");
        registrar.followNew("current_down");
        registrar.followNew("squid_ink");
        registrar.followNew("nautilus");
        registrar.followNew("dolphin");

        // 1.14
        registrar.followNew("sneeze");
        registrar.followNew("campfire_cosy_smoke");
        registrar.followNew("campfire_signal_smoke");
        registrar.followNew("composter");

        // 1.15
        registrar.followNew("dripping_honey");
        registrar.followNew("falling_nectar");
        registrar.followNew("falling_honey");
        registrar.followNew("landing_honey");

        // other working ones
        registrar.followNew("flash");
        registrar.followNew("falling_lava");
        registrar.followNew("falling_water");
        registrar.followNew("landing_lava");

        // 1.16
        registrar.followNew("soul_fire_flame");
        registrar.followNew("soul");
        registrar.followNew("ash");
        registrar.followNew("crimson_spore");
        registrar.followNew("warped_spore");
        registrar.followNew("dripping_obsidian_tear");
        registrar.followNew("falling_obsidian_tear");
        registrar.followNew("landing_obsidian_tear");
        registrar.followNew("reverse_portal");
        registrar.followNew("white_ash");

        // 1.17
        registrar.followNew("light");

        registrar.followNew("dust_color_transition");
        registrar.followNew("vibration");

        registrar.followNew("falling_spore_blossom");
        registrar.followNew("spore_blossom_air");

        registrar.followNew("small_flame");
        registrar.followNew("snowflake");

        registrar.followNew("dripping_dripstone_lava");
        registrar.followNew("dripping_dripstone_water");
        registrar.followNew("falling_dripstone_lava");
        registrar.followNew("falling_dripstone_water");

        registrar.followNew("glow_squid_ink");
        registrar.followNew("glow");

        registrar.followNew("wax_on");
        registrar.followNew("wax_off");

        registrar.followNew("electric_spark");
        registrar.followNew("scrape");
    }

    private void fillMap_1_18() {
        ParticleNodeRegistrar registrar = new ParticleNodeRegistrar(SpigotParticleVersion.V1_18);

        registrar.followRemoved("barrier");
        registrar.followRemoved("light");

        // 1.18
        registrar.followNew("block_marker");

        // 1.19
        registrar.followNew("sonic_boom");

        registrar.followNew("sculk_soul");
        registrar.followNew("sculk_charge");
        registrar.followNew("sculk_charge_pop");

        registrar.followNew("shriek");

        registrar.defaultFollowUnprocessedNodes();
    }

    class ParticleNodeRegistrar {

        private final SpigotParticleVersion currentVersion;

        private Map<String, ParticleNode> prevParticleMap;
        private final Map<String, ParticleNode> particleMap;

        ParticleNodeRegistrar(SpigotParticleVersion currentVersion) {
            this.currentVersion = currentVersion;

            // for this scenario, anything but followNew will throw NPE, so its fine
            int versionOrdinal = currentVersion.ordinal();
            if (versionOrdinal > 0) {
                prevParticleMap = particleMaps.get(versionOrdinal - 1);
            }

            particleMap = particleMaps.get(versionOrdinal);
        }

        void followNew(String particleName) {
            // if its fill in first version, just add new node
            if (currentVersion.equals(SpigotParticleVersion.INITIAL_VERSION)) {
                ParticleNode newParticleNode = new ParticleNode(SpigotParticleVersion.INITIAL_VERSION, particleName);
                putInCurrentMap(newParticleNode);
                return;
            }

            // for new node in newer versions, first fill maps with particle in non-existent state
            ParticleNode initialParticleNode = new ParticleNode(SpigotParticleVersion.INITIAL_VERSION, particleName, true);
            particleMaps.get(0).put(particleName, initialParticleNode);

            for (int versionOrdinal = 1; versionOrdinal < currentVersion.ordinal(); ++versionOrdinal) {
                Map<String, ParticleNode> prevMap = particleMaps.get(versionOrdinal - 1);
                Map<String, ParticleNode> currentMap = particleMaps.get(versionOrdinal);

                ParticleNode prevParticleNode = prevMap.get(particleName);
                ParticleNode newParticleNode = prevParticleNode.follow();

                currentMap.put(particleName, newParticleNode);
            }

            // add new particle with existent state
            followRestored(particleName);
        }

        void follow(String oldParticleName, String newParticleName) {
            ParticleNode prevParticleNode = prevParticleMap.get(oldParticleName);
            ParticleNode newParticleNode = prevParticleNode.follow(newParticleName);
            putInCurrentMap(newParticleNode);
        }

        void followMerge(String oldParticleName, String targetParticleName) {
            ParticleNode prevParticleNode = prevParticleMap.get(oldParticleName);
            ParticleNode targetParticleNode = particleMap.get(targetParticleName);

            // bind old name with new name
            prevParticleNode.follow(targetParticleNode);

            // persist old name as non existent in current version
            ParticleNode newRemovedParticleNode = new ParticleNode(currentVersion, oldParticleName, true);
            putInCurrentMap(newRemovedParticleNode);
        }

        void followRestored(String particleName) {
            ParticleNode prevParticleNode = prevParticleMap.get(particleName);
            ParticleNode newParticleNode = prevParticleNode.followRestored();
            putInCurrentMap(newParticleNode);
        }

        void followRemoved(String particleName) {
            ParticleNode prevParticleNode = prevParticleMap.get(particleName);
            ParticleNode newParticleNode = prevParticleNode.followRemoved();
            putInCurrentMap(newParticleNode);
        }

        void defaultFollowUnprocessedNodes() {
            for (Map.Entry<String, ParticleNode> prevNodeEntry : prevParticleMap.entrySet()) {
                ParticleNode unboundNode = prevNodeEntry.getValue();

                for (Map.Entry<String, ParticleNode> currentNodeEntry : particleMap.entrySet()) {
                    if (unboundNode.isBound(currentNodeEntry.getValue())) {
                        unboundNode = null;
                        break;
                    }
                }

                if (unboundNode != null) {
                    putInCurrentMap(unboundNode.follow());
                }
            }
        }

        /**
         * <p>Puts parameter node in map of current version.</p>
         * @param node a node to put in a map.
         */
        void putInCurrentMap(ParticleNode node) {
            particleMap.put(node.getName(), node);
        }

    }

}
