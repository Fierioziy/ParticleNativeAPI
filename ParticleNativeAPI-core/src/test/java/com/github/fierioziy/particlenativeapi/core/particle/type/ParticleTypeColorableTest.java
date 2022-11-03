package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeColorable;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeColorableTest {

    @Spy
    private ParticleTypeColorable invalidParticleType = new ParticleTypeColorableImpl();

    @Spy
    private ParticleTypeColorable particleType = new ParticleTypeColorableImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy object on packet method
        // to avoid ParticleException
        lenient().doReturn(mock(ParticlePacket.class)).when(particleType).packet(anyBoolean(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyInt());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPassWithDelta(FakePacket target) {
        double delta = 0.001;
        verify(particleType).packet(eq(true),
                AdditionalMatchers.eq(target.getX(),       delta), AdditionalMatchers.eq(target.getY(),       delta), AdditionalMatchers.eq(target.getZ(),       delta),
                AdditionalMatchers.eq(target.getOffsetX(), delta), AdditionalMatchers.eq(target.getOffsetY(), delta), AdditionalMatchers.eq(target.getOffsetZ(), delta),
                AdditionalMatchers.eq(target.getSpeed(),   delta), ArgumentMatchers.eq(target.getCount()));
    }

    /*
    Verify invalid particle type
     */

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);
            invalidParticleType.packet(true, target.getVector());
        });
    }

    /*
    Verify method argument expansion
     */

    /*
    Position with color
     */

    @Test
    public void test_packetColored_Location_Color() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getLocation(),
                Color.fromRGB(255, 120, 60));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetColored_Vector_Color() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getVector(),
                Color.fromRGB(255, 120, 60));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetColored_Pos_Color() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getX(), target.getY(), target.getZ(),
                Color.fromRGB(255, 120, 60));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    /*
    Position with color components
     */

    @Test
    public void test_packetColored_Location_Ints() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getLocation(),
                255, 120, 60);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetColored_Vector_Ints() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getVector(),
                255, 120, 60);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetColored_Pos_Ints() {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                255D / 255D,    120D / 255D,    60D / 255D,
                1D, 0);

        particleType.packetColored(true, target.getX(), target.getY(), target.getZ(),
                255, 120, 60);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

}
