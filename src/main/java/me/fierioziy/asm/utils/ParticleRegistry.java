package me.fierioziy.asm.utils;

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
 * in <code>ParticleNativeAPI</code> instance finishes.</p>
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

        return node.find(to).getName();
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

        put(particleMap.get("explode")          .followChanged("EXPLOSION_NORMAL"));
        put(particleMap.get("largeexplode")     .followChanged("EXPLOSION_LARGE"));
        put(particleMap.get("hugeexplosion")    .followChanged("EXPLOSION_HUGE"));
        put(particleMap.get("fireworksSpark")   .followChanged("FIREWORKS_SPARK"));
        put(particleMap.get("bubble")           .followChanged("WATER_BUBBLE"));
        put(particleMap.get("splash")           .followChanged("WATER_SPLASH"));
        put(particleMap.get("wake")             .followChanged("WATER_WAKE"));
        put(particleMap.get("suspended")        .followChanged("SUSPENDED"));
        put(particleMap.get("depthsuspend")     .followChanged("SUSPENDED_DEPTH"));
        put(particleMap.get("crit")             .followChanged("CRIT"));
        put(particleMap.get("magicCrit")        .followChanged("CRIT_MAGIC"));
        put(particleMap.get("smoke")            .followChanged("SMOKE_NORMAL"));
        put(particleMap.get("largesmoke")       .followChanged("SMOKE_LARGE"));
        put(particleMap.get("spell")            .followChanged("SPELL"));
        put(particleMap.get("instantSpell")     .followChanged("SPELL_INSTANT"));
        put(particleMap.get("mobSpell")         .followChanged("SPELL_MOB"));
        put(particleMap.get("mobSpellAmbient")  .followChanged("SPELL_MOB_AMBIENT"));
        put(particleMap.get("witchMagic")       .followChanged("SPELL_WITCH"));
        put(particleMap.get("dripWater")        .followChanged("DRIP_WATER"));
        put(particleMap.get("dripLava")         .followChanged("DRIP_LAVA"));
        put(particleMap.get("angryVillager")    .followChanged("VILLAGER_ANGRY"));
        put(particleMap.get("happyVillager")    .followChanged("VILLAGER_HAPPY"));
        put(particleMap.get("townaura")         .followChanged("TOWN_AURA"));
        put(particleMap.get("note")             .followChanged("NOTE"));
        put(particleMap.get("portal")           .followChanged("PORTAL"));
        put(particleMap.get("enchantmenttable") .followChanged("ENCHANTMENT_TABLE"));
        put(particleMap.get("flame")            .followChanged("FLAME"));
        put(particleMap.get("lava")             .followChanged("LAVA"));
        put(particleMap.get("footstep")         .followChanged("FOOTSTEP"));
        put(particleMap.get("cloud")            .followChanged("CLOUD"));
        put(particleMap.get("reddust")          .followChanged("REDSTONE"));
        put(particleMap.get("snowballpoof")     .followChanged("SNOWBALL"));
        put(particleMap.get("snowshovel")       .followChanged("SNOW_SHOVEL"));
        put(particleMap.get("slime")            .followChanged("SLIME"));
        put(particleMap.get("heart")            .followChanged("HEART"));
        put(particleMap.get("iconcrack_")       .followChanged("ITEM_CRACK"));
        put(particleMap.get("blockcrack_")      .followChanged("BLOCK_CRACK"));
        put(particleMap.get("blockdust_")       .followChanged("BLOCK_DUST"));

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

        ParticleNode poofNode = particleMap.get("EXPLOSION_NORMAL") .followChanged("POOF");
        ParticleNode blockNode = particleMap.get("BLOCK_CRACK")     .followChanged("BLOCK");

        put(particleMap.get("EXPLOSION_NORMAL")     .followMerge(poofNode));
        put(particleMap.get("EXPLOSION_LARGE")      .followChanged("EXPLOSION"));
        put(particleMap.get("EXPLOSION_HUGE")       .followChanged("EXPLOSION_EMITTER"));
        put(particleMap.get("FIREWORKS_SPARK")      .followChanged("FIREWORK"));
        put(particleMap.get("WATER_BUBBLE")         .followChanged("BUBBLE"));
        put(particleMap.get("WATER_SPLASH")         .followChanged("SPLASH"));
        put(particleMap.get("WATER_WAKE")           .followChanged("FISHING"));
        put(particleMap.get("SUSPENDED")            .followChanged("UNDERWATER"));
        put(particleMap.get("SUSPENDED_DEPTH")      .followRemoved());
        put(particleMap.get("CRIT")                 .followDefault());
        put(particleMap.get("CRIT_MAGIC")           .followChanged("ENCHANTED_HIT"));
        put(particleMap.get("SMOKE_NORMAL")         .followChanged("SMOKE"));
        put(particleMap.get("SMOKE_LARGE")          .followChanged("LARGE_SMOKE"));
        put(particleMap.get("SPELL")                .followChanged("EFFECT"));
        put(particleMap.get("SPELL_INSTANT")        .followChanged("INSTANT_EFFECT"));
        put(particleMap.get("SPELL_MOB")            .followChanged("ENTITY_EFFECT"));
        put(particleMap.get("SPELL_MOB_AMBIENT")    .followChanged("AMBIENT_ENTITY_EFFECT"));
        put(particleMap.get("SPELL_WITCH")          .followChanged("WITCH"));
        put(particleMap.get("DRIP_WATER")           .followChanged("DRIPPING_WATER"));
        put(particleMap.get("DRIP_LAVA")            .followChanged("DRIPPING_LAVA"));
        put(particleMap.get("VILLAGER_ANGRY")       .followChanged("ANGRY_VILLAGER"));
        put(particleMap.get("VILLAGER_HAPPY")       .followChanged("HAPPY_VILLAGER"));
        put(particleMap.get("TOWN_AURA")            .followChanged("MYCELIUM"));
        put(particleMap.get("NOTE")                 .followDefault());
        put(particleMap.get("PORTAL")               .followDefault());
        put(particleMap.get("ENCHANTMENT_TABLE")    .followChanged("ENCHANT"));
        put(particleMap.get("FLAME")                .followDefault());
        put(particleMap.get("LAVA")                 .followDefault());
        put(particleMap.get("FOOTSTEP")             .followRemoved());
        put(particleMap.get("CLOUD")                .followDefault());
        put(particleMap.get("REDSTONE")             .followRemoved());
        put(new ParticleNode(ParticleVersion.V1_13, "DUST"));
        put(particleMap.get("SNOWBALL")             .followChanged("ITEM_SNOWBALL"));
        put(particleMap.get("SNOW_SHOVEL")          .followMerge(poofNode));
        put(particleMap.get("SLIME")                .followChanged("ITEM_SLIME"));
        put(particleMap.get("HEART")                .followDefault());

        put(particleMap.get("BARRIER")              .followDefault());
        put(particleMap.get("ITEM_CRACK")           .followChanged("ITEM"));

        put(particleMap.get("BLOCK_CRACK")          .followMerge(blockNode));
        put(particleMap.get("BLOCK_DUST")           .followMerge(blockNode));

        put(particleMap.get("WATER_DROP")           .followChanged("RAIN"));
        put(particleMap.get("ITEM_TAKE")            .followRemoved());
        put(particleMap.get("MOB_APPEARANCE")       .followChanged("ELDER_GUARDIAN"));

        put(particleMap.get("DRAGON_BREATH")        .followDefault());
        put(particleMap.get("END_ROD")              .followDefault());
        put(particleMap.get("DAMAGE_INDICATOR")     .followDefault());
        put(particleMap.get("SWEEP_ATTACK")         .followDefault());

        put(particleMap.get("FALLING_DUST")         .followDefault());

        put(particleMap.get("TOTEM")                .followChanged("TOTEM_OF_UNDYING"));
        put(particleMap.get("SPIT")                 .followDefault());

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
