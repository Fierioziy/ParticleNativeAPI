package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeBlock;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeBlockTest {

    @Spy
    private ParticleTypeBlock invalidParticleType = new ParticleTypeBlockImpl();

    @Spy
    private ParticleTypeBlock particleType = new ParticleTypeBlockImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy particle type on 'of' method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticleType.class))
                .when(particleType)
                .of(any(Material.class), anyByte());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () -> invalidParticleType.of(Material.DIAMOND_BLOCK, (byte) 0));
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
