package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.entity.Player;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class ClickCrashListener extends SimplePacketListenerAbstract {
    private final InvalidPacketKicker invalidPacketKicker;
    private final YamlDocument config;

    public ClickCrashListener(InvalidPacketKicker invalidPacketKicker, YamlDocument config) {
        super(PacketListenerPriority.NORMAL);
        this.invalidPacketKicker = invalidPacketKicker;
        this.config = config;
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) return;
        User user = event.getUser();
        Player player = (Player) event.getPlayer();
        WrapperPlayClientClickWindow clickWindow = new WrapperPlayClientClickWindow(event);
        int slot = clickWindow.getSlot();
        if (slot == -999 || slot == -1) return;
        if (slot < 0 || slot >= player.getOpenInventory().countSlots()) {
            event.setCancelled(true);
            invalidPacketKicker.kickUser(user, config.getString("click-event-exploit.kick-message"), new CrashEvent(user, CrashType.CLICK_CRASH));
        }
    }
}
