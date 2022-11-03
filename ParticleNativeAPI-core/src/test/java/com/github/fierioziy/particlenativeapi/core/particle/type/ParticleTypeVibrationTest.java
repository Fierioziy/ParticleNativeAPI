package com.github.fierioziy.particlenativeapi.core.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.api.particle.type.ParticleTypeVibration;
import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.particle.type.ParticleTypeVibrationImpl;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticleTypeVibrationTest {

    @Spy
    private ParticleTypeVibration invalidParticleType = new ParticleTypeVibrationImpl();

    @Spy
    private ParticleTypeVibration particleType = new ParticleTypeVibrationImpl();

    @BeforeEach
    public void prepareParticleType() {
        // make it look like valid
        doReturn(true).when(particleType).isPresent();

        // make it return dummy object on packet method
        // to avoid ParticleException
        lenient().doReturn(mock(ParticlePacket.class)).when(particleType).packet(anyBoolean(),
                anyDouble(), anyDouble(), anyDouble(),
                anyDouble(), anyDouble(), anyDouble(),
                anyInt());

        assertFalse(invalidParticleType.isPresent(),
                "Invalid ParticleType is for some reason valid");

        assertTrue(particleType.isPresent(),
                "ParticleType is for some reason invalid");
    }

    @Test
    public void testExceptionOnInvalidType() {
        assertThrows(ParticleException.class, () ->
                invalidParticleType.packet(true,
                        1D, 2D, 3D,
                        4D, 5D, 6D,
                        1)
        );
    }

    /*
    Verify method argument expansion
     */

    /*
    Positions with ticks
     */

    @Test
    public void test_packet_Location_Location_Ticks() {
        Location start = new Location(null, 1D, 2D, 3D);
        Location end = new Location(null, 4D, 5D, 6D);

        particleType.packet(true, start, end, 7);
        verify(particleType).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);
    }

    @Test
    public void test_packet_Location_Vector_Ticks() {
        Location start = new Location(null, 1D, 2D, 3D);
        Vector end = new Vector(4D, 5D, 6D);

        particleType.packet(true, start, end, 7);
        verify(particleType).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);
    }

    @Test
    public void test_packet_Vector_Location_Ticks() {
        Vector start = new Vector(1D, 2D, 3D);
        Location end = new Location(null, 4D, 5D, 6D);

        particleType.packet(true, start, end, 7);
        verify(particleType).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);
    }

    @Test
    public void test_packet_Vector_Vector_Ticks() {
        Vector start = new Vector(1D, 2D, 3D);
        Vector end = new Vector(4D, 5D, 6D);

        particleType.packet(true, start, end, 7);
        verify(particleType).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);
    }

    @Test
    public void test_packet_Pos_Pos_Ticks() {
        particleType.packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);

        verify(particleType).packet(true,
                1D, 2D, 3D,
                4D, 5D, 6D,
                7);
    }

}
