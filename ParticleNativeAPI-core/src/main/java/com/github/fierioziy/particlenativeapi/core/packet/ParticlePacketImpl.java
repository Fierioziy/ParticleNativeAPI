package com.github.fierioziy.particlenativeapi.core.packet;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class ParticlePacketImpl implements ParticlePacket, Cloneable {

    protected double x, y, z;

    @Override
    public ParticlePacket detachCopy() {
        try {
            // this will also copy any fields created via ASM
            // so, it will copy cached NMS particle packet
            return (ParticlePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new ParticleException(
                    "Tried to copy ParticlePacket, but for some reason it is not possible", e
            );
        }
    }

    @Override
    public void sendTo(Player player) {
        throw new ParticleException(
                "Tried to send packet, but for some reason it is not possible"
        );
    }

    @Override
    public void sendInRadiusTo(Player player, double radius) {
        Location playerLoc = player.getLocation();

        double sub;
        if ((sub = playerLoc.getX() - x) * sub +
            (sub = playerLoc.getY() - y) * sub +
            (sub = playerLoc.getZ() - z) * sub <= radius * radius) {
            this.sendTo(player);
        }
    }

    @Override
    public void sendTo(Collection<? extends Player> players) {
        int length = players.size();
        Iterator<? extends Player> it = players.iterator();

        while (length > 0) {
            this.sendTo(it.next());
            --length;
        }
    }

    @Override
    public void sendTo(Collection<? extends Player> players,
                       Predicate<? super Player> predicate) {
        int length = players.size();
        Iterator<? extends Player> it = players.iterator();

        while (length > 0) {
            Player player = it.next();

            if (predicate.test(player)) {
                this.sendTo(player);
            }

            --length;
        }
    }

    @Override
    public void sendInRadiusTo(Collection<? extends Player> players, double radius) {
        radius *= radius;

        double x = this.x;
        double y = this.y;
        double z = this.z;

        int length = players.size();
        Iterator<? extends Player> it = players.iterator();

        while (length > 0) {
            Player player = it.next();
            Location playerLoc = player.getLocation();

            double sub;
            if ((sub = playerLoc.getX() - x) * sub +
                (sub = playerLoc.getY() - y) * sub +
                (sub = playerLoc.getZ() - z) * sub <= radius) {
                this.sendTo(player);
            }

            --length;
        }
    }

    @Override
    public void sendInRadiusTo(Collection<? extends Player> players, double radius,
                               Predicate<? super Player> predicate) {
        radius *= radius;

        double x = this.x;
        double y = this.y;
        double z = this.z;

        int length = players.size();
        Iterator<? extends Player> it = players.iterator();

        while (length > 0) {
            Player player = it.next();
            Location playerLoc = player.getLocation();

            double sub;
            if ((sub = playerLoc.getX() - x) * sub +
                (sub = playerLoc.getY() - y) * sub +
                (sub = playerLoc.getZ() - z) * sub <= radius
                    && predicate.test(player)) {
                this.sendTo(player);
            }

            --length;
        }
    }

}
