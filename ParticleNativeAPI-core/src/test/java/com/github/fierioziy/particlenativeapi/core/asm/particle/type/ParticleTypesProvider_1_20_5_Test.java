package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMockServerExtension;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3f;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParamBlock;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParamItem;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPosition;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPositionSource;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.EntityPositionSource_1_19;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.SculkChargeParticleOptions;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.ShriekParticleOption;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.VibrationParticleOption_1_19;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.DustColorTransitionOptions_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.ParticleParamRedstone_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_5.ColorParticleOption;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_5.Particles_1_20_5;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.common.CraftEntity;
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
public class ParticleTypesProvider_1_20_5_Test {

    private static ParticleNativeAPI api;
    private static final double DOUBLE_DELTA = 0.001D;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_20_5();
    }

    @Test
    public void test_ParticleType() {
        ParticleType type = api.LIST_1_8.HEART;

        Object objPacket = unwrapPacket(type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        verifyPacket(objPacket,
                Particles_1_18.HEART, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticlePacket packet1 = type.packet(false, 0D, 0D, 0D);
        ParticlePacket packet2 = type.packet(false, 0D, 0D, 0D);

        assertSame(packet1, packet2, "ParticleType returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleType_detachCopy() {
        ParticleType type = api.LIST_1_8.HEART;

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
    public void test_ParticleTypeColor() {
        ParticleTypeColor type = api.LIST_1_19_PART.ENTITY_EFFECT;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        int red = 255;
        int green = 125;
        int blue = 50;

        Object objPacket = unwrapPacket(type.color(red, green, blue).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8
        ));

        int expectedColor = 0xFF000000 | (red << 16) | (green << 8) | blue;

        verifyPacket(objPacket,
                ColorParticleOption.newByInt_obf(
                        Particles_1_20_5.ENTITY_EFFECT,
                        expectedColor
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticleType selectedType1 = type.color(255, 255, 255);
        ParticleType selectedType2 = type.color(255, 255, 255);

        assertSame(selectedType1, selectedType2, "ParticleTypeColor returns different wrapper particle type instance");
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
                new ParticleParamRedstone_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 50F / 255F),
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
                new DustColorTransitionOptions_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 50F / 255F),
                        new Vector3f(200F / 255F, 100F / 255F, 20F / 255F),
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
                new VibrationParticleOption_1_19(
                        new BlockPositionSource(
                                new BlockPosition(4, 5, 6)
                        ),
                        7
                ), true,
                1D, 2D, 3D,
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

        CraftEntity mockEntity = mock(CraftEntity.class);

        Object objPacket = unwrapPacket(type.packet(true,
                1D, 2D, 3D,
                mockEntity,
                7
        ));

        verifyPacket(objPacket,
                new VibrationParticleOption_1_19(
                        new EntityPositionSource_1_19(mockEntity.entity, 0F),
                        7
                ), true,
                1D, 2D, 3D,
                0F, 0F, 0F,
                0F, 1);

        ParticlePacket packet1 = type.packet(true, 1D, 2D, 3D, mockEntity, 7);
        ParticlePacket packet2 = type.packet(true, 1D, 2D, 3D, mockEntity, 7);

        assertSame(packet1, packet2, "ParticleTypeVibration returns different wrapper packet instance");
    }

    @Test
    public void test_ParticleTypeVibration_pos() {
        ParticleTypeVibration type = api.LIST_1_19_PART.VIBRATION;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.flyingTo(8D, 9D, 10D, 11).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7
        ));

        verifyPacket(objPacket,
                new VibrationParticleOption_1_19(
                        new BlockPositionSource(
                                new BlockPosition(8, 9, 10)
                        ),
                        11
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                0F, 7);

        ParticleType selectedType1 = type.flyingTo(8D, 9D, 10D, 11);
        ParticleType selectedType2 = type.flyingTo(8D, 9D, 10D, 11);

        assertSame(selectedType1, selectedType2,
                "ParticleTypeVibration returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeVibration_entity() {
        ParticleTypeVibration type = api.LIST_1_19_PART.VIBRATION;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        CraftEntity mockEntity = mock(CraftEntity.class);

        Object objPacket = unwrapPacket(type.flyingTo(mockEntity, 8).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7
        ));

        verifyPacket(objPacket,
                new VibrationParticleOption_1_19(
                        new EntityPositionSource_1_19(mockEntity.entity, 0F),
                        8
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                0F, 7);

        ParticleType selectedType1 = type.flyingTo(mockEntity, 8);
        ParticleType selectedType2 = type.flyingTo(mockEntity, 8);

        assertSame(selectedType1, selectedType2,
                "ParticleTypeVibration returns different wrapper particle type instance");    }

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
                new ParticleParamRedstone_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 20F / 255F),
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

    @Test
    public void test_ParticleTypeSculkChargeMotion() {
        ParticleTypeSculkChargeMotion type = api.LIST_1_13.SCULK_CHARGE;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.roll(16D).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7F, 8
        ));

        verifyPacket(objPacket,
                new SculkChargeParticleOptions(16F), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticleTypeMotion selectedType1 = type.roll(16D);
        ParticleTypeMotion selectedType2 = type.roll(16D);

        assertSame(selectedType1, selectedType2, "ParticleTypeSculkChargeMotion returns different wrapper particle type instance");
    }

    @Test
    public void test_ParticleTypeShriek() {
        ParticleTypeShriek type = api.LIST_1_13.SHRIEK;

        assertTrue(type.isPresent(), "Particle type is invalid for some reason");

        Object objPacket = unwrapPacket(type.delay(16).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7F, 8
        ));

        verifyPacket(objPacket,
                new ShriekParticleOption(16), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);

        ParticleType selectedType1 = type.delay(16);
        ParticleType selectedType2 = type.delay(16);

        assertSame(selectedType1, selectedType2, "ParticleTypeShriek returns different wrapper particle type instance");
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
