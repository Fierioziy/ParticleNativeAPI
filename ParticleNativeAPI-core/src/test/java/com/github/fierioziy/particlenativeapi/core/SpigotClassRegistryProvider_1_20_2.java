package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3f;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Entity;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPosition;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.BlockPositionSource;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.MinecraftKey;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.PositionSource;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.BuiltInRegistries;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.DustColorTransitionOptions_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.IRegistry_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3.ParticleParamRedstone_1_19_3;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_2.EntityPlayer_1_20_2;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_2.PlayerConnection_1_20_2;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_2.ServerCommonPacketListenerImpl;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.common.CraftEntity;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_20_2.entity.CraftPlayer_1_20_2;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SpigotClassRegistryProvider_1_20_2 implements SpigotClassRegistryProvider {

    public SpigotClassRegistry provideRegistry() {
        SpigotClassRegistry classRegistry = new SpigotClassRegistry();

        // Spigot
        classRegistry.material =                                    classRegistry.of(Material.class);
        classRegistry.blockData =                                   classRegistry.of(BlockData.class);
        classRegistry.itemStackBukkit =                             classRegistry.of(ItemStack.class);

        // OBC
        classRegistry.craftEntity =                                 classRegistry.of(CraftEntity.class);
        classRegistry.craftPlayer =                                 classRegistry.of(CraftPlayer_1_20_2.class);

        classRegistry.craftBlockData =                              classRegistry.of(CraftBlockData.class);
        classRegistry.craftItemStack =                              classRegistry.of(CraftItemStack.class);

        // NMS
        classRegistry.enumParticle =                                classRegistry.EMPTY_MAPPING;

        /*
        pre 1.17
         */
        classRegistry.entityPlayer_1_7 =                            classRegistry.EMPTY_MAPPING;
        classRegistry.packet_1_7 =                                  classRegistry.EMPTY_MAPPING;
        classRegistry.playerConnection_1_7 =                        classRegistry.EMPTY_MAPPING;

        classRegistry.itemStackNms_1_7 =                            classRegistry.EMPTY_MAPPING;
        classRegistry.iBlockData_1_7 =                              classRegistry.EMPTY_MAPPING;

        classRegistry.packetPlayOutWorldParticles_1_7 =             classRegistry.EMPTY_MAPPING;

        classRegistry.particlesNms_1_7 =                            classRegistry.EMPTY_MAPPING;
        classRegistry.particle_1_7 =                                classRegistry.EMPTY_MAPPING;
        classRegistry.particleParam_1_7 =                           classRegistry.EMPTY_MAPPING;
        classRegistry.particleTypeNms_1_7 =                         classRegistry.EMPTY_MAPPING;

        classRegistry.minecraftKey_1_7 =                            classRegistry.EMPTY_MAPPING;
        classRegistry.iRegistry_1_7 =                               classRegistry.EMPTY_MAPPING;

        classRegistry.particleParamBlock_1_7 =                      classRegistry.EMPTY_MAPPING;
        classRegistry.particleParamItem_1_7 =                       classRegistry.EMPTY_MAPPING;
        classRegistry.particleParamRedstone_1_7 =                   classRegistry.EMPTY_MAPPING;

        /*
        post 1.17
         */
        classRegistry.entityPlayer_1_17 =                           classRegistry.of(EntityPlayer_1_20_2.class);
        classRegistry.packet_1_17 =                                 classRegistry.of(Packet.class);
        classRegistry.playerConnection_1_17 =                       classRegistry.of(PlayerConnection_1_20_2.class);

        classRegistry.itemStackNms_1_17 =                           classRegistry.of(com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack.class);
        classRegistry.iBlockData_1_17 =                             classRegistry.of(IBlockData.class);

        classRegistry.packetPlayOutWorldParticles_1_17 =            classRegistry.of(PacketPlayOutWorldParticles_1_15.class);

        classRegistry.particles_1_17 =                              classRegistry.of(Particles_1_19.class);
        classRegistry.particle_1_17 =                               classRegistry.of(Particle.class);
        classRegistry.particleParam_1_17 =                          classRegistry.of(ParticleParam.class);
        classRegistry.particleTypeNms_1_17 =                        classRegistry.of(ParticleType.class);

        classRegistry.minecraftKey_1_17 =                           classRegistry.of(MinecraftKey.class);
        classRegistry.iRegistry_1_17 =                              classRegistry.of(IRegistry_1_19_3.class);
        classRegistry.builtInRegistries =                           classRegistry.of(BuiltInRegistries.class);

        classRegistry.particleParamBlock_1_17 =                     classRegistry.of(ParticleParamBlock.class);
        classRegistry.particleParamItem_1_17 =                      classRegistry.of(ParticleParamItem.class);
        classRegistry.particleParamRedstone_1_17 =                  classRegistry.of(ParticleParamRedstone_1_19_3.class);

        classRegistry.dustColorTransitionOptions =                  classRegistry.of(DustColorTransitionOptions_1_19_3.class);
        classRegistry.vibrationParticleOption =                     classRegistry.of(VibrationParticleOption_1_19.class);
        classRegistry.sculkChargeParticleOptions =                  classRegistry.of(SculkChargeParticleOptions.class);
        classRegistry.shriekParticleOption =                        classRegistry.of(ShriekParticleOption.class);

        classRegistry.vibrationPath =                               classRegistry.EMPTY_MAPPING;

        classRegistry.blockPosition =                               classRegistry.of(BlockPosition.class);
        classRegistry.positionSource =                              classRegistry.of(PositionSource.class);
        classRegistry.blockPositionSource =                         classRegistry.of(BlockPositionSource.class);
        classRegistry.entityPositionSource =                        classRegistry.of(EntityPositionSource_1_19.class);

        classRegistry.entity =                                      classRegistry.of(Entity.class);

        classRegistry.serverCommonPacketListenerImpl =              classRegistry.of(ServerCommonPacketListenerImpl.class);

        // mojang
        classRegistry.vector3fa =                                   classRegistry.EMPTY_MAPPING;
        classRegistry.vector3f =                                    classRegistry.of(Vector3f.class);

        return classRegistry;
    }

}
