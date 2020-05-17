package me.fierioziy;

import me.fierioziy.api.Particles_1_8;
import me.fierioziy.api.Particles_1_13;
import me.fierioziy.api.PlayerConnection;
import me.fierioziy.api.ServerConnection;
import me.fierioziy.asm.ParticlesASM;
import me.fierioziy.asm.ServerConnectionASM;
import me.fierioziy.utils.*;
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

    private static ParticleNativeAPI instance = null;

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
        instance = this;

        isValid = false;

        cl = new TempClassLoader(this.getClassLoader());

        try {
            String packageVersion = getServer().getClass().getPackage().getName().split("\\.")[3];

            ServerConnectionASM scASM = new ServerConnectionASM(packageVersion);

            define(PlayerConnection.class, scASM.createPlayerConnection());
            serverConnection = defineAndGet(
                    ServerConnection.class,
                    scASM.createServerConnection()
            );

            ParticlesASM ptASM = new ParticlesASM(packageVersion, cl);

            particles_1_8 = defineAndGet(
                    Particles_1_8.class,
                    ptASM.createParticles_1_8()
            );
            particles_1_13 = defineAndGet(
                    Particles_1_13.class,
                    ptASM.createParticles_1_13()
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
        instance = null;

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
        return instance;
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
     * <p>All particle list interfaces holds same particle types
     * where possible (for ex. FLAME particle from this instance should also be present
     * in other particle list version if it is same particle type or if particle type
     * handling haven't changed significantly.</p>
     *
     * <p>You should check if API has been successfully generated
     * using <code>isValid</code> method.</p>
     *
     * <p>Otherwise, this method might throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>Particles_1_8</code> instance.
     * @throws IllegalStateException if error occured during class generation.
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
     * <p>All particle list interfaces holds same particle types
     * where possible (for ex. FLAME particle from this instance should also be present
     * in other particle list version if it is same particle type or if particle type
     * handling haven't changed significantly.</p>
     *
     * <p>You should check if API has been successfully generated
     * using <code>isValid</code> method.
     * Otherwise, this method will throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>Particles_1_13</code> instance.
     * @throws IllegalStateException if error occured during class generation.
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
     * Otherwise, this method will throw <code>IllegalStateException</code> if class
     * generation failed.</p>
     *
     * @return a valid <code>ServerConnection</code> instance.
     * @throws IllegalStateException if error occured during class generation.
     */
    public ServerConnection getServerConnection() {
        if (!isValid) {
            throw new IllegalStateException("Tried to obtain invalid ServerConnection instance.");
        }
        return serverConnection;
    }

}
