package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.ParticlesASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.Type;

public class ParticleNativeCore implements ParticleNativeAPI {

    private TempClassLoader cl;

    private ServerConnection serverConnection;
    private Particles_1_8 particles_1_8;
    private Particles_1_13 particles_1_13;

    ParticleNativeCore() {}

    /**
     * <p>Generates particle API based on current server version.</p>
     *
     * @param plugin plugin on which class generation should occur.
     *
     * @return a valid ParticleNativeAPI instance containing API implementations.
     *
     * @throws ParticleException if error occurred during classes generation.
     */
    public static ParticleNativeAPI loadAPI(JavaPlugin plugin)
            throws ParticleException {
        return new ParticleNativeCore().setupCore(plugin);
    }

    ParticleNativeAPI setupCore(JavaPlugin plugin) throws ParticleException {
        try {
            // obtain necessary info for class generation
            InternalResolver resolver = resolveInternals(plugin);

            cl = resolver.getTempClassLoader();

            // generates PlayerConnection implementation
            // generates ServerConnection implementation
            // generates ParticleType related classes implementation
            ParticlesASM pASM = new ParticlesASM(resolver);
            particles_1_8 = defineAndGet(
                    Particles_1_8.class,
                    pASM.generateParticles_1_8()
            );
            particles_1_13 = defineAndGet(
                    Particles_1_13.class,
                    pASM.generateParticles_1_13()
            );
            serverConnection = particles_1_13;

            return this;
        } catch (Exception e) {
            throw new ParticleException("Failed to load particle library.", e);
        }
    }

    /**
     * <p>Resolves internal NMS and OBC classes, provides temporary class loader
     * and provides utility methods for internal class access.</p>
     *
     * <p>This method is used in unit tests. This is the reason, why class generation
     * has been split into empty constructor and method loading API container
     * internals.</p>
     *
     * <p>By splitting, it is possible to both, mock package names (to match
     * fake classes in unit tests) and bind <code>TempClassLoader</code> to other
     * class loader</p>
     *
     * @param plugin a plugin from which data should be obtained.
     *
     * @return an <code>InternalResolver</code> with ready <code>TempClassLoader</code>
     * and prepared NMS/OBC package names.
     */
    InternalResolver resolveInternals(JavaPlugin plugin) {
        return new InternalResolver(plugin);
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

    @Override
    public Particles_1_8 getParticles_1_8() {
        return particles_1_8;
    }

    @Override
    public Particles_1_13 getParticles_1_13() {
        return particles_1_13;
    }

    @Override
    @Deprecated
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

}
