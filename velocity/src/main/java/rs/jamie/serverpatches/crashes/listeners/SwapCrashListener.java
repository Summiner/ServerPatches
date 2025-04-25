package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import dev.dejvokep.boostedyaml.YamlDocument;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashEventDispatch;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class SwapCrashListener extends CrashListener {

    public SwapCrashListener(InvalidPacketKicker packetKicker, YamlDocument config, CrashEventDispatch eventDispatch) {
        super(PacketListenerPriority.NORMAL, packetKicker, config, eventDispatch);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) return;
        User user = event.getUser();
        int button = new WrapperPlayClientClickWindow(event).getButton();
        if (button < 0 || button > 40) {
            event.setCancelled(true);
            packetKicker.kickUser(user, config.getString("click-swap-exploit.kick-message"));
            eventDispatch.dispatchEvent(new CrashEvent(user, CrashType.SWAP_CRASH));
        }
    }
}