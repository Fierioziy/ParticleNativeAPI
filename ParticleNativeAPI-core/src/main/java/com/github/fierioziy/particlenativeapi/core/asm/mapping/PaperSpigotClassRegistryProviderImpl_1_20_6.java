package com.github.fierioziy.particlenativeapi.core.asm.mapping;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PaperSpigotClassRegistryProviderImpl_1_20_6 implements SpigotClassRegistryProvider {

    private final String packageVersion;

    public PaperSpigotClassRegistryProviderImpl_1_20_6(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    /*
    Note:
    Some of the classes have different name than on pure Spigot, but their location is the same
    so the rest of the ASM code that relies on references from our registry can stay the same
     */
    public SpigotClassRegistry provideRegistry() {
        SpigotClassRegistry classRegistry = new SpigotClassRegistry();

        // Spigot
        classRegistry.material =                                    classRegistry.of(Material.class);
        classRegistry.blockData =                                   classRegistry.of("org/bukkit/block/data/BlockData");
        classRegistry.itemStackBukkit =                             classRegistry.of(ItemStack.class);

        // OBC
        classRegistry.craftEntity =                                 classRegistry.of("org/bukkit/craftbukkit/entity/CraftEntity");
        classRegistry.craftPlayer =                                 classRegistry.of("org/bukkit/craftbukkit/entity/CraftPlayer");

        classRegistry.craftBlockData =                              classRegistry.of("org/bukkit/craftbukkit/block/data/CraftBlockData");
        classRegistry.craftItemStack =                              classRegistry.of("org/bukkit/craftbukkit/inventory/CraftItemStack");

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

        classRegistry.particlesNms_1_7 =                            classRegistry.of(format("net/minecraft/server/%s/Particles"));
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
        classRegistry.entityPlayer_1_17 =                           classRegistry.of("net/minecraft/server/level/ServerPlayer");
        classRegistry.packet_1_17 =                                 classRegistry.of("net/minecraft/network/protocol/Packet");
        classRegistry.playerConnection_1_17 =                       classRegistry.of("net/minecraft/server/network/ServerGamePacketListenerImpl");

        classRegistry.itemStackNms_1_17 =                           classRegistry.of("net/minecraft/world/item/ItemStack");
        classRegistry.iBlockData_1_17 =                             classRegistry.of("net/minecraft/world/level/block/state/BlockState");

        classRegistry.packetPlayOutWorldParticles_1_17 =            classRegistry.of("net/minecraft/network/protocol/game/ClientboundLevelParticlesPacket");

        classRegistry.particles_1_17 =                              classRegistry.of("net/minecraft/core/particles/ParticleTypes");
        classRegistry.particle_1_17 =                               classRegistry.of("net/minecraft/core/particles/ParticleType");
        classRegistry.particleParam_1_17 =                          classRegistry.of("net/minecraft/core/particles/ParticleOptions");
        classRegistry.particleTypeNms_1_17 =                        classRegistry.of("net/minecraft/core/particles/SimpleParticleType");

        classRegistry.minecraftKey_1_17 =                           classRegistry.of("net/minecraft/resources/ResourceLocation");
        classRegistry.iRegistry_1_17 =                              classRegistry.of("net/minecraft/core/Registry");
        classRegistry.builtInRegistries =                           classRegistry.of("net/minecraft/core/registries/BuiltInRegistries");

        classRegistry.particleParamBlock_1_17 =                     classRegistry.of("net/minecraft/core/particles/BlockParticleOption");
        classRegistry.particleParamItem_1_17 =                      classRegistry.of("net/minecraft/core/particles/ItemParticleOption");
        classRegistry.particleParamRedstone_1_17 =                  classRegistry.of("net/minecraft/core/particles/DustParticleOptions");

        classRegistry.dustColorTransitionOptions =                  classRegistry.of("net/minecraft/core/particles/DustColorTransitionOptions");
        classRegistry.vibrationParticleOption =                     classRegistry.of("net/minecraft/core/particles/VibrationParticleOption");
        classRegistry.sculkChargeParticleOptions =                  classRegistry.of("net/minecraft/core/particles/SculkChargeParticleOptions");
        classRegistry.shriekParticleOption =                        classRegistry.of("net/minecraft/core/particles/ShriekParticleOption");

        classRegistry.vibrationPath =                               classRegistry.of("net/minecraft/world/level/gameevent/vibrations/VibrationPath");

        classRegistry.blockPosition =                               classRegistry.of("net/minecraft/core/BlockPos");
        classRegistry.positionSource =                              classRegistry.of("net/minecraft/world/level/gameevent/PositionSource");
        classRegistry.blockPositionSource =                         classRegistry.of("net/minecraft/world/level/gameevent/BlockPositionSource");
        classRegistry.entityPositionSource =                        classRegistry.of("net/minecraft/world/level/gameevent/EntityPositionSource");

        classRegistry.entity =                                      classRegistry.of("net/minecraft/world/entity/Entity");

        classRegistry.serverCommonPacketListenerImpl =              classRegistry.of("net/minecraft/server/network/ServerCommonPacketListenerImpl");

        classRegistry.colorParticleOption =                         classRegistry.of("net/minecraft/core/particles/ColorParticleOption");
        classRegistry.spellParticleOption =                         classRegistry.of("net/minecraft/core/particles/SpellParticleOption");
        classRegistry.powerParticleOption =                         classRegistry.of("net/minecraft/core/particles/PowerParticleOption");

        // mojang
        classRegistry.vector3fa =                                   classRegistry.of("com/mojang/math/Vector3fa");
        classRegistry.vector3f =                                    classRegistry.of("org/joml/Vector3f");

        return classRegistry;
    }

    private String format(String className) {
        return String.format(className, packageVersion);
    }

}
