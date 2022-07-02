package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PacketPlayOutWorldParticles_1_7;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypesProvider_1_7_Test {

    private static ParticleNativeAPI api;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_7();
    }

    private void verifyPacket(Object objPacket, String particle,
                              float x, float y, float z,
                              float offsetX, float offsetY, float offsetZ,
                              float speed, int count) {
        assertTrue(objPacket instanceof PacketPlayOutWorldParticles_1_7,
                "Packet isn't instance of PacketPlayOutWorldParticles");

        //make sure packet wasn't modified during sending
        PacketPlayOutWorldParticles_1_7 packet = (PacketPlayOutWorldParticles_1_7) objPacket;
        assertEquals(particle, packet.particle);
        assertEquals(x, packet.x, DELTA);
        assertEquals(y, packet.y, DELTA);
        assertEquals(z, packet.z, DELTA);
        assertEquals(offsetX, packet.offsetX, DELTA);
        assertEquals(offsetY, packet.offsetY, DELTA);
        assertEquals(offsetZ, packet.offsetZ, DELTA);
        assertEquals(speed, packet.speed, DELTA);
        assertEquals(count, packet.count);
    }

    @Test
    public void test_ParticleType() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleType type = particles_1_8.SUSPENDED();

        Object objPacket = type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket, "suspended",
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8);
    }

    /*
    There are no particle with ParticleTypeBlock behavior in 1.7
     */

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeBlockMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlockMotion type = particles_1_8.BLOCK_CRACK();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.of(Material.LEGACY_DIAMOND_BLOCK).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket, "blockcrack_57_0",
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeColorable() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeColorable type = particles_1_8.SPELL_MOB();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20);

        verifyPacket(objPacket, "mobSpell",
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0);
    }

    /*
    There are no particle with ParticleTypeDust behavior in 1.7
     */

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeItemMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeItemMotion type = particles_1_8.ITEM_CRACK();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.of(Material.LEGACY_DIAMOND_BLOCK).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket, "iconcrack_57",
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeMotion type = particles_1_8.FLAME();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        );

        verifyPacket(objPacket, "flame",
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0);
    }

    @Test
    public void test_ParticleTypeNote() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeNote type = particles_1_8.NOTE();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packetNote(true,
                1D, 2D, 3D,
                255, 0, 255);

        verifyPacket(objPacket, "note",
                1F, 2F, 3F,
                10F / 24F,
                0F,
                0F,
                1F, 0);
    }

    @Test
    public void test_ParticleTypeRedstone() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeRedstone type = particles_1_8.REDSTONE();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20);

        verifyPacket(objPacket, "reddust",
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0);
    }

}
