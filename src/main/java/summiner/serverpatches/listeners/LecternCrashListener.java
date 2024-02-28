package summiner.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import summiner.serverpatches.Main;
import summiner.serverpatches.api.CrashEvent;
import summiner.serverpatches.api.CrashType;
import summiner.serverpatches.utils.InvalidPacket;

public class LecternCrashListener extends SimplePacketListenerAbstract {

    public LecternCrashListener() {
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
            if(player.getOpenInventory().getType() == InventoryType.LECTERN) {
                event.setCancelled(true);
                InvalidPacket.kickFromAsync(player, Main.config.getString("LecternExploit.kick-message"), new CrashEvent(user, CrashType.LECTERN_CRASH));
            }
        }
    }

}
