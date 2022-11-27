package com.github.fierioziy.particlenativebenchmark.spawners;

import com.github.fierioziy.particlenativebenchmark.command.CommandPNAB;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParticleSpawnerReflection implements ParticleSpawner {

    private Object flameParticleTypeObj;

    private Method getHandleMethod;
    private Field playerConnectionField;
    private Method sendPacketMethod;

    private Constructor<?> particlePacketCtr;

    public ParticleSpawnerReflection(CommandPNAB.Context context) {
        try {
            // assume those class elements have to be dynamically resolved
            // by version in package path
            flameParticleTypeObj = Particles.C; // C is flame particle type

            Class<?> craftPlayerClass = CraftPlayer.class;

            getHandleMethod = craftPlayerClass.getMethod("getHandle");
            getHandleMethod.setAccessible(true);

            playerConnectionField = EntityPlayer.class.getField("b"); // b is player connection
            playerConnectionField.setAccessible(true);

            sendPacketMethod = PlayerConnection.class.getMethod("sendPacket", Packet.class);
            sendPacketMethod.setAccessible(true);

            particlePacketCtr = PacketPlayOutWorldParticles.class
                    .getConstructor(
                            ParticleParam.class,
                            boolean.class,
                            double.class, double.class, double.class,
                            float.class, float.class, float.class,
                            float.class, int.class
                    );
            particlePacketCtr.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void spawnParticle(Player player, double x, double y, double z) {
        try {
            sendPacketMethod.invoke(
                    playerConnectionField.get(getHandleMethod.invoke(player)),
                    particlePacketCtr.newInstance(flameParticleTypeObj, true,
                            x, y, z,
                            0F, 0F, 0F, 0F, 1
                    )
            );
        } catch (Exception e) {
            player.sendMessage("Error occurred while sending");
            e.printStackTrace();
        }
    }
}
