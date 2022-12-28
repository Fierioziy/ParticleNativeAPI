package com.github.fierioziy.particlenativeapi.core.asm.mapping;

import com.github.fierioziy.particlenativeapi.api.Particles_1_13;
import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import com.github.fierioziy.particlenativeapi.api.PlayerConnection;
import com.github.fierioziy.particlenativeapi.api.ServerConnection;
import com.github.fierioziy.particlenativeapi.api.types.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.PlayerPredicate;
import org.objectweb.asm.Type;

public class ClassRegistry {

    public final ClassMapping EMPTY_MAPPING =                       of("non/existent/clazz");
    public final ClassMapping OBJECT =                              of(Object.class);

    // ParticleNativeAPI
    public ClassMapping particles_1_8 =                             of(Particles_1_8.class);
    public ClassMapping particles_1_13 =                            of(Particles_1_13.class);

    public ClassMapping particleType =                              of(ParticleType.class);
    public ClassMapping particleTypeBlock =                         of(ParticleTypeBlock.class);
    public ClassMapping particleTypeBlockMotion =                   of(ParticleTypeBlockMotion.class);
    public ClassMapping particleTypeColorable =                     of(ParticleTypeColorable.class);
    public ClassMapping particleTypeMotion =                        of(ParticleTypeMotion.class);
    public ClassMapping particleTypeDust =                          of(ParticleTypeDust.class);
    public ClassMapping particleTypeDustTransition =                of(ParticleTypeDustColorTransition.class);
    public ClassMapping particleTypeItemMotion =                    of(ParticleTypeItemMotion.class);
    public ClassMapping particleTypeNote =                          of(ParticleTypeNote.class);
    public ClassMapping particleTypeRedstone =                      of(ParticleTypeRedstone.class);
    public ClassMapping particleTypeVibration =                     of(ParticleTypeVibration.class);
    public ClassMapping particleTypeSculkChargeMotion =             of(ParticleTypeSculkChargeMotion.class);
    public ClassMapping particleTypeShriek =                        of(ParticleTypeShriek.class);

    public ClassMapping serverConnType =                            of(ServerConnection.class);
    public ClassMapping playerConnType =                            of(PlayerConnection.class);
    public ClassMapping playerPredicateType =                       of(PlayerPredicate.class);

    // Spigot
    public ClassMapping material =                                  unregistered("Material");
    public ClassMapping blockData =                                 unregistered("BlockData");
    public ClassMapping itemStackBukkit =                           unregistered("ItemStack_Bukkit");

    // OBC
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
    public ClassMapping builtInRegistries =                         unregistered("BuiltInRegistries");

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

    // mojang
    public ClassMapping vector3fa =                                 unregistered("Vector3fa");
    public ClassMapping vector3f =                                  unregistered("Vector3f");

    public ClassRegistry() {

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

    public static class RegisteredClassMapping implements ClassMapping {

        private final String internalName;
        private final String className;
        private final String desc;

        public RegisteredClassMapping(String internalName) {
            this.internalName = internalName;

            Type type = Type.getObjectType(internalName);
            this.className = type.getClassName();
            this.desc = type.getDescriptor();
        }

        public RegisteredClassMapping(Class<?> clazz) {
            Type type = Type.getType(clazz);
            this.internalName = type.getInternalName();
            this.className = type.getClassName();
            this.desc = type.getDescriptor();
        }

        @Override
        public String internalName() {
            return internalName;
        }

        @Override
        public String className() {
            return className;
        }

        @Override
        public String desc() {
            return desc;
        }

        @Override
        public ClassMapping impl(String suffix) {
            return new RegisteredClassMapping(internalName + suffix);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RegisteredClassMapping)) return false;

            RegisteredClassMapping that = (RegisteredClassMapping) o;

            return internalName.equals(that.internalName);
        }

        @Override
        public int hashCode() {
            return internalName.hashCode();
        }

        @Override
        public String toString() {
            return className;
        }
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

        @Override
        public ClassMapping impl(String suffix) {
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
