package com.github.fierioziy.particlenativeapi.core.asm.mapping;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class SpigotClassRegistryProvider implements ClassRegistryProvider {

    private final String packageVersion;

    public SpigotClassRegistryProvider(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public ClassRegistry provideRegistry() {
        ClassRegistry classRegistry = new ClassRegistry();

        // Spigot
        classRegistry.material =                                    classRegistry.of(Material.class);
        classRegistry.blockData =                                   classRegistry.of(BlockData.class);
        classRegistry.itemStackBukkit =                             classRegistry.of(ItemStack.class);

        // OBC
        classRegistry.craftPlayer =                                 classRegistry.of(format("org/bukkit/craftbukkit/%s/entity/CraftPlayer"));

        classRegistry.craftBlockData =                              classRegistry.of(format("org/bukkit/craftbukkit/%s/block/data/CraftBlockData"));
        classRegistry.craftItemStack =                              classRegistry.of(format("org/bukkit/craftbukkit/%s/inventory/CraftItemStack"));

        // NMS
        classRegistry.enumParticle =                                classRegistry.of(format("net/minecraft/server/%s/EnumParticle"));

        /*
        pre 1.17
         */
        classRegistry.entityPlayer_1_7 =                            classRegistry.of(format("net/minecraft/server/%s/EntityPlayer"));
        classRegistry.packet_1_7 =                                  classRegistry.of(format("net/minecraft/server/%s/Packet"));
        classRegistry.playerConnection_1_7 =                        classRegistry.of(format("net/minecraft/server/%s/PlayerConnection"));

        classRegistry.itemStackNms_1_7 =                            classRegistry.of(format("net/minecraft/server/%s/ItemStack"));
        classRegistry.iBlockData_1_7 =                              classRegistry.of(format("net/minecraft/server/%s/IBlockData"));

        classRegistry.packetPlayOutWorldParticles_1_7 =             classRegistry.of(format("net/minecraft/server/%s/PacketPlayOutWorldParticles"));

        classRegistry.particlesNms_1_7 =                               classRegistry.of(format("net/minecraft/server/%s/Particles"));
        classRegistry.particle_1_7 =                                classRegistry.of(format("net/minecraft/server/%s/Particle"));
        classRegistry.particleParam_1_7 =                           classRegistry.of(format("net/minecraft/server/%s/ParticleParam"));
        classRegistry.particleTypeNms_1_7 =                         classRegistry.of(format("net/minecraft/server/%s/ParticleType"));

        classRegistry.minecraftKey_1_7 =                            classRegistry.of(format("net/minecraft/server/%s/MinecraftKey"));
        classRegistry.iRegistry_1_7 =                               classRegistry.of(format("net/minecraft/server/%s/IRegistry"));

        classRegistry.particleParamBlock_1_7 =                      classRegistry.of(format("net/minecraft/server/%s/ParticleParamBlock"));
        classRegistry.particleParamItem_1_7 =                       classRegistry.of(format("net/minecraft/server/%s/ParticleParamItem"));
        classRegistry.particleParamRedstone_1_7 =                   classRegistry.of(format("net/minecraft/server/%s/ParticleParamRedstone"));

        /*
        post 1.17
         */
        classRegistry.entityPlayer_1_17 =                           classRegistry.of("net/minecraft/server/level/EntityPlayer");
        classRegistry.packet_1_17 =                                 classRegistry.of("net/minecraft/network/protocol/Packet");
        classRegistry.playerConnection_1_17 =                       classRegistry.of("net/minecraft/server/network/PlayerConnection");

        classRegistry.itemStackNms_1_17 =                           classRegistry.of("net/minecraft/world/item/ItemStack");
        classRegistry.iBlockData_1_17 =                             classRegistry.of("net/minecraft/world/level/block/state/IBlockData");

        classRegistry.packetPlayOutWorldParticles_1_17 =            classRegistry.of("net/minecraft/network/protocol/game/PacketPlayOutWorldParticles");

        classRegistry.particles_1_17 =                              classRegistry.of("net/minecraft/core/particles/Particles");
        classRegistry.particle_1_17 =                               classRegistry.of("net/minecraft/core/particles/Particle");
        classRegistry.particleParam_1_17 =                          classRegistry.of("net/minecraft/core/particles/ParticleParam");
        classRegistry.particleTypeNms_1_17 =                        classRegistry.of("net/minecraft/core/particles/ParticleType");

        classRegistry.minecraftKey_1_17 =                           classRegistry.of("net/minecraft/resources/MinecraftKey");
        classRegistry.iRegistry_1_17 =                              classRegistry.of("net/minecraft/core/IRegistry");

        classRegistry.particleParamBlock_1_17 =                     classRegistry.of("net/minecraft/core/particles/ParticleParamBlock");
        classRegistry.particleParamItem_1_17 =                      classRegistry.of("net/minecraft/core/particles/ParticleParamItem");
        classRegistry.particleParamRedstone_1_17 =                  classRegistry.of("net/minecraft/core/particles/ParticleParamRedstone");

        classRegistry.dustColorTransitionOptions =                  classRegistry.of("net/minecraft/core/particles/DustColorTransitionOptions");
        classRegistry.vibrationParticleOption =                     classRegistry.of("net/minecraft/core/particles/VibrationParticleOption");
        classRegistry.sculkChargeParticleOptions =                  classRegistry.of("net/minecraft/core/particles/SculkChargeParticleOptions");
        classRegistry.shriekParticleOption =                        classRegistry.of("net/minecraft/core/particles/ShriekParticleOption");

        classRegistry.vibrationPath =                               classRegistry.of("net/minecraft/world/level/gameevent/vibrations/VibrationPath");

        classRegistry.blockPosition =                               classRegistry.of("net/minecraft/core/BlockPosition");
        classRegistry.positionSource =                              classRegistry.of("net/minecraft/world/level/gameevent/PositionSource");
        classRegistry.blockPositionSource =                         classRegistry.of("net/minecraft/world/level/gameevent/BlockPositionSource");

        // mojang
        classRegistry.vector3fa =                                   classRegistry.of("com/mojang/math/Vector3fa");

        return classRegistry;
    }

    private String format(String className) {
        return String.format(className, packageVersion);
    }

}
