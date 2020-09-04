package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.EntityPlayer;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.ItemStack;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.PlayerConnection;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.*;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_15.PacketPlayOutWorldParticles_1_15;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PacketPlayOutWorldParticles_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.EnumParticle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_8.PacketPlayOutWorldParticles_1_8;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.common.entity.CraftPlayer;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.block.data.CraftBlockData;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_13.inventory.CraftItemStack;
import com.github.fierioziy.particlenativeapi.core.utils.TempClassLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.objectweb.asm.Type;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParticleNativeCoreTest {

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;

    private static boolean initialized = false;

    /**
     * Lazily initializes API on all MC versions
     *
     * It can throw exceptions if class generation fails.
     */
    private static void initializeAPI() {
        if (initialized) return;

        api_1_7 = loadAPI_1_7();
        api_1_8 = loadAPI_1_8();
        api_1_13 = loadAPI_1_13();
        api_1_15 = loadAPI_1_15();

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

    /*
    Stubbing helper methods
     */

    private static void registerExceptionOnMissedStub(InternalResolver internalMock) {
        lenient().doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) {
                String path = invocation.getArgument(0);
                throw new RuntimeException("Unregistered NMS stubbing: " + path);
            }
        }).when(internalMock).getNMS(anyString());

        lenient().doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) {
                String path = invocation.getArgument(0);
                throw new RuntimeException("Unregistered OBC stubbing: " + path);
            }
        }).when(internalMock).getOBC(anyString());
    }

    private static void registerStubNMS(InternalResolver internalMock,
                                        String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getNMS(classPath);
    }

    private static void registerStubOBC(InternalResolver internalMock,
                                        String classPath, Class<?> returnClass) {
        doReturn(Type.getType(returnClass))
                .when(internalMock).getOBC(classPath);
    }

    private static void registerFailNMS(InternalResolver internalMock, String classPath) {
        doReturn(Type.getType("LNonExistentClass;"))
                .when(internalMock).getNMS(classPath);
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
        InternalResolver internalMock = spy(InternalResolver.class);

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
        registerStubNMS(internalMock, "EntityPlayer", EntityPlayer.class);
        registerStubNMS(internalMock, "Packet", Packet.class);
        registerStubNMS(internalMock, "PlayerConnection", PlayerConnection.class);

        registerStubNMS(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_7.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer.class);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doReturn(internalMock).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock).isPacketConstructor_1_7();
        verify(internalMock, never()).isPacketConstructor_1_8();
        verify(internalMock, never()).isPacketConstructor_1_13();
        verify(internalMock, never()).isPacketConstructor_1_15();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_8() {
        /*
        Prepare InternalResolver
         */
        InternalResolver internalMock = spy(InternalResolver.class);

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
        registerStubNMS(internalMock, "EntityPlayer", EntityPlayer.class);
        registerStubNMS(internalMock, "Packet", Packet.class);
        registerStubNMS(internalMock, "PlayerConnection", PlayerConnection.class);

        registerStubNMS(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_8.class);
        registerStubNMS(internalMock, "EnumParticle", EnumParticle.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer.class);

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doReturn(internalMock).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock).isPacketConstructor_1_7();
        verify(internalMock).isPacketConstructor_1_8();
        verify(internalMock, never()).isPacketConstructor_1_13();
        verify(internalMock, never()).isPacketConstructor_1_15();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_13() {
        /*
        Prepare InternalResolver
         */
        InternalResolver internalMock = spy(InternalResolver.class);

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
        registerStubNMS(internalMock, "EntityPlayer", EntityPlayer.class);
        registerStubNMS(internalMock, "Packet", Packet.class);
        registerStubNMS(internalMock, "PlayerConnection", PlayerConnection.class);

        registerStubNMS(internalMock, "ItemStack", ItemStack.class);
        registerStubNMS(internalMock, "IBlockData", IBlockData.class);
        registerStubNMS(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_13.class);
        registerStubNMS(internalMock, "Particle", Particle.class);
        registerStubNMS(internalMock, "ParticleParam", ParticleParam.class);
        registerStubNMS(internalMock, "ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS(internalMock, "ParticleParamItem", ParticleParamItem.class);
        registerStubNMS(internalMock, "ParticleParamRedstone", ParticleParamRedstone.class);
        registerStubNMS(internalMock, "Particles", Particles.class);
        registerStubNMS(internalMock, "ParticleType", ParticleType.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isPacketConstructor_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS(internalMock, "EnumParticle");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doReturn(internalMock).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock).isPacketConstructor_1_7();
        verify(internalMock).isPacketConstructor_1_8();
        verify(internalMock).isPacketConstructor_1_13();
        verify(internalMock, never()).isPacketConstructor_1_15();

        return api;
    }

    private static ParticleNativeAPI loadAPI_1_15() {
        /*
        Prepare InternalResolver
         */
        InternalResolver internalMock = spy(InternalResolver.class);

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
        registerStubNMS(internalMock, "EntityPlayer", EntityPlayer.class);
        registerStubNMS(internalMock, "Packet", Packet.class);
        registerStubNMS(internalMock, "PlayerConnection", PlayerConnection.class);

        registerStubNMS(internalMock, "ItemStack", ItemStack.class);
        registerStubNMS(internalMock, "IBlockData", IBlockData.class);
        registerStubNMS(internalMock, "PacketPlayOutWorldParticles", PacketPlayOutWorldParticles_1_15.class);
        registerStubNMS(internalMock, "Particle", Particle.class);
        registerStubNMS(internalMock, "ParticleParam", ParticleParam.class);
        registerStubNMS(internalMock, "ParticleParamBlock", ParticleParamBlock.class);
        registerStubNMS(internalMock, "ParticleParamItem", ParticleParamItem.class);
        registerStubNMS(internalMock, "ParticleParamRedstone", ParticleParamRedstone.class);
        registerStubNMS(internalMock, "Particles", Particles.class);
        registerStubNMS(internalMock, "ParticleType", ParticleType.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer.class);

        registerStubOBC(internalMock, "block/data/CraftBlockData", CraftBlockData.class);
        registerStubOBC(internalMock, "inventory/CraftItemStack", CraftItemStack.class);

        // make isPacketConstructor_1_8 return false
        // by failing to find MC 1.8 classes
        registerFailNMS(internalMock, "EnumParticle");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doReturn(internalMock).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver
        ParticleNativeAPI api = core.setupCore(mock(JavaPlugin.class));

        // make sure that correct generation path has been chosen
        verify(internalMock).isPacketConstructor_1_7();
        verify(internalMock).isPacketConstructor_1_8();
        verify(internalMock).isPacketConstructor_1_13();
        verify(internalMock).isPacketConstructor_1_15();

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
        InternalResolver internalMock = spy(InternalResolver.class);

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
        registerStubNMS(internalMock, "EntityPlayer", EntityPlayer.class);
        registerStubNMS(internalMock, "Packet", Packet.class);
        registerStubNMS(internalMock, "PlayerConnection", PlayerConnection.class);

        // OBC
        registerStubOBC(internalMock, "entity/CraftPlayer", CraftPlayer.class);

        // make isPacketConstructor methods return false
        // by failing to find their classes
        registerFailNMS(internalMock, "PacketPlayOutWorldParticles");
        registerFailNMS(internalMock, "EnumParticle");
        registerFailNMS(internalMock, "ParticleParam");

        /*
        Prepare ParticleNativeCore
         */
        ParticleNativeCore core = spy(new ParticleNativeCore());

        // return mock InternalResolver to intercept class generation
        doReturn(internalMock).when(core).resolveInternals(any(JavaPlugin.class));

        // try to generate API using mock InternalResolver, it should fail internally
        // by throwing ParticleException (any other type = class generation fail)
        try {
            core.setupCore(mock(JavaPlugin.class));
        } catch (ParticleException e) {
            throw e.getCause();
        }
    }

}
