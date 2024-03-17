package rs.jamie.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import org.apache.commons.lang.StringUtils;
import rs.jamie.serverpatches.Main;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacket;

public class DatacommandCrashListener extends SimplePacketListenerAbstract {

    public DatacommandCrashListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            User user = event.getUser();
            WrapperPlayClientTabComplete tabComplete = new WrapperPlayClientTabComplete(event);
            String str = tabComplete.getText();
            boolean pass = true;
            if(str.contains("nbt")) {
                if(StringUtils.countMatches(str, "[")>15||StringUtils.countMatches(str, "{")>25) {
                    pass = false;
                }
            }
            if(str.length()>256) {
                pass = false;
            }
            if(!pass) {
                event.setCancelled(true);
                InvalidPacket.kickUser(user, Main.config.getString("DataCommandFilter.kick-message"), new CrashEvent(user, CrashType.DATA_COMMAND_CRASH));
            }
        }
    }

}