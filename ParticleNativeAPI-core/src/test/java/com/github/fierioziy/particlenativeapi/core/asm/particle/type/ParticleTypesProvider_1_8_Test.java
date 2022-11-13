package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.EnumParticle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.PacketPlayOutWorldParticles_1_8;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.fierioziy.particlenativeapi.core.asm.particle.type.ParticleTypesUtils.unwrapPacket;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypesProvider_1_8_Test {

    private static ParticleNativeAPI api;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_8();
    }

    @Test
    public void test_ParticleType() {
        ParticleType type = api.LIST_1_8.LAVA;

        Object objPacket = unwrapPacket(type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                EnumParticle.LAVA, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8,
                new int[0]);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleType returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleType_detachCopy() {
        ParticleType type = api.LIST_1_8.LAVA;

        ParticleType detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeBlock() {
        ParticleTypeBlock type = api.LIST_1_8.FALLING_DUST;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.of(Material.LEGACY_WOOL, 1).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                EnumParticle.FALLING_DUST, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8,
                new int[] { 35, 35 | (1 << 12) });

        ParticleType selectedType1 = type.of(Material.LEGACY_WOOL);
        ParticleType selectedType2 = type.of(Material.LEGACY_WOOL);

        assertSame(selectedType1, selectedType2, "ParticleTypeBlock returns different wrapper particle type instance");
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeBlockMotion() {
        ParticleTypeBlockMotion type = api.LIST_1_8.BLOCK_CRACK;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.of(Material.LEGACY_WOOL, 1).packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        ));

        verifyPacket(objPacket,
                EnumParticle.BLOCK_CRACK, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[] { 35, 35 | (1 << 12) });

        ParticleTypeMotion selectedType1 = type.of(Material.LEGACY_WOOL);
        ParticleTypeMotion selectedType2 = type.of(Material.LEGACY_WOOL);

        assertSame(selectedType1, selectedType2, "ParticleTypeBlockMotion returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeColorable() {
        ParticleTypeColorable type = api.LIST_1_8.SPELL_MOB;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20
        ));

        verifyPacket(objPacket,
                EnumParticle.SPELL_MOB, true,
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0,
                new int[0]);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleTypeColorable returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeColorable_detachCopy() {
        ParticleTypeColorable type = api.LIST_1_8.SPELL_MOB;

        ParticleTypeColorable detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticleType bridgedDetachedType = ((ParticleType) type).detachCopy();
        assertEquals(type.getClass(), bridgedDetachedType.getClass(), "Bridged detached type is not the same class as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    /*
    There are no particle with ParticleTypeDust behavior in 1.8
     */

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeItemMotion() {
        ParticleTypeItemMotion type = api.LIST_1_8.ITEM_CRACK;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.of(Material.LEGACY_DIAMOND_BLOCK).packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        ));

        verifyPacket(objPacket,
                EnumParticle.ITEM_CRACK, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[] { 57, 0 });

        ParticleTypeMotion selectedType1 = type.of(Material.LEGACY_DIAMOND_BLOCK);
        ParticleTypeMotion selectedType2 = type.of(Material.LEGACY_DIAMOND_BLOCK);

        assertSame(selectedType1, selectedType2, "ParticleTypeItemMotion returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeMotion() {
        ParticleTypeMotion type = api.LIST_1_8.FLAME;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        ));

        verifyPacket(objPacket,
                EnumParticle.FLAME, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[0]);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleTypeMotion returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeMotion_detachCopy() {
        ParticleTypeMotion type = api.LIST_1_8.FLAME;

        ParticleTypeMotion detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticleType bridgedDetachedType = ((ParticleType) type).detachCopy();
        assertEquals(type.getClass(), bridgedDetachedType.getClass(), "Bridged detached type is not the same class as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    @Test
    public void test_ParticleTypeNote() {
        ParticleTypeNote type = api.LIST_1_8.NOTE;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetNote(true,
                1D, 2D, 3D,
                255, 0, 255
        ));

        verifyPacket(objPacket,
                EnumParticle.NOTE, true,
                1F, 2F, 3F,
                10F / 24F,
                0F,
                0F,
                1F, 0,
                new int[0]);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleTypeNote returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeNote_detachCopy() {
        ParticleTypeNote type = api.LIST_1_8.NOTE;

        ParticleTypeNote detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticleType bridgedDetachedType = ((ParticleType) type).detachCopy();
        assertEquals(type.getClass(), bridgedDetachedType.getClass(), "Bridged detached type is not the same class as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    @Test
    public void test_ParticleTypeRedstone() {
        ParticleTypeRedstone type = api.LIST_1_8.REDSTONE;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20
        ));

        verifyPacket(objPacket,
                EnumParticle.REDSTONE, true,
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0,
                new int[0]);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleTypeRedstone returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeRedstone_detachCopy() {
        ParticleTypeRedstone type = api.LIST_1_8.REDSTONE;

        ParticleTypeRedstone detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticleType bridgedDetachedType = ((ParticleType) type).detachCopy();
        assertEquals(type.getClass(), bridgedDetachedType.getClass(), "Bridged detached type is not the same class as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    private void verifyPacket(Object objPacket,
                              EnumParticle particle,
                              boolean far,
                              float x, float y, float z,
                              float offsetX, float offsetY, float offsetZ,
                              float speed, int count, int[] data) {
        assertTrue(objPacket instanceof PacketPlayOutWorldParticles_1_8,
                "Packet isn't instance of PacketPlayOutWorldParticles");

        // make sure packet wasn't modified during sending
        PacketPlayOutWorldParticles_1_8 packet = (PacketPlayOutWorldParticles_1_8) objPacket;
        assertEquals(particle, packet.particle);
        assertEquals(far, packet.far);
        assertEquals(x, packet.x, DELTA);
        assertEquals(y, packet.y, DELTA);
        assertEquals(z, packet.z, DELTA);
        assertEquals(offsetX, packet.offsetX, DELTA);
        assertEquals(offsetY, packet.offsetY, DELTA);
        assertEquals(offsetZ, packet.offsetZ, DELTA);
        assertEquals(speed, packet.speed, DELTA);
        assertEquals(count, packet.count);

        assertEquals(data.length, packet.data.length);
        for (int i = 0; i < data.length; ++i) {
            assertEquals(data[i], packet.data[i]);
        }
    }

}
