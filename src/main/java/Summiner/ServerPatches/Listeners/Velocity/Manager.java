package Summiner.ServerPatches.Listeners.Velocity;

import Summiner.ServerPatches.Velocity;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import dev.simplix.protocolize.data.inventory.InventoryType;
import dev.simplix.protocolize.data.packets.*;
import net.kyori.adventure.text.Component;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;

import java.util.HashMap;
import java.util.UUID;

public class Manager {

    private class clickWindow1 extends AbstractPacketListener<ClickWindow> {
        public clickWindow1() {super(ClickWindow.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<ClickWindow> event) {
            InvExploit(event);
        }
        @Override public void packetSend(PacketSendEvent<ClickWindow> packetSendEvent) {}
    }
    private class clickWindow2 extends AbstractPacketListener<ClickWindow> {
        public clickWindow2() {super(ClickWindow.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<ClickWindow> event) {
            LecternExploit(event);
        }
        @Override public void packetSend(PacketSendEvent<ClickWindow> packetSendEvent) {}
    }
    private class windowItems1 extends AbstractPacketListener<OpenWindow> {
        public windowItems1() {super(OpenWindow.class, Direction.DOWNSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<OpenWindow> event) {
            LastInv(event);
        }
        @Override public void packetSend(PacketSendEvent<OpenWindow> packetSendEvent) {}
    }
    private class windowItems2 extends AbstractPacketListener<CloseWindow> {
        public windowItems2() {super(CloseWindow.class, Direction.DOWNSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<CloseWindow> event) {
            LastInv2(event);
        }
        @Override public void packetSend(PacketSendEvent<CloseWindow> packetSendEvent) {}
    }
    private class limiter1 extends AbstractPacketListener<ClickWindow> {
        public limiter1() {super(ClickWindow.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<ClickWindow> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<ClickWindow> packetSendEvent) {}
    }
    private class limiter2 extends AbstractPacketListener<CloseWindow> {
        public limiter2() {super(CloseWindow.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<CloseWindow> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<CloseWindow> packetSendEvent) {}
    }
    private class limiter3 extends AbstractPacketListener<BlockPlacement> {
        public limiter3() {super(BlockPlacement.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<BlockPlacement> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<BlockPlacement> packetSendEvent) {}
    }
    private class limiter4 extends AbstractPacketListener<HeldItemChange> {
        public limiter4() {super(HeldItemChange.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<HeldItemChange> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<HeldItemChange> packetSendEvent) {}
    }
    private class limiter5 extends AbstractPacketListener<PlayerLook> {
        public limiter5() {super(PlayerLook.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<PlayerLook> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<PlayerLook> packetSendEvent) {}
    }
    private class limiter6 extends AbstractPacketListener<PlayerPosition> {
        public limiter6() {super(PlayerPosition.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<PlayerPosition> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<PlayerPosition> packetSendEvent) {}
    }
    private class limiter7 extends AbstractPacketListener<SetSlot> {
        public limiter7() {super(SetSlot.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<SetSlot> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<SetSlot> packetSendEvent) {}
    }
    private class limiter8 extends AbstractPacketListener<UseItem> {
        public limiter8() {super(UseItem.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<UseItem> event) {
            PacketLimiter(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<UseItem> packetSendEvent) {}
    }
    private class lecternpacket1 extends AbstractPacketListener<OpenWindow> {
        public lecternpacket1() {super(OpenWindow.class, Direction.DOWNSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<OpenWindow> event) {
            currentInvType.putIfAbsent(event.player().uniqueId(), null);
            currentInvType.replace(event.player().uniqueId(), event.packet().inventoryType());
        }
        @Override public void packetSend(PacketSendEvent<OpenWindow> packetSendEvent) {}
    }
    private class lecternpacket2 extends AbstractPacketListener<CloseWindow> {
        public lecternpacket2() {super(CloseWindow.class, Direction.UPSTREAM, 0);}
        @Override public void packetReceive(PacketReceiveEvent<CloseWindow> event) {
            currentInvType.putIfAbsent(event.player().uniqueId(), null);
            currentInvType.remove(event.player().uniqueId());
        }
        @Override public void packetSend(PacketSendEvent<CloseWindow> packetSendEvent) {}
    }

    Config config = Velocity.config;
    private final HashMap<UUID, Integer> lastSize = new HashMap<>();
    private final HashMap<UUID, InventoryType> currentInvType = new HashMap<>();
    private TasksPerMinute PacketTimer;

    public Manager() {
        PacketTimer = null;
        if(config.ClickEventExploit_enabled) {
            Protocolize.listenerProvider().registerListener(new clickWindow1());
            Protocolize.listenerProvider().registerListener(new windowItems1());
            Protocolize.listenerProvider().registerListener(new windowItems2());
        }
        if(config.Packet_enabled) {
            PacketTimer = new TasksPerMinute("packets", config.Packet_interval);
            Protocolize.listenerProvider().registerListener(new limiter1());
            Protocolize.listenerProvider().registerListener(new limiter2());
            Protocolize.listenerProvider().registerListener(new limiter3());
            Protocolize.listenerProvider().registerListener(new limiter4());
            Protocolize.listenerProvider().registerListener(new limiter5());
            Protocolize.listenerProvider().registerListener(new limiter6());
            Protocolize.listenerProvider().registerListener(new limiter7());
            Protocolize.listenerProvider().registerListener(new limiter8());
        }
        if(config.LecternExploit_enabled) {
            Protocolize.listenerProvider().registerListener(new clickWindow2());
            Protocolize.listenerProvider().registerListener(new lecternpacket1());
            Protocolize.listenerProvider().registerListener(new lecternpacket2());
        }
    }

    private void PacketLimiter(UUID uuid) {
        if(PacketTimer.addExecution(uuid) >= config.Packet_maxRate) {
            for(RegisteredServer server : Velocity.server.getAllServers()) {
                for(Player plr : server.getPlayersConnected()) {
                    if(plr.getUniqueId().equals(uuid)) {
                        plr.disconnect(Component.text(config.Packet_kickMessage));
                    }
                }
            }
        }
    }

    private void LastInv(PacketReceiveEvent<OpenWindow> event) {
        lastSize.putIfAbsent(event.player().uniqueId(), 0);
        lastSize.replace(event.player().uniqueId(), event.packet().inventoryType().getTypicalSize(756)+46);
    }

    private void LastInv2(PacketReceiveEvent<CloseWindow> event) {
        lastSize.putIfAbsent(event.player().uniqueId(), 0);
        lastSize.replace(event.player().uniqueId(), 36);
    }

    private void InvExploit(PacketReceiveEvent<ClickWindow> event) {
        lastSize.putIfAbsent(event.player().uniqueId(), 46);
        ClickWindow packet = event.packet();
        int rawSlot = packet.slot();
        if (rawSlot == -999 || rawSlot == -1)
            return;
        if (rawSlot < 0 || rawSlot >= lastSize.get(event.player().uniqueId())) {
            event.cancelled(true);
            for(RegisteredServer server : Velocity.server.getAllServers()) {
                for(Player plr : server.getPlayersConnected()) {
                    if(plr.getUniqueId().equals(event.player().uniqueId())) {
                        plr.disconnect(Component.text(config.ClickEventExploit_kickMessage));
                    }
                }
            }
        }
    }

    private void LecternExploit(PacketReceiveEvent<ClickWindow> event) {
        InventoryType type = currentInvType.get(event.player().uniqueId());
        if(type != null && type.equals(InventoryType.LECTERN)) {
            event.cancelled(true);
            currentInvType.remove(event.player().uniqueId());
            for(RegisteredServer server : Velocity.server.getAllServers()) {
                for(Player plr : server.getPlayersConnected()) {
                    if(plr.getUniqueId().equals(event.player().uniqueId())) {
                        plr.disconnect(Component.text(config.LecternExploit_kickMessage));
                    }
                }
            }
        }
    }

}
