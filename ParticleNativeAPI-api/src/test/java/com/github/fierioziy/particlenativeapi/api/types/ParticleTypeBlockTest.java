package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ParticleTypeBlockTest {

    @Spy
    private ParticleTypeBlock invalidParticleType = new ParticleTypeBlock();

    @Spy
    private ParticleTypeBlock particleType = new ParticleTypeBlock();

    @Before
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isValid();

        // make it return dummy particle type on 'of' method
        // to avoid ParticleException
        doReturn(new ParticleType()).when(particleType).of(
                any(Material.class), anyByte()
        );

        assertFalse("Invalid ParticleType is for some reason valid",
                invalidParticleType.isValid());

        assertTrue("ParticleType is for some reason invalid",
                particleType.isValid());
    }

    /*
    Verify invalid particle type
     */

    @Test(expected = ParticleException.class)
    public void testExceptionOnInvalidType() {
        invalidParticleType.of(Material.DIAMOND_BLOCK, (byte) 0);
    }

    /*
    Verify method argument expansion
     */

    @Test
    public void test_of_Material() {
        Material targetMat = Material.DIAMOND_BLOCK;
        byte targetByte = 0;

        particleType.of(targetMat);

        verify(particleType).of(targetMat, targetByte);
    }

    @Test
    public void test_of_Material_Int() {
        Material targetMat = Material.DIAMOND_BLOCK;
        byte targetByte = 2;

        particleType.of(targetMat, (int) targetByte);

        verify(particleType).of(targetMat, targetByte);
    }

}
