package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMock;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import org.bukkit.Material;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticleTypesProvider_1_15_Test {

    private static ParticleNativeAPI api;
    private static final double DOUBLE_DELTA = 0.001D;
    private static final float DELTA = 0.001F;

    @BeforeClass
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_15();
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
                Particles_1_13.BARRIER, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeBlock() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlock type = particles_1_8.FALLING_DUST();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);

        StaticMock.ofServer(serverMock -> {
            when(serverMock.createBlockData(Material.DIAMOND_BLOCK)).thenReturn(mockCraftBlockData);

            Object objPacket = type.of(Material.DIAMOND_BLOCK, 1).packet(true,
                    1D, 2D, 3D,
                    4D, 5D, 6D,
                    7D, 8);

            verifyPacket(objPacket,
                    new ParticleParamBlock(Particles_1_13.FALLING_DUST, mockCraftBlockData.iBlockData), true,
                    1D, 2D, 3D,
                    4F, 5F, 6F,
                    7F, 8);
        });
    }

    @Test
    public void test_ParticleTypeBlockMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlockMotion type = particles_1_8.BLOCK_CRACK();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);

        StaticMock.ofServer(serverMock -> {
            when(serverMock.createBlockData(Material.DIAMOND_BLOCK)).thenReturn(mockCraftBlockData);

            Object objPacket = type.of(Material.DIAMOND_BLOCK, 1).packet(true,
                    1D, 2D, 3D,
                    4D, 5D, 6D,
                    7D, 8);

            verifyPacket(objPacket,
                    new ParticleParamBlock(
                            Particles_1_13.BLOCK, mockCraftBlockData.iBlockData), true,
                    1D, 2D, 3D,
                    4F, 5F, 6F,
                    7F, 8);
        });
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
                Particles_1_13.ENTITY_EFFECT, true,
                1D, 2D, 3D,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0);
    }

    @Test
    public void test_ParticleTypeDust() {
        com.github.fierioziy.particlenativeapi.api.Particles_1_13 particles_1_13 = api.getParticles_1_13();

        ParticleTypeDust type = particles_1_13.DUST();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        Object objPacket = type.color(255, 125, 50, 2F).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                new ParticleParamRedstone_1_13(
                        255F / 255F,
                        125F / 255F,
                        50F / 255F,
                        2F
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeItemMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeItemMotion type = particles_1_8.ITEM_CRACK();

        assertTrue("Particle type is invalid for some reason", type.isValid());

        // mock return value of CraftItemStack#asNMSCopy
        CraftItemStack.nmsItemStack = spy(new ItemStack(
                new org.bukkit.inventory.ItemStack(Material.DIAMOND_BLOCK, 1)
        ));

        Object objPacket = type.of(Material.DIAMOND_BLOCK).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                new ParticleParamItem(Particles_1_13.ITEM, CraftItemStack.nmsItemStack), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
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
                Particles_1_13.FLAME, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                1F, 0);
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
                Particles_1_13.NOTE, true,
                1D, 2D, 3D,
                10F / 24F,
                0F,
                0F,
                1F, 0);
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
                new ParticleParamRedstone_1_13(
                        255F / 255F,
                        125F / 255F,
                        20F / 255F,
                        1F
                ), true,
                1D, 2D, 3D,
                0F, 0F, 0F,
                0F, 1);
    }

    private void verifyPacket(Object objPacket,
                              ParticleParam particle,
                              boolean far,
                              double x, double y, double z,
                              float offsetX, float offsetY, float offsetZ,
                              float speed, int count) {
        assertTrue("Packet isn't instance of PacketPlayOutWorldParticles",
                objPacket instanceof PacketPlayOutWorldParticles_1_15);

        // make sure packet wasn't modified during sending
        // ParticleParam classes have overridden equals method to simplify its verification
        PacketPlayOutWorldParticles_1_15 packet = (PacketPlayOutWorldParticles_1_15) objPacket;
        assertEquals(particle, packet.particle);
        assertEquals(far, packet.far);
        assertEquals(x, packet.x, DOUBLE_DELTA);
        assertEquals(y, packet.y, DOUBLE_DELTA);
        assertEquals(z, packet.z, DOUBLE_DELTA);
        assertEquals(offsetX, packet.offsetX, DELTA);
        assertEquals(offsetY, packet.offsetY, DELTA);
        assertEquals(offsetZ, packet.offsetZ, DELTA);
        assertEquals(speed, packet.speed, DELTA);
        assertEquals(count, packet.count);
    }

}
