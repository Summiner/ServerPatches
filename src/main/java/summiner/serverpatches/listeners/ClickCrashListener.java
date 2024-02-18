package summiner.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import summiner.serverpatches.Main;
import summiner.serverpatches.utils.InvalidPacket;

public class ClickCrashListener extends SimplePacketListenerAbstract {

    public ClickCrashListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            User user = event.getUser();
            Player player = Bukkit.getPlayer(user.getUUID());
            if(player == null) {
                event.setCancelled(true);
                return;
            }
            WrapperPlayClientClickWindow clickWindow = new WrapperPlayClientClickWindow(event);
            int slot = clickWindow.getSlot();
            if (slot == -999 || slot == -1) return;
            if(slot < 0 || slot >= player.getInventory().getSize()) {
                event.setCancelled(true);
                InvalidPacket.kickFromAsync(player, Main.config.getString("ClickEventExploit.kick-message"));
            }
        }
    }
}
