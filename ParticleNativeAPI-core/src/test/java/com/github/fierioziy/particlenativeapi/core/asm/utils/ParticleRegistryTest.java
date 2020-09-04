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
        assertFull(     "explode",          "EXPLOSION_NORMAL",         "POOF");
        assertFull(     "largeexplode",     "EXPLOSION_LARGE",          "EXPLOSION");
        assertFull(     "hugeexplosion",    "EXPLOSION_HUGE",           "EXPLOSION_EMITTER");
        assertFull(     "fireworksSpark",   "FIREWORKS_SPARK",          "FIREWORK");
        assertFull(     "bubble",           "WATER_BUBBLE",             "BUBBLE");
        assertFull(     "splash",           "WATER_SPLASH",             "SPLASH");
        assertFull(     "wake",             "WATER_WAKE",               "FISHING");
        assertFull(     "suspended",        "SUSPENDED",                "UNDERWATER");
        assertFull(     "depthsuspend",     "SUSPENDED_DEPTH",          null);
        assertFull(     "crit",             "CRIT",                     "CRIT");
        assertFull(     "magicCrit",        "CRIT_MAGIC",               "ENCHANTED_HIT");
        assertFull(     "smoke",            "SMOKE_NORMAL",             "SMOKE");
        assertFull(     "largesmoke",       "SMOKE_LARGE",              "LARGE_SMOKE");
        assertFull(     "spell",            "SPELL",                    "EFFECT");
        assertFull(     "instantSpell",     "SPELL_INSTANT",            "INSTANT_EFFECT");
        assertFull(     "mobSpell",         "SPELL_MOB",                "ENTITY_EFFECT");
        assertFull(     "mobSpellAmbient",  "SPELL_MOB_AMBIENT",        "AMBIENT_ENTITY_EFFECT");
        assertFull(     "witchMagic",       "SPELL_WITCH",              "WITCH");
        assertFull(     "dripWater",        "DRIP_WATER",               "DRIPPING_WATER");
        assertFull(     "dripLava",         "DRIP_LAVA",                "DRIPPING_LAVA");
        assertFull(     "angryVillager",    "VILLAGER_ANGRY",           "ANGRY_VILLAGER");
        assertFull(     "happyVillager",    "VILLAGER_HAPPY",           "HAPPY_VILLAGER");
        assertFull(     "townaura",         "TOWN_AURA",                "MYCELIUM");
        assertFull(     "note",             "NOTE",                     "NOTE");
        assertFull(     "portal",           "PORTAL",                   "PORTAL");
        assertFull(     "enchantmenttable", "ENCHANTMENT_TABLE",        "ENCHANT");
        assertFull(     "flame",            "FLAME",                    "FLAME");
        assertFull(     "lava",             "LAVA",                     "LAVA");
        assertFull(     "footstep",         "FOOTSTEP",                 null);
        assertFull(     "cloud",            "CLOUD",                    "CLOUD");

        assertForward(  "reddust",          "REDSTONE",                 "DUST");
        assertBackward( null,               null,                       "DUST");

        assertFull(     "snowballpoof",     "SNOWBALL",                 "ITEM_SNOWBALL");

        assertForward(  "snowshovel",       "SNOW_SHOVEL",              "POOF");
        assertBackward( "explode",          "EXPLOSION_NORMAL",         "POOF");

        assertFull(     "slime",            "SLIME",                    "ITEM_SLIME");
        assertFull(     "heart",            "HEART",                    "HEART");
        assertFull(     "iconcrack_",       "ITEM_CRACK",               "ITEM");
        assertFull(     "blockcrack_",      "BLOCK_CRACK",              "BLOCK");

        assertForward(  "blockdust_",       "BLOCK_DUST",               "BLOCK");
        assertBackward( "blockcrack_",      "BLOCK_CRACK",              "BLOCK");

        // since 1.8
        assertFull(     null,               "BARRIER",                  "BARRIER");
        assertFull(     null,               "WATER_DROP",               "RAIN");
        assertFull(     null,               "ITEM_TAKE",                null);
        assertFull(     null,               "MOB_APPEARANCE",           "ELDER_GUARDIAN");
        assertFull(     null,               "DRAGON_BREATH",            "DRAGON_BREATH");
        assertFull(     null,               "END_ROD",                  "END_ROD");
        assertFull(     null,               "DAMAGE_INDICATOR",         "DAMAGE_INDICATOR");
        assertFull(     null,               "SWEEP_ATTACK",             "SWEEP_ATTACK");
        assertFull(     null,               "FALLING_DUST",             "FALLING_DUST");
        assertFull(     null,               "TOTEM",                    "TOTEM_OF_UNDYING");
        assertFull(     null,               "SPIT",                     "SPIT");
        
        // since 1.13
        assertFull(     null,               null,                       "BUBBLE_COLUMN_UP");          
        assertFull(     null,               null,                       "BUBBLE_POP");          
        assertFull(     null,               null,                       "CURRENT_DOWN");          
        assertFull(     null,               null,                       "SQUID_INK");          
        assertFull(     null,               null,                       "NAUTILUS");          
        assertFull(     null,               null,                       "DOLPHIN");          
        assertFull(     null,               null,                       "SNEEZE");          
        assertFull(     null,               null,                       "CAMPFIRE_COSY_SMOKE");          
        assertFull(     null,               null,                       "CAMPFIRE_SIGNAL_SMOKE");          

        // other working ones
        assertFull(     null,               null,                       "COMPOSTER");          
        assertFull(     null,               null,                       "FLASH");          
        assertFull(     null,               null,                       "DRIPPING_HONEY");          

        assertFull(     null,               null,                       "FALLING_NECTAR");          
        assertFull(     null,               null,                       "FALLING_HONEY");          
        assertFull(     null,               null,                       "FALLING_LAVA");          
        assertFull(     null,               null,                       "FALLING_WATER");          

        assertFull(     null,               null,                       "LANDING_HONEY");          
        assertFull(     null,               null,                       "LANDING_LAVA");          

        // 1.16
        assertFull(     null,               null,                       "SOUL_FIRE_FLAME");          
        assertFull(     null,               null,                       "SOUL");          
        assertFull(     null,               null,                       "ASH");          
        assertFull(     null,               null,                       "CRIMSON_SPORE");          
        assertFull(     null,               null,                       "WARPED_SPORE");          
        assertFull(     null,               null,                       "DRIPPING_OBSIDIAN_TEAR");          
        assertFull(     null,               null,                       "FALLING_OBSIDIAN_TEAR");          
        assertFull(     null,               null,                       "LANDING_OBSIDIAN_TEAR");          
        assertFull(     null,               null,                       "REVERSE_PORTAL");          
        assertFull(     null,               null,                       "WHITE_ASH");          
    }

    @Test
    public void testNulls() {
        // make sure that null permission will always return null
        assertFull(true, null, null, null);
    }

}
