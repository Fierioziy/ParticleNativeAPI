package com.github.fierioziy.particlenativeapi.core.asm.mapping;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.particle.type.*;

public class SpigotClassRegistry {

    public final ClassMapping EMPTY_MAPPING =                       of("non/existent/clazz");

    // ParticleNativeAPI
    public ClassMapping particleNativeAPI =                         of(ParticleNativeAPI.class);

    public ClassMapping particleType =                              of(ParticleType.class);
    public ClassMapping particleTypeMotion =                        of(ParticleTypeMotion.class);

    public ClassMapping particleTypeImpl =                          of(ParticleTypeImpl.class);
    public ClassMapping particleTypeBlock =                         of(ParticleTypeBlock.class);

    public ClassMapping particlePacket =                            of(ParticlePacket.class);

    // Spigot
    public ClassMapping material =                                  unregistered("Material");
    public ClassMapping blockData =                                 unregistered("BlockData");
    public ClassMapping itemStackBukkit =                           unregistered("ItemStack_Bukkit");

    // OBC
    public ClassMapping craftEntity =                               unregistered("CraftEntity");
    public ClassMapping craftPlayer =                               unregistered("CraftPlayer");

    public ClassMapping craftBlockData =                            unregistered("CraftBlockData");
    public ClassMapping craftItemStack =                            unregistered("CraftItemStack");

    // NMS
    public ClassMapping enumParticle =                              unregistered("EnumParticle");

    /*
    pre 1.17
     */
    public ClassMapping entityPlayer_1_7 =                          unregistered("EntityPlayer_1_7");
    public ClassMapping packet_1_7 =                                unregistered("Packet_1_7");
    public ClassMapping playerConnection_1_7 =                      unregistered("PlayerConnection_1_7");

    public ClassMapping itemStackNms_1_7 =                          unregistered("ItemStack_NMS_1_7");
    public ClassMapping iBlockData_1_7 =                            unregistered("IBlockData_1_7");

    public ClassMapping packetPlayOutWorldParticles_1_7 =           unregistered("PacketPlayOutWorldParticles_1_7");

    public ClassMapping particlesNms_1_7 =                          unregistered("Particles_1_7");
    public ClassMapping particle_1_7 =                              unregistered("Particle_1_7");
    public ClassMapping particleParam_1_7 =                         unregistered("ParticleParam_1_7");
    public ClassMapping particleTypeNms_1_7 =                       unregistered("ParticleType_NMS_1_7");

    public ClassMapping minecraftKey_1_7 =                          unregistered("MinecraftKey_1_7");
    public ClassMapping iRegistry_1_7 =                             unregistered("IRegistry_1_7");

    public ClassMapping particleParamBlock_1_7 =                    unregistered("ParticleParamBlock_1_7");
    public ClassMapping particleParamItem_1_7 =                     unregistered("ParticleParamItem_1_7");
    public ClassMapping particleParamRedstone_1_7 =                 unregistered("ParticleParamRedstone_1_7");

    /*
    post 1.17
     */
    public ClassMapping entityPlayer_1_17 =                         unregistered("EntityPlayer_1_17");
    public ClassMapping packet_1_17 =                               unregistered("Packet_1_17");
    public ClassMapping playerConnection_1_17 =                     unregistered("PlayerConnection_1_17");

    public ClassMapping itemStackNms_1_17 =                         unregistered("ItemStack_NMS_1_17");
    public ClassMapping iBlockData_1_17 =                           unregistered("IBlockData_1_17");

    public ClassMapping packetPlayOutWorldParticles_1_17 =          unregistered("PacketPlayOutWorldParticles_1_17");

    public ClassMapping particles_1_17 =                            unregistered("Particles_1_17");
    public ClassMapping particle_1_17 =                             unregistered("Particle_1_17");
    public ClassMapping particleParam_1_17 =                        unregistered("ParticleParam_1_17");
    public ClassMapping particleTypeNms_1_17 =                      unregistered("ParticleType_NMS_1_17");

    public ClassMapping minecraftKey_1_17 =                         unregistered("MinecraftKey_1_17");
    public ClassMapping iRegistry_1_17 =                            unregistered("IRegistry_1_17");

    public ClassMapping particleParamBlock_1_17 =                   unregistered("ParticleParamBlock_1_17");
    public ClassMapping particleParamItem_1_17 =                    unregistered("ParticleParamItem_1_17");
    public ClassMapping particleParamRedstone_1_17 =                unregistered("ParticleParamRedstone_1_17");

    public ClassMapping dustColorTransitionOptions =                unregistered("DustColorTransitionOptions");
    public ClassMapping vibrationParticleOption =                   unregistered("VibrationParticleOption");
    public ClassMapping sculkChargeParticleOptions =                unregistered("SculkChargeParticleOptions");
    public ClassMapping shriekParticleOption =                      unregistered("ShriekParticleOption");

    public ClassMapping vibrationPath =                             unregistered("VibrationPath");

    public ClassMapping blockPosition =                             unregistered("BlockPosition");
    public ClassMapping positionSource =                            unregistered("PositionSource");
    public ClassMapping blockPositionSource =                       unregistered("BlockPositionSource");
    public ClassMapping entityPositionSource =                      unregistered("EntityPositionSource");

    public ClassMapping entity =                                    unregistered("Entity");

    // mojang
    public ClassMapping vector3fa =                                 unregistered("Vector3fa");

    public SpigotClassRegistry() {

    }

    public ClassMapping of(String internalName) {
        return new RegisteredClassMapping(internalName);
    }

    public ClassMapping of(Class<?> clazz) {
        return new RegisteredClassMapping(clazz);
    }

    private ClassMapping unregistered(String name) {
        return new EmptyClassMapping(name);
    }

    static class EmptyClassMapping implements ClassMapping {

        private final String name;

        EmptyClassMapping(String name) {
            this.name = name;
        }

        @Override
        public String internalName() {
            throw unregisteredException();
        }

        @Override
        public String className() {
            throw unregisteredException();
        }

        @Override
        public String desc() {
            throw unregisteredException();
        }

        private ParticleException unregisteredException() {
            return new ParticleException(String.format(
                    "Unregistered mapping for %s", name
            ));
        }

        @Override
        public String toString() {
            return String.format("Unregistered mapping for %s", name);
        }
    }

}
