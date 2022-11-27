package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Entity;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.SculkChargeParticleOptions;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.ShriekParticleOption;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.common.CraftEntity;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_17.entity.CraftPlayer_1_17;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SpigotClassRegistryProvider_1_17 implements SpigotClassRegistryProvider {

    public SpigotClassRegistry provideRegistry() {
        SpigotClassRegistry classRegistry = new SpigotClassRegistry();

        // Spigot
        classRegistry.material =                                    classRegistry.of(Material.class);
        classRegistry.blockData =                                   classRegistry.of(BlockData.class);
        classRegistry.itemStackBukkit =                             classRegistry.of(ItemStack.class);

        // OBC
        classRegistry.craftEntity =                                 classRegistry.of(CraftEntity.class);
        classRegistry.craftPlayer =                                 classRegistry.of(CraftPlayer_1_17.class);

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

        classRegistry.entityPlayer_1_17 =                           classRegistry.of(EntityPlayer_1_17.class);
        classRegistry.packet_1_17 =                                 classRegistry.of(Packet.class);
        classRegistry.playerConnection_1_17 =                       classRegistry.of(PlayerConnection_1_17.class);

        classRegistry.itemStackNms_1_17 =                           classRegistry.of(com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack.class);
        classRegistry.iBlockData_1_17 =                             classRegistry.of(IBlockData.class);

        classRegistry.packetPlayOutWorldParticles_1_17 =            classRegistry.of(PacketPlayOutWorldParticles_1_15.class);

        classRegistry.particles_1_17 =                              classRegistry.of(Particles_1_17.class);
        classRegistry.particle_1_17 =                               classRegistry.of(Particle.class);
        classRegistry.particleParam_1_17 =                          classRegistry.of(ParticleParam.class);
        classRegistry.particleTypeNms_1_17 =                        classRegistry.of(ParticleType.class);

        classRegistry.minecraftKey_1_17 =                           classRegistry.of(MinecraftKey.class);
        classRegistry.iRegistry_1_17 =                              classRegistry.of(IRegistry.class);

        classRegistry.particleParamBlock_1_17 =                     classRegistry.of(ParticleParamBlock.class);
        classRegistry.particleParamItem_1_17 =                      classRegistry.of(ParticleParamItem.class);
        classRegistry.particleParamRedstone_1_17 =                  classRegistry.of(ParticleParamRedstone_1_17.class);

        classRegistry.dustColorTransitionOptions =                  classRegistry.of(DustColorTransitionOptions.class);
        classRegistry.vibrationParticleOption =                     classRegistry.of(VibrationParticleOption_1_17.class);
        classRegistry.sculkChargeParticleOptions =                  classRegistry.of(SculkChargeParticleOptions.class);
        classRegistry.shriekParticleOption =                        classRegistry.of(ShriekParticleOption.class);

        classRegistry.vibrationPath =                               classRegistry.of(VibrationPath.class);

        classRegistry.blockPosition =                               classRegistry.of(BlockPosition.class);
        classRegistry.positionSource =                              classRegistry.of(PositionSource.class);
        classRegistry.blockPositionSource =                         classRegistry.of(BlockPositionSource.class);
        classRegistry.entityPositionSource =                        classRegistry.of(EntityPositionSource_1_17.class);

        classRegistry.entity =                                      classRegistry.of(Entity.class);

        // mojang
        classRegistry.vector3fa =                                   classRegistry.of(Vector3fa.class);

        return classRegistry;
    }

}
