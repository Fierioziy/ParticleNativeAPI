package com.github.fierioziy;

import com.github.fierioziy.api.*;
import com.github.fierioziy.asm.ParticlesASM;
import com.github.fierioziy.asm.PlayerConnectionASM;
import com.github.fierioziy.asm.ServerConnectionASM;
import com.github.fierioziy.utils.TempClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.Type;

/**
 * <p>A main <code>JavaPlugin</code> instance.</p>
 *
 * <p>It is responsible for providing class implementation for
 * particle type list. If any error occurs during class
 * generation, a <code>isValid</code> method will return false
 * and any API access to this instance will
 * throw <code>IllegalStateException</code>.</p>
 *
 * <p>Therefore, before accessing any particles list you must
 * check, if plugin API is valid
 * by a <code>isValid</code> method call.</p>
 */
public class ParticleNativeAPI extends JavaPlugin {

    private static ParticleNativeAPI INSTANCE = null;

    private boolean isValid = false;

    /**
     * <p>A class loader instance used to define class implementations
     * by a bytecode generated using ASM.</p>
     */
    private TempClassLoader cl;

    /*
     * Dynamically generated implementations of interfaces.
     */
    private ServerConnection serverConnection;
    private Particles_1_8 particles_1_8;
    private Particles_1_13 particles_1_13;

    @Override
    public void onLoad() {
        INSTANCE = this;

        isValid = false;

        cl = new TempClassLoader(this.getClassLoader());

        try {
            // get package version
            String packageVersion = getServer().getClass().getPackage().getName().split("\\.")[3];

            // generate PlayerConnection implementation
            PlayerConnectionASM pcASM = new PlayerConnectionASM(packageVersion);
            define(PlayerConnection.class, pcASM.generatePlayerConnectionCode());

            // generate ServerConnection implementation
            ServerConnectionASM scASM = new ServerConnectionASM(packageVersion);
            serverConnection = defineAndGet(
                    ServerConnection.class,
                    scASM.generateServerConnectionCode()
            );

            // generate Particles interface implementations
            // and ParticleType related classes implementation
            ParticlesASM pASM = new ParticlesASM(packageVersion, cl);
            particles_1_8 = defineAndGet(
                    Particles_1_8.class,
                    pASM.generateParticles_1_8()
            );
            particles_1_13 = defineAndGet(
                    Particles_1_13.class,
                    pASM.generateParticles_1_13()
            );

            isValid = true;
        } catch (Exception e) {
            isValid = false;
            getLogger().severe("Failed to dynamically create class library using ASM.");
            getLogger().severe("This API might not be compatible with current server version.");
            getLogger().severe("If you suspect it is a bug, report it on issue tracker on plugin's github page with provided stacktrace:");
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        if (!isValid) {
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        INSTANCE = null;

        isValid = false;
        cl = null;
        serverConnection = null;
        particles_1_8 = null;
        particles_1_13 = null;
    }

    /**
     * <p>Gets this plugin's instance</p>
     * @return a <code>ParticleNativeAPI</code> plugin's instance.
     */
    public static ParticleNativeAPI getPlugin() {
        return INSTANCE;
    }

    /**
     * Checks if an API has been properly generated and
     * is ready for use.
     *
     * @return true if API has been successfully created, false otherwise.
     */
    public boolean isValid() {
        return isValid;
    }

    private <T> T defineAndGet(Class<T> clazz, byte[] code) throws Exception {
        return clazz.cast(
                define(clazz, code).getConstructor().newInstance()
        );
    }

    private Class<?> define(Class<?> clazz, byte[] code) {
        return cl.defineClass(
                Type.getType(clazz).getClassName() + "_Impl",
                code
        );
    }

    /**
     * <p>Gets instance of interface holding particle types
     * prior to 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from <code>Particles_1_13</code> should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isValid</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isValid</code> defined by all particle types in this interface.</p>
     *
     * <p>Otherwise, this method might throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>Particles_1_8</code> instance.
     * @throws IllegalStateException if error occurred during class generation.
     */
    public Particles_1_8 getParticles_1_8() {
        if (!isValid) {
            throw new IllegalStateException("Tried to obtain invalid Particles_1_8 instance.");
        }
        return particles_1_8;
    }

    /**
     * <p>Gets instance of interface holding particle types
     * since 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from <code>Particles_1_13</code> should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isValid</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isValid</code> defined by all particle types in this interface.</p>
     *
     * <p>Otherwise, this method will throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>Particles_1_13</code> instance.
     * @throws IllegalStateException if error occurred during class generation.
     */
    public Particles_1_13 getParticles_1_13() {
        if (!isValid) {
            throw new IllegalStateException("Tried to obtain invalid Particles_1_13 instance.");
        }
        return particles_1_13;
    }

    /**
     * <p>Gets instance of <code>ServerConnection</code>.</p>
     *
     * <p>You should check if API has been successfully generated
     * using <code>isValid</code> method.
     *
     * Otherwise, this method will throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>ServerConnection</code> instance.
     * @throws IllegalStateException if error occurred during class generation.
     * @deprecated use any particle list instead, it contains exact same functionality.
     */
    @Deprecated
    public ServerConnection getServerConnection() {
        if (!isValid) {
            throw new IllegalStateException("Tried to obtain invalid ServerConnection instance.");
        }
        return serverConnection;
    }

}
