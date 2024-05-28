package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.*;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistryProvider;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistryProviderImpl;
import com.github.fierioziy.particlenativeapi.core.asm.particle.ParticleListProvider;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;
import com.github.fierioziy.particlenativeapi.core.utils.ParticleNativeClassLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class ParticleNativeCore {

    private final ParticleNativeClassLoader classLoader;
    private final SpigotClassRegistryProvider spigotClassRegistryProvider;

    ParticleNativeCore(ParticleNativeClassLoader classLoader,
                       SpigotClassRegistryProvider spigotClassRegistryProvider) {
        this.classLoader = classLoader;
        this.spigotClassRegistryProvider = spigotClassRegistryProvider;
    }

    /**
     * <p>Generates particle API based on current server version.</p>
     *
     * @param plugin plugin on which class generation should occur.
     * @return a valid {@link ParticleNativeAPI} instance containing API implementations.
     * @throws ParticleException if error occurred during classes generation.
     */
    public static ParticleNativeAPI loadAPI(JavaPlugin plugin)
            throws ParticleException {
        ClassLoader pluginClassLoader = plugin.getClass().getClassLoader();
        ParticleNativeClassLoader classLoader = new ParticleNativeClassLoader(pluginClassLoader);

        String serverPackageName = plugin.getServer().getClass().getPackage().getName();
        String[] split = serverPackageName.split("\\.");
        String packageVersion = split.length > 4 ? serverPackageName.split("\\.")[3] : "Unknown";

        SpigotClassRegistryProvider spigotClassRegistryProvider = new SpigotClassRegistryProviderImpl(packageVersion, serverPackageName);

        ParticleNativeCore core = new ParticleNativeCore(classLoader, spigotClassRegistryProvider);
        return core.setupCore().api;
    }

    GenerationResult setupCore() throws ParticleException {
        try {
            // obtain necessary info for class generation
            SpigotClassRegistry classRegistry = spigotClassRegistryProvider.provideRegistry();
            InternalResolver resolver = new InternalResolver(classLoader, classRegistry);

            ContextASM context = new ContextASM(resolver);

            /*
            Registers:
            - ParticlePacket implementation
            - ParticleType related class implementations
            - Particles list implementations
             */
            ParticleListProvider particleListProvider = new ParticleListProvider(context);
            particleListProvider.registerClasses();

            Constructor<?> particles_1_8_ctor = particleListProvider
                    .getParticleListASM_1_8()
                    .loadClass()
                    .getConstructor(ParticleNativeAPI.class);
            Constructor<?> particles_1_13_ctor = particleListProvider
                    .getParticleListASM_1_13()
                    .loadClass()
                    .getConstructor(ParticleNativeAPI.class);
            Constructor<?> particles_1_19_part_ctor = particleListProvider
                    .getParticleListASM_1_19_part()
                    .loadClass()
                    .getConstructor(ParticleNativeAPI.class);

            ParticleNativeAPI api = new ParticleNativeAPI_Impl(
                    particles_1_8_ctor,
                    particles_1_13_ctor,
                    particles_1_19_part_ctor);

            return new GenerationResult(api, context.currentVersion);
        } catch (Exception e) {
            throw new ParticleException("Failed to load particle library.", e);
        }
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
