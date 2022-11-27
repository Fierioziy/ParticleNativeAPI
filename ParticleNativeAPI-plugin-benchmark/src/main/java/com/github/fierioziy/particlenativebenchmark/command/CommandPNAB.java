package com.github.fierioziy.particlenativebenchmark.command;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativebenchmark.ParticleNativeBenchmarkPlugin;
import com.github.fierioziy.particlenativebenchmark.spawners.ParticleSpawner;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandPNAB implements CommandExecutor {

    private final ParticleNativeAPI particleApi;

    public CommandPNAB(ParticleNativeBenchmarkPlugin plugin) {
        this.particleApi = plugin.getAPI();

        if (!particleApi.LIST_1_8.FLAME.isPresent()) {
            throw new IllegalStateException("Flame is not present on this server version.");
        }
    }

    @Override
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
            p.sendMessage(ChatColor.RED + "Wrong syntax, use /pnab <nms/pna/spigot/ref> <packet_count>");
            return true;
        }

        BenchmarkCase benchmarkCase;
        ParticleSpawner particleSpawner;
        try {
            benchmarkCase = BenchmarkCase.getByShortName(args[0]);
            particleSpawner = benchmarkCase.createParticleSpawner(new Context(particleApi));
        }
        catch (IllegalArgumentException e)
        {
            p.sendMessage(ChatColor.RED + "Mode " + args[0] + " does not match any mode from <nms/pna/spigot/ref>");
            return true;
        }

        int packetCount;
        try {
            packetCount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            p.sendMessage(ChatColor.RED + "Provided packet count is not a number");
            return true;
        }

        Location startLoc = p.getLocation().subtract(3D, 3D, 3D);
        double startX = startLoc.getX();
        double startY = startLoc.getY();
        double startZ = startLoc.getZ();

        Random random = new Random();
        double randomX, randomY, randomZ;
        long start, end;

        // this is not the perfect way to benchmark code, however JMH cannot
        // be reliably used with server jar without either hanging on server
        // or making absurd non-Mockito mocks in isolated environment
        start = System.nanoTime();
        for (int i = 0; i < packetCount; ++i) {
            randomX = startX + random.nextDouble() * 6D;
            randomY = startY + random.nextDouble() * 6D;
            randomZ = startZ + random.nextDouble() * 6D;

            // location is randomized to simulate similar conditions
            // in which particle spawning normally occur
            particleSpawner.spawnParticle(p, randomX, randomY, randomZ);
        }
        end = System.nanoTime();

        p.sendMessage(ChatColor.GREEN + String.format(
                "Test of %d particles (%s): %.3f ms",
                packetCount,
                benchmarkCase.name().toLowerCase(),
                ((end - start) / 1_000_000D)
        ));

        return true;
    }

    public static class Context {

        private final ParticleNativeAPI particleApi;

        public Context(ParticleNativeAPI particleApi) {
            this.particleApi = particleApi;
        }

        public ParticleNativeAPI getParticleApi() {
            return particleApi;
        }

    }

}
