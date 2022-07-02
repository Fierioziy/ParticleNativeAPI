package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.utils.PlayerPredicate;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.EntityPlayer_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_7.PlayerConnection_1_7;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_7.entity.CraftPlayer_1_7;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Connection_1_7_Test {

    private static ParticleNativeAPI api_1_7;
    private static ParticleNativeAPI api_1_8;
    private static ParticleNativeAPI api_1_13;
    private static ParticleNativeAPI api_1_15;

    @BeforeAll
    public static void prepareAPI() {
        api_1_7 = ParticleNativeCoreTest.getAPI_1_7();
        api_1_8 = ParticleNativeCoreTest.getAPI_1_8();
        api_1_13 = ParticleNativeCoreTest.getAPI_1_13();
        api_1_15 = ParticleNativeCoreTest.getAPI_1_15();
    }

    /*
    MC 1.7
     */

    @Test
    public void test_sendPacket_Player_Object_1_7() {
        test_sendPacket_Player_Object(api_1_7);
    }

    @Test
    public void test_sendPacket_Collection_Object_1_7() {
        test_sendPacket_Collection_Object(api_1_7);
    }

    @Test
    public void test_sendPacketIf_Collection_Object_Predicate_1_7() {
        test_sendPacketIf_Collection_Object_Predicate(api_1_7);
    }

    @Test
    public void test_sendPacket_Location_Radius_Object_1_7() {
        test_sendPacket_Location_Radius_Object(api_1_7);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Object_Predicate_1_7() {
        test_sendPacketIf_Location_Radius_Object_Predicate(api_1_7);
    }

    /*
    MC 1.8
     */

    @Test
    public void test_sendPacket_Player_Object_1_8() {
        test_sendPacket_Player_Object(api_1_8);
    }

    @Test
    public void test_sendPacket_Collection_Object_1_8() {
        test_sendPacket_Collection_Object(api_1_8);
    }

    @Test
    public void test_sendPacketIf_Collection_Object_Predicate_1_8() {
        test_sendPacketIf_Collection_Object_Predicate(api_1_8);
    }

    @Test
    public void test_sendPacket_Location_Radius_Object_1_8() {
        test_sendPacket_Location_Radius_Object(api_1_8);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Object_Predicate_1_8() {
        test_sendPacketIf_Location_Radius_Object_Predicate(api_1_8);
    }

    /*
    MC 1.13
     */

    @Test
    public void test_sendPacket_Player_Object_1_13() {
        test_sendPacket_Player_Object(api_1_13);
    }

    @Test
    public void test_sendPacket_Collection_Object_1_13() {
        test_sendPacket_Collection_Object(api_1_13);
    }

    @Test
    public void test_sendPacketIf_Collection_Object_Predicate_1_13() {
        test_sendPacketIf_Collection_Object_Predicate(api_1_13);
    }

    @Test
    public void test_sendPacket_Location_Radius_Object_1_13() {
        test_sendPacket_Location_Radius_Object(api_1_13);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Object_Predicate_1_13() {
        test_sendPacketIf_Location_Radius_Object_Predicate(api_1_13);
    }

    /*
    MC 1.15
     */

    @Test
    public void test_sendPacket_Player_Object_1_15() {
        test_sendPacket_Player_Object(api_1_15);
    }

    @Test
    public void test_sendPacket_Collection_Object_1_15() {
        test_sendPacket_Collection_Object(api_1_15);
    }

    @Test
    public void test_sendPacketIf_Collection_Object_Predicate_1_15() {
        test_sendPacketIf_Collection_Object_Predicate(api_1_15);
    }

    @Test
    public void test_sendPacket_Location_Radius_Object_1_15() {
        test_sendPacket_Location_Radius_Object(api_1_15);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Object_Predicate_1_15() {
        test_sendPacketIf_Location_Radius_Object_Predicate(api_1_15);
    }

    /*
    Methods to test packet sending to one player
     */

    @SuppressWarnings("deprecation")
    private void test_sendPacket_Player_Object(ParticleNativeAPI api) {
        test_sendPacket_Player_Object(api.getParticles_1_8());
        test_sendPacket_Player_Object(api.getParticles_1_13());
        test_sendPacket_Player_Object(api.getServerConnection());

        test_PlayerConnection_sendPacket_Object(api.getParticles_1_8());
        test_PlayerConnection_sendPacket_Object(api.getParticles_1_13());
        test_PlayerConnection_sendPacket_Object(api.getServerConnection());
    }

    private void test_sendPacket_Player_Object(ServerConnection conn) {
        CraftPlayer_1_7 craftPlayer = mockCraftPlayer_1_7("josh", 0D, 0D, 0D);
        Packet packet = mock(Packet.class);

        conn.sendPacket(craftPlayer, packet);

        // make sure packet was actually sent
        verify(craftPlayer.ep.playerConnection).sendPacket(packet);
    }

    private void test_PlayerConnection_sendPacket_Object(ServerConnection conn) {
        CraftPlayer_1_7 craftPlayer = mockCraftPlayer_1_7("josh", 0D, 0D, 0D);
        Packet packet = mock(Packet.class);

        PlayerConnection pc = conn.createPlayerConnection(craftPlayer);
        pc.sendPacket(packet);

        // make sure packet was actually sent
        verify(craftPlayer.ep.playerConnection).sendPacket(packet);
    }

    /*
    Methods to test packet sending to many players
     */

    @SuppressWarnings("deprecation")
    private void test_sendPacket_Collection_Object(ParticleNativeAPI api) {
        test_sendPacket_Collection_Object(api.getParticles_1_8());
        test_sendPacket_Collection_Object(api.getParticles_1_13());
        test_sendPacket_Collection_Object(api.getServerConnection());
    }

    private void test_sendPacket_Collection_Object(ServerConnection conn) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_7("josh", 1D * i, 1D * i, 1D * i));
        }

        Packet packet = mock(Packet.class);

        conn.sendPacket(players, packet);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            verify(((CraftPlayer_1_7) p).ep.playerConnection).sendPacket(packet);
        }
    }

    /*
    Methods to test packet sending to many players with predicate
     */

    @SuppressWarnings("deprecation")
    private void test_sendPacketIf_Collection_Object_Predicate(ParticleNativeAPI api) {
        test_sendPacketIf_Collection_Object_Predicate(api.getParticles_1_8());
        test_sendPacketIf_Collection_Object_Predicate(api.getParticles_1_13());
        test_sendPacketIf_Collection_Object_Predicate(api.getServerConnection());
    }

    private void test_sendPacketIf_Collection_Object_Predicate(ServerConnection conn) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_7("josh", 1D * i, 1D * i, 1D * i));
        }

        Packet packet = mock(Packet.class);

        PlayerPredicate predicate = player -> player.getLocation().getX() > 2.5D;

        conn.sendPacketIf(players, packet, predicate);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            if (predicate.shouldSend(p)) {
                verify(((CraftPlayer_1_7) p).ep.playerConnection).sendPacket(packet);
            }
            else {
                verify(((CraftPlayer_1_7) p).ep.playerConnection, never()).sendPacket(packet);
            }
        }
    }

    /*
    Methods to test packet sending to all players within location's radius
     */

    @SuppressWarnings("deprecation")
    private void test_sendPacket_Location_Radius_Object(ParticleNativeAPI api) {
        test_sendPacket_Location_Radius_Object(api.getParticles_1_8());
        test_sendPacket_Location_Radius_Object(api.getParticles_1_13());
        test_sendPacket_Location_Radius_Object(api.getServerConnection());
    }

    private void test_sendPacket_Location_Radius_Object(ServerConnection conn) {
        List<Player> players = new ArrayList<>(5);

        //                    loc: -2D,  2D, 2D
        players.add(mockCraftPlayer_1_7("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_7("josh", 1D, -2D, 2D));// 5.0 true
        players.add(mockCraftPlayer_1_7("josh", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_7("josh", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_7("josh", 0D, 4D, -2D));// 4.9 true

        // mock getPlayers() from mock World instance
        // to return some players to test
        World mockWorld = mock(World.class);
        when(mockWorld.getPlayers()).thenReturn(players);

        Location loc = new Location(mockWorld, -2D, 2D, 2D);
        double radius = 5.2D;

        Packet packet = mock(Packet.class);

        conn.sendPacket(loc, radius, packet);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_7) players.get(0)).ep.playerConnection, never()).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(1)).ep.playerConnection).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(2)).ep.playerConnection, never()).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(3)).ep.playerConnection).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(4)).ep.playerConnection).sendPacket(packet);
    }

    /*
    Methods to test packet sending to all players within location's radius with predicate
     */

    @SuppressWarnings("deprecation")
    private void test_sendPacketIf_Location_Radius_Object_Predicate(ParticleNativeAPI api) {
        test_sendPacketIf_Location_Radius_Object_Predicate(api.getParticles_1_8());
        test_sendPacketIf_Location_Radius_Object_Predicate(api.getParticles_1_13());
        test_sendPacketIf_Location_Radius_Object_Predicate(api.getServerConnection());
    }

    private void test_sendPacketIf_Location_Radius_Object_Predicate(ServerConnection conn) {
        List<Player> players = new ArrayList<>(5);

        //                    loc: -2D,  2D, 2D
        players.add(mockCraftPlayer_1_7("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_7("josh", 1D, -2D, 2D));// 5.0 true, but false due to name
        players.add(mockCraftPlayer_1_7("benny", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_7("benny", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_7("josh", 0D, 4D, -2D));// 4.9 true, but false due to name

        // mock getPlayers() from mock World instance
        // to return some players to test
        World mockWorld = mock(World.class);
        when(mockWorld.getPlayers()).thenReturn(players);

        Location loc = new Location(mockWorld, -2D, 2D, 2D);
        double radius = 5.2D;

        Packet packet = mock(Packet.class);

        PlayerPredicate predicate = player -> player.getName().equals("benny");

        conn.sendPacketIf(loc, radius, packet, predicate);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_7) players.get(0)).ep.playerConnection, never()).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(1)).ep.playerConnection, never()).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(2)).ep.playerConnection, never()).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(3)).ep.playerConnection).sendPacket(packet);
        verify(((CraftPlayer_1_7) players.get(4)).ep.playerConnection, never()).sendPacket(packet);
    }

    private CraftPlayer_1_7 mockCraftPlayer_1_7(String name, double x, double y, double z) {
        CraftPlayer_1_7 craftPlayer = spy(CraftPlayer_1_7.class);

        craftPlayer.name = name;
        // ughh
        craftPlayer.ep = spy(new EntityPlayer_1_7(
                spy(new PlayerConnection_1_7()), x, y, z));

        return craftPlayer;
    }
    
}
