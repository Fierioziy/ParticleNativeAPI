package com.github.fierioziy.particlenativetest.command.utils;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacketFactory {

    private final Player player;
    private final Location startLoc;
    private final double speed;

    public PacketFactory(Player player, Location startLoc, double speed) {
        this.player = player;
        this.startLoc = startLoc;
        this.speed = speed;
    }

    public List<ParticlePacket> createPackets(Object particleListObj, Field particleTypeField)
            throws IllegalAccessException {
        Class<?> particleTypeClass = particleTypeField.getType();
        Color color = Color.fromRGB(255, 0, 255);
        Color color2 = Color.fromRGB(0, 255, 255);

        List<ParticlePacket> packets = new ArrayList<>(2);

        if (ParticleTypeBlock.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeBlock type = (ParticleTypeBlock) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .of(Material.DIAMOND_BLOCK)
                        .packet(true, startLoc, 0D, 0D, 0D, 0D, 1)
                        .detachCopy());
            }
        }
        else if (ParticleTypeBlockMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeBlockMotion type = (ParticleTypeBlockMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .of(Material.DIAMOND_BLOCK)
                        .packetMotion(true, startLoc, 0D, speed, speed)
                        .detachCopy());
            }
        }
        else if (ParticleTypeColorable.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeColorable type = (ParticleTypeColorable) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packetColored(true, startLoc, color)
                        .detachCopy());
            }
        }
        else if (ParticleTypeMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeMotion type = (ParticleTypeMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packetMotion(true, startLoc, 0D, speed, speed)
                        .detachCopy());
            }
        }
        else if (ParticleTypeDust.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeDust type = (ParticleTypeDust) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .color(color, 1F)
                        .packet(true, startLoc)
                        .detachCopy());
            }
        }
        else if (ParticleTypeDustColorTransition.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeDustColorTransition type = (ParticleTypeDustColorTransition) particleTypeField.get(
                    particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .color(color, color2, 1F)
                        .packet(true, startLoc)
                        .detachCopy());
            }
        }
        else if (ParticleTypeVibrationSingle.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeVibrationSingle type = (ParticleTypeVibrationSingle) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packet(true, startLoc, startLoc.clone().add(0D, 0D, 3D), 20)
                        .detachCopy());
                packets.add(type
                        .packet(true, startLoc.clone().add(0D, 0D, -1D), player, 20)
                        .detachCopy());
            }
        }
        else if (ParticleTypeVibration.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeVibration type = (ParticleTypeVibration) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .flyingTo(startLoc.clone().add(0D, 0D, 3D), 20)
                        .packet(true, startLoc)
                        .detachCopy());
                packets.add(type
                        .flyingTo(player, 20)
                        .packet(false, startLoc.clone().add(0D, 0D, -1D))
                        .detachCopy());
            }
        }
        else if (ParticleTypeSculkChargeMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeSculkChargeMotion type = (ParticleTypeSculkChargeMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .roll(Math.PI / 2D)
                        .packetMotion(true, startLoc, 0D, speed, speed)
                        .detachCopy());
            }
        }
        else if (ParticleTypeShriek.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeShriek type = (ParticleTypeShriek) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .delay(10)
                        .packet(true, startLoc)
                        .detachCopy());
            }
        }
        else if (ParticleTypeItemMotion.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeItemMotion type = (ParticleTypeItemMotion) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .of(Material.GOLDEN_APPLE)
                        .packetMotion(true, startLoc, 0D, speed, speed)
                        .detachCopy());
            }
        }
        else if (ParticleTypeNote.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeNote type = (ParticleTypeNote) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packetNote(true, startLoc, color)
                        .detachCopy());
            }
        }
        else if (ParticleTypeRedstone.class.isAssignableFrom(particleTypeClass)) {
            ParticleTypeRedstone type = (ParticleTypeRedstone) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packetColored(true, startLoc, color)
                        .detachCopy());
            }
        }
        else if (ParticleType.class.isAssignableFrom(particleTypeClass)) {
            ParticleType type = (ParticleType) particleTypeField.get(particleListObj);
            if (type.isPresent()) {
                packets.add(type
                        .packet(true, startLoc)
                        .detachCopy());
            }
        }
        else {
            throw new UnsupportedOperationException("Unknown particle " + particleTypeField.getName());
        }

        return packets;
    }

    public Location getSpawnLocation() {
        return startLoc;
    }

}
