package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.IRegistry;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.MinecraftKey;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.EntityPlayer_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PlayerConnection_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_7.entity.CraftPlayer_1_7;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SpigotClassRegistryProvider_1_13 implements ClassRegistryProvider {

    public ClassRegistry provideRegistry() {
        ClassRegistry classRegistry = new ClassRegistry();

        // Spigot
        classRegistry.material =                                    classRegistry.of(Material.class);
        classRegistry.blockData =                                   classRegistry.of(BlockData.class);
        classRegistry.itemStackBukkit =                             classRegistry.of(ItemStack.class);

        // OBC
        classRegistry.craftPlayer =                                 classRegistry.of(CraftPlayer_1_7.class);

        classRegistry.craftBlockData =                              classRegistry.of(CraftBlockData.class);
        classRegistry.craftItemStack =                              classRegistry.of(CraftItemStack.class);

        // NMS
        classRegistry.enumParticle =                                classRegistry.EMPTY_MAPPING;

        /*
        pre 1.17
         */
        classRegistry.entityPlayer_1_7 =                            classRegistry.of(EntityPlayer_1_7.class);
        classRegistry.packet_1_7 =                                  classRegistry.of(Packet.class);
        classRegistry.playerConnection_1_7 =                        classRegistry.of(PlayerConnection_1_7.class);

        classRegistry.itemStackNms_1_7 =                            classRegistry.of(com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack.class);
        classRegistry.iBlockData_1_7 =                              classRegistry.of(IBlockData.class);

        classRegistry.packetPlayOutWorldParticles_1_7 =             classRegistry.of(PacketPlayOutWorldParticles_1_13.class);

        classRegistry.particlesNms_1_7 =                            classRegistry.of(Particles_1_13.class);
        classRegistry.particle_1_7 =                                classRegistry.of(Particle.class);
        classRegistry.particleParam_1_7 =                           classRegistry.of(ParticleParam.class);
        classRegistry.particleTypeNms_1_7 =                         classRegistry.of(ParticleType.class);

        classRegistry.minecraftKey_1_7 =                            classRegistry.of(MinecraftKey.class);
        classRegistry.iRegistry_1_7 =                               classRegistry.of(IRegistry.class);

        classRegistry.particleParamBlock_1_7 =                      classRegistry.of(ParticleParamBlock.class);
        classRegistry.particleParamItem_1_7 =                       classRegistry.of(ParticleParamItem.class);
        classRegistry.particleParamRedstone_1_7 =                   classRegistry.of(ParticleParamRedstone_1_13.class);

        return classRegistry;
    }

}
