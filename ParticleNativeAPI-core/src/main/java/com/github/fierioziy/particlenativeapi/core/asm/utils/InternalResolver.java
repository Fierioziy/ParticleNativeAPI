package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
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
    public final SpigotClassRegistry refs;

    public InternalResolver(ParticleNativeClassLoader classLoader, SpigotClassRegistry classRegistry) {
        this.classLoader = classLoader;
        this.refs = classRegistry;
    }

    public ParticleNativeClassLoader getParticleNativeClassLoader() {
        return classLoader;
    }

    /**
     * <p>Returns particles name {@link Set} from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.8.</p>
     *
     * @return particles name {@link Set} from current server version.
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
     * <p>Returns particles name {@link Set} from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.13.</p>
     *
     * @return particles name {@link Set} from current server version.
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
     * <p>Returns particles name-to-field {@link Map} from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.17.</p>
     *
     * @return particles name-to-field {@link Map} from current server version.
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
     * <p>Returns particles name-to-field {@link Map} from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.19.</p>
     *
     * @return particles name-to-field {@link Map} from current server version.
     */
    public Map<String, String> getParticles_1_19_3() {
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());
        Class<?> particlesClass = tryGetClass(refs.particles_1_17.className());

        Class<?> iRegistryClass = tryGetClass(refs.iRegistry_1_17.className());
        Class<?> builtInRegistriesClass = tryGetClass(refs.builtInRegistries.className());

        Class<?> minecraftKeyClass = tryGetClass(refs.minecraftKey_1_17.className());

        String regGetKeyMethodName = RefUtils.tryInferMethodName(iRegistryClass, minecraftKeyClass, Object.class);
        Method regGetKeyMethod = RefUtils.tryGetMethod(iRegistryClass, regGetKeyMethodName, Object.class);

        Object particleRegistry = null;
        for (Field field : builtInRegistriesClass.getDeclaredFields()) {
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
     * <p>Returns particles name-to-field {@link Map} from current server version.</p>
     *
     * <p>It attempts to access particle types using classes from MC 1.21.11.</p>
     *
     * @return particles name-to-field {@link Map} from current server version.
     */
    public Map<String, String> getParticles_1_21_11() {
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());
        Class<?> particlesClass = tryGetClass(refs.particles_1_17.className());

        Class<?> iRegistryClass = tryGetClass(refs.iRegistry_1_17.className());
        Class<?> builtInRegistriesClass = tryGetClass(refs.builtInRegistries.className());

        Class<?> minecraftKeyClass = tryGetClass(refs.minecraftKey_1_21_11.className());

        String regGetKeyMethodName = RefUtils.tryInferMethodName(iRegistryClass, minecraftKeyClass, Object.class);
        Method regGetKeyMethod = RefUtils.tryGetMethod(iRegistryClass, regGetKeyMethodName, Object.class);

        Object particleRegistry = null;
        for (Field field : builtInRegistriesClass.getDeclaredFields()) {
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
     * <p>Gets sendPacket method name in <code>PlayerConnection</code> class since 1.18.</p>
     *
     * @return a sendPacket method name in <code>PlayerConnection</code> class.
     */
    public String getSendPacketMethodName_1_18() {
        Class<?> playerConnectionClass = tryGetClass(refs.playerConnection_1_17.className());
        Class<?> packetClass = tryGetClass(refs.packet_1_17.className());

        return RefUtils.tryInferMethodName(playerConnectionClass, void.class, packetClass);
    }

    /**
     * <p>Gets sendPacket method name in <code>PlayerConnection</code> class since 1.20.2.</p>
     *
     * @return a sendPacket method name in <code>PlayerConnection</code> class.
     */
    public String getSendPacketMethodName_1_20_2() {
        Class<?> serverCommonPacketListenerImplClass = tryGetClass(refs.serverCommonPacketListenerImpl.className());
        Class<?> packetClass = tryGetClass(refs.packet_1_17.className());

        return RefUtils.tryInferMethodName(serverCommonPacketListenerImplClass, void.class, false, packetClass);
    }

    /**
     * <p>Gets ColorParticleOption method name that calls constructor in 1.20.5.</p>
     *
     * @return a method creating instance of ColorParticleOption.
     */
    public String getColorParticleOptionFactoryMethodName_1_20_5() {
        Class<?> colorParticleOptionClass = tryGetClass(refs.colorParticleOption.className());
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());

        return RefUtils.tryInferMethodName(colorParticleOptionClass,
                colorParticleOptionClass, particleClass, int.class);
    }

    /**
     * <p>Gets SpellParticleOption method name that calls constructor in 1.21.10.</p>
     *
     * @return a method creating instance of SpellParticleOption.
     */
    public String getSpellParticleOptionFactoryMethodName_1_21_10() {
        Class<?> spellParticleOptionClass = tryGetClass(refs.spellParticleOption.className());
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());

        return RefUtils.tryInferMethodName(spellParticleOptionClass,
                spellParticleOptionClass, particleClass, int.class, float.class);
    }

    /**
     * <p>Gets SpellParticleOption method name that calls constructor in 1.21.10.</p>
     *
     * @return a method creating instance of SpellParticleOption.
     */
    public String getPowerParticleOptionFactoryMethodName_1_21_10() {
        Class<?> powerParticleOptionClass = tryGetClass(refs.powerParticleOption.className());
        Class<?> particleClass = tryGetClass(refs.particle_1_17.className());

        return RefUtils.tryInferMethodName(powerParticleOptionClass,
                powerParticleOptionClass, particleClass, float.class);
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.7 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.7 version, false otherwise.
     */
    public boolean isVersion_1_7() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
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
            clazz(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    clazz(refs.enumParticle.className()), boolean.class,
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
            clazz(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    clazz(refs.particleParam_1_7.className()), boolean.class,
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
            clazz(refs.packetPlayOutWorldParticles_1_7.className()).getConstructor(
                    clazz(refs.particleParam_1_7.className()), boolean.class,
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
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );
            Class<?> packetClass = clazz(refs.packet_1_17.className());
            clazz(refs.playerConnection_1_17.className())
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
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationPath.className());

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
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3fa.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.19.3 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.19.3 version, false otherwise.
     */
    public boolean isVersion_1_19_3() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_18();

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.20.2 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.20.2 version, false otherwise.
     */
    public boolean isVersion_1_20_2() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            assertNotExists(refs.colorParticleOption.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.20.5 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.20.5 version, false otherwise.
     */
    public boolean isVersion_1_20_5() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            clazz(refs.colorParticleOption.className());
            clazz(refs.particleParamRedstone_1_17.className()).getConstructor(
                    clazz(refs.vector3f.className()), float.class
            );

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.21.3 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.21.3 version, false otherwise.
     */
    public boolean isVersion_1_21_3() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()), boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            clazz(refs.colorParticleOption.className());
            clazz(refs.particleParamRedstone_1_17.className()).getConstructor(
                    int.class, float.class
            );

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.21.4 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.20.2 version, false otherwise.
     */
    public boolean isVersion_1_21_4() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()),
                    boolean.class, boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            clazz(refs.colorParticleOption.className());
            clazz(refs.particleParamRedstone_1_17.className()).getConstructor(
                    int.class, float.class
            );
            assertNotExists(refs.spellParticleOption.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.21.10 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.20.10 version, false otherwise.
     */
    public boolean isVersion_1_21_10() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()),
                    boolean.class, boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            clazz(refs.colorParticleOption.className());
            clazz(refs.particleParamRedstone_1_17.className()).getConstructor(
                    int.class, float.class
            );
            clazz(refs.spellParticleOption.className());
            clazz(refs.minecraftKey_1_17.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    /**
     * <p>Checks whenever current Spigot version is around MC 1.21.11 version.</p>
     *
     * @return true if this Spigot version has constructor
     * from MC 1.20.11 version, false otherwise.
     */
    public boolean isVersion_1_21_11() {
        try {
            clazz(refs.packetPlayOutWorldParticles_1_17.className()).getConstructor(
                    clazz(refs.particleParam_1_17.className()),
                    boolean.class, boolean.class,
                    double.class, double.class, double.class,
                    float.class, float.class, float.class,
                    float.class, int.class
            );

            clazz(refs.vibrationParticleOption.className()).getConstructor(
                    clazz(refs.positionSource.className()), int.class
            );

            clazz(refs.vector3f.className());

            getSendPacketMethodName_1_20_2();
            clazz(refs.colorParticleOption.className());
            clazz(refs.particleParamRedstone_1_17.className()).getConstructor(
                    int.class, float.class
            );
            clazz(refs.spellParticleOption.className());
            clazz(refs.minecraftKey_1_21_11.className());

            return true;
        } catch (NoSuchMethodException | ClassNotFoundException | ParticleException e) {
            return false;
        }
    }

    private Class<?> tryGetClass(String className) {
        try {
            return clazz(className);
        } catch (ClassNotFoundException e) {
            throw new ParticleException(String.format(
                    "Class %s could not be found", className
            ));
        }
    }

    private void assertNotExists(String className) {
        try {
            clazz(className);
            throw new ParticleException(String.format(
                    "Class %s was found, but it shouldn't be present", className
            ));
        }
        catch (ClassNotFoundException ignored) {

        }
    }

    public Class<?> clazz(String className) throws ClassNotFoundException {
        return Class.forName(className, false, classLoader);
    }

}
