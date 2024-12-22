package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.particle.type.CraftBlockDataMock;
import com.github.fierioziy.particlenativeapi.core.mocks.StaticMock;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;
import com.github.fierioziy.particlenativeapi.core.utils.ParticleNativeClassLoader;
import org.bukkit.Material;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParticleNativeCoreTest {

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;
    private static ParticleNativeAPI api_1_17;
    private static ParticleNativeAPI api_1_18;
    private static ParticleNativeAPI api_1_19;
    private static ParticleNativeAPI api_1_19_3;
    private static ParticleNativeAPI api_1_20_2;
    private static ParticleNativeAPI api_1_20_5;
    private static ParticleNativeAPI api_1_21_3;
    private static ParticleNativeAPI api_1_21_4;

    private static boolean initialized = false;

    /**
     * <p>Lazily initializes API on all MC versions.</p>
     *
     * <p>It can throw exceptions if class generation fails.</p>
     */
    private static void initializeAPI() {
        if (initialized) return;

        StaticMock.ofServer(serverMock -> {
            // prepare Bukkit class to return proper block data on calls
            CraftBlockData barrierBlockData = CraftBlockDataMock.of(Material.BARRIER);
            CraftBlockData lightBlockData = CraftBlockDataMock.of(Material.LIGHT);
            when(serverMock.createBlockData(Material.BARRIER)).thenReturn(barrierBlockData);
            when(serverMock.createBlockData(Material.LIGHT)).thenReturn(lightBlockData);

            api_1_7 = loadAPI_1_7();
            api_1_8 = loadAPI_1_8();
            api_1_13 = loadAPI_1_13();
            api_1_15 = loadAPI_1_15();
            api_1_17 = loadAPI_1_17();
            api_1_18 = loadAPI_1_18();
            api_1_19 = loadAPI_1_19();
            api_1_19_3 = loadAPI_1_19_3();
            api_1_20_2 = loadAPI_1_20_2();
            api_1_20_5 = loadAPI_1_20_5();
            api_1_21_3 = loadAPI_1_21_3();
            api_1_21_4 = loadAPI_1_21_4();
        });

        initialized = true;
    }

    /*
    API getters
     */

    public static ParticleNativeAPI getAPI_1_7() {
        initializeAPI();
        return api_1_7;
    }

    public static ParticleNativeAPI getAPI_1_8() {
        initializeAPI();
        return api_1_8;
    }

    public static ParticleNativeAPI getAPI_1_13() {
        initializeAPI();
        return api_1_13;
    }

    public static ParticleNativeAPI getAPI_1_15() {
        initializeAPI();
        return api_1_15;
    }

    public static ParticleNativeAPI getAPI_1_17() {
        initializeAPI();
        return api_1_17;
    }

    public static ParticleNativeAPI getAPI_1_18() {
        initializeAPI();
        return api_1_18;
    }

    public static ParticleNativeAPI getAPI_1_19() {
        initializeAPI();
        return api_1_19;
    }

    public static ParticleNativeAPI getAPI_1_19_3() {
        initializeAPI();
        return api_1_19_3;
    }

    public static ParticleNativeAPI getAPI_1_20_2() {
        initializeAPI();
        return api_1_20_2;
    }

    public static ParticleNativeAPI getAPI_1_20_5() {
        initializeAPI();
        return api_1_20_5;
    }

    public static ParticleNativeAPI getAPI_1_21_3() {
        initializeAPI();
        return api_1_21_3;
    }

    public static ParticleNativeAPI getAPI_1_21_4() {
        initializeAPI();
        return api_1_21_4;
    }

    /*
    API loading methods
     */

    private static ParticleNativeAPI loadAPI_1_7() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_7 classRegistryProvider = new SpigotClassRegistryProvider_1_7();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_7, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_8() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_8 classRegistryProvider = new SpigotClassRegistryProvider_1_8();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_8, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_13() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_13 classRegistryProvider = new SpigotClassRegistryProvider_1_13();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_13, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_15() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_15 classRegistryProvider = new SpigotClassRegistryProvider_1_15();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_15, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_17() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_17 classRegistryProvider = new SpigotClassRegistryProvider_1_17();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_17, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_18() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_18 classRegistryProvider = new SpigotClassRegistryProvider_1_18();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_18, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_19() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_19 classRegistryProvider = new SpigotClassRegistryProvider_1_19();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_19, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_19_3() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_19_3 classRegistryProvider = new SpigotClassRegistryProvider_1_19_3();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_19_3, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_20_2() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_20_2 classRegistryProvider = new SpigotClassRegistryProvider_1_20_2();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_20_2, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_20_5() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_20_5 classRegistryProvider = new SpigotClassRegistryProvider_1_20_5();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_20_5, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_21_3() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_21_3 classRegistryProvider = new SpigotClassRegistryProvider_1_21_3();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_21_3, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeAPI loadAPI_1_21_4() {
        ParticleNativeClassLoader classLoader = prepareProperClassLoader();
        SpigotClassRegistryProvider_1_21_4 classRegistryProvider = new SpigotClassRegistryProvider_1_21_4();

        ParticleNativeCore core = new ParticleNativeCore(classLoader, classRegistryProvider);
        ParticleNativeCore.GenerationResult generationResult = core.setupCore();

        assertEquals(SpigotVersion.V1_21_4, generationResult.spigotVersion);

        return generationResult.api;
    }

    private static ParticleNativeClassLoader prepareProperClassLoader() {
        return new ParticleNativeClassLoader(ParticleNativeCoreTest.class.getClassLoader());
    }

}
