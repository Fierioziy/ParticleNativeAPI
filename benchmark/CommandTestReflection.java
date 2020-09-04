package me.authorname.commands;

import me.authorname.ParticleNativeAPI_Test;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

public class CommandTestReflection implements CommandExecutor {
    private ParticleNativeAPI_Test plugin;

    private Object flameObj;

    private Method getHandleMethod;
    private Field playerConnectionField;
    private Method sendPacketMethod;

    private Constructor particleCtr;

    public CommandTestReflection(ParticleNativeAPI_Test plugin) {
        this.plugin = plugin;

        try {
            String packageVersion = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
            String OBC = "org.bukkit.craftbukkit." + packageVersion;
            String NMS = "net.minecraft.server." + packageVersion;

            flameObj = Class.forName(NMS + ".Particles").getField("FLAME").get(null);

            Class<?> craftPlayerClass = Class.forName(OBC + ".entity.CraftPlayer");

            getHandleMethod = craftPlayerClass.getMethod("getHandle");
            getHandleMethod.setAccessible(true);

            playerConnectionField = Class.forName(NMS + ".EntityPlayer").getField("playerConnection");
            playerConnectionField.setAccessible(true);

            sendPacketMethod = Class.forName(NMS + ".PlayerConnection").getMethod("sendPacket",
                    Class.forName(NMS + ".Packet"));
            sendPacketMethod.setAccessible(true);

            particleCtr = Class.forName(NMS + ".PacketPlayOutWorldParticles")
                    .getConstructor(
                            Class.forName(NMS + ".ParticleParam"),
                            boolean.class,
                            float.class, float.class, float.class,
                            float.class, float.class, float.class,
                            float.class, int.class
                    );
            particleCtr.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
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
            p.sendMessage(ChatColor.RED + "Wrong syntax, use /pref <number>");
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

            try {
                sendPacketMethod.invoke(
                        playerConnectionField.get(getHandleMethod.invoke(p)),
                        particleCtr.newInstance(flameObj, true,
                                (float) randomLoc.getX(), (float) randomLoc.getY(), (float) randomLoc.getZ(),
                                0F, 0F, 0F, 0F, 1
                        )
                );
            } catch (Exception e) {
                p.sendMessage("Error occured while sending.");
                e.printStackTrace();
            }
        }
        end = System.nanoTime();

        p.sendMessage(ChatColor.GREEN + "asdf.Test of " + amount + " particles (Reflection): " + ((end - start) / 1000000D) + " ms");

        return true;
    }

}
