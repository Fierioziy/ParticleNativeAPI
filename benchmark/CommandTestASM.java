package me.authorname.commands;

import me.authorname.ParticleNativeAPI_Test;
import me.fierioziy.ParticleNativeAPI;
import me.fierioziy.api.Particles_1_13;
import me.fierioziy.api.Particles_1_8;
import me.fierioziy.api.ServerConnection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandTestASM implements CommandExecutor {
    private ParticleNativeAPI_Test plugin;

    private ServerConnection serverConn;
    private Particles_1_8 particles_1_8;
    private Particles_1_13 particles_1_13;

    public CommandTestASM(ParticleNativeAPI_Test plugin) {
        this.plugin = plugin;

        ParticleNativeAPI api = plugin.getAPI();

        if (!api.isPresent()) {
            throw new IllegalStateException("Error occured in particle library.");
        }

        serverConn = api.getServerConnection();
        particles_1_8 = api.getParticles_1_8();
        particles_1_13 = api.getParticles_1_13();

        if (!particles_1_8.FLAME().isPresent()) {
            throw new IllegalStateException("Flame is not present on this server version.");
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is designed for players!");
            return true;
        }
        final Player p = (Player) sender;

        if (!p.isOp()) {
            sender.sendMessage(ChatColor.RED + "This is command for OP users only!");
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(ChatColor.RED + "Wrong syntax, use /pasm <number>");
            return true;
        }

        final int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "Its not a number!");
            return true;
        }

        Random random = new Random();
        Location loc = p.getLocation().subtract(3D, 3D, 3D);
        Location randomLoc = p.getLocation();
        long start, end;

        start = System.nanoTime();
        for (int i = 0; i < amount; ++i) {
            randomLoc.setX(loc.getX() + random.nextDouble() * 6D);
            randomLoc.setY(loc.getY() + random.nextDouble() * 6D);
            randomLoc.setZ(loc.getZ() + random.nextDouble() * 6D);

            serverConn.sendPacket(p, particles_1_8.FLAME().packet(true, randomLoc));
        }
        end = System.nanoTime();

        p.sendMessage(ChatColor.GREEN + "Test of " + amount + " particles (ParticleNativeAPI): " + ((end - start) / 1000000D) + " ms");

        return true;
    }

}
