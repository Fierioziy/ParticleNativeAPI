package me.authorname.commands;

import me.authorname.ParticleNativeAPI_Test;
import net.minecraft.server.v1_14_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_14_R1.Particles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandTestNative implements CommandExecutor {
    private ParticleNativeAPI_Test plugin;

    public CommandTestNative(ParticleNativeAPI_Test plugin) {
        this.plugin = plugin;
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
            p.sendMessage(ChatColor.RED + "Wrong syntax, use /pnative <number>");
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

            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(
                    new PacketPlayOutWorldParticles(Particles.FLAME, true,
                            (float) randomLoc.getX(), (float) randomLoc.getY(), (float) randomLoc.getZ(),
                            0F, 0F, 0F, 0F, 1)
            );
        }
        end = System.nanoTime();

        p.sendMessage(ChatColor.GREEN + "Test of " + amount + " particles (version specific): " + ((end - start) / 1000000D) + " ms");

        return true;
    }

}
