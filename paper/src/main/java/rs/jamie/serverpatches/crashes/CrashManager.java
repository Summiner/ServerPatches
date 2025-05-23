package rs.jamie.serverpatches.crashes;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.plugin.Plugin;
import rs.jamie.serverpatches.api.CrashEventDispatch;
import rs.jamie.serverpatches.crashes.listeners.BundleSelectCrashListener;
import rs.jamie.serverpatches.crashes.listeners.DataCommandCrashListener;
import rs.jamie.serverpatches.crashes.listeners.SwapCrashListener;
import rs.jamie.serverpatches.utils.InvalidPacketKicker;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class CrashManager {

    private final YamlDocument config;
    private final HashMap<String, PacketListenerCommon> listeners = new HashMap<>();
    private final CrashEventDispatch eventDispatch = new CrashEventDispatch();

    private final InvalidPacketKicker invalidPacketKicker;

    public CrashManager(Plugin plugin, YamlDocument config) {
        this.config = config;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin));
        PacketEvents.getAPI().getSettings()
                .reEncodeByDefault(false)
                .checkForUpdates(false)
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
            if (config.getBoolean("click-swap-exploit.enabled")) listeners.put("exploits_crash_swap", new SwapCrashListener(invalidPacketKicker, config, eventDispatch));
            if (config.getBoolean("data-command-filter.enabled")) listeners.put("exploits_crash_datacmd", new DataCommandCrashListener(invalidPacketKicker, config, eventDispatch));
            if (config.getBoolean("bundle-select-exploit.enabled")) listeners.put("exploits_crash_bundle_select", new BundleSelectCrashListener(invalidPacketKicker, config, eventDispatch));
            listeners.forEach((key,value) -> PacketEvents.getAPI().getEventManager().registerListener(value));
        });
    }
}
