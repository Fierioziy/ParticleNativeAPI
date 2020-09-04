package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Class used to resolve internal NMS and OBC classes, provide temporary class loader
 * and provide utility methods for internal class access.</p>
 *
 * <p>It is used in unit tests. This is the reason, why class generation
 * has been split into empty constructor and method loading API container
 * internals.</p>
 */
public class InternalResolver {

    private TempClassLoader cl;
    private String packageVersion;

    InternalResolver() {}

    public InternalResolver(JavaPlugin plugin) {
        cl = new TempClassLoader(plugin.getClass().getClassLoader());

        // get package version
        packageVersion = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public TempClassLoader getTempClassLoader() {
        return cl;
    }

    public void setTempClassLoader(TempClassLoader cl) {
        this.cl = cl;
    }

    public Type getNMS(String classPath) {
        return Type.getType(String.format("Lnet/minecraft/server/%s/%s;",
                getPackageVersion(), classPath));
    }

    public Type getOBC(String classPath) {
        return Type.getType(String.format("Lorg/bukkit/craftbukkit/%s/%s;",
                getPackageVersion(), classPath));
    }

    /**
     * <p>Returns particles name <code>Set</code> from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.8.</p>
     *
     * @return particles name <code>Set</code> from current server version.
     * @throws ClassNotFoundException when error occurred during particles obtaining.
     */
    public Set<String> getParticles_1_8() throws ClassNotFoundException {
        Class<?> enumClass = Class.forName(getNMS("EnumParticle").getClassName());

        Set<String> currentParticleSet = new HashSet<>();
        for (Object o : enumClass.getEnumConstants()) {
            currentParticleSet.add(((Enum<?>) o).name());
        }

        return currentParticleSet;
    }

    /**
     * <p>Returns particles name <code>Set</code> from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.13.</p>
     *
     * @return particles name <code>Set</code> from current server version.
     * @throws ClassNotFoundException when error occurred during particles obtaining.
     */
    public Set<String> getParticles_1_13() throws ClassNotFoundException {
        Class<?> particleClass = Class.forName(getNMS("Particle").getClassName());
        Class<?> particlesClass = Class.forName(getNMS("Particles").getClassName());

        Set<String> currentParticleSet = new HashSet<>();
        for (Field f : particlesClass.getFields()) {
            // make sure field is of Particle class
            if (particleClass.isAssignableFrom(f.getType())) {
                currentParticleSet.add(f.getName());
            }
        }

        return currentParticleSet;
    }


    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.7 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.7 version, false otherwise.
     */
    public boolean isPacketConstructor_1_7() {
        try {
            Class.forName(getNMS("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    String.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.8 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.8 version, false otherwise.
     */
    public boolean isPacketConstructor_1_8() {
        try {
            Class.forName(getNMS("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS("EnumParticle").getClassName()), boolean.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class, int[].class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.13 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.13 version, false otherwise.
     */
    public boolean isPacketConstructor_1_13() {
        try {
            Class.forName(getNMS("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS("ParticleParam").getClassName()), boolean.class,
                    float.class, float.class, float.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version has particle packet
     * constructor from MC 1.15 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.15 version, false otherwise.
     */
    public boolean isPacketConstructor_1_15() {
        try {
            Class.forName(getNMS("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS("ParticleParam").getClassName()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

}
