package Summiner.ServerPatches.Listeners.Spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class InvalidPacket extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String reason;

    public InvalidPacket(Player player, String reason) {
        super(player);
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
