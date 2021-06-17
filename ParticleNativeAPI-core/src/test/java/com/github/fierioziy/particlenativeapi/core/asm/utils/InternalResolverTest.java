package com.github.fierioziy.particlenativeapi.core.asm.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InternalResolverTest {

    @Spy
    private InternalResolver internal = new InternalResolver();

    @Before
    public void prepare() {
        doReturn("v_someVersion").when(internal).getPackageVersion();
    }

    /*
    Simple tests prior to MC 1.17
     */

    @Test
    public void test_getNMS_Simple_1_7() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(true).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("net/minecraft/server/v_someVersion/XD",
                internal.getNMS("XD").getInternalName());
    }

    @Test
    public void test_getOBC_Simple_1_7() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(true).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("org/bukkit/craftbukkit/v_someVersion/XD",
                internal.getOBC("XD").getInternalName());
    }

    @Test
    public void test_getNMS_Extended_1_7() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(true).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("net/minecraft/server/v_someVersion/XD/DDDD",
                internal.getNMS("XD/DDDD").getInternalName());
    }

    @Test
    public void test_getOBC_Extended_1_7() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(true).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("org/bukkit/craftbukkit/v_someVersion/XD/DDDD",
                internal.getOBC("XD/DDDD").getInternalName());
    }

    /*
    Simple tests since MC 1.17
     */

    @Test
    public void test_getNMS_Simple_1_17() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(false).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("net/minecraft/XD",
                internal.getNMS("XD").getInternalName());
    }

    @Test
    public void test_getOBC_Simple_1_17() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(false).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("org/bukkit/craftbukkit/v_someVersion/XD",
                internal.getOBC("XD").getInternalName());
    }

    @Test
    public void test_getNMS_Extended_1_17() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(false).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("net/minecraft/XD/DDDD",
                internal.getNMS("XD/DDDD").getInternalName());
    }

    @Test
    public void test_getOBC_Extended_1_17() {
        doReturn(false).when(internal).isVersion_1_7();
        doReturn(false).when(internal).isVersion_1_8();
        doReturn(false).when(internal).isVersion_1_13();
        doReturn(false).when(internal).isVersion_1_15();
        internal.checkMappings();

        assertEquals("org/bukkit/craftbukkit/v_someVersion/XD/DDDD",
                internal.getOBC("XD/DDDD").getInternalName());
    }

}
