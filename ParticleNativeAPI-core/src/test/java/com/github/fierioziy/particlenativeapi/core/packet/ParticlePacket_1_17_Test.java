package com.github.fierioziy.particlenativeapi.core.packet;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.EntityPlayer_1_17;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.PlayerConnection_1_17;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_17.entity.CraftPlayer_1_17;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static com.github.fierioziy.particlenativeapi.core.asm.particle.type.ParticleTypesUtils.unwrapPacket;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticlePacket_1_17_Test {

    private static ParticleNativeAPI api_1_17;

    @BeforeAll
    public static void prepareAPI() {
        api_1_17 = ParticleNativeCoreTest.getAPI_1_17();
    }

    /*
    MC 1.17
     */

    @Test
    public void test_sendTo_Player_1_17() {
        test_sendTo_Player(api_1_17);
    }

    @Test
    public void test_sendInRadiusTo_Player_1_17() {
        test_sendInRadiusTo_Player(api_1_17);
    }

    @Test
    public void test_sendTo_Collection_1_17() {
        test_sendTo_Collection(api_1_17);
    }

    @Test
    public void test_sendTo_Collection_Predicate_1_17() {
        test_sendTo_Collection_Predicate(api_1_17);
    }

    @Test
    public void test_sendInRadiusTo_Location_Radius_1_17() {
        test_sendInRadiusTo_Location_Radius(api_1_17);
    }

    @Test
    public void test_sendInRadiusTo_Location_Radius_Predicate_1_17() {
        test_sendInRadiusTo_Location_Radius_Predicate(api_1_17);
    }

    @Test
    public void test_detachCopy_1_17() {
        test_detachCopy(api_1_17);
    }

    /*
    Methods to test packet sending to one player
     */

    private void test_sendTo_Player(ParticleNativeAPI api) {
        CraftPlayer_1_17 craftPlayer = mockCraftPlayer_1_17("josh", 0D, 0D, 0D);

        api.LIST_1_8.BARRIER
                .packet(false, 0D, 0D, 0D)
                .sendTo(craftPlayer);

        // make sure packet was actually sent
        verify(craftPlayer.ep.playerConnection).sendPacket(any(Packet.class));
    }

    private void test_sendInRadiusTo_Player(ParticleNativeAPI api) {
        CraftPlayer_1_17 craftPlayer = mockCraftPlayer_1_17("josh", 0D, 0D, 0D);

        api.LIST_1_8.BARRIER
                .packet(false, 0D, 6D, 0D)
                .sendInRadiusTo(craftPlayer, 5D);

        // make sure packet was not sent
        verify(craftPlayer.ep.playerConnection, never()).sendPacket(any(Packet.class));

        api.LIST_1_8.BARRIER
                .packet(false, 0D, 3D, 0D)
                .sendInRadiusTo(craftPlayer, 5D);

        // make sure packet was actually sent
        verify(craftPlayer.ep.playerConnection).sendPacket(any(Packet.class));
    }


    /*
    Methods to test packet sending to many players
     */

    private void test_sendTo_Collection(ParticleNativeAPI api) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_17("josh", 1D * i, 1D * i, 1D * i));
        }

        api.LIST_1_8.BARRIER
                .packet(false, 0D, 0D, 0D)
                .sendTo(players);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            verify(((CraftPlayer_1_17) p).ep.playerConnection).sendPacket(any(Packet.class));
        }
    }

    /*
    Methods to test packet sending to many players with predicate
     */

    private void test_sendTo_Collection_Predicate(ParticleNativeAPI api) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_17("josh", 1D * i, 1D * i, 1D * i));
        }

        Predicate<Player> predicate = player -> player.getLocation().getX() > 2.5D;

        api.LIST_1_8.BARRIER
                .packet(false, 0D, 0D, 0D)
                .sendTo(players, predicate);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            if (predicate.test(p)) {
                verify(((CraftPlayer_1_17) p).ep.playerConnection).sendPacket(any(Packet.class));
            }
            else {
                verify(((CraftPlayer_1_17) p).ep.playerConnection, never()).sendPacket(any());
            }
        }
    }

    /*
    Methods to test packet sending to all players within location's radius
     */

    private void test_sendInRadiusTo_Location_Radius(ParticleNativeAPI api) {
        List<Player> players = new ArrayList<>(5);

        //                                 loc: -2D,  2D, 2D   r: 5.2
        players.add(mockCraftPlayer_1_17("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_17("josh", 1D, -2D, 2D));// 5.0 true
        players.add(mockCraftPlayer_1_17("josh", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_17("josh", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_17("josh", 0D, 4D, -2D));// 4.9 true

        api.LIST_1_8.BARRIER
                .packet(false, -2D, 2D, 2D)
                .sendInRadiusTo(players, 5.2D);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_17) players.get(0)).ep.playerConnection, never()).sendPacket(any());
        verify(((CraftPlayer_1_17) players.get(1)).ep.playerConnection).sendPacket(any(Packet.class));
        verify(((CraftPlayer_1_17) players.get(2)).ep.playerConnection, never()).sendPacket(any());
        verify(((CraftPlayer_1_17) players.get(3)).ep.playerConnection).sendPacket(any(Packet.class));
        verify(((CraftPlayer_1_17) players.get(4)).ep.playerConnection).sendPacket(any(Packet.class));
    }

    /*
    Methods to test packet sending to all players within location's radius with predicate
     */

    private void test_sendInRadiusTo_Location_Radius_Predicate(ParticleNativeAPI api) {
        List<Player> players = new ArrayList<>(5);

        //                                 loc: -2D,  2D, 2D   r: 5.2
        players.add(mockCraftPlayer_1_17("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_17("josh", 1D, -2D, 2D));// 5.0 true, but false due to name
        players.add(mockCraftPlayer_1_17("benny", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_17("benny", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_17("josh", 0D, 4D, -2D));// 4.9 true, but false due to name

        Predicate<Player> predicate = player -> player.getName().equals("benny");

        api.LIST_1_8.BARRIER
                .packet(false, -2D, 2D, 2D)
                .sendInRadiusTo(players, 5.2D, predicate);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_17) players.get(0)).ep.playerConnection, never()).sendPacket(any());
        verify(((CraftPlayer_1_17) players.get(1)).ep.playerConnection, never()).sendPacket(any());
        verify(((CraftPlayer_1_17) players.get(2)).ep.playerConnection, never()).sendPacket(any());
        verify(((CraftPlayer_1_17) players.get(3)).ep.playerConnection).sendPacket(any(Packet.class));
        verify(((CraftPlayer_1_17) players.get(4)).ep.playerConnection, never()).sendPacket(any());
    }

    private void test_detachCopy(ParticleNativeAPI api) {
        ParticlePacket packet = api.LIST_1_8.BARRIER
                .packet(false, -2D, 2D, 2D);

        ParticlePacket detachedPacket = packet.detachCopy();

        assertNotSame(detachedPacket, packet, "ParticlePacket detachCopy return same object");

        Object objPacket = unwrapPacket(packet);
        Object objDetachedPacket = unwrapPacket(detachedPacket);

        assertNotNull(objPacket, "ParticlePacket has null wrapped packet");
        assertNotNull(objDetachedPacket, "Detached ParticlePacket has null wrapped packet");

        assertSame(objPacket, objDetachedPacket, "Detached ParticlePacket does not have same wrapped packet");
    }

    private CraftPlayer_1_17 mockCraftPlayer_1_17(String name, double x, double y, double z) {
        CraftPlayer_1_17 craftPlayer = spy(CraftPlayer_1_17.class);

        craftPlayer.name = name;
        // ughh
        craftPlayer.ep = spy(new EntityPlayer_1_17(
                spy(new PlayerConnection_1_17()), x, y, z)
        );

        return craftPlayer;
    }
    
}
