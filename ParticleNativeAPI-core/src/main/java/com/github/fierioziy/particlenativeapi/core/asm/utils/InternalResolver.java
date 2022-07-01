package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistry;
import com.github.fierioziy.particlenativeapi.core.utils.ParticleNativeClassLoader;

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

    private final ParticleNativeClassLoader classLoader;
    public final ClassRegistry refs;

    public InternalResolver(ParticleNativeClassLoader classLoader, ClassRegistry classRegistry) {
        this.classLoader = classLoader;
        this.refs = classRegistry;
    }

    public ParticleNativeClassLoader getParticleNativeClassLoader() {
        return classLoader;
    }

    /**
     * <p>Returns particles name <code>Set</code> from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.8.</p>
     *
     * @return particles name <code>Set</code> from current server version.
     */
    public Set<String> getParticles_1_8() {
        Class<?> enumClass = tryGetClass(refs.enumParticle.className());

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
        Class<?> particleClass = tryGetClass(refs.particle_1_7.className());
        Class<?> particlesClass = tryGetClass(refs.particlesNms_1_7.className());

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
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());
        Class<?> particlesClass = tryGetClass(refs.particles_1_17.className());
        Class<?> iRegistryClass = tryGetClass(refs.iRegistry_1_17.className());
        Class<?> minecraftKeyClass = tryGetClass(refs.minecraftKey_1_17.className());

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
        Class<?> entityPlayerClass = tryGetClass(refs.entityPlayer_1_17.className());
        Class<?> playerConnectionClass = tryGetClass(refs.playerConnection_1_17.className());

        return RefUtils.tryInferFieldName(entityPlayerClass, playerConnectionClass);
    }

    /**
     * <p>Gets sendPacket method name in <code>PlayerConnection</code> class.</p>
     *
     * @return a sendPacket method name in <code>PlayerConnection</code> class.
     */
    public String getSendPacketMethodName_1_18() {
        Class<?> playerConnectionClass = tryGetClass(refs.playerConnection_1_17.className());
        Class<?> packetClass = tryGetClass(refs.packet_1_17.className());

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
            Class.forName(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
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
            Class.forName(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    Class.forName(refs.enumParticle.className()), boolean.class,
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
            Class.forName(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    Class.forName(refs.particleParam_1_7.className()), boolean.class,
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
            Class.forName(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    Class.forName(refs.particleParam_1_7.className()), boolean.class,
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
            Class.forName(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    Class.forName(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            Class<?> packetClass = Class.forName(refs.packet_1_17.className());
            Class.forName(refs.playerConnection_1_17.className())
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
            Class.forName(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    Class.forName(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            Class.forName(refs.vibrationPath.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.19 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.19 version, false otherwise.
     */
    public boolean isVersion_1_19() {
        try {
            Class.forName(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    Class.forName(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            Class.forName(refs.vibrationParticleOption.className()).getConstructor(
                    Class.forName(refs.positionSource.className()), int.class
            );

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    public Class<?> tryGetClass(String className) {
        try {
            return Class.forName(className, false, classLoader);
        } catch (ClassNotFoundException e) {
            throw new ParticleException(String.format(
                    "Class %s could not be found", className
            ));
        }
    }

}
