package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.asm.particle.ParticlesProvider;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;
import com.github.fierioziy.particlenativeapi.core.utils.ParticleNativeClassLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticleNativeCore implements ParticleNativeAPI {

    private final ParticleNativeClassLoader classLoader;
    private final ClassRegistryProvider classRegistryProvider;

    private ServerConnection serverConnection;
    private Particles_1_8 particles_1_8;
    private Particles_1_13 particles_1_13;

    ParticleNativeCore(ParticleNativeClassLoader classLoader,
                       ClassRegistryProvider classRegistryProvider) {
        this.classLoader = classLoader;
        this.classRegistryProvider = classRegistryProvider;
    }

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
        ClassLoader pluginClassLoader = plugin.getClass().getClassLoader();
        ParticleNativeClassLoader classLoader = new ParticleNativeClassLoader(pluginClassLoader);

        String serverPackageName = plugin.getServer().getClass().getPackage().getName();
        String packageVersion = serverPackageName.split("\\.")[3];

        ClassRegistryProvider classRegistryProvider = new SpigotClassRegistryProvider(packageVersion);

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        return core.setupCore().api;
    }

    GenerationResult setupCore() throws ParticleException {
        try {
            // obtain necessary info for class generation
            ClassRegistry classRegistry = classRegistryProvider.provideRegistry();

            InternalResolver resolver = new InternalResolver(classLoader, classRegistry);

            ParticlesProvider particlesProvider = new ParticlesProvider(resolver);

            // generates PlayerConnection implementation
            // generates ServerConnection implementation
            // generates ParticleType related classes implementation
            particlesProvider.defineClasses();

            particles_1_8 = (Particles_1_8) particlesProvider
                    .getParticles_1_8_class()
                    .newInstance();
            particles_1_13 = (Particles_1_13) particlesProvider
                    .getParticles_1_13_class()
                    .newInstance();
            serverConnection = particles_1_13;

            return new GenerationResult(this, particlesProvider.getChosenVersion());
        } catch (Exception e) {
            throw new ParticleException("Failed to load particle library.", e);
        }
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

    static class GenerationResult {

        ParticleNativeAPI api;
        SpigotVersion spigotVersion;

        public GenerationResult(ParticleNativeAPI api, SpigotVersion spigotVersion) {
            this.api = api;
            this.spigotVersion = spigotVersion;
        }

    }

}
