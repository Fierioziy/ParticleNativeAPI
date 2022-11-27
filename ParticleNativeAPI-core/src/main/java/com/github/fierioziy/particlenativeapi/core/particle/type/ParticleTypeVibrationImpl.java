package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeVibration;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ParticleTypeVibrationImpl implements ParticleTypeVibration {

    @Override
    public @Shared ParticleType flyingTo(Location target, int ticks) {
        return flyingTo(target.getX(), target.getY(), target.getZ(), ticks);
    }

    @Override
    public @Shared ParticleType flyingTo(Vector target, int ticks) {
        return flyingTo(target.getX(), target.getY(), target.getZ(), ticks);
    }

    @Override
    public @Shared ParticleType flyingTo(double targetX, double targetY, double targetZ, int ticks) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public @Shared ParticleType flyingTo(Entity targetEntity, int ticks) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
