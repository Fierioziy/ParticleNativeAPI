package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeVibrationSingle;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.api.utils.Shared;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class ParticleTypeVibrationSingleImpl implements ParticleTypeVibrationSingle {

    @Override
    public ParticlePacket packet(boolean far, Location loc, Location target, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                target.getX(), target.getY(), target.getZ(),
                ticks);
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc, Vector target, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                target.getX(), target.getY(), target.getZ(),
                ticks);
    }

    @Override
    public @Shared ParticlePacket packet(boolean far, Location loc, Entity targetEntity, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                targetEntity, ticks);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc, Location target, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                target.getX(), target.getY(), target.getZ(),
                ticks);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc, Vector target, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                target.getX(), target.getY(), target.getZ(),
                ticks);
    }

    @Override
    public @Shared ParticlePacket packet(boolean far, Vector loc, Entity targetEntity, int ticks) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                targetEntity, ticks);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z,
                                 double targetX, double targetY, double targetZ,
                                 int ticks) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public @Shared ParticlePacket packet(boolean far, double x, double y, double z,
                                         Entity targetEntity, int ticks) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isPresent() {
        return false;
    }

}
