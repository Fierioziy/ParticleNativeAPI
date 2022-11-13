package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleType;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeTest {

    @Spy
    private ParticleType invalidParticleType = new ParticleTypeImpl();

    @Spy
    private ParticleType particleType = new ParticleTypeImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy object on packet method
        // to avoid ParticleException
        lenient()
                .doReturn(mock(ParticlePacket.class))
                .when(particleType)
                .packet(anyBoolean(),
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
    Only position
     */

    @Test
    public void test_packet_Location() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 1);

        particleType.packet(true, target.getLocation());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Vector() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 1);

        particleType.packet(true, target.getVector());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Pos() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 1);

        particleType.packet(true, target.getX(), target.getY(), target.getZ());

        verifyArgumentPass(target);
    }
    /*
    Position with count
     */

    @Test
    public void test_packet_Location_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 9);

        particleType.packet(true, target.getLocation(),
                target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Vector_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 9);

        particleType.packet(true, target.getVector(),
                target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Pos_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                0D, 9);

        particleType.packet(true, target.getX(), target.getY(), target.getZ(),
                target.getCount());

        verifyArgumentPass(target);
    }

    /*
    Position with speed and count
     */

    @Test
    public void test_packet_Location_Speed_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                8D, 9);

        particleType.packet(true, target.getLocation(),
                target.getSpeed(), target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Vector_Speed_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                8D, 9);

        particleType.packet(true, target.getVector(),
                target.getSpeed(), target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Pos_Speed_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                0D, 0D, 0D,
                8D, 9);

        particleType.packet(true, target.getX(), target.getY(), target.getZ(),
                target.getSpeed(), target.getCount());

        verifyArgumentPass(target);
    }

    /*
    Position with offsets and count
     */

    @Test
    public void test_packet_Location_Offsets_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                5D, 6D, 7D,
                0D, 9);

        particleType.packet(true, target.getLocation(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ(),
                target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Vector_Offsets_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                5D, 6D, 7D,
                0D, 9);

        particleType.packet(true, target.getVector(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ(),
                target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Pos_Offsets_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                5D, 6D, 7D,
                0D, 9);

        particleType.packet(true, target.getX(), target.getY(), target.getZ(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ(),
                target.getCount());

        verifyArgumentPass(target);
    }

    /*
    Position with offsets, speed and count
     */

    @Test
    public void test_packet_Location_Offsets_Speed_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                5D, 6D, 7D,
                8D, 9);

        particleType.packet(true, target.getLocation(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ(),
                target.getSpeed(), target.getCount());

        verifyArgumentPass(target);
    }

    @Test
    public void test_packet_Vector_Offsets_Speed_Count() {
        FakePacket target = new FakePacket(true,
                2D, 3D, 4D,
                5D, 6D, 7D,
                8D, 9);

        particleType.packet(true, target.getVector(),
                target.getOffsetX(), target.getOffsetY(), target.getOffsetZ(),
                target.getSpeed(), target.getCount());

        verifyArgumentPass(target);
    }

}
