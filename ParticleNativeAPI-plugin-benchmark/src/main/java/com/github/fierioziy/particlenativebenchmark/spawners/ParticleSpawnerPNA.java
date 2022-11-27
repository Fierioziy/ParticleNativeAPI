package com.github.fierioziy.particlenativebenchmark.spawners;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativebenchmark.command.CommandPNAB;
import org.bukkit.entity.Player;

public class ParticleSpawnerPNA implements ParticleSpawner {

    private final ParticleNativeAPI particleApi;

    public ParticleSpawnerPNA(CommandPNAB.Context context) {
        this.particleApi = context.getParticleApi();
    }

    @Override
    public void spawnParticle(Player player, double x, double y, double z) {
        particleApi.LIST_1_8.FLAME
                .packet(true, x, y, z)
                .sendTo(player);
    }

}
