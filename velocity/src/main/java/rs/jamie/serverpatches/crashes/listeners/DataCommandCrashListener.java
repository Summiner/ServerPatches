package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.apache.commons.lang3.StringUtils;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashEventDispatch;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class DataCommandCrashListener extends CrashListener {

    public DataCommandCrashListener(InvalidPacketKicker packetKicker, YamlDocument config, CrashEventDispatch eventDispatch) {
        super(PacketListenerPriority.NORMAL, packetKicker, config, eventDispatch);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.TAB_COMPLETE) return;
        User user = event.getUser();
        WrapperPlayClientTabComplete tabComplete = new WrapperPlayClientTabComplete(event);
        String str = tabComplete.getText();
        if (str.length() > 256 ||
                str.contains("nbt") && StringUtils.countMatches(str, "[") > 15 ||
                StringUtils.countMatches(str, "{") > 25) {
            event.setCancelled(true);
            packetKicker.kickUser(user, config.getString("data-command-filter.kick-message"));
            eventDispatch.dispatchEvent(new CrashEvent(user, CrashType.DATA_COMMAND_CRASH));
        }
    }

}
