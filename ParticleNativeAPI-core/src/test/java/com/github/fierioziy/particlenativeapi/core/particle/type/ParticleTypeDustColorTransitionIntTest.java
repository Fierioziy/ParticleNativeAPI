package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDustColorTransition;
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
public class ParticleTypeDustColorTransitionIntTest {

    @Spy
    private ParticleTypeDustColorTransition invalidParticleType = new ParticleTypeDustColorTransitionIntImpl();

    @Spy
    private ParticleTypeDustColorTransition particleType = new ParticleTypeDustColorTransitionIntImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy particle type on 'color' method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticleType.class))
                .when(particleType)
                .color(anyInt(), anyInt(), anyInt(),
                        anyInt(), anyInt(), anyInt(),
                        anyDouble());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPassWithDelta(int r, int g, int b,
                                             int tr, int tg, int tb,
                                             double size) {
        double delta = 0.001D;
        verify(particleType).color(
                eq(r), eq(g), eq(b),
                eq(tr), eq(tg), eq(tb),
                AdditionalMatchers.eq(size, delta)
        );
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () ->
                invalidParticleType.color(
                        1, 1, 1,
                        1, 1, 1,
                        1D)
        );
    }

    /*
    Verify method argument expansion
     */

    @Test
    public void test_color_Color_Color_Size() {
        Color color = Color.fromRGB(255, 120, 60);
        Color fade = Color.fromRGB(120, 255, 80);
        double size = 2D;

        particleType.color(color, fade, size);

        verifyArgumentPassWithDelta(
                255, 120, 60,
                120, 255, 80,
                2D
        );
    }

    @Test
    public void test_color_Floats_Floats_SizeF() {
        particleType.color(
                255F / 255F, 120F / 255F, 60F / 255F,
                120F / 255F, 255F / 255F, 80F / 255F,
                2F
        );

        verifyArgumentPassWithDelta(
                255, 120, 60,
                120, 255, 80,
                2D
        );
    }

}
