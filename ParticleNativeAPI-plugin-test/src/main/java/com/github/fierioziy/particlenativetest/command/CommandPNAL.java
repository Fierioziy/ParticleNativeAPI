package com.github.fierioziy.particlenativetest.command;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativetest.ParticleNativeTestPlugin;
import com.github.fierioziy.particlenativetest.command.utils.SneakToggle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/*
 * I barely recalled what I wrote here few years ago.
 * Someday to rewrite it. Right now I've written some comments in code.
 */
public class CommandPNAL implements CommandExecutor {
    private final ParticleNativeTestPlugin plugin;
    private final ParticleNativeAPI particleApi;

    public CommandPNAL(ParticleNativeTestPlugin plugin) {
        this.plugin = plugin;
        this.particleApi = plugin.getAPI();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is designed for players!");
            return true;
        }
        final Player player = (Player) sender;

        if (!player.isOp()) {
            sender.sendMessage(ChatColor.RED + "This is command for OP users only!");
            return true;
        }

        if (args.length != 4) {
            player.sendMessage(ChatColor.RED + "Wrong syntax, use /pnal <raysCount> <period> <ringsCount> <radius>");
            player.sendMessage(ChatColor.RED + "To cancel particles, tap 3 times crouch.");
            return true;
        }

        final int raysCount, period, ringsCount;
        final double radius;
        try {
            raysCount = Integer.parseInt(args[0]);
            period = Integer.parseInt(args[1]);
            ringsCount = Integer.parseInt(args[2]);
            radius = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Its not a number!");
            return true;
        }

        new BukkitRunnable() {

            private final Location startLoc = player.getLocation().add(0D, 0.5D, 0D);
            private final SneakToggle sneakToggle = new SneakToggle();

            private int ticks = 0;

            @Override
            public void run() {
                if (!player.isOnline()) this.cancel();

                sneakToggle.tick(player.isSneaking());
                if (sneakToggle.isFinished()) {
                    this.cancel();
                    return;
                }

                if (ticks < period) {
                    ++ticks;
                    return;
                }
                else ticks = 0;

                particleApi.LIST_1_8.REDSTONE
                        .packetColored(true, startLoc, 255, 255, 255)
                        .sendTo(player);

                for (int currentRing = ringsCount; currentRing >= 1; --currentRing) {
                    double ringProp = currentRing / (double) ringsCount;

                    for (int deg = 0; deg < 360; deg += (360 / raysCount)) {
                        double rad = Math.toRadians(deg);
                        Location next = new Location(startLoc.getWorld(),
                                startLoc.getX() + Math.sin(rad) * radius * ringProp,
                                startLoc.getY(),
                                startLoc.getZ() + Math.cos(rad) * radius * ringProp
                        );

                        // reverse 1.0 -> 0.0 to 0.0 -> 1.0
                        // so 0.0 means outside black ring is black and 1.0 means center white ring
                        ringProp = 1D - ringProp;

                        /*
                        outside rings to center ring will be linearly colored from black to full color
                        inner rings from center ring will be linearly brightened from full color to white

                        ringProp [0.0, 0.5] -> darkenProp [0.0, 1.0] and then stays 1.0
                        ringProp [0.0, 0.5] -> brightenProp 0.0 and then goes [0.0, 1.0]
                         */

                        double darkenProp =   ringProp <= 0.5D ? 2D * ringProp : 1D;
                        double brightenProp = ringProp <= 0.5D ? 0D : 2D * ringProp - 1D;

                        /*
                        - the more deg is closer to boundaries center, the closer to 255 it will be
                        - outside of boundaries it will be 0
                          -   0 deg = (255,   0,   0) red color
                          - 120 deg = (  0,   0, 255) blue color
                          - 240 deg = (  0, 255,   0) green color
                        - (240, deg, 120) will be properly scaled in [240, 360] and [0, 120] as if being one
                         */
                        int colorR = selectBySpectrum(240, deg, 120);
                        int colorG = selectBySpectrum(0,   deg, 240);
                        int colorB = selectBySpectrum(120, deg, 360);

                        /*
                        - first, there is no brighten:
                          - so  (255D * brightenProp) is 0.0
                          - and (  1D - brightenProp) is 1.0
                        - the more darkenProp increase, the more "full color" will be gained starting from black
                        - when darkenProp is 1.0, then brightenProp starts increasing from 0.0 to 1.0
                        - scaling color is done so:
                          - (255D * brightenProp) will gradually increase, so there will be more constant white in color
                          - (  1D - brightenProp) will scale color to fit in the remaining gap left by upper said white
                         */
                        particleApi.LIST_1_8.REDSTONE
                                .packetColored(true, next,
                                        (int) (255D * brightenProp + colorR * darkenProp * (1D - brightenProp)),
                                        (int) (255D * brightenProp + colorG * darkenProp * (1D - brightenProp)),
                                        (int) (255D * brightenProp + colorB * darkenProp * (1D - brightenProp)))
                                .sendTo(player);
                    }
                }

            }

        }.runTaskTimer(plugin, 0L, 1L);

        return true;
    }

    private int selectBySpectrum(int startDeg, int deg, int endDeg) {
        if (endDeg < startDeg) {
            endDeg += 360;
        }

        if (deg < startDeg) deg += 360;
        if (deg > endDeg) deg -= 360;

        // what the hell is 510?
        // oh, 2 * 255
        // ???
        int halfBoundsDeg = (endDeg - startDeg) / 2;
        if (deg - startDeg <= halfBoundsDeg) {
            return cap(510 * (deg - startDeg) / halfBoundsDeg);
        }
        return cap(510 - 510 * (deg - halfBoundsDeg - startDeg) / halfBoundsDeg);
    }
    
    private int cap(int i) {
        if (i > 255) return 255;
        if (i < 0) return 0;
        return i;
    }

}
