package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMockServerExtension;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.particle.type.CraftBlockDataMock;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.fierioziy.particlenativeapi.core.asm.particle.type.ParticleTypesUtils.unwrapPacket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypesProvider_1_17_Test {

    private static ParticleNativeAPI api;
    private static final double DOUBLE_DELTA = 0.001D;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_17();
    }

    @Test
    public void test_ParticleType() {
        ParticleType type = api.LIST_1_8.BARRIER;

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

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleType returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleType_detachCopy() {
        ParticleType type = api.LIST_1_8.BARRIER;

        ParticleType detachedType = type.detachCopy();

        assertEquals(type.getClass(), detachedType.getClass(), "Detached type is not the same class as original");
        assertNotSame(type, detachedType, "Detached type is same instance as original");

        ParticlePacket packet = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packetFromDetached = detachedType.packet(false, 0D, 0D, 0D);

        assertEquals(packet.getClass(), packetFromDetached.getClass(), "Detached packet wrapper is not the same class as original");

        assertNotSame(packet, packetFromDetached, "Detached type returns same wrapper packet instance as original");
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlock(Server serverMock) {
        ParticleTypeBlock type = api.LIST_1_8.FALLING_DUST;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

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

        ParticleType selectedType1 = type.of(Material.DIAMOND_BLOCK);
        ParticleType selectedType2 = type.of(Material.DIAMOND_BLOCK);

        assertSame(selectedType1, selectedType2, "ParticleTypeBlock returns different wrapper particle type instance");
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlockMotion(Server serverMock) {
        ParticleTypeBlockMotion type = api.LIST_1_8.BLOCK_CRACK;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

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

        ParticleTypeMotion selectedType1 = type.of(Material.DIAMOND_BLOCK);
        ParticleTypeMotion selectedType2 = type.of(Material.DIAMOND_BLOCK);

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
                Particles_1_13.ENTITY_EFFECT, true,
                1D, 2D, 3D,
                255F / 255F,
                125F / 255F,
                20F / 255F,
                1F, 0);

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

    @Test
    public void test_ParticleTypeDust() {
        ParticleTypeDust type = api.LIST_1_13.DUST;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.color(255, 125, 50, 2F).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                new ParticleParamRedstone_1_17(
                        new Vector3fa(255F / 255F, 125F / 255F, 50F / 255F),
                        2F
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticleType selectedType1 = type.color(255, 255, 255, 1F);
        ParticleType selectedType2 = type.color(255, 255, 255, 1F);

        assertSame(selectedType1, selectedType2, "ParticleTypeDust returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeDustColorTransition() {
        ParticleTypeDustColorTransition type = api.LIST_1_13.DUST_COLOR_TRANSITION;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.color(255, 125, 50, 200, 100, 20, 2D).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                new DustColorTransitionOptions(
                        new Vector3fa(255F / 255F, 125F / 255F, 50F / 255F),
                        new Vector3fa(200F / 255F, 100F / 255F, 20F / 255F),
                        2F
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticleType selectedType1 = type.color(255, 255, 255, 255, 255, 255, 1D);
        ParticleType selectedType2 = type.color(255, 255, 255, 255, 255, 255, 1D);

        assertSame(selectedType1, selectedType2, "ParticleTypeDustColorTransition returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeVibrationSingle_pos() {
        ParticleTypeVibrationSingle type = api.LIST_1_13.VIBRATION;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7
        ));

        verifyPacket(objPacket,
                new VibrationParticleOption_1_17(
                        new VibrationPath(
                                new BlockPosition(1, 2, 3),
                                new BlockPositionSource(
                                        new BlockPosition(4, 5, 6)
                                ),
                                7
                        )
                ), true,
                0D, 0D, 0D,
                0F, 0F, 0F,
                0F, 1);

        ParticlePacket packet1 = type.packet(true, 1D, 2D, 3D, 4D, 5D, 6D, 7);
        ParticlePacket packet2 = type.packet(true, 1D, 2D, 3D, 4D, 5D, 6D, 7);

        assertSame(packet1, packet2, "ParticleTypeVibration returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeVibrationSingle_entity() {
        ParticleTypeVibrationSingle type = api.LIST_1_13.VIBRATION;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Entity mockEntity = mock(Entity.class);
        when(mockEntity.getEntityId()).thenReturn(10);

        Object objPacket = unwrapPacket(type.packet(true,
                1D, 2D, 3D,
                mockEntity,
                7
        ));

        verifyPacket(objPacket,
                new VibrationParticleOption_1_17(
                        new VibrationPath(
                                new BlockPosition(1, 2, 3),
                                new EntityPositionSource_1_17(10),
                                7
                        )
                ), true,
                0D, 0D, 0D,
                0F, 0F, 0F,
                0F, 1);

        ParticlePacket packet1 = type.packet(true, 1D, 2D, 3D, mockEntity, 7);
        ParticlePacket packet2 = type.packet(true, 1D, 2D, 3D, mockEntity, 7);

        assertSame(packet1, packet2, "ParticleTypeVibration returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeItemMotion() {
        ParticleTypeItemMotion type = api.LIST_1_8.ITEM_CRACK;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

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

        ParticleTypeMotion selectedType1 = type.of(Material.DIAMOND_BLOCK);
        ParticleTypeMotion selectedType2 = type.of(Material.DIAMOND_BLOCK);

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
                Particles_1_13.FLAME, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                1F, 0);

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
                Particles_1_13.NOTE, true,
                1D, 2D, 3D,
                10F / 24F,
                0F,
                0F,
                1F, 0);

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
                new ParticleParamRedstone_1_17(
                        new Vector3fa(255F / 255F, 125F / 255F, 20F / 255F),
                        1F
                ), true,
                1D, 2D, 3D,
                0F, 0F, 0F,
                0F, 1);

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
