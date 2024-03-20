package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import com.github.retrooper.packetevents.event.simple.PacketPlayReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import rs.jamie.serverpatches.api.CrashEvent;
import rs.jamie.serverpatches.api.CrashType;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class LecternCrashListener extends SimplePacketListenerAbstract {
    private final InvalidPacketKicker invalidPacketKicker;
    private final YamlDocument config;

    public LecternCrashListener(InvalidPacketKicker invalidPacketKicker, YamlDocument config) {
        super(PacketListenerPriority.NORMAL);
        this.invalidPacketKicker = invalidPacketKicker;
        this.config = config;
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) return;
        Player player = (Player) event.getPlayer();
        if (player.getOpenInventory().getType() != InventoryType.LECTERN) return;
        event.setCancelled(true);
        invalidPacketKicker.kickUser(event.getUser(), config.getString("lectern-exploit.kick-message"), new CrashEvent(event.getUser(), CrashType.LECTERN_CRASH));
    }
}
