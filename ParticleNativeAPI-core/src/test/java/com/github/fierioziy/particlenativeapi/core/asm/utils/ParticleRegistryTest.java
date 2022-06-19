package com.github.fierioziy.particlenativeapi.core.asm.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ParticleRegistryTest {

    private static ParticleRegistry reg;

    @BeforeClass
    public static void prepareParticleRegistry() {
        reg = new ParticleRegistry();
    }

    private static void assertFull(String... particleNames) {
        assertBackward(particleNames);
        assertForward(particleNames);
    }

    private static void assertForward(String... particleNames) {
        ParticleVersion[] versions = ParticleVersion.values();

        int end = particleNames.length;

        assertEquals("Wrong argument count", versions.length, end);

        for (int older = 0; older < end; ++older) {
            for (int newer = older; newer < end; ++newer) {
                String inputName = inferInputName(particleNames, older);

                String resolvedName = reg.find(
                        versions[older],
                        inputName,
                        versions[newer]
                ).orElse(null);

                assertEquals("Resolve from "
                                + versions[older].name() + " to "
                                + versions[newer].name() + " failed",
                        particleNames[newer],
                        resolvedName
                );
            }
        }
    }

    private static void assertBackward(String... particleNames) {
        ParticleVersion[] versions = ParticleVersion.values();

        int end = particleNames.length;

        assertEquals("Wrong argument count", versions.length, end);

        for (int newer = end - 1; newer >= 0; --newer) {
            for (int older = newer; older >= 0; --older) {
                String inputName = inferInputName(particleNames, newer);

                String resolvedName = reg.find(
                        versions[newer],
                        inputName,
                        versions[older]
                ).orElse(null);

                assertEquals("Resolve from "
                                + versions[newer].name() + " to "
                                + versions[older].name() + " failed",
                        particleNames[older],
                        resolvedName
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


    @Test
    public void testRegistry() {
        // since 1.7
        assertFull(     "explode",          "explosion_normal",         "poof",                     "poof");
        assertFull(     "largeexplode",     "explosion_large",          "explosion",                "explosion");
        assertFull(     "hugeexplosion",    "explosion_huge",           "explosion_emitter",        "explosion_emitter");
        assertFull(     "fireworksSpark",   "fireworks_spark",          "firework",                 "firework");
        assertFull(     "bubble",           "water_bubble",             "bubble",                   "bubble");
        assertFull(     "splash",           "water_splash",             "splash",                   "splash");
        assertFull(     "wake",             "water_wake",               "fishing",                  "fishing");
        assertFull(     "suspended",        "suspended",                "underwater",               "underwater");
        assertFull(     "depthsuspend",     "suspended_depth",          null,                       null);
        assertFull(     "crit",             "crit",                     "crit",                     "crit");
        assertFull(     "magicCrit",        "crit_magic",               "enchanted_hit",            "enchanted_hit");
        assertFull(     "smoke",            "smoke_normal",             "smoke",                    "smoke");
        assertFull(     "largesmoke",       "smoke_large",              "large_smoke",              "large_smoke");
        assertFull(     "spell",            "spell",                    "effect",                   "effect");
        assertFull(     "instantSpell",     "spell_instant",            "instant_effect",           "instant_effect");
        assertFull(     "mobSpell",         "spell_mob",                "entity_effect",            "entity_effect");
        assertFull(     "mobSpellAmbient",  "spell_mob_ambient",        "ambient_entity_effect",    "ambient_entity_effect");
        assertFull(     "witchMagic",       "spell_witch",              "witch",                    "witch");
        assertFull(     "dripWater",        "drip_water",               "dripping_water",           "dripping_water");
        assertFull(     "dripLava",         "drip_lava",                "dripping_lava",            "dripping_lava");
        assertFull(     "angryVillager",    "villager_angry",           "angry_villager",           "angry_villager");
        assertFull(     "happyVillager",    "villager_happy",           "happy_villager",           "happy_villager");
        assertFull(     "townaura",         "town_aura",                "mycelium",                 "mycelium");
        assertFull(     "note",             "note",                     "note",                     "note");
        assertFull(     "portal",           "portal",                   "portal",                   "portal");
        assertFull(     "enchantmenttable", "enchantment_table",        "enchant",                  "enchant");
        assertFull(     "flame",            "flame",                    "flame",                    "flame");
        assertFull(     "lava",             "lava",                     "lava",                     "lava");
        assertFull(     "footstep",         "footstep",                 null,                       null);
        assertFull(     "cloud",            "cloud",                    "cloud",                    "cloud");

        assertFull(     "reddust",          "redstone",                 null,                       null);
        assertFull(     null,               null,                       "dust",                     "dust");

        assertFull(     "snowballpoof",     "snowball",                 "item_snowball",            "item_snowball");

        assertFull(     "explode",          "explosion_normal",         "poof",                     "poof");
        assertForward(  "snowshovel",       "snow_shovel",              "poof",                     "poof");

        assertFull(     "slime",            "slime",                    "item_slime",               "item_slime");
        assertFull(     "heart",            "heart",                    "heart",                    "heart");
        assertFull(     "iconcrack_",       "item_crack",               "item",                     "item");
        assertFull(     "blockcrack_",      "block_crack",              "block",                    "block");

        assertFull(     "blockcrack_",      "block_crack",              "block",                    "block");
        assertForward(  "blockdust_",       "block_dust",               "block",                    "block");

        // since 1.8
        assertFull(     null,               "barrier",                  "barrier",                  null);
        assertFull(     null,               "water_drop",               "rain",                     "rain");
        assertFull(     null,               "item_take",                null,                       null);
        assertFull(     null,               "mob_appearance",           "elder_guardian",           "elder_guardian");
        assertFull(     null,               "dragon_breath",            "dragon_breath",            "dragon_breath");
        assertFull(     null,               "end_rod",                  "end_rod",                  "end_rod");
        assertFull(     null,               "damage_indicator",         "damage_indicator",         "damage_indicator");
        assertFull(     null,               "sweep_attack",             "sweep_attack",             "sweep_attack");
        assertFull(     null,               "falling_dust",             "falling_dust",             "falling_dust");
        assertFull(     null,               "totem",                    "totem_of_undying",         "totem_of_undying");
        assertFull(     null,               "spit",                     "spit",                     "spit");
        
        // since 1.13
        assertFull(     null,               null,                       "bubble_column_up",         "bubble_column_up");
        assertFull(     null,               null,                       "bubble_pop",               "bubble_pop");
        assertFull(     null,               null,                       "current_down",             "current_down");
        assertFull(     null,               null,                       "squid_ink",                "squid_ink");
        assertFull(     null,               null,                       "nautilus",                 "nautilus");
        assertFull(     null,               null,                       "dolphin",                  "dolphin");

        // 1.14
        assertFull(     null,               null,                       "sneeze",                   "sneeze");
        assertFull(     null,               null,                       "campfire_cosy_smoke",      "campfire_cosy_smoke");
        assertFull(     null,               null,                       "campfire_signal_smoke",    "campfire_signal_smoke");
        assertFull(     null,               null,                       "composter",                "composter");

        // 1.15
        assertFull(     null,               null,                       "dripping_honey",           "dripping_honey");
        assertFull(     null,               null,                       "falling_nectar",           "falling_nectar");
        assertFull(     null,               null,                       "falling_honey",            "falling_honey");
        assertFull(     null,               null,                       "landing_honey",            "landing_honey");

        // other working ones
        assertFull(     null,               null,                       "flash",                    "flash");
        assertFull(     null,               null,                       "falling_lava",             "falling_lava");
        assertFull(     null,               null,                       "falling_water",            "falling_water");
        assertFull(     null,               null,                       "landing_lava",             "landing_lava");

        // 1.16
        assertFull(     null,               null,                       "soul_fire_flame",          "soul_fire_flame");
        assertFull(     null,               null,                       "soul",                     "soul");
        assertFull(     null,               null,                       "ash",                      "ash");
        assertFull(     null,               null,                       "crimson_spore",            "crimson_spore");
        assertFull(     null,               null,                       "warped_spore",             "warped_spore");
        assertFull(     null,               null,                       "dripping_obsidian_tear",   "dripping_obsidian_tear");
        assertFull(     null,               null,                       "falling_obsidian_tear",    "falling_obsidian_tear");
        assertFull(     null,               null,                       "landing_obsidian_tear",    "landing_obsidian_tear");
        assertFull(     null,               null,                       "reverse_portal",           "reverse_portal");
        assertFull(     null,               null,                       "white_ash",                "white_ash");

        // 1.17
        assertFull(     null,               null,                       "light",                    null);
        assertFull(     null,               null,                       "dust_color_transition",    "dust_color_transition");
        assertFull(     null,               null,                       "vibration",                "vibration");
        assertFull(     null,               null,                       "falling_spore_blossom",    "falling_spore_blossom");
        assertFull(     null,               null,                       "spore_blossom_air",        "spore_blossom_air");
        assertFull(     null,               null,                       "small_flame",              "small_flame");
        assertFull(     null,               null,                       "snowflake",                "snowflake");
        assertFull(     null,               null,                       "dripping_dripstone_lava",  "dripping_dripstone_lava");
        assertFull(     null,               null,                       "dripping_dripstone_water", "dripping_dripstone_water");
        assertFull(     null,               null,                       "falling_dripstone_lava",   "falling_dripstone_lava");
        assertFull(     null,               null,                       "falling_dripstone_water",  "falling_dripstone_water");
        assertFull(     null,               null,                       "glow_squid_ink",           "glow_squid_ink");
        assertFull(     null,               null,                       "glow",                     "glow");
        assertFull(     null,               null,                       "wax_on",                   "wax_on");
        assertFull(     null,               null,                       "wax_off",                  "wax_off");
        assertFull(     null,               null,                       "electric_spark",           "electric_spark");
        assertFull(     null,               null,                       "scrape",                   "scrape");

        // 1.18
        assertFull(     null,               null,                       null,                       "block_marker");

        // 1.19
        assertFull(     null,               null,                       null,                       "sonic_boom");
        assertFull(     null,               null,                       null,                       "sculk_soul");
        assertFull(     null,               null,                       null,                       "sculk_charge");
        assertFull(     null,               null,                       null,                       "sculk_charge_pop");
        assertFull(     null,               null,                       null,                       "shriek");
    }

}
