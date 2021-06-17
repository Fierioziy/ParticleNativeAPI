package com.github.fierioziy.particlenativeapi.core.asm.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ParticleRegistryTest {

    private static ParticleRegistry reg;

    @BeforeClass
    public static void prepareParticleRegistry() {
        reg = new ParticleRegistry();
    }

    private static void assertFull(String... particleNames) {
        assertFull(false, particleNames);
    }

    private static void assertFull(boolean acceptNulls, String... particleNames) {
        assertBackward(acceptNulls, particleNames);
        assertForward(acceptNulls, particleNames);
    }

    private static void assertForward(String... particleNames) {
        assertForward(false, particleNames);
    }

    private static void assertForward(boolean acceptNulls, String... particleNames) {
        ParticleVersion[] versions = ParticleVersion.values();

        int end = particleNames.length;

        assertEquals("Wrong argument count", versions.length, end);

        for (int older = 0; older < end; ++older) {
            for (int newer = older; newer < end; ++newer) {
                if (particleNames[older] == null && !acceptNulls) continue;

                String resolvedName = reg.find(
                        versions[older],
                        particleNames[older],
                        versions[newer]
                );

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
        assertBackward(false, particleNames);
    }

    private static void assertBackward(boolean acceptNulls, String... particleNames) {
        ParticleVersion[] versions = ParticleVersion.values();

        int end = particleNames.length;

        assertEquals("Wrong argument count", versions.length, end);

        for (int newer = end - 1; newer >= 0; --newer) {
            for (int older = newer; older >= 0; --older) {
                if (particleNames[newer] == null && !acceptNulls) continue;

                String resolvedName = reg.find(
                        versions[newer],
                        particleNames[newer],
                        versions[older]
                );

                assertEquals("Resolve from "
                                + versions[newer].name() + " to "
                                + versions[older].name() + " failed",
                        particleNames[older],
                        resolvedName
                );
            }
        }
    }

    @Test
    public void testRegistry() {
        // since 1.7
        assertFull(     "explode",          "EXPLOSION_NORMAL",         "POOF",                     "poof");
        assertFull(     "largeexplode",     "EXPLOSION_LARGE",          "EXPLOSION",                "explosion");
        assertFull(     "hugeexplosion",    "EXPLOSION_HUGE",           "EXPLOSION_EMITTER",        "explosion_emitter");
        assertFull(     "fireworksSpark",   "FIREWORKS_SPARK",          "FIREWORK",                 "firework");
        assertFull(     "bubble",           "WATER_BUBBLE",             "BUBBLE",                   "bubble");
        assertFull(     "splash",           "WATER_SPLASH",             "SPLASH",                   "splash");
        assertFull(     "wake",             "WATER_WAKE",               "FISHING",                  "fishing");
        assertFull(     "suspended",        "SUSPENDED",                "UNDERWATER",               "underwater");
        assertFull(     "depthsuspend",     "SUSPENDED_DEPTH",          null,                       null);
        assertFull(     "crit",             "CRIT",                     "CRIT",                     "crit");
        assertFull(     "magicCrit",        "CRIT_MAGIC",               "ENCHANTED_HIT",            "enchanted_hit");
        assertFull(     "smoke",            "SMOKE_NORMAL",             "SMOKE",                    "smoke");
        assertFull(     "largesmoke",       "SMOKE_LARGE",              "LARGE_SMOKE",              "large_smoke");
        assertFull(     "spell",            "SPELL",                    "EFFECT",                   "effect");
        assertFull(     "instantSpell",     "SPELL_INSTANT",            "INSTANT_EFFECT",           "instant_effect");
        assertFull(     "mobSpell",         "SPELL_MOB",                "ENTITY_EFFECT",            "entity_effect");
        assertFull(     "mobSpellAmbient",  "SPELL_MOB_AMBIENT",        "AMBIENT_ENTITY_EFFECT",    "ambient_entity_effect");
        assertFull(     "witchMagic",       "SPELL_WITCH",              "WITCH",                    "witch");
        assertFull(     "dripWater",        "DRIP_WATER",               "DRIPPING_WATER",           "dripping_water");
        assertFull(     "dripLava",         "DRIP_LAVA",                "DRIPPING_LAVA",            "dripping_lava");
        assertFull(     "angryVillager",    "VILLAGER_ANGRY",           "ANGRY_VILLAGER",           "angry_villager");
        assertFull(     "happyVillager",    "VILLAGER_HAPPY",           "HAPPY_VILLAGER",           "happy_villager");
        assertFull(     "townaura",         "TOWN_AURA",                "MYCELIUM",                 "mycelium");
        assertFull(     "note",             "NOTE",                     "NOTE",                     "note");
        assertFull(     "portal",           "PORTAL",                   "PORTAL",                   "portal");
        assertFull(     "enchantmenttable", "ENCHANTMENT_TABLE",        "ENCHANT",                  "enchant");
        assertFull(     "flame",            "FLAME",                    "FLAME",                    "flame");
        assertFull(     "lava",             "LAVA",                     "LAVA",                     "lava");
        assertFull(     "footstep",         "FOOTSTEP",                 null,                       null);
        assertFull(     "cloud",            "CLOUD",                    "CLOUD",                    "cloud");

        assertForward(  "reddust",          "REDSTONE",                 "DUST",                     "dust");
        assertBackward( null,               null,                       "DUST",                     "dust");

        assertFull(     "snowballpoof",     "SNOWBALL",                 "ITEM_SNOWBALL",            "item_snowball");

        assertForward(  "snowshovel",       "SNOW_SHOVEL",              "POOF",                     "poof");
        assertBackward( "explode",          "EXPLOSION_NORMAL",         "POOF",                     "poof");

        assertFull(     "slime",            "SLIME",                    "ITEM_SLIME",               "item_slime");
        assertFull(     "heart",            "HEART",                    "HEART",                    "heart");
        assertFull(     "iconcrack_",       "ITEM_CRACK",               "ITEM",                     "item");
        assertFull(     "blockcrack_",      "BLOCK_CRACK",              "BLOCK",                    "block");

        assertForward(  "blockdust_",       "BLOCK_DUST",               "BLOCK",                    "block");
        assertBackward( "blockcrack_",      "BLOCK_CRACK",              "BLOCK",                    "block");

        // since 1.8
        assertFull(     null,               "BARRIER",                  "BARRIER",                  "barrier");
        assertFull(     null,               "WATER_DROP",               "RAIN",                     "rain");
        assertFull(     null,               "ITEM_TAKE",                null,                       null);
        assertFull(     null,               "MOB_APPEARANCE",           "ELDER_GUARDIAN",           "elder_guardian");
        assertFull(     null,               "DRAGON_BREATH",            "DRAGON_BREATH",            "dragon_breath");
        assertFull(     null,               "END_ROD",                  "END_ROD",                  "end_rod");
        assertFull(     null,               "DAMAGE_INDICATOR",         "DAMAGE_INDICATOR",         "damage_indicator");
        assertFull(     null,               "SWEEP_ATTACK",             "SWEEP_ATTACK",             "sweep_attack");
        assertFull(     null,               "FALLING_DUST",             "FALLING_DUST",             "falling_dust");
        assertFull(     null,               "TOTEM",                    "TOTEM_OF_UNDYING",         "totem_of_undying");
        assertFull(     null,               "SPIT",                     "SPIT",                     "spit");
        
        // since 1.13
        assertFull(     null,               null,                       "BUBBLE_COLUMN_UP",         "bubble_column_up");
        assertFull(     null,               null,                       "BUBBLE_POP",               "bubble_pop");
        assertFull(     null,               null,                       "CURRENT_DOWN",             "current_down");
        assertFull(     null,               null,                       "SQUID_INK",                "squid_ink");
        assertFull(     null,               null,                       "NAUTILUS",                 "nautilus");
        assertFull(     null,               null,                       "DOLPHIN",                  "dolphin");
        assertFull(     null,               null,                       "SNEEZE",                   "sneeze");
        assertFull(     null,               null,                       "CAMPFIRE_COSY_SMOKE",      "campfire_cosy_smoke");
        assertFull(     null,               null,                       "CAMPFIRE_SIGNAL_SMOKE",    "campfire_signal_smoke");

        // other working ones
        assertFull(     null,               null,                       "COMPOSTER",                "composter");
        assertFull(     null,               null,                       "FLASH",                    "flash");
        assertFull(     null,               null,                       "DRIPPING_HONEY",           "dripping_honey");

        assertFull(     null,               null,                       "FALLING_NECTAR",           "falling_nectar");
        assertFull(     null,               null,                       "FALLING_HONEY",            "falling_honey");
        assertFull(     null,               null,                       "FALLING_LAVA",             "falling_lava");
        assertFull(     null,               null,                       "FALLING_WATER",            "falling_water");

        assertFull(     null,               null,                       "LANDING_HONEY",            "landing_honey");
        assertFull(     null,               null,                       "LANDING_LAVA",             "landing_lava");

        // 1.16
        assertFull(     null,               null,                       "SOUL_FIRE_FLAME",          "soul_fire_flame");
        assertFull(     null,               null,                       "SOUL",                     "soul");
        assertFull(     null,               null,                       "ASH",                      "ash");
        assertFull(     null,               null,                       "CRIMSON_SPORE",            "crimson_spore");
        assertFull(     null,               null,                       "WARPED_SPORE",             "warped_spore");
        assertFull(     null,               null,                       "DRIPPING_OBSIDIAN_TEAR",   "dripping_obsidian_tear");
        assertFull(     null,               null,                       "FALLING_OBSIDIAN_TEAR",    "falling_obsidian_tear");
        assertFull(     null,               null,                       "LANDING_OBSIDIAN_TEAR",    "landing_obsidian_tear");
        assertFull(     null,               null,                       "REVERSE_PORTAL",           "reverse_portal");
        assertFull(     null,               null,                       "WHITE_ASH",                "white_ash");

        // 1.17
        assertFull(     null,               null,                       null,                       "light");
        assertFull(     null,               null,                       null,                       "dust_color_transition");
        assertFull(     null,               null,                       null,                       "vibration");
        assertFull(     null,               null,                       null,                       "falling_spore_blossom");
        assertFull(     null,               null,                       null,                       "spore_blossom_air");
        assertFull(     null,               null,                       null,                       "small_flame");
        assertFull(     null,               null,                       null,                       "snowflake");
        assertFull(     null,               null,                       null,                       "dripping_dripstone_lava");
        assertFull(     null,               null,                       null,                       "dripping_dripstone_water");
        assertFull(     null,               null,                       null,                       "falling_dripstone_lava");
        assertFull(     null,               null,                       null,                       "falling_dripstone_water");
        assertFull(     null,               null,                       null,                       "glow_squid_ink");
        assertFull(     null,               null,                       null,                       "glow");
        assertFull(     null,               null,                       null,                       "wax_on");
        assertFull(     null,               null,                       null,                       "wax_off");
        assertFull(     null,               null,                       null,                       "electric_spark");
        assertFull(     null,               null,                       null,                       "scrape");
    }

    @Test
    public void testNulls() {
        // make sure that null permission will always return null
        assertFull(true, null, null, null, null);
    }

}
