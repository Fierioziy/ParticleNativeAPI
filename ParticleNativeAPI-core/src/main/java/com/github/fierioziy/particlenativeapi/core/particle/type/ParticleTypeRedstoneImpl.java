package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeRedstone;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class ParticleTypeRedstoneImpl extends ParticleTypeImpl implements ParticleTypeRedstone {

    @Override
    public ParticleTypeRedstone detachCopy() {
        try {
            // this will also copy any fields created via ASM
            // so, it will copy cached NMS particle type related object
            return (ParticleTypeRedstone) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ParticleException(
                    "Tried to copy ParticleTypeRedstone, but for some reason it is not possible", e
            );
        }
    }

    @Override
    public ParticlePacket packetColored(boolean far, Location loc,
                                        Color color) {
        int r = color.getRed();
        if (r == 0) r = 1;
        return packet(far,
                loc.getX(),     loc.getY(),                 loc.getZ(),
                r / 255D,       color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    @Override
    public ParticlePacket packetColored(boolean far, Vector loc,
                                        Color color) {
        int r = color.getRed();
        if (r == 0) r = 1;
        return packet(far,
                loc.getX(),     loc.getY(),                 loc.getZ(),
                r / 255D,       color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    @Override
    public ParticlePacket packetColored(boolean far, double x, double y, double z,
                                        Color color) {
        int r = color.getRed();
        if (r == 0) r = 1;
        return packet(far, x, y, z,
                r / 255D,   color.getGreen() / 255D,    color.getBlue() / 255D,
                1D, 0);
    }

    @Override
    public ParticlePacket packetColored(boolean far, Location loc,
                                        int r, int g, int b) {
        if (r == 0) r = 1;
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

    @Override
    public ParticlePacket packetColored(boolean far, Vector loc,
                                        int r, int g, int b) {
        if (r == 0) r = 1;
        return packet(far,
                loc.getX(), loc.getY(), loc.getZ(),
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

    @Override
    public ParticlePacket packetColored(boolean far, double x, double y, double z,
                                        int r, int g, int b) {
        if (r == 0) r = 1;
        return packet(far,
                x,          y,          z,
                r / 255D,   g / 255D,   b / 255D,
                1D, 0);
    }

}
