package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeImpl implements ParticleType, Cloneable {

    @Override
    public ParticleType detachCopy() {
        try {
            // this will also copy any fields created via ASM
            // so, it will copy cached NMS particle type related object
            return (ParticleType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ParticleException(
                    "Tried to copy ParticleType, but for some reason it is not possible", e
            );
        }
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                0D, 1);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                0D, 1);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z) {
        return packet(far,
                x, y, z,
                0D, 0D, 0D,
                0D, 1);
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z, int count) {
        return packet(far,
                x, y, z,
                0D, 0D, 0D,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc, double speed, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                speed, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc, double speed, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                0D, 0D, 0D,
                speed, count);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z,
                                 double speed, int count) {
        return packet(far,
                x, y, z,
                0D, 0D, 0D,
                speed, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc,
                                 double offsetX, double offsetY, double offsetZ,
                                 int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                offsetX, offsetY, offsetZ,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc,
                                 double offsetX, double offsetY, double offsetZ,
                                 int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                offsetX, offsetY, offsetZ,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z,
                                 double offsetX, double offsetY, double offsetZ,
                                 int count) {
        return packet(far,
                x, y, z,
                offsetX, offsetY, offsetZ,
                0D, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Location loc,
                                 double offsetX, double offsetY, double offsetZ,
                                 double speed, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                offsetX, offsetY, offsetZ,
                speed, count);
    }

    @Override
    public ParticlePacket packet(boolean far, Vector loc,
                                 double offsetX, double offsetY, double offsetZ,
                                 double speed, int count) {
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                offsetX, offsetY, offsetZ,
                speed, count);
    }

    @Override
    public ParticlePacket packet(boolean far, double x, double y, double z,
                                 double offsetX, double offsetY, double offsetZ,
                                 double speed, int count) {
        throw new ParticleException(
                "Requested particle type is not supported by this server version!"
        );
    }

    @Override
    public boolean isValid() {
        return false;
    }


}
