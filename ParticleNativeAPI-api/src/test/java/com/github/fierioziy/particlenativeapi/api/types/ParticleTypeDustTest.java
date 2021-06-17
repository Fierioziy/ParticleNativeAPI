package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ParticleTypeDustTest {

    @Spy
    private ParticleTypeDust invalidParticleType = new ParticleTypeDust();

    @Spy
    private ParticleTypeDust particleType = new ParticleTypeDust();

    @Before
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isValid();

        // make it return dummy particle type on 'color' method
        // to avoid ParticleException
        doReturn(new ParticleType()).when(particleType).color(
                anyFloat(), anyFloat(), anyFloat(), anyFloat()
        );

        assertFalse("Invalid ParticleType is for some reason valid",
                invalidParticleType.isValid());

        assertTrue("ParticleType is for some reason invalid",
                particleType.isValid());
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

    @Test(expected = ParticleException.class)
    public void testExceptionOnInvalidType() {
        invalidParticleType.color(1F, 1F, 1F, 1F);
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
