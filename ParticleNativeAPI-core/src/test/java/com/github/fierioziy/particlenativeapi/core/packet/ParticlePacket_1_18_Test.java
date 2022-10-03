package com.github.fierioziy.particlenativeapi.core.packet;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;
import com.github.fierioziy.particlenativeapi.core.ParticleNativeCoreTest;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.EntityPlayer_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.PlayerConnection_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.obc.v1_18.entity.CraftPlayer_1_18;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticlePacket_1_18_Test {

    private static ParticleNativeAPI api_1_18;
    private static ParticleNativeAPI api_1_19;

    @BeforeAll
    public static void prepareAPI() {
        api_1_18 = ParticleNativeCoreTest.getAPI_1_18();
        api_1_19 = ParticleNativeCoreTest.getAPI_1_19();
    }

    /*
    MC 1.18
     */

    @Test
    public void test_sendPacket_Player_1_18() {
        test_sendPacket_Player(api_1_18);
    }

    @Test
    public void test_sendPacket_Collection_1_18() {
        test_sendPacket_Collection(api_1_18);
    }

    @Test
    public void test_sendPacketIf_Collection_Predicate_1_18() {
        test_sendPacketIf_Collection_Predicate(api_1_18);
    }

    @Test
    public void test_sendPacket_Location_Radius_1_18() {
        test_sendPacket_Location_Radius(api_1_18);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Predicate_1_18() {
        test_sendPacketIf_Location_Radius_Predicate(api_1_18);
    }

    /*
    MC 1.19
     */

    @Test
    public void test_sendPacket_Player_1_19() {
        test_sendPacket_Player(api_1_19);
    }

    @Test
    public void test_sendPacket_Collection_1_19() {
        test_sendPacket_Collection(api_1_19);
    }

    @Test
    public void test_sendPacketIf_Collection_Predicate_1_19() {
        test_sendPacketIf_Collection_Predicate(api_1_19);
    }

    @Test
    public void test_sendPacket_Location_Radius_1_19() {
        test_sendPacket_Location_Radius(api_1_19);
    }

    @Test
    public void test_sendPacketIf_Location_Radius_Predicate_1_19() {
        test_sendPacketIf_Location_Radius_Predicate(api_1_19);
    }

    /*
    Methods to test packet sending to one player
     */

    private void test_sendPacket_Player(ParticleNativeAPI api) {
        CraftPlayer_1_18 craftPlayer = mockCraftPlayer_1_18("josh", 0D, 0D, 0D);

        api.LIST_1_8.HEART
                .packet(false, 0D, 0D, 0D)
                .sendTo(craftPlayer);

        // make sure packet was actually sent
        verify(craftPlayer.ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
    }

    /*
    Methods to test packet sending to many players
     */

    private void test_sendPacket_Collection(ParticleNativeAPI api) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_18("josh", 1D * i, 1D * i, 1D * i));
        }

        api.LIST_1_8.HEART
                .packet(false, 0D, 0D, 0D)
                .sendTo(players);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            verify(((CraftPlayer_1_18) p).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
        }
    }

    /*
    Methods to test packet sending to many players with predicate
     */

    private void test_sendPacketIf_Collection_Predicate(ParticleNativeAPI api) {
        Collection<Player> players = new ArrayList<>(10);
        for (int i = 0; i < 10 ; ++i) {
            players.add(mockCraftPlayer_1_18("josh", 1D * i, 1D * i, 1D * i));
        }

        Predicate<Player> predicate = player -> player.getLocation().getX() > 2.5D;

        api.LIST_1_8.HEART
                .packet(false, 0D, 0D, 0D)
                .sendToIf(players, predicate);

        // make sure packet was actually sent to all players
        for (Player p : players) {
            if (predicate.test(p)) {
                verify(((CraftPlayer_1_18) p).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
            }
            else {
                verify(((CraftPlayer_1_18) p).ep.playerConnection_obf, never()).sendPacket_obf(any());
            }
        }
    }

    /*
    Methods to test packet sending to all players within location's radius
     */

    private void test_sendPacket_Location_Radius(ParticleNativeAPI api) {
        List<Player> players = new ArrayList<>(5);

        //                                 loc: -2D,  2D, 2D   r: 5.2
        players.add(mockCraftPlayer_1_18("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_18("josh", 1D, -2D, 2D));// 5.0 true
        players.add(mockCraftPlayer_1_18("josh", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_18("josh", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_18("josh", 0D, 4D, -2D));// 4.9 true

        api.LIST_1_8.HEART
                .packet(false, -2D, 2D, 2D)
                .sendInRadiusTo(players, 5.2D);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_18) players.get(0)).ep.playerConnection_obf, never()).sendPacket_obf(any());
        verify(((CraftPlayer_1_18) players.get(1)).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
        verify(((CraftPlayer_1_18) players.get(2)).ep.playerConnection_obf, never()).sendPacket_obf(any());
        verify(((CraftPlayer_1_18) players.get(3)).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
        verify(((CraftPlayer_1_18) players.get(4)).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
    }

    /*
    Methods to test packet sending to all players within location's radius with predicate
     */

    private void test_sendPacketIf_Location_Radius_Predicate(ParticleNativeAPI api) {
        List<Player> players = new ArrayList<>(5);

        //                                 loc: -2D,  2D, 2D   r: 5.2
        players.add(mockCraftPlayer_1_18("josh", 1D, -4D, 2D));// 6.7 false
        players.add(mockCraftPlayer_1_18("josh", 1D, -2D, 2D));// 5.0 true, but false due to name
        players.add(mockCraftPlayer_1_18("benny", 2D, -4D, 0D));// 7.48 false
        players.add(mockCraftPlayer_1_18("benny", -4D, -1D, 0D));// 4.12 true
        players.add(mockCraftPlayer_1_18("josh", 0D, 4D, -2D));// 4.9 true, but false due to name

        Predicate<Player> predicate = player -> player.getName().equals("benny");

        api.LIST_1_8.HEART
                .packet(false, -2D, 2D, 2D)
                .sendInRadiusToIf(players, 5.2D, predicate);

        // make sure packet was sent to correct players
        verify(((CraftPlayer_1_18) players.get(0)).ep.playerConnection_obf, never()).sendPacket_obf(any());
        verify(((CraftPlayer_1_18) players.get(1)).ep.playerConnection_obf, never()).sendPacket_obf(any());
        verify(((CraftPlayer_1_18) players.get(2)).ep.playerConnection_obf, never()).sendPacket_obf(any());
        verify(((CraftPlayer_1_18) players.get(3)).ep.playerConnection_obf).sendPacket_obf(any(Packet.class));
        verify(((CraftPlayer_1_18) players.get(4)).ep.playerConnection_obf, never()).sendPacket_obf(any());
    }

    private CraftPlayer_1_18 mockCraftPlayer_1_18(String name, double x, double y, double z) {
        CraftPlayer_1_18 craftPlayer = spy(CraftPlayer_1_18.class);

        craftPlayer.name = name;
        // ughh
        craftPlayer.ep = spy(new EntityPlayer_1_18(
                spy(new PlayerConnection_1_18()), x, y, z)
        );

        return craftPlayer;
    }
    
}
