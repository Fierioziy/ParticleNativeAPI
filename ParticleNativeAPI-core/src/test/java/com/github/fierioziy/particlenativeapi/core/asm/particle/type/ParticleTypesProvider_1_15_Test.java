package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMockServerExtension;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.particle.type.CraftBlockDataMock;
import org.bukkit.Material;
import org.bukkit.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.fierioziy.particlenativeapi.core.asm.particle.type.ParticleTypesUtils.unwrapPacket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypesProvider_1_15_Test {

    private static ParticleNativeAPI api;
    private static final double DOUBLE_DELTA = 0.001D;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_15();
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
                Particles_1_13.LAVA, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlock(Server serverMock) {
        ParticleTypeBlock type = api.LIST_1_8.FALLING_DUST;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);
        when(serverMock.createBlockData(Material.DIAMOND_BLOCK)).thenReturn(mockCraftBlockData);

        Object objPacket = unwrapPacket(type.of(Material.DIAMOND_BLOCK, 1).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                new ParticleParamBlock(Particles_1_13.FALLING_DUST, mockCraftBlockData.iBlockData), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlockMotion(Server serverMock) {
        ParticleTypeBlockMotion type = api.LIST_1_8.BLOCK_CRACK;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);
        when(serverMock.createBlockData(Material.DIAMOND_BLOCK)).thenReturn(mockCraftBlockData);

        Object objPacket = unwrapPacket(type.of(Material.DIAMOND_BLOCK, 1).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                new ParticleParamBlock(
                        Particles_1_13.BLOCK, mockCraftBlockData.iBlockData), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeColorable() {
        ParticleTypeColorable type = api.LIST_1_8.SPELL_MOB;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20
        ));

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
        ParticleTypeDust type = api.LIST_1_13.DUST;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.color(255, 125, 50, 2F).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

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
        ParticleTypeItemMotion type = api.LIST_1_8.ITEM_CRACK;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        // mock return value of CraftItemStack#asNMSCopy
        CraftItemStack.nmsItemStack = spy(new ItemStack(
                new org.bukkit.inventory.ItemStack(Material.DIAMOND_BLOCK, 1)
        ));

        Object objPacket = unwrapPacket(type.of(Material.DIAMOND_BLOCK).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                new ParticleParamItem(Particles_1_13.ITEM, CraftItemStack.nmsItemStack), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeMotion() {
        ParticleTypeMotion type = api.LIST_1_8.FLAME;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetMotion(true,
                1D, 2D, 3D,
                4D, 5D, 6D
        ));

        verifyPacket(objPacket,
                Particles_1_13.FLAME, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                1F, 0);
    }

    @Test
    public void test_ParticleTypeNote() {
        ParticleTypeNote type = api.LIST_1_8.NOTE;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetNote(true,
                1D, 2D, 3D,
                255, 0, 255
        ));

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
        ParticleTypeRedstone type = api.LIST_1_8.REDSTONE;

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20
        ));

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
        assertTrue(objPacket instanceof PacketPlayOutWorldParticles_1_15,
                "Packet isn't instance of PacketPlayOutWorldParticles");

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
