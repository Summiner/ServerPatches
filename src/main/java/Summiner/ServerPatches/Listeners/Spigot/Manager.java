package Summiner.ServerPatches.Listeners.Spigot;

import Summiner.ServerPatches.Spigot;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import org.bukkit.Bukkit;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;

public class Manager {

    private final Plugin plugin = Spigot.getPlugin(Spigot.class);
    private final Config config = new Config(plugin.getConfig());
    private TasksPerMinute PacketTimer;
    public static PacketType[] Packets = new PacketType[]{PacketType.Play.Client.BLOCK_DIG, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.CHAT, PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CLOSE_WINDOW, PacketType.Play.Client.B_EDIT, PacketType.Play.Client.TAB_COMPLETE, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.USE_ITEM, PacketType.Play.Client.CLIENT_COMMAND, PacketType.Play.Client.BOAT_MOVE, PacketType.Play.Client.VEHICLE_MOVE, PacketType.Play.Client.UPDATE_SIGN, PacketType.Play.Client.HELD_ITEM_SLOT, PacketType.Play.Client.BEACON, PacketType.Play.Client.AUTO_RECIPE, PacketType.Play.Client.KEEP_ALIVE, PacketType.Play.Client.TELEPORT_ACCEPT, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.GROUND, PacketType.Play.Client.PONG, PacketType.Play.Client.STEER_VEHICLE, PacketType.Play.Client.STRUCT, PacketType.Play.Client.TILE_NBT_QUERY};



    public Manager() {
        PacketTimer = null;
        if(config.Packet_enabled) {
            PacketTimer = new TasksPerMinute("packets", config.Packet_interval);
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, Packets) {
                public void onPacketReceiving(PacketEvent event) {
                    if (event.getPacket().getType().equals(PacketType.Play.Client.WINDOW_CLICK) && config.ClickEventExploit_enabled) {
                        InvExploit(event);
                    }
                    if(config.Packet_enabled) {
                        PacketEvent(event);
                    }
                }
            });
        });
    }

    public void InvExploit(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        int rawSlot = packet.getIntegers().read(2);
        if (rawSlot == -999 || rawSlot == -1)
            return;
        InventoryView view = event.getPlayer().getOpenInventory();
        if (rawSlot < 0 || rawSlot >= view.countSlots()) {
            event.setCancelled(true);
            event.getPlayer().kickPlayer(config.ClickEventExploit_kickMessage);
//            Bukkit.getScheduler().runTask(plugin, () -> {
//                InvalidPacket ev = new InvalidPacket(event.getPlayer(), config.ClickEventExploit_kickMessage);
//                Bukkit.getPluginManager().callEvent(ev);
//                ev.getPlayer().kickPlayer(ev.getReason());
//            });
        }
    }

    public void PacketEvent(PacketEvent event) {
        if(PacketTimer.addExecution(event.getPlayer()) >= config.Packet_maxRate) {
            event.setCancelled(true);
            event.getPlayer().kickPlayer(config.Packet_kickMessage);
//            Bukkit.getScheduler().runTask(plugin, () -> {
//
//            });
        }
    }
}
