package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMockServerExtension;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3f;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParam;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParamBlock;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.ParticleParamItem;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPosition;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPositionSource;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.DustColorTransitionOptions_1_17;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.ParticleParamRedstone_1_17;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.SculkChargeParticleOptions;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.ShriekParticleOption;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.VibrationParticleOption_1_19;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.DustColorTransitionOptions_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.ParticleParamRedstone_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import org.bukkit.Material;
import org.bukkit.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParticleTypesProvider_1_19_3_Test {

    private static ParticleNativeAPI api;
    private static final double DOUBLE_DELTA = 0.001D;
    private static final float DELTA = 0.001F;

    @BeforeAll
    public static void prepareAPI() {
        api = ParticleNativeCoreTest.getAPI_1_19_3();
    }

    @Test
    public void test_ParticleType() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleType type = particles_1_8.HEART();

        Object objPacket = type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                Particles_1_18.HEART, true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlock(Server serverMock) {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlock type = particles_1_8.FALLING_DUST();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);
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
    }

    @Test
    @ExtendWith(StaticMockServerExtension.class)
    public void test_ParticleTypeBlockMotion(Server serverMock) {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeBlockMotion type = particles_1_8.BLOCK_CRACK();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        CraftBlockData mockCraftBlockData = CraftBlockDataMock.of(Material.DIAMOND_BLOCK);
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
    }

    @Test
    public void test_ParticleTypeColorable() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeColorable type = particles_1_8.SPELL_MOB();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

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

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.color(255, 125, 50, 2F).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                new ParticleParamRedstone_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 50F / 255F),
                        2F
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeDustColorTransition() {
        com.github.fierioziy.particlenativeapi.api.Particles_1_13 particles_1_13 = api.getParticles_1_13();

        ParticleTypeDustColorTransition type = particles_1_13.DUST_COLOR_TRANSITION();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.color(255, 125, 50, 200, 100, 20, 2D).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7D, 8);

        verifyPacket(objPacket,
                new DustColorTransitionOptions_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 50F / 255F),
                        new Vector3f(200F / 255F, 100F / 255F, 20F / 255F),
                        2F
                ), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeVibration() {
        com.github.fierioziy.particlenativeapi.api.Particles_1_13 particles_1_13 = api.getParticles_1_13();

        ParticleTypeVibration type = particles_1_13.VIBRATION();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);

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
    }

    @Test
    public void test_ParticleTypeItemMotion() {
        Particles_1_8 particles_1_8 = api.getParticles_1_8();

        ParticleTypeItemMotion type = particles_1_8.ITEM_CRACK();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

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

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

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

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

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

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.packetColored(true,
                1D, 2D, 3D,
                255, 125, 20);

        verifyPacket(objPacket,
                new ParticleParamRedstone_1_19_3(
                        new Vector3f(255F / 255F, 125F / 255F, 20F / 255F),
                        1F
                ), true,
                1D, 2D, 3D,
                0F, 0F, 0F,
                0F, 1);
    }

    @Test
    public void test_ParticleTypeSculkChargeMotion() {
        com.github.fierioziy.particlenativeapi.api.Particles_1_13 particles_1_13 = api.getParticles_1_13();

        ParticleTypeSculkChargeMotion type = particles_1_13.SCULK_CHARGE();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.roll(16D).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7F, 8);

        verifyPacket(objPacket,
                new SculkChargeParticleOptions(16F), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
    }

    @Test
    public void test_ParticleTypeShriek() {
        com.github.fierioziy.particlenativeapi.api.Particles_1_13 particles_1_13 = api.getParticles_1_13();

        ParticleTypeShriek type = particles_1_13.SHRIEK();

        assertTrue(type.isValid(), "Particle type is invalid for some reason");

        Object objPacket = type.delay(16).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7F, 8);

        verifyPacket(objPacket,
                new ShriekParticleOption(16), true,
                1D, 2D, 3D,
                4F, 5F, 6F,
                7F, 8);
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
