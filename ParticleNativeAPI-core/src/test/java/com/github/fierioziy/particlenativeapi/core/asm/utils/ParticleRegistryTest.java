package com.github.fierioziy.particlenativeapi.core.asm.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParticleRegistryTest {

    private static ParticleRegistry reg;

    @BeforeAll
    public static void prepareParticleRegistry() {
        reg = new ParticleRegistry();
    }


    @Test
    public void testRegistry() {
        // this move's gonna cost us over 100 generated lambdas, damn
        // but, we're getting independent assertions!
        assertAll(
                // since 1.7
                () -> assertFull(   "explode",          "explosion_normal",         "poof",                     "poof",                         "poof"),
                () -> assertFull(   "largeexplode",     "explosion_large",          "explosion",                "explosion",                    "explosion"),
                () -> assertFull(   "hugeexplosion",    "explosion_huge",           "explosion_emitter",        "explosion_emitter",            "explosion_emitter"),
                () -> assertFull(   "fireworksSpark",   "fireworks_spark",          "firework",                 "firework",                     "firework"),
                () -> assertFull(   "bubble",           "water_bubble",             "bubble",                   "bubble",                       "bubble"),
                () -> assertFull(   "splash",           "water_splash",             "splash",                   "splash",                       "splash"),
                () -> assertFull(   "wake",             "water_wake",               "fishing",                  "fishing",                      "fishing"),
                () -> assertFull(   "suspended",        "suspended",                "underwater",               "underwater",                   "underwater"),
                () -> assertFull(   "depthsuspend",     "suspended_depth",          null,                       null,                           null),
                () -> assertFull(   "crit",             "crit",                     "crit",                     "crit",                         "crit"),
                () -> assertFull(   "magicCrit",        "crit_magic",               "enchanted_hit",            "enchanted_hit",                "enchanted_hit"),
                () -> assertFull(   "smoke",            "smoke_normal",             "smoke",                    "smoke",                        "smoke"),
                () -> assertFull(   "largesmoke",       "smoke_large",              "large_smoke",              "large_smoke",                  "large_smoke"),
                () -> assertFull(   "spell",            "spell",                    "effect",                   "effect",                       "effect"),
                () -> assertFull(   "instantSpell",     "spell_instant",            "instant_effect",           "instant_effect",               "instant_effect"),
                () -> assertFull(   "mobSpell",         "spell_mob",                "entity_effect",            "entity_effect",                "entity_effect"),
                () -> assertFull(   "mobSpellAmbient",  "spell_mob_ambient",        "ambient_entity_effect",    "ambient_entity_effect",        null),
                () -> assertFull(   "witchMagic",       "spell_witch",              "witch",                    "witch",                        "witch"),
                () -> assertFull(   "dripWater",        "drip_water",               "dripping_water",           "dripping_water",               "dripping_water"),
                () -> assertFull(   "dripLava",         "drip_lava",                "dripping_lava",            "dripping_lava",                "dripping_lava"),
                () -> assertFull(   "angryVillager",    "villager_angry",           "angry_villager",           "angry_villager",               "angry_villager"),
                () -> assertFull(   "happyVillager",    "villager_happy",           "happy_villager",           "happy_villager",               "happy_villager"),
                () -> assertFull(   "townaura",         "town_aura",                "mycelium",                 "mycelium",                     "mycelium"),
                () -> assertFull(   "note",             "note",                     "note",                     "note",                         "note"),
                () -> assertFull(   "portal",           "portal",                   "portal",                   "portal",                       "portal"),
                () -> assertFull(   "enchantmenttable", "enchantment_table",        "enchant",                  "enchant",                      "enchant"),
                () -> assertFull(   "flame",            "flame",                    "flame",                    "flame",                        "flame"),
                () -> assertFull(   "lava",             "lava",                     "lava",                     "lava",                         "lava"),
                () -> assertFull(   "footstep",         "footstep",                 null,                       null,                           null),
                () -> assertFull(   "cloud",            "cloud",                    "cloud",                    "cloud",                        "cloud"),

                () -> assertFull(   "reddust",          "redstone",                 null,                       null,                           null),
                () -> assertFull(   null,               null,                       "dust",                     "dust",                         "dust"),

                () -> assertFull(   "snowballpoof",     "snowball",                 "item_snowball",            "item_snowball",                "item_snowball"),

                () -> assertForward("snowshovel",       "snow_shovel",              "poof",                     "poof",                         "poof"),

                () -> assertFull(   "slime",            "slime",                    "item_slime",               "item_slime",                   "item_slime"),
                () -> assertFull(   "heart",            "heart",                    "heart",                    "heart",                        "heart"),
                () -> assertFull(   "iconcrack_",       "item_crack",               "item",                     "item",                         "item"),
                () -> assertFull(   "blockcrack_",      "block_crack",              "block",                    "block",                        "block"),
                () -> assertForward("blockdust_",       "block_dust",               "block",                    "block",                        "block"),

                // since 1.8
                () -> assertFull(   null,               "barrier",                  "barrier",                  null,                           null),
                () -> assertFull(   null,               "water_drop",               "rain",                     "rain",                         "rain"),
                () -> assertFull(   null,               "item_take",                null,                       null,                           null),
                () -> assertFull(   null,               "mob_appearance",           "elder_guardian",           "elder_guardian",               "elder_guardian"),
                () -> assertFull(   null,               "dragon_breath",            "dragon_breath",            "dragon_breath",                "dragon_breath"),
                () -> assertFull(   null,               "end_rod",                  "end_rod",                  "end_rod",                      "end_rod"),
                () -> assertFull(   null,               "damage_indicator",         "damage_indicator",         "damage_indicator",             "damage_indicator"),
                () -> assertFull(   null,               "sweep_attack",             "sweep_attack",             "sweep_attack",                 "sweep_attack"),
                () -> assertFull(   null,               "falling_dust",             "falling_dust",             "falling_dust",                 "falling_dust"),
                () -> assertFull(   null,               "totem",                    "totem_of_undying",         "totem_of_undying",             "totem_of_undying"),
                () -> assertFull(   null,               "spit",                     "spit",                     "spit",                         "spit"),

                // since 1.13
                () -> assertFull(   null,               null,                       "bubble_column_up",         "bubble_column_up",             "bubble_column_up"),
                () -> assertFull(   null,               null,                       "bubble_pop",               "bubble_pop",                   "bubble_pop"),
                () -> assertFull(   null,               null,                       "current_down",             "current_down",                 "current_down"),
                () -> assertFull(   null,               null,                       "squid_ink",                "squid_ink",                    "squid_ink"),
                () -> assertFull(   null,               null,                       "nautilus",                 "nautilus",                     "nautilus"),
                () -> assertFull(   null,               null,                       "dolphin",                  "dolphin",                      "dolphin"),

                // 1.14
                () -> assertFull(   null,               null,                       "sneeze",                   "sneeze",                       "sneeze"),
                () -> assertFull(   null,               null,                       "campfire_cosy_smoke",      "campfire_cosy_smoke",          "campfire_cosy_smoke"),
                () -> assertFull(   null,               null,                       "campfire_signal_smoke",    "campfire_signal_smoke",        "campfire_signal_smoke"),
                () -> assertFull(   null,               null,                       "composter",                "composter",                    "composter"),

                // 1.15
                () -> assertFull(   null,               null,                       "dripping_honey",           "dripping_honey",               "dripping_honey"),
                () -> assertFull(   null,               null,                       "falling_nectar",           "falling_nectar",               "falling_nectar"),
                () -> assertFull(   null,               null,                       "falling_honey",            "falling_honey",                "falling_honey"),
                () -> assertFull(   null,               null,                       "landing_honey",            "landing_honey",                "landing_honey"),

                // other working ones
                () -> assertFull(   null,               null,                       "flash",                    "flash",                        "flash"),
                () -> assertFull(   null,               null,                       "falling_lava",             "falling_lava",                 "falling_lava"),
                () -> assertFull(   null,               null,                       "falling_water",            "falling_water",                "falling_water"),
                () -> assertFull(   null,               null,                       "landing_lava",             "landing_lava",                 "landing_lava"),

                // 1.16
                () -> assertFull(   null,               null,                       "soul_fire_flame",          "soul_fire_flame",              "soul_fire_flame"),
                () -> assertFull(   null,               null,                       "soul",                     "soul",                         "soul"),
                () -> assertFull(   null,               null,                       "ash",                      "ash",                          "ash"),
                () -> assertFull(   null,               null,                       "crimson_spore",            "crimson_spore",                "crimson_spore"),
                () -> assertFull(   null,               null,                       "warped_spore",             "warped_spore",                 "warped_spore"),
                () -> assertFull(   null,               null,                       "dripping_obsidian_tear",   "dripping_obsidian_tear",       "dripping_obsidian_tear"),
                () -> assertFull(   null,               null,                       "falling_obsidian_tear",    "falling_obsidian_tear",        "falling_obsidian_tear"),
                () -> assertFull(   null,               null,                       "landing_obsidian_tear",    "landing_obsidian_tear",        "landing_obsidian_tear"),
                () -> assertFull(   null,               null,                       "reverse_portal",           "reverse_portal",               "reverse_portal"),
                () -> assertFull(   null,               null,                       "white_ash",                "white_ash",                    "white_ash"),

                // 1.17
                () -> assertFull(   null,               null,                       "light",                    null,                           null),
                () -> assertFull(   null,               null,                       "dust_color_transition",    "dust_color_transition",        "dust_color_transition"),
                () -> assertFull(   null,               null,                       "vibration",                "vibration",                    "vibration"),
                () -> assertFull(   null,               null,                       "falling_spore_blossom",    "falling_spore_blossom",        "falling_spore_blossom"),
                () -> assertFull(   null,               null,                       "spore_blossom_air",        "spore_blossom_air",            "spore_blossom_air"),
                () -> assertFull(   null,               null,                       "small_flame",              "small_flame",                  "small_flame"),
                () -> assertFull(   null,               null,                       "snowflake",                "snowflake",                    "snowflake"),
                () -> assertFull(   null,               null,                       "dripping_dripstone_lava",  "dripping_dripstone_lava",      "dripping_dripstone_lava"),
                () -> assertFull(   null,               null,                       "dripping_dripstone_water", "dripping_dripstone_water",     "dripping_dripstone_water"),
                () -> assertFull(   null,               null,                       "falling_dripstone_lava",   "falling_dripstone_lava",       "falling_dripstone_lava"),
                () -> assertFull(   null,               null,                       "falling_dripstone_water",  "falling_dripstone_water",      "falling_dripstone_water"),
                () -> assertFull(   null,               null,                       "glow_squid_ink",           "glow_squid_ink",               "glow_squid_ink"),
                () -> assertFull(   null,               null,                       "glow",                     "glow",                         "glow"),
                () -> assertFull(   null,               null,                       "wax_on",                   "wax_on",                       "wax_on"),
                () -> assertFull(   null,               null,                       "wax_off",                  "wax_off",                      "wax_off"),
                () -> assertFull(   null,               null,                       "electric_spark",           "electric_spark",               "electric_spark"),
                () -> assertFull(   null,               null,                       "scrape",                   "scrape",                       "scrape"),

                // 1.18
                () -> assertFull(   null,               null,                       null,                       "block_marker",                 "block_marker"),

                // 1.19
                () -> assertFull(   null,               null,                       null,                       "sonic_boom",                   "sonic_boom"),
                () -> assertFull(   null,               null,                       null,                       "sculk_soul",                   "sculk_soul"),
                () -> assertFull(   null,               null,                       null,                       "sculk_charge",                 "sculk_charge"),
                () -> assertFull(   null,               null,                       null,                       "sculk_charge_pop",             "sculk_charge_pop"),
                () -> assertFull(   null,               null,                       null,                       "shriek",                       "shriek"),

                // 1.20.1
                () -> assertFull(   null,               null,                       null,                       "cherry_leaves",                "cherry_leaves"),
                () -> assertFull(   null,               null,                       null,                       "egg_crack",                    "egg_crack"),

                // 1.20.5
                () -> assertFull(   null,               null,                       null,                       null,                           "gust"),
                () -> assertFull(   null,               null,                       null,                       null,                           "small_gust"),
                () -> assertFull(   null,               null,                       null,                       null,                           "gust_emitter_large"),
                () -> assertFull(   null,               null,                       null,                       null,                           "gust_emitter_small"),
                () -> assertFull(   null,               null,                       null,                       null,                           "infested"),
                () -> assertFull(   null,               null,                       null,                       null,                           "item_cobweb"),
                () -> assertFull(   null,               null,                       null,                       null,                           "white_smoke"),
                () -> assertFull(   null,               null,                       null,                       null,                           "dust_plume"),
                () -> assertFull(   null,               null,                       null,                       null,                           "dust_pillar"),
                () -> assertFull(   null,               null,                       null,                       null,                           "trial_spawner_detection"),
                () -> assertFull(   null,               null,                       null,                       null,                           "trial_spawner_detection_ominous"),
                () -> assertFull(   null,               null,                       null,                       null,                           "ominous_spawning"),
                () -> assertFull(   null,               null,                       null,                       null,                           "vault_connection"),
                () -> assertFull(   null,               null,                       null,                       null,                           "raid_omen"),
                () -> assertFull(   null,               null,                       null,                       null,                           "trial_omen"),

                // 1.21.4
                () -> assertFull(   null,               null,                       null,                       null,                           "pale_oak_leaves")
        );
    }

    private static void assertFull(String... particleNames) {
        assertBackward(particleNames);
        assertForward(particleNames);
    }

    private static void assertForward(String... particleNames) {
        SpigotParticleVersion[] versions = SpigotParticleVersion.values();

        int end = particleNames.length;

        assertEquals(versions.length, end, "Wrong argument count");

        for (int older = 0; older < end; ++older) {
            for (int newer = older; newer < end; ++newer) {
                String inputName = inferInputName(particleNames, older);

                String resolvedName = reg.find(
                        versions[older],
                        inputName,
                        versions[newer]
                ).orElse(null);

                assertEquals(particleNames[newer], resolvedName,
                        "Resolve from "
                                + versions[older].name() + " to "
                                + versions[newer].name() + " failed"
                );
            }
        }
    }

    private static void assertBackward(String... particleNames) {
        SpigotParticleVersion[] versions = SpigotParticleVersion.values();

        int end = particleNames.length;

        assertEquals(versions.length, end, "Wrong argument count");

        for (int newer = end - 1; newer >= 0; --newer) {
            for (int older = newer; older >= 0; --older) {
                String inputName = inferInputName(particleNames, newer);

                String resolvedName = reg.find(
                        versions[newer],
                        inputName,
                        versions[older]
                ).orElse(null);

                assertEquals(particleNames[older], resolvedName,
                        "Resolve from "
                                + versions[newer].name() + " to "
                                + versions[older].name() + " failed"
                );
            }
        }
    }

    private static String inferInputName(String[] particleNames, int from) {
        for (int i = from; i >= 0; --i) {
            if (particleNames[i] != null) {
                return particleNames[i];
            }
        }

        for (int i = from; i < particleNames.length; ++i) {
            if (particleNames[i] != null) {
                return particleNames[i];
            }
        }

        throw new RuntimeException("Could not infer name");
    }

}
