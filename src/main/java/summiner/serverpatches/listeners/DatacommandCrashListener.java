package summiner.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import summiner.serverpatches.Main;
import summiner.serverpatches.api.CrashEvent;
import summiner.serverpatches.api.CrashType;
import summiner.serverpatches.utils.InvalidPacket;

public class DatacommandCrashListener extends SimplePacketListenerAbstract {

    public DatacommandCrashListener() {
        super(PacketListenerPriority.HIGHEST);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            User user = event.getUser();
            Player player = Bukkit.getPlayer(user.getUUID());
            if(player == null) {
                event.setCancelled(true);
                return;
            }
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
                InvalidPacket.kickFromAsync(player, Main.config.getString("DataCommandFilter.kick-message"));
                Bukkit.getPluginManager().callEvent(new CrashEvent(user, CrashType.DATA_COMMAND_CRASH));
            }
        }
    }

}
