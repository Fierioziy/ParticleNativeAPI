package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeVibration;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeVibrationImpl implements ParticleTypeVibration {

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
    public ParticlePacket packet(boolean far, double x, double y, double z,
                                 double targetX, double targetY, double targetZ,
                                 int ticks) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }

}
