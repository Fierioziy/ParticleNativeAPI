package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeSpell;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeSpellTest {

    @Spy
    private ParticleTypeSpell invalidParticleType = new ParticleTypeSpellImpl();

    @Spy
    private ParticleTypeSpell particleType = new ParticleTypeSpellImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy particle type on 'spell' method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticleType.class))
                .when(particleType)
                .spell(anyInt(), anyInt(), anyInt(), anyInt(), anyDouble());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPass(int r, int g, int b, int alpha, double power) {
        verify(particleType).spell(r, g, b, alpha, power);
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () -> invalidParticleType.spell(255, 255, 255, 255, 2D));
    }

    /*
    Verify method argument expansion
     */

    @Test
    public void test_spell_Color_alpha_power() {
        int red = 255;
        int green = 120;
        int blue = 60;
        int alpha = 125;
        double power = 2D;
        Color color = Color.fromRGB(red, green, blue);

        particleType.spell(color, alpha, power);

        verifyArgumentPass(red, green, blue, alpha, power);
    }

}
