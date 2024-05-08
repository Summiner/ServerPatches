package rs.jamie.serverpatches.crashes;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import rs.jamie.serverpatches.crashes.listeners.*;
import org.bukkit.plugin.Plugin;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class CrashManager {

    private final YamlDocument config;
    private final HashMap<String, PacketListenerCommon> listeners = new HashMap<>();

    private final InvalidPacketKicker invalidPacketKicker;

    public CrashManager(Plugin plugin, YamlDocument config) {
        this.config = config;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().getSettings()
                .reEncodeByDefault(false)
                .checkForUpdates(true)
                .kickOnPacketException(true)
                .bStats(true);
        PacketEvents.getAPI().load();

        invalidPacketKicker = new InvalidPacketKicker(plugin);
    }

    public void unload() {
        listeners.forEach((key,value) -> PacketEvents.getAPI().getEventManager().unregisterListener(value));
        listeners.clear();
    }

    public CompletableFuture<Void> reload() {
        return CompletableFuture.runAsync(() -> {
            unload();
            if (config.getBoolean("click-event-exploit.enabled")) listeners.put("exploits_crash_slot", new ClickCrashListener(invalidPacketKicker, config));
            if (config.getBoolean("lectern-exploit.enabled")) listeners.put("exploits_crash_lectern", new LecternCrashListener(invalidPacketKicker, config));
            if (config.getBoolean("click-swap-exploit.enabled")) listeners.put("exploits_crash_swap", new SwapCrashListener(invalidPacketKicker, config));
            if (config.getBoolean("data-command-filter.enabled")) listeners.put("exploits_crash_datacmd", new DataCommandCrashListener(invalidPacketKicker, config));
            listeners.forEach((key,value) -> PacketEvents.getAPI().getEventManager().registerListener(value));
        });
    }
}
