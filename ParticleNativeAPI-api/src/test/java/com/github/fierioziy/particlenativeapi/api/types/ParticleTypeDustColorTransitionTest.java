package com.github.fierioziy.particlenativeapi.api.types;

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
public class ParticleTypeDustColorTransitionTest {

    @Spy
    private ParticleTypeDustColorTransition invalidParticleType = new ParticleTypeDustColorTransition();

    @Spy
    private ParticleTypeDustColorTransition particleType = new ParticleTypeDustColorTransition();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isValid();

        // make it return dummy particle type on 'color' method
        // to avoid ParticleException
        lenient().doReturn(new ParticleType()).when(particleType).color(
                anyFloat(), anyFloat(), anyFloat(),
                anyFloat(), anyFloat(), anyFloat(),
                anyFloat()
        );

        assertFalse(invalidParticleType.isValid(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isValid(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPassWithDelta(float r, float g, float b,
                                             float tr, float tg, float tb,
                                             float size) {
        float delta = 0.001F;
        verify(particleType).color(
                AdditionalMatchers.eq(r, delta), AdditionalMatchers.eq(g, delta), AdditionalMatchers.eq(b, delta),
                AdditionalMatchers.eq(tr, delta), AdditionalMatchers.eq(tg, delta), AdditionalMatchers.eq(tb, delta),
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
                        1F, 1F, 1F,
                        1F, 1F, 1F,
                        1F)
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
                255F / 255F, 120F / 255F, 60F/ 255F,
                120F / 255F, 255F / 255F, 80F/ 255F,
                2F
        );
    }

    @Test
    public void test_color_Ints_Ints_Size() {
        double size = 2D;

        particleType.color(
                255, 120, 60,
                120, 255, 80,
                size
        );

        verifyArgumentPassWithDelta(
                255F / 255F, 120F / 255F, 60F/ 255F,
                120F / 255F, 255F / 255F, 80F/ 255F,
                2F
        );
    }

}
