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
import summiner.serverpatches.api.CrashEvent;
import summiner.serverpatches.api.CrashType;
import summiner.serverpatches.utils.InvalidPacket;

public class SwapCrashListener extends SimplePacketListenerAbstract {

    public SwapCrashListener() {
        super(PacketListenerPriority.NORMAL);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            User user = event.getUser();
            Player player = Bukkit.getPlayer(user.getUUID());
            int button = new WrapperPlayClientClickWindow(event).getButton();
            if(player == null) {
                event.setCancelled(true);
                return;
            }
            if(button<0||button>40) {
                event.setCancelled(true);
                InvalidPacket.kickFromAsync(player, Main.config.getString("ClickSwapExploit.kick-message"), new CrashEvent(user, CrashType.SWAP_CRASH));
            }
        }
    }

}