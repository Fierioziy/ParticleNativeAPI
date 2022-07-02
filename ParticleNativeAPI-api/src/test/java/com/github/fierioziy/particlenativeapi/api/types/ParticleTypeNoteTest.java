package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.FakePacket;
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
public class ParticleTypeNoteTest {

    @Spy
    private ParticleTypeNote invalidParticleType = new ParticleTypeNote();

    @Spy
    private ParticleTypeNote particleType = new ParticleTypeNote();

    @BeforeEach
    public void prepareParticleNoteType() {
        // make it look like valid
        doReturn(true).when(particleType).isValid();

        // make it return dummy object on packet method
        // to avoid ParticleException
        lenient().doReturn(new Object()).when(particleType).packet(anyBoolean(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyInt());

        assertFalse(invalidParticleType.isValid(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isValid(),
                "ParticleType is for some reason invalid");
    }

    private void verifyArgumentPass(FakePacket target) {
        // all arguments should be exactly passed to root method
        verify(particleType).packet(true,
                target.getX(),          target.getY(),          target.getZ(),
                target.getOffsetX(),    target.getOffsetY(),    target.getOffsetZ(),
                target.getSpeed(),      target.getCount());
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
            invalidParticleType.packetNote(true, target.getVector(), Color.fromRGB(255, 0, 0));
        });
    }

    /*
    Verify method argument expansion
     */

    /*
    Position with color
     */

    @Test
    public void test_packetNote_Location_Color() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getLocation(),
                Color.fromRGB(255, 0, 255));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetNote_Vector_Color() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getVector(),
                Color.fromRGB(255, 0, 255));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetNote_Pos_Color() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getX(), target.getY(), target.getZ(),
                Color.fromRGB(255, 0, 255));

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    /*
    Position with color components
     */

    @Test
    public void test_packetNote_Location_Ints() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getLocation(),
                255, 0, 255);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetNote_Vector_Ints() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getVector(),
                255, 0, 255);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetNote_Pos_Ints() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getX(), target.getY(), target.getZ(),
                255, 0, 255);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    /*
    Position with color value
     */

    @Test
    public void test_packetNote_Location_Double() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getLocation(),
                target.getOffsetX());

        // expect color to be exactly passed
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetNote_Vector_Double() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getVector(),
                target.getOffsetX());

        // expect color to be exactly passed
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetNote_Pos_Double() {
        // target pink color
        FakePacket target = new FakePacket(true,
                2D,         3D,         4D,
                10D / 24D,  0D,         0D,
                1D,         0);

        particleType.packetNote(true,
                target.getX(), target.getY(), target.getZ(),
                target.getOffsetX());

        // expect color to be exactly passed
        verifyArgumentPass(target);
    }

    /*
    Verify method creating particle note with color components
     */

    private void testPacketNoteWithColorObject(double targetColor, Color color) {
        FakePacket target = new FakePacket(true,
                2D,             3D,             4D,
                targetColor,    0D,             0D,
                1D,         0);

        particleType.packetNote(true, target.getVector(), color);

        // expect color to be calculated, needs delta check
        verifyArgumentPassWithDelta(target);
    }

    @Test
    public void test_packetNote_RedColor() {
        testPacketNoteWithColorObject(6D / 24D, Color.fromRGB(255, 0, 0));
    }

    @Test
    public void test_packetNote_GreenColor() {
        testPacketNoteWithColorObject(22D / 24D, Color.fromRGB(0, 255, 0));
    }

    @Test
    public void test_packetNote_BlueColor() {
        testPacketNoteWithColorObject(14D / 24D, Color.fromRGB(0, 0, 255));
    }

    @Test
    public void test_packetNote_YellowColor() {
        testPacketNoteWithColorObject(2D / 24D, Color.fromRGB(255, 255, 0));
    }

    @Test
    public void test_packetNote_CyanColor() {
        testPacketNoteWithColorObject(18D / 24D, Color.fromRGB(0, 255, 255));
    }

    @Test
    public void test_packetNote_PinkColor() {
        testPacketNoteWithColorObject(10D / 24D, Color.fromRGB(255, 0, 255));
    }

    /*
    Verify method creating particle note with invalid color components
     */

    @Test
    public void testTooManyChannels() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);

            particleType.packetNote(true, target.getVector(), Color.fromRGB(255, 125, 125));
        });
    }

    @Test
    public void testNotEnoughChannelsRed() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);

            particleType.packetNote(true, target.getVector(), Color.fromRGB(220, 0, 0));
        });
    }

    @Test
    public void testNotEnoughChannelsGreen() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);

            particleType.packetNote(true, target.getVector(), Color.fromRGB(0, 220, 0));
        });
    }

    @Test
    public void testNotEnoughChannelsBlue() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);

            particleType.packetNote(true, target.getVector(), Color.fromRGB(0, 0, 220));
        });
    }

    @Test
    public void testNotEnoughChannelsNothing() {
        assertThrows(UnsupportedOperationException.class, () -> {
            FakePacket target = new FakePacket(true,
                    2D, 3D, 4D,
                    0D, 0D, 0D,
                    1D, 0);

            particleType.packetNote(true, target.getVector(), Color.fromRGB(0, 0, 0));
        });
    }

    /*
    Verify method creating particle note with direct color value
     */

    private void testPacketNoteWithColorValue(double color) {
        FakePacket target = new FakePacket(true,
                2D,     3D,     4D,
                color,  0D,     0D,
                1D,     0);

        particleType.packetNote(true, target.getVector(), target.getOffsetX());

        // expect color to be exactly passed
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetNote_DirectChannelPositive() {
        testPacketNoteWithColorValue(0.4D);
    }

    @Test
    public void test_packetNote_DirectChannelPositiveAbnormal() {
        testPacketNoteWithColorValue(300D);
    }

    @Test
    public void test_packetNote_DirectChannelNegative() {
        testPacketNoteWithColorValue(-0.4D);
    }

    @Test
    public void test_packetNote_DirectChannelNegativeAbnormal() {
        testPacketNoteWithColorValue(-300D);
    }

}
