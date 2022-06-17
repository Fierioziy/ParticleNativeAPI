package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.mocks.mojang.common.Vector3fa;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.EntityPlayer_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.PlayerConnection_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_v1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.VibrationParticleOption_1_19;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.EntityPlayer_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PlayerConnection_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PacketPlayOutWorldParticles_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.EnumParticle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.PacketPlayOutWorldParticles_1_8;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_17.entity.CraftPlayer_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_7.entity.CraftPlayer_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticleNativeCoreTest {

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;
    private static ParticleNativeAPI api_1_17;
    private static ParticleNativeAPI api_1_18;
    private static ParticleNativeAPI api_1_19;

    private static Server mockServer;

    private static boolean initialized = false;

    /**
     * <p>Lazily initializes API on all MC versions.</p>
     *
     * <p>It can throw exceptions if class generation fails.</p>
     */
    private static void initializeAPI() {
        if (initialized) return;

        mockServer = mock(Server.class);

        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            serverField.setAccessible(true);
            serverField.set(null, mockServer);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        api_1_7 = loadAPI_1_7();
        api_1_8 = loadAPI_1_8();
        api_1_13 = loadAPI_1_13();
        api_1_15 = loadAPI_1_15();
        api_1_17 = loadAPI_1_17();
        api_1_18 = loadAPI_1_18();
        api_1_19 = loadAPI_1_19();

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

    public static Server getMockedServer() {
        initializeAPI();
        return mockServer;
    }

    private static CraftBlockData mockCraftBlockData() {
        CraftBlockData mockCraftBlockData = mock(CraftBlockData.class);
        mockCraftBlockData.iBlockData = mock(IBlockData.class);
        when(mockCraftBlockData.getState()).thenCallRealMethod();

        return mockCraftBlockData;
    }

    /*
    Stubbing helper methods for MC 1.7
     */

    private static void registerExceptionOnMissedStub(InternalResolver internalMock) {
        lenient().doAnswer((Answer<String>) invocation -> {
            String path = invocation.getArgument(0);
            throw new RuntimeException("Unregistered NMS 1.7 stubbing: " + path);
        }).when(internalMock).getNMS_1_7(anyString());

        lenient().doAnswer((Answer<String>) invocation -> {
            String path = invocation.getArgument(0);
            throw new RuntimeException("Unregistered NMS 1.17 stubbing: " + path);
        }).when(internalMock).getNMS_1_17(anyString());

        lenient().doAnswer((Answer<String>) invocation -> {
            String path = invocation.getArgument(0);
            throw new RuntimeException("Unregistered OBC stubbing: " + path);
        }).when(internalMock).getOBC(anyString());

        lenient().doAnswer((Answer<String>) invocation -> {
            String path = invocation.getArgument(0);
            throw new RuntimeException("Unregistered Other stubbing: " + path);
        }).when(internalMock).getOther(anyString());
    }

    private static void registerStubNMS_1_7(InternalResolver internalMock,
                                            String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getNMS_1_7(classPath);
    }


    private static void registerStubNMS_1_17(InternalResolver internalMock,
                                             String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getNMS_1_17(classPath);
    }

    private static void registerStubOther(InternalResolver internalMock,
                                          String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getOther(classPath);
    }

    private static void registerStubOBC(InternalResolver internalMock,
                                        String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getOBC(classPath);
    }

    private static void registerFailNMS_1_7(InternalResolver internalMock, String classPath) {
        doReturn(Type.getType("LNonExistentClass;"))
                .when(internalMock).getNMS_1_7(classPath);
    }

    private static void registerFailNMS_1_17(InternalResolver internalMock, String classPath) {
        doReturn(Type.getType("LNonExistentClass;"))
                .when(internalMock).getNMS_1_17(classPath);
    }

    private static void registerFailOBC(InternalResolver internalMock, String classPath) {
        doReturn(Type.getType("LNonExistentClass;"))
                .when(internalMock).getOBC(classPath);
    }

    /*
    API loading methods
     */

    private static ParticleNativeAPI loadAPI_1_7() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS
        registerStubNMS_1_7(internalMock, "EntityPlayer", EntityPlayer_1_7.class);
        registerStubNMS_1_7(internalMock, "Packet", Packet.class);
        registerStubNMS_1_7(internalMock, "PlayerConnection", PlayerConnection_1_7.class);

        registerStubNMS_1_7(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_7.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_7.class);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, never()).isVersion_1_8();
        verify(internalMock, never()).isVersion_1_13();
        verify(internalMock, never()).isVersion_1_15();
        verify(internalMock, never()).isVersion_1_17();
        verify(internalMock, never()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_8() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS
        registerStubNMS_1_7(internalMock, "EntityPlayer", EntityPlayer_1_7.class);
        registerStubNMS_1_7(internalMock, "Packet", Packet.class);
        registerStubNMS_1_7(internalMock, "PlayerConnection", PlayerConnection_1_7.class);

        registerStubNMS_1_7(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_8.class);
        registerStubNMS_1_7(internalMock, "EnumParticle", EnumParticle.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_7.class);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, never()).isVersion_1_13();
        verify(internalMock, never()).isVersion_1_15();
        verify(internalMock, never()).isVersion_1_17();
        verify(internalMock, never()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_13() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS
        registerStubNMS_1_7(internalMock, "EntityPlayer", EntityPlayer_1_7.class);
        registerStubNMS_1_7(internalMock, "Packet", Packet.class);
        registerStubNMS_1_7(internalMock, "PlayerConnection", PlayerConnection_1_7.class);

        registerStubNMS_1_7(internalMock, "ItemStack", ItemStack.class);
        registerStubNMS_1_7(internalMock, "IBlockData", IBlockData.class);
        registerStubNMS_1_7(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_13.class);
        registerStubNMS_1_7(internalMock, "Particle", Particle.class);
        registerStubNMS_1_7(internalMock, "ParticleParam", ParticleParam.class);
        registerStubNMS_1_7(internalMock, "ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS_1_7(internalMock, "ParticleParamItem", ParticleParamItem.class);
        registerStubNMS_1_7(internalMock, "ParticleParamRedstone", ParticleParamRedstone_1_13.class);
        registerStubNMS_1_7(internalMock, "Particles", Particles_v1_13.class);
        registerStubNMS_1_7(internalMock, "ParticleType", ParticleType.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_7.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isVersion_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS_1_7(internalMock, "EnumParticle");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, atLeastOnce()).isVersion_1_13();
        verify(internalMock, never()).isVersion_1_15();
        verify(internalMock, never()).isVersion_1_17();
        verify(internalMock, never()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_15() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS
        registerStubNMS_1_7(internalMock, "EntityPlayer", EntityPlayer_1_7.class);
        registerStubNMS_1_7(internalMock, "Packet", Packet.class);
        registerStubNMS_1_7(internalMock, "PlayerConnection", PlayerConnection_1_7.class);

        registerStubNMS_1_7(internalMock, "ItemStack", ItemStack.class);
        registerStubNMS_1_7(internalMock, "IBlockData", IBlockData.class);
        registerStubNMS_1_7(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_15.class);
        registerStubNMS_1_7(internalMock, "Particle", Particle.class);
        registerStubNMS_1_7(internalMock, "ParticleParam", ParticleParam.class);
        registerStubNMS_1_7(internalMock, "ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS_1_7(internalMock, "ParticleParamItem", ParticleParamItem.class);
        registerStubNMS_1_7(internalMock, "ParticleParamRedstone", ParticleParamRedstone_1_13.class);
        registerStubNMS_1_7(internalMock, "Particles", Particles_v1_13.class);
        registerStubNMS_1_7(internalMock, "ParticleType", ParticleType.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_7.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isVersion_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS_1_7(internalMock, "EnumParticle");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, atLeastOnce()).isVersion_1_13();
        verify(internalMock, atLeastOnce()).isVersion_1_15();
        verify(internalMock, never()).isVersion_1_17();
        verify(internalMock, never()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_17() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS prior to 1.17
        registerFailNMS_1_7(internalMock, "EntityPlayer");
        registerFailNMS_1_7(internalMock, "Packet");
        registerFailNMS_1_7(internalMock, "PlayerConnection");

        registerFailNMS_1_7(internalMock, "ItemStack");
        registerFailNMS_1_7(internalMock, "IBlockData");
        registerFailNMS_1_7(internalMock, "PacketPlayOutWorldParticles");
        registerFailNMS_1_7(internalMock, "Particle");
        registerFailNMS_1_7(internalMock, "ParticleParam");
        registerFailNMS_1_7(internalMock, "ParticleParamBlock");
        registerFailNMS_1_7(internalMock, "ParticleParamItem");
        registerFailNMS_1_7(internalMock, "ParticleParamRedstone");
        registerFailNMS_1_7(internalMock, "Particles");
        registerFailNMS_1_7(internalMock, "ParticleType");

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_7.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isVersion_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS_1_7(internalMock, "EnumParticle");

        // NMS since 1.17
        registerStubNMS_1_17(internalMock, "server/level/EntityPlayer", EntityPlayer_1_7.class);
        registerStubNMS_1_17(internalMock, "network/protocol/Packet", Packet.class);
        registerStubNMS_1_17(internalMock, "server/network/PlayerConnection", PlayerConnection_1_7.class);

        registerStubNMS_1_17(internalMock, "world/item/ItemStack", ItemStack.class);
        registerStubNMS_1_17(internalMock, "world/level/block/state/IBlockData", IBlockData.class);
        registerStubNMS_1_17(internalMock, "network/protocol/game/PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_15.class);
        registerStubNMS_1_17(internalMock, "core/particles/Particle", Particle.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParam", ParticleParam.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamItem", ParticleParamItem.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamRedstone", ParticleParamRedstone_1_17.class);
        registerStubNMS_1_17(internalMock, "core/particles/DustColorTransitionOptions", DustColorTransitionOptions.class);

        registerStubNMS_1_17(internalMock, "core/particles/VibrationParticleOption", VibrationParticleOption_1_17.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/vibrations/VibrationPath", VibrationPath.class);
        registerStubNMS_1_17(internalMock, "core/BlockPosition", BlockPosition.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/PositionSource", PositionSource.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/BlockPositionSource", BlockPositionSource.class);

        registerStubNMS_1_17(internalMock, "core/particles/Particles", Particles_v1_17.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleType", ParticleType.class);

        registerStubNMS_1_17(internalMock, "resources/MinecraftKey", MinecraftKey.class);
        registerStubNMS_1_17(internalMock, "core/IRegistry", IRegistry.class);

        // others
        registerStubOther(internalMock, "com/mojang/math/Vector3fa", Vector3fa.class);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, atLeastOnce()).isVersion_1_13();
        verify(internalMock, atLeastOnce()).isVersion_1_15();
        verify(internalMock, atLeastOnce()).isVersion_1_17();
        verify(internalMock, never()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_18() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS prior to 1.17
        registerFailNMS_1_7(internalMock, "EntityPlayer");
        registerFailNMS_1_7(internalMock, "Packet");
        registerFailNMS_1_7(internalMock, "PlayerConnection");

        registerFailNMS_1_7(internalMock, "ItemStack");
        registerFailNMS_1_7(internalMock, "IBlockData");
        registerFailNMS_1_7(internalMock, "PacketPlayOutWorldParticles");
        registerFailNMS_1_7(internalMock, "Particle");
        registerFailNMS_1_7(internalMock, "ParticleParam");
        registerFailNMS_1_7(internalMock, "ParticleParamBlock");
        registerFailNMS_1_7(internalMock, "ParticleParamItem");
        registerFailNMS_1_7(internalMock, "ParticleParamRedstone");
        registerFailNMS_1_7(internalMock, "Particles");
        registerFailNMS_1_7(internalMock, "ParticleType");

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_18.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isVersion_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS_1_7(internalMock, "EnumParticle");

        // NMS since 1.17
        registerStubNMS_1_17(internalMock, "server/level/EntityPlayer", EntityPlayer_1_18.class);
        registerStubNMS_1_17(internalMock, "network/protocol/Packet", Packet.class);
        registerStubNMS_1_17(internalMock, "server/network/PlayerConnection", PlayerConnection_1_18.class);

        registerStubNMS_1_17(internalMock, "world/item/ItemStack", ItemStack.class);
        registerStubNMS_1_17(internalMock, "world/level/block/state/IBlockData", IBlockData.class);
        registerStubNMS_1_17(internalMock, "network/protocol/game/PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_15.class);
        registerStubNMS_1_17(internalMock, "core/particles/Particle", Particle.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParam", ParticleParam.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamItem", ParticleParamItem.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamRedstone", ParticleParamRedstone_1_17.class);
        registerStubNMS_1_17(internalMock, "core/particles/DustColorTransitionOptions", DustColorTransitionOptions.class);

        registerStubNMS_1_17(internalMock, "core/particles/VibrationParticleOption", VibrationParticleOption_1_17.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/vibrations/VibrationPath", VibrationPath.class);
        registerStubNMS_1_17(internalMock, "core/BlockPosition", BlockPosition.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/PositionSource", PositionSource.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/BlockPositionSource", BlockPositionSource.class);

        registerStubNMS_1_17(internalMock, "core/particles/Particles", Particles_v1_18.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleType", ParticleType.class);

        registerStubNMS_1_17(internalMock, "resources/MinecraftKey", MinecraftKey.class);
        registerStubNMS_1_17(internalMock, "core/IRegistry", IRegistry.class);

        // others
        registerStubOther(internalMock, "com/mojang/math/Vector3fa", Vector3fa.class);

        // prepare Bukkit class to return proper block data on calls
        CraftBlockData barrierBlockData = mockCraftBlockData();
        CraftBlockData lightBlockData = mockCraftBlockData();
        when(mockServer.createBlockData(Material.BARRIER)).thenReturn(barrierBlockData);
        when(mockServer.createBlockData(Material.LIGHT)).thenReturn(lightBlockData);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, atLeastOnce()).isVersion_1_13();
        verify(internalMock, atLeastOnce()).isVersion_1_15();
        verify(internalMock, atLeastOnce()).isVersion_1_17();
        verify(internalMock, atLeastOnce()).isVersion_1_18();
        verify(internalMock, never()).isVersion_1_19();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_19() {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);

        // NMS prior to 1.17
        registerFailNMS_1_7(internalMock, "EntityPlayer");
        registerFailNMS_1_7(internalMock, "Packet");
        registerFailNMS_1_7(internalMock, "PlayerConnection");

        registerFailNMS_1_7(internalMock, "ItemStack");
        registerFailNMS_1_7(internalMock, "IBlockData");
        registerFailNMS_1_7(internalMock, "PacketPlayOutWorldParticles");
        registerFailNMS_1_7(internalMock, "Particle");
        registerFailNMS_1_7(internalMock, "ParticleParam");
        registerFailNMS_1_7(internalMock, "ParticleParamBlock");
        registerFailNMS_1_7(internalMock, "ParticleParamItem");
        registerFailNMS_1_7(internalMock, "ParticleParamRedstone");
        registerFailNMS_1_7(internalMock, "Particles");
        registerFailNMS_1_7(internalMock, "ParticleType");

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer_1_18.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isVersion_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS_1_7(internalMock, "EnumParticle");

        // make isVersion_1_18 return false
        // by failing to find MC 1.18 classes
        registerFailNMS_1_17(internalMock, "world/level/gameevent/vibrations/VibrationPath");

        // NMS since 1.17
        registerStubNMS_1_17(internalMock, "server/level/EntityPlayer", EntityPlayer_1_18.class);
        registerStubNMS_1_17(internalMock, "network/protocol/Packet", Packet.class);
        registerStubNMS_1_17(internalMock, "server/network/PlayerConnection", PlayerConnection_1_18.class);

        registerStubNMS_1_17(internalMock, "world/item/ItemStack", ItemStack.class);
        registerStubNMS_1_17(internalMock, "world/level/block/state/IBlockData", IBlockData.class);
        registerStubNMS_1_17(internalMock, "network/protocol/game/PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_15.class);
        registerStubNMS_1_17(internalMock, "core/particles/Particle", Particle.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParam", ParticleParam.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamItem", ParticleParamItem.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleParamRedstone", ParticleParamRedstone_1_17.class);
        registerStubNMS_1_17(internalMock, "core/particles/DustColorTransitionOptions", DustColorTransitionOptions.class);

        registerStubNMS_1_17(internalMock, "core/particles/VibrationParticleOption", VibrationParticleOption_1_19.class);
        registerStubNMS_1_17(internalMock, "core/BlockPosition", BlockPosition.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/PositionSource", PositionSource.class);
        registerStubNMS_1_17(internalMock, "world/level/gameevent/BlockPositionSource", BlockPositionSource.class);

        registerStubNMS_1_17(internalMock, "core/particles/Particles", Particles_v1_18.class);
        registerStubNMS_1_17(internalMock, "core/particles/ParticleType", ParticleType.class);

        registerStubNMS_1_17(internalMock, "resources/MinecraftKey", MinecraftKey.class);
        registerStubNMS_1_17(internalMock, "core/IRegistry", IRegistry.class);

        // others
        registerStubOther(internalMock, "com/mojang/math/Vector3fa", Vector3fa.class);

        // prepare Bukkit class to return proper block data on calls
        CraftBlockData barrierBlockData = mockCraftBlockData();
        CraftBlockData lightBlockData = mockCraftBlockData();
        when(mockServer.createBlockData(Material.BARRIER)).thenReturn(barrierBlockData);
        when(mockServer.createBlockData(Material.LIGHT)).thenReturn(lightBlockData);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock, atLeastOnce()).isVersion_1_7();
        verify(internalMock, atLeastOnce()).isVersion_1_8();
        verify(internalMock, atLeastOnce()).isVersion_1_13();
        verify(internalMock, atLeastOnce()).isVersion_1_15();
        verify(internalMock, atLeastOnce()).isVersion_1_17();
        verify(internalMock, atLeastOnce()).isVersion_1_18();
        verify(internalMock, atLeastOnce()).isVersion_1_19();

        return api;
    }

    /*
    Test expected exceptions
     */

    @Test(expected = ParticleException.class)
    public void testExceptionOnUnknownServerVersion() throws Throwable {
        /*
        Prepare InternalResolver
         */
        final InternalResolver internalMock = spy(InternalResolver.class);

        // give InternalResolver proper TempClassLoader
        internalMock.setTempClassLoader(
                new TempClassLoader(ParticleNativeCoreTest.class.getClassLoader())
        );

        // make InternalResolver return Type objects representing fake NMS/OBC classes
        // if TempClassLoader throws any errors during class defining, then there are
        // some mistakes in the code generation classes related to names/descriptors
        // of classes/methods/fields etc.
        registerExceptionOnMissedStub(internalMock);
        // make isVersion methods return false
        // by failing to find their classes
        registerFailNMS_1_7(internalMock, "PacketPlayOutWorldParticles");
        registerFailNMS_1_17(internalMock, "network/protocol/game/PacketPlayOutWorldParticles");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doAnswer((Answer<InternalResolver>) invocation -> {
            internalMock.checkMappings();
            return internalMock;
        }).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver, it should fail internally
        // by throwing ParticleException (any other type = class generation fail)
        try {
            core.setupCore(mock(JavaPlugin.class));
        } catch (ParticleException e) {
            throw e.getCause();
        }
    }

}
