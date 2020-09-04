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
        doReturn("caveupdate").when(internal).getPackageVersion();
    }

    @Test
    public void test_getNMS_Simple() {
        assertEquals("net/minecraft/server/caveupdate/XD",
                internal.getNMS("XD").getInternalName());
    }

    @Test
    public void test_getOBC_Simple() {
        assertEquals("org/bukkit/craftbukkit/caveupdate/XD",
                internal.getOBC("XD").getInternalName());
    }

    @Test
    public void test_getNMS_Extended() {
        assertEquals("net/minecraft/server/caveupdate/XD/DDDD",
                internal.getNMS("XD/DDDD").getInternalName());
    }

    @Test
    public void test_getOBC_Extended() {
        assertEquals("org/bukkit/craftbukkit/caveupdate/XD/DDDD",
                internal.getOBC("XD/DDDD").getInternalName());
    }

}
