package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.EnumParticle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.PacketPlayOutWorldParticles_1_8;
import org.bukkit.Material;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticleTypesASM_1_8_Test {

    private static ParticleNativeAPI api;
    private static final float DELTA = 0.001F;

    @BeforeClass
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_8();
    }

    private void verifyPacket(Object objPacket,
                              EnumParticle particle,
                              boolean far,
                              float x, float y, float z,
                              float offsetX, float offsetY, float offsetZ,
                              float speed, int count, int[] data) {
        assertTrue("Packet isn't instance of PacketPlayOutWorldParticles",
                objPacket instanceof PacketPlayOutWorldParticles_1_8);

        //make sure packet wasn't modified during sending
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

    @Test
    public void test_ParticleType() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleType type = particles_1_8.BARRIER();

        Object objPacket = type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                EnumParticle.BARRIER, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8,
                new int[0]);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeBlock() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlock type = particles_1_8.FALLING_DUST();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.of(Material.LEGACY_WOOL, 1).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                EnumParticle.FALLING_DUST, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                7F, 8,
                new int[] { 35, 35 | (1 << 12) });
    }

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeBlockMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlockMotion type = particles_1_8.BLOCK_CRACK();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.of(Material.LEGACY_WOOL, 1).packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D);

        verifyPacket(objPacket,
                EnumParticle.BLOCK_CRACK, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[] { 35, 35 | (1 << 12) });
    }

    @Test
    public void test_ParticleTypeColorable() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeColorable type = particles_1_8.SPELL_MOB();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20);

        verifyPacket(objPacket,
                EnumParticle.SPELL_MOB, true,
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0,
                new int[0]);
    }

    /*
    There are no particle with ParticleTypeDust behavior in 1.8
     */

    @SuppressWarnings("deprecation")
    @Test
    public void test_ParticleTypeItemMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeItemMotion type = particles_1_8.ITEM_CRACK();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.of(Material.LEGACY_DIAMOND_BLOCK).packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D);

        verifyPacket(objPacket,
                EnumParticle.ITEM_CRACK, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[] { 57, 0 });
    }

    @Test
    public void test_ParticleTypeMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeMotion type = particles_1_8.FLAME();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        );

        verifyPacket(objPacket,
                EnumParticle.FLAME, true,
                1F, 2F, 3F,
                4F, 5F, 6F,
                1F, 0,
                new int[0]);
    }

    @Test
    public void test_ParticleTypeNote() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeNote type = particles_1_8.NOTE();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.packetNote(true,
                1D, 2D, 3D,
                255, 0, 255);

        verifyPacket(objPacket,
                EnumParticle.NOTE, true,
                1F, 2F, 3F,
                10F / 24F,
                0F,
                0F,
                1F, 0,
                new int[0]);
    }

    @Test
    public void test_ParticleTypeRedstone() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeRedstone type = particles_1_8.REDSTONE();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20);

        verifyPacket(objPacket,
                EnumParticle.REDSTONE, true,
                1F, 2F, 3F,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0,
                new int[0]);
    }

}
