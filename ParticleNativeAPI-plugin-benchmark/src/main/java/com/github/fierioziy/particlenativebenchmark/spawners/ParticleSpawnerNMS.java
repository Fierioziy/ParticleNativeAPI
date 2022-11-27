package com.github.fierioziy.particlenativebenchmark.spawners;

import com.github.fierioziy.particlenativebenchmark.command.CommandPNAB;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ParticleSpawnerNMS implements ParticleSpawner {

    public ParticleSpawnerNMS(CommandPNAB.Context context) {

    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z) {
        ((CraftPlayer) player).getHandle().b.sendPacket(           // b is player connection
                new PacketPlayOutWorldParticles(Particles.C, true, // C is flame particle type
                        x, y, z,
                        0F, 0F, 0F, 0F, 1)
        );
    }

}
