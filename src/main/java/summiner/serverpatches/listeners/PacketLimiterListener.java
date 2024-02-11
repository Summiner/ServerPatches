package summiner.serverpatches.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.player.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import summiner.serverpatches.Main;
import summiner.serverpatches.utils.InvalidPacket;
import summiner.serverpatches.utils.TimedExecutions;

public class PacketLimiterListener extends SimplePacketListenerAbstract {

    private TimedExecutions packetTimer;

    public PacketLimiterListener() {
        super(PacketListenerPriority.NORMAL);
        packetTimer = null;
        packetTimer = new TimedExecutions(Main.config.getLong("PacketLimiter.check-interval"));
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        User user = event.getUser();
        Player player = Bukkit.getPlayer(user.getUUID());
        if(player==null) return;
        int max = Main.config.getInt("PacketLimiter.max-rate");
        if(packetTimer.addExecution(user.getUUID()) >= max) {
            event.setCancelled(true);
            InvalidPacket.kickFromAsync(player, Main.config.getString("PacketLimiter.kick-message"));
        }
    }

}

