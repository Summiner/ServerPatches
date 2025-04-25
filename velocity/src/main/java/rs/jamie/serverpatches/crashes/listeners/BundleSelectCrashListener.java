package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSelectBundleItem;
import dev.dejvokep.boostedyaml.YamlDocument;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashEventDispatch;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class BundleSelectCrashListener extends CrashListener {

    public BundleSelectCrashListener(InvalidPacketKicker packetKicker, YamlDocument config, CrashEventDispatch eventDispatch) {
        super(PacketListenerPriority.NORMAL, packetKicker, config, eventDispatch);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.SELECT_BUNDLE_ITEM) return;
        User user = event.getUser();
        WrapperPlayClientSelectBundleItem packet = new WrapperPlayClientSelectBundleItem(event);
        if(packet.getSelectedItemIndex() >= -1) return;
        event.setCancelled(true);
        packetKicker.kickUser(user, config.getString("bundle-select-exploit.kick-message"));
        eventDispatch.dispatchEvent(new CrashEvent(user, CrashType.BUNDLE_SELECT_CRASH));
    }
}
