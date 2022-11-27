package com.github.fierioziy.particlenativetest.command.utils;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.util.Optional;

public class PacketFactory {

    private final Location startLoc;
    private final double speed;

    public PacketFactory(Location startLoc, double speed) {
        this.startLoc = startLoc;
        this.speed = speed;
    }

    public Optional<ParticlePacket> createPacket(Object particleListObj, Field particleTypeField)
            throws IllegalAccessException {
        Class<?> particleTypeClass = particleTypeField.getType();
        Color color = Color.fromRGB(255, 0, 255);
        Color color2 = Color.fromRGB(0, 255, 255);

        ParticlePacket packet = null;

        if (ParticleTypeBlock.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeBlock type = (ParticleTypeBlock) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.of(Material.DIAMOND_BLOCK).packet(true, startLoc, 0D, 0D, 0D, 0D, 1);
            }
        }
        else if (ParticleTypeBlockMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeBlockMotion type = (ParticleTypeBlockMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.of(Material.DIAMOND_BLOCK).packetMotion(true, startLoc, 0D, speed, speed);
            }
        }
        else if (ParticleTypeColorable.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeColorable type = (ParticleTypeColorable) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packetColored(true, startLoc, color);
            }
        }
        else if (ParticleTypeMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeMotion type = (ParticleTypeMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packetMotion(true, startLoc, 0D, speed, speed);
            }
        }
        else if (ParticleTypeDust.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeDust type = (ParticleTypeDust) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.color(color, 1F).packet(true, startLoc);
            }
        }
        else if (ParticleTypeDustColorTransition.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeDustColorTransition type = (ParticleTypeDustColorTransition) particleTypeField.get(
                    particleListObj);
            if (type.isPresent()) {
                packet = type.color(color, color2, 1F).packet(true, startLoc);
            }
        }
        else if (ParticleTypeVibrationSingle.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeVibrationSingle type = (ParticleTypeVibrationSingle) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packet(true, startLoc, startLoc.clone().add(0D, 0D, 3D), 20);
            }
        }
        else if (ParticleTypeVibration.class.isAssignableFrom(particleTypeClass)) {
            // TODO also handle entity target
            ParticleTypeVibration type = (ParticleTypeVibration) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.flyingTo(startLoc.clone().add(0D, 0D, 3D), 20).packet(true, startLoc);
            }
        }
        else if (ParticleTypeSculkChargeMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeSculkChargeMotion type = (ParticleTypeSculkChargeMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.roll(Math.PI / 2D).packetMotion(true, startLoc, 0D, speed, speed);
            }
        }
        else if (ParticleTypeShriek.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeShriek type = (ParticleTypeShriek) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.delay(10).packet(true, startLoc);
            }
        }
        else if (ParticleTypeItemMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeItemMotion type = (ParticleTypeItemMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.of(Material.GOLDEN_APPLE).packetMotion(true, startLoc, 0D, speed, speed);
            }
        }
        else if (ParticleTypeNote.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeNote type = (ParticleTypeNote) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packetNote(true, startLoc, color);
            }
        }
        else if (ParticleTypeRedstone.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeRedstone type = (ParticleTypeRedstone) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packetColored(true, startLoc, color);
            }
        }
        else if (ParticleType.class.isAssignableFrom(particleTypeClass)) {
            ParticleType type = (ParticleType) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packet = type.packet(true, startLoc);
            }
        }
        else {
            throw new UnsupportedOperationException("Unknown particle " + particleTypeField.getName());
        }

        return Optional.ofNullable(packet)
                .map(ParticlePacket::detachCopy);
    }

    public Location getSpawnLocation() {
        return startLoc;
    }

}
