package summiner.serverpatches.utils;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import summiner.serverpatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import summiner.serverpatches.listeners.*;

import java.util.HashMap;

public class Manager {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private final HashMap<String, PacketListenerCommon> listeners = new HashMap<>();

    public Manager() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(Main.getPlugin(Main.class)));
        PacketEvents.getAPI().getSettings().reEncodeByDefault(false).checkForUpdates(true).bStats(true);
        PacketEvents.getAPI().load();
    }

    public void unload() {
        listeners.forEach((key,value) -> PacketEvents.getAPI().getEventManager().unregisterListener(value));
        listeners.clear();
    }

    public void reload() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            unload();
            if(Main.config.getBoolean("ClickEventExploit.enabled")) {
                listeners.put("exploits_crash_slot", new ClickCrashListener());
            }
            if(Main.config.getBoolean("LecternExploit.enabled")) {
                listeners.put("exploits_crash_lectern", new LecternCrashListener());
            }
            if(Main.config.getBoolean("ClickSwapExploit.enabled")) {
                listeners.put("exploits_crash_swap", new SwapCrashListener());
            }
            if(Main.config.getBoolean("DataCommandFilter.enabled")) {
                listeners.put("exploits_crash_datacmd", new DatacommandCrashListener());
            }
            listeners.forEach((key,value) -> PacketEvents.getAPI().getEventManager().registerListener(value));
        });
    }
}
