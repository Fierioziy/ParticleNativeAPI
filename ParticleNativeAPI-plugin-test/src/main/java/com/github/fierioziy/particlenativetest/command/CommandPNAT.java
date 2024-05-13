package com.github.fierioziy.particlenativetest.command;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativetest.ParticleNativeTestPlugin;
import com.github.fierioziy.particlenativetest.command.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandPNAT implements CommandExecutor {

    private final ParticleNativeTestPlugin plugin;
    private final ParticleNativeAPI particleApi;

    public CommandPNAT(ParticleNativeTestPlugin plugin) {
        this.plugin = plugin;
        this.particleApi = plugin.getAPI();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is designed for players!");
            return true;
        }
        Player p = (Player) sender;

        if (!p.isOp()) {
            sender.sendMessage(ChatColor.RED + "This is command for OP users only!");
            return true;
        }

        if (args.length != 2) {
            p.sendMessage(ChatColor.RED + "Wrong syntax, use /pnat <1.8/1.13/1.19> <speed>");
            p.sendMessage(ChatColor.RED + "To cancel particles, tap 3 times crouch.");
            return true;
        }

        double speed;
        try {
            speed = Double.parseDouble(args[1]);
        }
        catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "Its not a number!");
            return true;
        }

        // this is hard-coded because reflected elements doesn't have strict order
        switch (args[0]) {
            case "1.8": {
                testParticles(p, speed, particleApi.LIST_1_8, ParticleListFields_1_8.FIELDS);
                break;
            }
            case "1.13": {
                testParticles(p, speed, particleApi.LIST_1_13, ParticleListFields_1_13.FIELDS);
                break;
            }
            case "1.19": {
                testParticles(p, speed, particleApi.LIST_1_19_PART, ParticleListFields_1_19.FIELDS);
                break;
            }
            default: {
                p.sendMessage(ChatColor.RED + "Wrong syntax, use /pnat <1.8/1.13/1.19>");
                break;
            }
        }

        return true;
    }

    private void testParticles(final Player player, final double speed,
                               final Object particleListObj, final String[] particleTypeFieldNames) {
        final List<Player> players = player.getWorld().getPlayers();

        new BukkitRunnable() {

            private final Location startLoc = player.getLocation().add(0D, 0.5D, 0D);
            private final SneakToggle sneakToggle = new SneakToggle();

            private int ticks = 0;
            private final int waitPeriod = 10;

            @Override
            public void run() {
                if (!player.isOnline())
                    this.cancel();

                sneakToggle.tick(player.isSneaking());
                if (sneakToggle.isFinished()) {
                    this.cancel();
                    return;
                }

                if (ticks < waitPeriod) {
                    ++ticks;
                    return;
                }
                else {
                    ticks = 0;
                }

                int packetsGroupsLen = 6;

                List<PacketFactory> packetFactories = new ArrayList<>(packetsGroupsLen);
                for (int i = 0; i < packetsGroupsLen; ++i) {
                    packetFactories.add(new PacketFactory(
                            player,
                            new Location(startLoc.getWorld(), startLoc.getX(), startLoc.getY() + i, startLoc.getZ()),
                            speed
                    ));
                }

                for (String particleTypeFieldName : particleTypeFieldNames) {
                    Field particleTypeField;
                    try {
                        particleTypeField = particleListObj
                                .getClass()
                                .getField(particleTypeFieldName);
                    }
                    catch (NoSuchFieldException e) {
                        player.sendMessage(ChatColor.RED + "Field " + particleTypeFieldName + " does not exist");
                        this.cancel();
                        return;
                    }

                    try {
                        // this looks awful, but I want it that way
                        List<List<ParticlePacket>> packetsGroups = new ArrayList<>(packetsGroupsLen);
                        for (PacketFactory packetFactory : packetFactories) {
                            packetsGroups.add(packetFactory.createPackets(
                                    particleListObj, particleTypeField
                            ));
                        }

                        packetsGroups.get(0).forEach(packet -> packet.sendTo(player));
                        packetsGroups.get(1).forEach(packet -> packet.sendTo(players));
                        packetsGroups.get(2).forEach(packet -> packet.sendTo(players, Player::isSneaking));
                        packetsGroups.get(3).forEach(packet -> packet.sendInRadiusTo(player, 5D));
                        packetsGroups.get(4).forEach(packet -> packet.sendInRadiusTo(players, 5D));
                        packetsGroups.get(5).forEach(packet -> packet.sendInRadiusTo(players, 5D, Player::isSneaking));
                    }
                    catch (IllegalAccessException e) {
                        player.sendMessage(ChatColor.RED + "Cast error on particle " + particleTypeField.getName());
                        this.cancel();
                        return;
                    }

                    for (PacketFactory packetFactory : packetFactories) {
                        packetFactory.getSpawnLocation().add(1D, 0D, 0D);
                    }
                }
            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

}
