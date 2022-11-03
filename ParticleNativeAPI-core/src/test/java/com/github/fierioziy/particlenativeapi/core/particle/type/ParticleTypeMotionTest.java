package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeMotion;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeMotionTest {

    @Spy
    private ParticleTypeMotion invalidParticleType = new ParticleTypeMotionImpl();

    @Spy
    private ParticleTypeMotion particleType = new ParticleTypeMotionImpl();

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

    private void verifyArgumentPass(FakePacket target) {
        // all arguments should be exactly passed to root method
        verify(particleType).packet(true,
                target.getX(),          target.getY(),          target.getZ(),
                target.getOffsetX(),    target.getOffsetY(),    target.getOffsetZ(),
                target.getSpeed(),      target.getCount());
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
    Position with vector
     */

    @Test
    public void test_packetMotion_Location_Vector() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getLocation(),
                new Vector(4D, 5D, 6D));

        // expect exact argument pass
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetMotion_Vector_Vector() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getVector(),
                new Vector(4D, 5D, 6D));

        // expect exact argument pass
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetMotion_Pos_Vector() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getX(), target.getY(), target.getZ(),
                new Vector(4D, 5D, 6D));

        // expect exact argument pass
        verifyArgumentPass(target);
    }

    /*
    Position with vector components
     */

    @Test
    public void test_packetMotion_Location_Doubles() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getLocation(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ());

        // expect exact argument pass
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetMotion_Vector_Doubles() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getVector(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ());

        // expect exact argument pass
        verifyArgumentPass(target);
    }

    @Test
    public void test_packetMotion_Pos_Doubles() {
        FakePacket target = new FakePacket(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                1D, 0);

        particleType.packetMotion(true,
                target.getX(), target.getY(), target.getZ(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ());

        // expect exact argument pass
        verifyArgumentPass(target);
    }

}
