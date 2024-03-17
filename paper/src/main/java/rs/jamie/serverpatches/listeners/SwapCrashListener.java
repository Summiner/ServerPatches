package rs.jamie.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import rs.jamie.serverpatches.Main;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacket;

public class SwapCrashListener extends SimplePacketListenerAbstract {


    public SwapCrashListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            User user = event.getUser();
            int button = new WrapperPlayClientClickWindow(event).getButton();
            if(button<0||button>40) {
                event.setCancelled(true);
                InvalidPacket.kickUser(user, Main.config.getString("ClickSwapExploit.kick-message"), new CrashEvent(user, CrashType.SWAP_CRASH));
            }
        }
    }

}