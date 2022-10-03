package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeMotionImpl extends ParticleTypeImpl implements ParticleTypeMotion {

    @Override
    public ParticleTypeMotion detachCopy() {
        try {
            // this will also copy any fields created via ASM
            // so, it will copy cached NMS particle type related object
            return (ParticleTypeMotion) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ParticleException(
                    "Tried to copy ParticleTypeMotion, but for some reason it is not possible", e
            );
        }
    }

    @Override
    public ParticlePacket packetMotion(boolean far, Location loc, Vector dir) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    @Override
    public ParticlePacket packetMotion(boolean far, Vector loc, Vector dir) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    @Override
    public ParticlePacket packetMotion(boolean far, double x, double y, double z,
                                       Vector dir) {
        return packet(far,
                x,          y,          z,
                dir.getX(), dir.getY(), dir.getZ(),
                1D, 0);
    }

    @Override
    public ParticlePacket packetMotion(boolean far, Location loc,
                                       double dirX, double dirY, double dirZ) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dirX,       dirY,       dirZ,
                1D, 0);
    }

    @Override
    public ParticlePacket packetMotion(boolean far, Vector loc,
                                       double dirX, double dirY, double dirZ) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                dirX,       dirY,       dirZ,
                1D, 0);
    }

    @Override
    public ParticlePacket packetMotion(boolean far, double x, double y, double z,
                                       double dirX, double dirY, double dirZ) {
        return packet(far,
                x,          y,          z,
                dirX,       dirY,       dirZ,
                1D, 0);
    }

}
