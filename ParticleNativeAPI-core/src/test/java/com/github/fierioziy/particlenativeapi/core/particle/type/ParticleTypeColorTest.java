package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeColor;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeColorTest {

    @Spy
    private ParticleTypeColor invalidParticleType = new ParticleTypeColorImpl();

    @Spy
    private ParticleTypeColor particleType = new ParticleTypeColorImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy particle type on 'color' method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticleType.class))
                .when(particleType)
                .color(anyInt(), anyInt(), anyInt(), anyInt());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPass(int r, int g, int b, int alpha) {
        verify(particleType).color(r, g, b, alpha);
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () -> invalidParticleType.color(255, 255, 255));
    }

    /*
    Verify method argument expansion
     */

    @Test
    public void test_color_Color() {
        int red = 255;
        int green = 120;
        int blue = 60;
        Color color = Color.fromRGB(red, green, blue);

        particleType.color(color);

        verifyArgumentPass(red, green, blue, 255);
    }

    @Test
    public void test_color_Color_alpha() {
        int red = 255;
        int green = 120;
        int blue = 60;
        int alpha = 125;
        Color color = Color.fromRGB(red, green, blue);

        particleType.color(color, alpha);

        verifyArgumentPass(red, green, blue, alpha);
    }

    @Test
    public void test_color_rgb() {
        int red = 255;
        int green = 120;
        int blue = 60;
        particleType.color(red, green, blue);

        verifyArgumentPass(red, green, blue, 255);
    }

}
