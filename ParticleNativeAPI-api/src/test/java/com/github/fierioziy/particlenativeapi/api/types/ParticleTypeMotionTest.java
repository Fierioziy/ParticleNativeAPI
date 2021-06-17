package com.github.fierioziy.particlenativeapi.api.types;

import com.github.fierioziy.particlenativeapi.api.utils.FakePacket;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.bukkit.util.Vector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ParticleTypeMotionTest {

    @Spy
    private ParticleTypeMotion invalidParticleType = new ParticleTypeMotion();

    @Spy
    private ParticleTypeMotion particleType = new ParticleTypeMotion();

    @Before
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isValid();

        // make it return dummy object on packet method
        // to avoid ParticleException
        doReturn(new Object()).when(particleType).packet(anyBoolean(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyInt());

        assertFalse("Invalid ParticleType is for some reason valid",
                invalidParticleType.isValid());

        assertTrue("ParticleType is for some reason invalid",
                particleType.isValid());
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

    @Test(expected = ParticleException.class)
    public void testExceptionOnInvalidType() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                1D, 0);
        invalidParticleType.packet(true, target.getVector());
    }

    /*
    Verify method argument expansion
     */

    /*
    Position with vector
     */

    @Test
    public void test_packetpacketMotion_Location_Vector() {
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
