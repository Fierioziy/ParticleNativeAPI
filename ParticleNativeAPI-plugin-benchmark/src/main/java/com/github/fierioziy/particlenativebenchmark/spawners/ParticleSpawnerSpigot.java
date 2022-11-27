package com.github.fierioziy.particlenativebenchmark.spawners;

import com.github.fierioziy.particlenativebenchmark.command.CommandPNAB;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleSpawnerSpigot implements ParticleSpawner {

    public ParticleSpawnerSpigot(CommandPNAB.Context context) {

    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z) {
        player.spawnParticle(Particle.FLAME, x, y, z, 1, 0D, 0D, 0D, 0D);
    }

}
