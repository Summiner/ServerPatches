package Summiner.ServerPatches.Utils;

import Summiner.ServerPatches.Main;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class Manager {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private TimedExecutions PacketTimer;
    public static PacketType[] Packets = new PacketType[]{PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.CHAT, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLOSE_WINDOW, PacketType.Play.Client.B_EDIT, PacketType.Play.Client.TAB_COMPLETE, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.USE_ITEM, PacketType.Play.Client.CLIENT_COMMAND, PacketType.Play.Client.UPDATE_SIGN, PacketType.Play.Client.HELD_ITEM_SLOT, PacketType.Play.Client.BEACON, PacketType.Play.Client.AUTO_RECIPE, PacketType.Play.Client.KEEP_ALIVE, PacketType.Play.Client.TELEPORT_ACCEPT, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.GROUND, PacketType.Play.Client.PONG, PacketType.Play.Client.STRUCT, PacketType.Play.Client.TILE_NBT_QUERY};
    private final InvalidPacket invalidPacket = new InvalidPacket();
    private final HashMap<String, PacketListener> listeners = new HashMap<>();
    private final ProtocolManager protocolManager;

    public Manager() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void unload() {
        listeners.forEach((key,value) -> protocolManager.removePacketListener(value));
        listeners.clear();
    }

    public void reload() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            unload();
            if(Main.config.getBoolean("ClickEventExploit.enabled")) {
                listeners.put("exploits_crash_slot", new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        int rawSlot = packet.getIntegers().read(2);
                        if (rawSlot == -999 || rawSlot == -1)
                            return;
                        InventoryView view = event.getPlayer().getOpenInventory();
                        if (rawSlot < 0 || rawSlot >= view.countSlots()) {
                            event.setCancelled(true);
                            invalidPacket.kickFromAsync(event.getPlayer(), Main.config.getString("ClickEventExploit.kick-message"));
                        }
                    }
                });
            }
            if(Main.config.getBoolean("LecternExploit.enabled")) {
                listeners.put("exploits_crash_lectern", new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
                    public void onPacketReceiving(PacketEvent event) {
                        if(event.getPlayer().getOpenInventory().getType() == InventoryType.LECTERN) {
                            event.setCancelled(true);
                            invalidPacket.kickFromAsync(event.getPlayer(), Main.config.getString("LecternExploit.kick-message"));
                        }
                    }
                });
            }
            if(Main.config.getBoolean("ClickSwapExploit.enabled")) {
                listeners.put("exploits_crash_swap", new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.WINDOW_CLICK) {
                    public void onPacketReceiving(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        int button = packet.getIntegers().read(3);
                        if(button>=0&&button<=40) return;
                        event.setCancelled(true);
                        invalidPacket.kickFromAsync(event.getPlayer(), Main.config.getString("ClickSwapExploit.kick-message"));
                    }
                });
            }
            if(Main.config.getBoolean("PacketLimiter.enabled")) {
                PacketTimer = null;
                PacketTimer = new TimedExecutions(Main.config.getLong("PacketLimiter.check-interval"));
                listeners.put("rate_limit", new PacketAdapter(plugin, ListenerPriority.NORMAL, Packets) {
                    public void onPacketReceiving(PacketEvent event) {
                        if(event.getPlayer()==null) return;
                        int max = Main.config.getInt("PacketLimiter.max-rate");
                        if(PacketTimer.addExecution(event.getPlayer().getUniqueId()) >= max) {
                            event.setCancelled(true);
                            invalidPacket.kickFromAsync(event.getPlayer(), Main.config.getString("PacketLimiter.kick-message").replaceAll("\\$\\{max}", String.valueOf(max)));
                        }
                    }
                });
            }
            listeners.forEach((key,value) -> protocolManager.addPacketListener(value));
        });
    }
}
