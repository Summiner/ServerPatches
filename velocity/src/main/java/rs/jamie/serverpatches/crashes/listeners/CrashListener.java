package rs.jamie.serverpatches.crashes.listeners;

import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.SimplePacketListenerAbstract;
import dev.dejvokep.boostedyaml.YamlDocument;
import rs.jamie.serverpatches.api.CrashEventDispatch;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

public class CrashListener extends SimplePacketListenerAbstract {

    protected final InvalidPacketKicker packetKicker;
    protected final YamlDocument config;
    protected final CrashEventDispatch eventDispatch;

    public CrashListener(PacketListenerPriority priority, InvalidPacketKicker invalidPacketKicker, YamlDocument config, CrashEventDispatch eventDispatch) {
        super(priority);
        this.packetKicker = invalidPacketKicker;
        this.config = config;
        this.eventDispatch = eventDispatch;
    }

    public boolean run() {
        return true;
    }

}
