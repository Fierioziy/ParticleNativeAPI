package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeDust;
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
public class ParticleTypeDustFloatTest {

    @Spy
    private ParticleTypeDust invalidParticleType = new ParticleTypeDustFloatImpl();

    @Spy
    private ParticleTypeDust particleType = new ParticleTypeDustFloatImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy particle type on 'color' method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticleType.class))
                .when(particleType)
                .color(anyFloat(), anyFloat(), anyFloat(), anyFloat());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPassWithDelta(float r, float g, float b, float size) {
        float delta = 0.001F;
        verify(particleType).color(
                AdditionalMatchers.eq(r, delta), AdditionalMatchers.eq(g, delta), AdditionalMatchers.eq(b, delta),
                AdditionalMatchers.eq(size, delta)
        );
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () -> invalidParticleType.color(1F, 1F, 1F, 1F));
    }

    /*
    Verify method argument expansion
     */

    @Test
    public void test_color_Color_SizeD() {
        Color color = Color.fromRGB(255, 120, 60);
        double size = 2D;

        particleType.color(color, size);

        verifyArgumentPassWithDelta(255F / 255F, 120F / 255F, 60F/ 255F, 2F);
    }

    @Test
    public void test_color_Color_SizeF() {
        Color color = Color.fromRGB(255, 120, 60);
        float size = 2F;

        particleType.color(color, size);

        verifyArgumentPassWithDelta(255F / 255F, 120F / 255F, 60F/ 255F, 2F);
    }

    @Test
    public void test_color_Ints_SizeD() {
        double size = 2D;

        particleType.color(255, 120, 60, size);

        verifyArgumentPassWithDelta(255F / 255F, 120F / 255F, 60F/ 255F, 2F);
    }

    @Test
    public void test_color_Ints_SizeF() {
        float size = 2F;

        particleType.color(255, 120, 60, size);

        verifyArgumentPassWithDelta(255F / 255F, 120F / 255F, 60F/ 255F, 2F);
    }

}
