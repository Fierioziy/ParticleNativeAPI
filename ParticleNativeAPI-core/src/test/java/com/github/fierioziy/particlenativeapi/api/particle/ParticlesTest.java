package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.particle.type.*;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParticlesTest {

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;
    private static ParticleNativeAPI api_1_17;
    private static ParticleNativeAPI api_1_18;
    private static ParticleNativeAPI api_1_19;
    private static ParticleNativeAPI api_1_19_3;
    private static ParticleNativeAPI api_1_20_2;
    private static ParticleNativeAPI api_1_20_5;
    private static ParticleNativeAPI api_1_21_3;
    private static ParticleNativeAPI api_1_21_4;
    private static ParticleNativeAPI api_1_21_10;

    @BeforeAll
    public static void prepareAPI() {
        api_1_7 = ParticleNativeCoreTest.getAPI_1_7();
        api_1_8 = ParticleNativeCoreTest.getAPI_1_8();
        api_1_13 = ParticleNativeCoreTest.getAPI_1_13();
        api_1_15 = ParticleNativeCoreTest.getAPI_1_15();
        api_1_17 = ParticleNativeCoreTest.getAPI_1_17();
        api_1_18 = ParticleNativeCoreTest.getAPI_1_18();
        api_1_19 = ParticleNativeCoreTest.getAPI_1_19();
        api_1_19_3 = ParticleNativeCoreTest.getAPI_1_19_3();
        api_1_20_2 = ParticleNativeCoreTest.getAPI_1_20_2();
        api_1_20_5 = ParticleNativeCoreTest.getAPI_1_20_5();
        api_1_21_3 = ParticleNativeCoreTest.getAPI_1_21_3();
        api_1_21_4 = ParticleNativeCoreTest.getAPI_1_21_4();
        api_1_21_10 = ParticleNativeCoreTest.getAPI_1_21_10();
    }

    @Test
    public void test_isPresent_1_7() throws ReflectiveOperationException {
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

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_7);
    }

    @Test
    public void test_isPresent_1_8() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_8,
                "LAVA",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_8,
                "LAVA",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_8);
    }

    @Test
    public void test_isPresent_1_13() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_13,
                "LAVA",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_13,
                "LAVA",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_13);
    }

    @Test
    public void test_isPresent_1_15() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_15,
                "LAVA",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_15,
                "LAVA",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_15);
    }

    @Test
    public void test_isPresent_1_17() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_17,
                "BARRIER",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_17,
                "BARRIER",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_17);
    }

    @Test
    public void test_isPresent_1_18() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_18,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_18,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_18);
    }

    @Test
    public void test_isPresent_1_19() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_19,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_19,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_19,
                "VIBRATION"
        );
    }

    @Test
    public void test_isPresent_1_19_3() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_19_3,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_19_3,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_19_3,
                "VIBRATION"
        );
    }

    @Test
    public void test_isPresent_1_20_2() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_20_2,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "SPELL_MOB",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_20_2,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ENTITY_EFFECT",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_20_2,
                "VIBRATION"
        );
    }

    @Test
    public void test_isPresent_1_20_5() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_20_5,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_20_5,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_20_5,
                "VIBRATION",
                "ENTITY_EFFECT"
        );
    }

    @Test
    public void test_isPresent_1_21_3() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_21_3,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_21_3,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_21_3,
                "VIBRATION",
                "ENTITY_EFFECT"
        );
    }

    @Test
    public void test_isPresent_1_21_4() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_21_4,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_21_4,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_21_4,
                "VIBRATION",
                "ENTITY_EFFECT"
        );
    }

    @Test
    public void test_isPresent_1_21_10() throws ReflectiveOperationException {
        verify_Particles_1_8_thatOnlyThoseAreValid(api_1_21_10,
                "BARRIER",
                "HEART",
                "FALLING_DUST",
                "BLOCK_CRACK",
                "BLOCK_DUST",
                "ITEM_CRACK",
                "FLAME",
                "NOTE",
                "REDSTONE",// forward compatibility
                "DRAGON_BREATH",
                "SPELL"
        );

        verify_Particles_1_13_thatOnlyThoseAreValid(api_1_21_10,
                "BARRIER",// forward compatibility
                "LIGHT",// forward compatibility
                "HEART",
                "FALLING_DUST",
                "BLOCK",
                "ITEM",
                "FLAME",
                "NOTE",
                "DUST",
                "DUST_COLOR_TRANSITION",
                "VIBRATION",
                "BLOCK_MARKER",
                "SCULK_CHARGE",
                "SHRIEK",
                "DRAGON_BREATH",
                "EFFECT"
        );

        verify_Particles_1_19_Part_thatOnlyThoseAreValid(api_1_21_10,
                "VIBRATION",
                "ENTITY_EFFECT",
                "DRAGON_BREATH",
                "EFFECT"
        );
    }

    private void verify_Particles_1_8_thatOnlyThoseAreValid(ParticleNativeAPI api,
                                                            String... particleNames)
            throws ReflectiveOperationException {
        verifyParticlesIn(api, api.LIST_1_8, ParticleList_1_8.class, particleNames);
    }

    private void verify_Particles_1_13_thatOnlyThoseAreValid(ParticleNativeAPI api,
                                                             String... particleNames)
            throws ReflectiveOperationException {
        verifyParticlesIn(api, api.LIST_1_13, ParticleList_1_13.class, particleNames);
    }

    private void verify_Particles_1_19_Part_thatOnlyThoseAreValid(ParticleNativeAPI api,
                                                                  String... particleNames)
            throws ReflectiveOperationException {
        verifyParticlesIn(api, api.LIST_1_19_PART, ParticleList_1_19_Part.class, particleNames);
    }

    private void verifyParticlesIn(ParticleNativeAPI api,
                                   Object listInstance,
                                   Class<?> particleListClass,
                                   String[] particleNames)
            throws ReflectiveOperationException
    {
        Set<String> particleNamesList = new HashSet<>(Arrays.asList(particleNames));

        for (Field getter : particleListClass.getDeclaredFields()) {
            if (Modifier.isPrivate(getter.getModifiers())) {
                continue;
            }

            Object particleType = getter.get(listInstance);

            boolean expected = particleNamesList.contains(getter.getName());

            boolean actual = (boolean) particleType.getClass().getMethod(BaseASM.IS_PRESENT_METHOD_NAME)
                    .invoke(particleType);

            assertEquals(expected, actual, "Wrong particle type valid status of "
                    + getter.getName() + " in " + particleListClass.getSimpleName());

            if (!actual) {
                tryInvokePacketAndAssertException(getter.getName(), api, particleType);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void tryInvokePacketAndAssertException(String getter, ParticleNativeAPI api,
                                                   Object particleType) {
        Material material = Material.DIAMOND_BLOCK;
        if (api == api_1_7 || api == api_1_8) {
            material = Material.LEGACY_DIAMOND_BLOCK;
        }

        try {
            if (particleType instanceof ParticleTypeImpl) {
                ((ParticleTypeImpl) particleType).packet(true, 0D, 0D, 0D);
            }
            else if (particleType instanceof ParticleTypeBlockImpl) {
                ((ParticleTypeBlockImpl) particleType).of(material, 0);
            }
            else if (particleType instanceof ParticleTypeBlockMotionImpl) {
                ((ParticleTypeBlockMotionImpl) particleType).of(material, 0);
            }
            else if (particleType instanceof ParticleTypeItemMotionImpl) {
                ((ParticleTypeItemMotionImpl) particleType).of(material);
            }
            else if (particleType instanceof ParticleTypeDustFloatImpl) {
                ((ParticleTypeDustFloatImpl) particleType).color(255F, 0F, 0F, 2F);
            }
            else if (particleType instanceof ParticleTypeDustIntImpl) {
                ((ParticleTypeDustIntImpl) particleType).color(255, 0, 0, 2F);
            }
            else if (particleType instanceof ParticleTypeColorImpl) {
                ((ParticleTypeColorImpl) particleType).color(255, 0, 0);
            }
            else if (particleType instanceof ParticleTypeDustColorTransitionFloatImpl) {
                ((ParticleTypeDustColorTransitionFloatImpl) particleType).color(255F, 0F, 0F, 255F, 0F, 0F, 2F);
            }
            else if (particleType instanceof ParticleTypeDustColorTransitionIntImpl) {
                ((ParticleTypeDustColorTransitionIntImpl) particleType).color(255, 0, 0, 255, 0, 0, 2D);
            }
            else if (particleType instanceof ParticleTypeVibrationSingleImpl) {
                ((ParticleTypeVibrationSingleImpl) particleType).packet(true, 0D, 0D, 0D, 0D, 0D, 0D, 1);
            }
            else if (particleType instanceof ParticleTypeVibrationImpl) {
                ((ParticleTypeVibrationImpl) particleType).flyingTo(0D, 0D, 0D, 1);
            }
            else if (particleType instanceof ParticleTypeSculkChargeMotionImpl) {
                ((ParticleTypeSculkChargeMotionImpl) particleType).roll(16D);
            }
            else if (particleType instanceof ParticleTypePowerMotionImpl) {
                ((ParticleTypePowerMotionImpl) particleType).power(2D);
            }
            else if (particleType instanceof ParticleTypeSpellImpl) {
                ((ParticleTypeSpellImpl) particleType).spell(255, 0, 0, 255, 2D);
            }
            else if (particleType instanceof ParticleTypeShriekImpl) {
                ((ParticleTypeShriekImpl) particleType).delay(16);
            }
            else {
                fail("Unknown particle type: " + getter);
            }
        } catch (ParticleException e) {
            return;
        }
        fail("Expected ParticleException on " + getter + ", but it wasn't thrown");
    }

}
