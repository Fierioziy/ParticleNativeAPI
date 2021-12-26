package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    private boolean isMapping_1_17;

    InternalResolver() {}

    public InternalResolver(JavaPlugin plugin) {
        cl = new TempClassLoader(plugin.getClass().getClassLoader());

        // get package version
        packageVersion = plugin.getServer().getClass().getPackage().getName().split("\\.")[3];
        checkMappings();
    }

    /*
     * ughhhh
     */
    public void checkMappings() {
        isMapping_1_17 = false;
        if (!isVersion_1_7() && !isVersion_1_8() && !isVersion_1_13() && !isVersion_1_15()) {
            isMapping_1_17 = true;
        }
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

    public Type getOther(String classPath) {
        return Type.getType(String.format("L%s;", classPath));
    }

    public Type getNMS(String classPath) {
        if (isMapping_1_17) {
            return getNMS_1_17(classPath);
        }
        return getNMS_1_7(classPath);
    }

    public Type getNMS_1_17(String classPath) {
        return Type.getType(String.format("Lnet/minecraft/%s;", classPath));
    }

    public Type getNMS_1_7(String classPath) {
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
     */
    public Set<String> getParticles_1_8() {
        Class<?> enumClass = RefUtils.tryGetClass(getNMS("EnumParticle").getClassName());

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
     */
    public Set<String> getParticles_1_13() {
        Class<?> particleClass = RefUtils.tryGetClass(getNMS("Particle").getClassName());
        Class<?> particlesClass = RefUtils.tryGetClass(getNMS("Particles").getClassName());

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
     * <p>Returns particles name-to-field <code>Map</code> from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.17.</p>
     *
     * @return particles name-to-field <code>Map</code> from current server version.
     */
    public Map<String, String> getParticles_1_17() {
        Class<?> particleClass = RefUtils.tryGetClass(getNMS("core/particles/Particle").getClassName());
        Class<?> particlesClass = RefUtils.tryGetClass(getNMS("core/particles/Particles").getClassName());
        Class<?> iRegistryClass = RefUtils.tryGetClass(getNMS("core/IRegistry").getClassName());
        Class<?> minecraftKeyClass = RefUtils.tryGetClass(getNMS("resources/MinecraftKey").getClassName());

        String regGetKeyMethodName = RefUtils.tryInferMethodName(iRegistryClass, minecraftKeyClass, Object.class);
        Method regGetKeyMethod = RefUtils.tryGetMethod(iRegistryClass, regGetKeyMethodName, Object.class);

        Object particleRegistry = null;
        for (Field field : iRegistryClass.getDeclaredFields()) {
            // make sure field is of IRegistry class
            if (!iRegistryClass.isAssignableFrom(field.getType())) {
                continue;
            }

            java.lang.reflect.Type genericType = field.getGenericType();

            // make sure its generic
            if (!(genericType instanceof ParameterizedType)) {
                continue;
            }

            ParameterizedType pType = (ParameterizedType) genericType;
            java.lang.reflect.Type[] rawTypes = pType.getActualTypeArguments();

            // make sure it has only 1 generic parameter
            if (rawTypes.length != 1) {
                continue;
            }

            java.lang.reflect.Type rawType = rawTypes[0];

            // make sure parameter is also generic (Particle is generic)
            if (!(rawType instanceof ParameterizedType)) {
                continue;
            }

            pType = (ParameterizedType) rawType;
            rawType = pType.getRawType();

            // if it is Particle class, then get that static registry
            if (particleClass.isAssignableFrom((Class<?>) rawType)) {
                particleRegistry = RefUtils.tryGet(null, field);
                break;
            }
        }

        if (particleRegistry == null) {
            throw new ParticleException("Particle registry cannot be found.");
        }

        Map<String, String> currentParticlesMap = new HashMap<>();
        for (Field field : particlesClass.getFields()) {
            // make sure field is of Particle class
            if (!particleClass.isAssignableFrom(field.getType())) {
                continue;
            }

            String fieldName = field.getName();
            Object particleType = RefUtils.tryGet(null, field);

            Object mcKey = RefUtils.tryInvoke(particleRegistry, regGetKeyMethod, particleType);
            String fullParticleName = mcKey.toString();

            String particleName = fullParticleName.substring(fullParticleName.indexOf(":") + 1);
            currentParticlesMap.put(particleName, fieldName);
        }

        return currentParticlesMap;
    }

    /**
     * <p>Gets <code>PlayerConnection</code> field name in <code>EntityPlayer</code> class.</p>
     *
     * @return a <code>PlayerConnection</code> field name in <code>EntityPlayer</code> class.
     */
    public String getPlayerConnectionFieldName_1_17() {
        Class<?> entityPlayerClass = RefUtils.tryGetClass(getNMS_1_17("server/level/EntityPlayer").getClassName());
        Class<?> playerConnectionClass = RefUtils.tryGetClass(getNMS_1_17("server/network/PlayerConnection").getClassName());

        return RefUtils.tryInferFieldName(entityPlayerClass, playerConnectionClass);
    }

    /**
     * <p>Gets sendPacket method name in <code>PlayerConnection</code> class.</p>
     *
     * @return a sendPacket method name in <code>PlayerConnection</code> class.
     */
    public String getSendPacketMethodName_1_18() {
        Class<?> playerConnectionClass = RefUtils.tryGetClass(getNMS_1_17("server/network/PlayerConnection").getClassName());
        Class<?> packetClass = RefUtils.tryGetClass(getNMS_1_17("network/protocol/Packet").getClassName());

        return RefUtils.tryInferMethodName(playerConnectionClass, void.class, packetClass);
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.7 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.7 version, false otherwise.
     */
    public boolean isVersion_1_7() {
        try {
            Class.forName(getNMS_1_7("PacketPlayOutWorldParticles").getClassName()).getConstructor(
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
     * <p>Checks whenever current Spigot version is around MC 1.8 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.8 version, false otherwise.
     */
    public boolean isVersion_1_8() {
        try {
            Class.forName(getNMS_1_7("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS_1_7("EnumParticle").getClassName()), boolean.class,
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
     * <p>Checks whenever current Spigot version is around MC 1.13 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.13 version, false otherwise.
     */
    public boolean isVersion_1_13() {
        try {
            Class.forName(getNMS_1_7("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS_1_7("ParticleParam").getClassName()), boolean.class,
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
     * <p>Checks whenever current Spigot version is around MC 1.15 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.15 version, false otherwise.
     */
    public boolean isVersion_1_15() {
        try {
            Class.forName(getNMS_1_7("PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS_1_7("ParticleParam").getClassName()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.17 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.17 version, false otherwise.
     */
    public boolean isVersion_1_17() {
        try {
            Class.forName(getNMS_1_17("network/protocol/game/PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS_1_17("core/particles/ParticleParam").getClassName()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            Class<?> packetClass = Class.forName(getNMS_1_17("network/protocol/Packet").getClassName());
            Class.forName(getNMS_1_17("server/network/PlayerConnection").getClassName())
                    .getDeclaredMethod("sendPacket", packetClass);

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.18 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.18 version, false otherwise.
     */
    public boolean isVersion_1_18() {
        try {
            Class.forName(getNMS_1_17("network/protocol/game/PacketPlayOutWorldParticles").getClassName()).getConstructor(
                    Class.forName(getNMS_1_17("core/particles/ParticleParam").getClassName()), boolean.class,
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
