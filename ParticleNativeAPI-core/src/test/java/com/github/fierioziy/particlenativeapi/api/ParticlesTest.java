package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.types.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.asm.utils.ParticleRegistry;
import org.bukkit.Material;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticlesTest {

    private static ParticleRegistry registry;

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;

    @BeforeClass
    public static void prepareAPI() {
        registry = new ParticleRegistry();

        api_1_7 = ParticleNativeCoreTest.getAPI_1_7();
        api_1_8 = ParticleNativeCoreTest.getAPI_1_8();
        api_1_13 = ParticleNativeCoreTest.getAPI_1_13();
        api_1_15 = ParticleNativeCoreTest.getAPI_1_15();
    }

    @SuppressWarnings("deprecation")
    private void tryInvokePacketAndAssertException(String getter, ParticleNativeAPI api,
                                                   Object particleType) {
        Material material = Material.DIAMOND_BLOCK;
        if (api == api_1_7 || api == api_1_8) {
            material = Material.LEGACY_DIAMOND_BLOCK;
        }

        try {
            if (particleType instanceof ParticleType) {
                ((ParticleType) particleType).packet(true, 0D, 0D, 0D);
            }
            else if (particleType instanceof ParticleTypeBlock) {
                ((ParticleTypeBlock) particleType).of(material, 0);
            }
            else if (particleType instanceof ParticleTypeBlockMotion) {
                ((ParticleTypeBlockMotion) particleType).of(material, 0);
            }
            else if (particleType instanceof ParticleTypeItemMotion) {
                ((ParticleTypeItemMotion) particleType).of(material);
            }
            else if (particleType instanceof ParticleTypeDust) {
                ((ParticleTypeDust) particleType).color(255F, 0F, 0F, 2F);
            }
            else {
                fail("Unknown particle type: " + getter);
            }
        } catch (ParticleException e) {
            return;
        }
        fail("Expected ParticleException on " + getter + ", but it wasn't thrown");
    }

    private void verify_Particles_1_8_thatOnlyThoseAreValid(ParticleNativeAPI api,
                                                            String... particleNames)
            throws ReflectiveOperationException {
        List<String> particleNamesList = Arrays.asList(particleNames);

        for (Method getter : Particles_1_8.class.getDeclaredMethods()) {
            Object particleType = getter.invoke(api.getParticles_1_8());

            boolean expected = particleNamesList.contains(getter.getName());

            boolean actual = (boolean) particleType.getClass().getMethod("isValid")
                    .invoke(particleType);

            assertEquals("Wrong particle type valid status of "
                            + getter.getName() + " in Particles_1_8",
                    expected, actual);

            if (!actual) {
                tryInvokePacketAndAssertException(getter.getName(), api, particleType);
            }
        }
    }
    private void verify_Particles_1_13_thatOnlyThoseAreValid(ParticleNativeAPI api,
                                                             String... particleNames)
            throws ReflectiveOperationException {
        List<String> particleNamesList = Arrays.asList(particleNames);

        for (Method getter : Particles_1_13.class.getDeclaredMethods()) {
            Object particleType = getter.invoke(api.getParticles_1_13());

            boolean expected = particleNamesList.contains(getter.getName());

            boolean actual = (boolean) particleType.getClass().getMethod("isValid")
                    .invoke(particleType);

            assertEquals("Wrong particle type valid status of "
                            + getter.getName() + " in Particles_1_13",
                    expected, actual);

            if (!actual) {
                tryInvokePacketAndAssertException(getter.getName(), api, particleType);
            }
        }
    }

    @Test
    public void test_isValid_1_7() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_7,
                "EXPLOSION_NORMAL",
                "EXPLOSION_LARGE",
                "EXPLOSION_HUGE",
                "FIREWORKS_SPARK",
                "WATER_BUBBLE",
                "WATER_SPLASH",
                "WATER_WAKE",
                "SUSPENDED",
                "SUSPENDED_DEPTH",
                "CRIT",
                "CRIT_MAGIC",
                "SMOKE_NORMAL",
                "SMOKE_LARGE",
                "SPELL",
                "SPELL_INSTANT",
                "SPELL_MOB",
                "SPELL_MOB_AMBIENT",
                "SPELL_WITCH",
                "DRIP_WATER",
                "DRIP_LAVA",
                "VILLAGER_ANGRY",
                "VILLAGER_HAPPY",
                "TOWN_AURA",
                "NOTE",
                "PORTAL",
                "ENCHANTMENT_TABLE",
                "FLAME",
                "LAVA",
                "FOOTSTEP",
                "CLOUD",
                "REDSTONE",
                "SNOWBALL",
                "SNOW_SHOVEL",
                "SLIME",
                "HEART",
                "ITEM_CRACK",
                "BLOCK_CRACK",
                "BLOCK_DUST"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_7,
                "POOF",
                "EXPLOSION",
                "EXPLOSION_EMITTER",
                "FIREWORK",
                "BUBBLE",
                "SPLASH",
                "FISHING",
                "UNDERWATER",
                "CRIT",
                "ENCHANTED_HIT",
                "SMOKE",
                "LARGE_SMOKE",
                "EFFECT",
                "INSTANT_EFFECT",
                "ENTITY_EFFECT",
                "AMBIENT_ENTITY_EFFECT",
                "WITCH",
                "DRIPPING_WATER",
                "DRIPPING_LAVA",
                "ANGRY_VILLAGER",
                "HAPPY_VILLAGER",
                "MYCELIUM",
                "NOTE",
                "PORTAL",
                "ENCHANT",
                "FLAME",
                "LAVA",
                "CLOUD",
                "ITEM_SNOWBALL",
                "POOF",
                "ITEM_SLIME",
                "HEART",
                "ITEM",
                "BLOCK"
        );
    }

    @Test
    public void test_isValid_1_8() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_8,
                "SUSPENDED",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_8,
                "UNDERWATER",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE"
        );
    }

    @Test
    public void test_isValid_1_13() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_13,
                "SUSPENDED",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_13,
                "UNDERWATER",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST"
        );
    }

    @Test
    public void test_isValid_1_15() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_15,
                "SUSPENDED",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_15,
                "UNDERWATER",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST"
        );
    }

}
