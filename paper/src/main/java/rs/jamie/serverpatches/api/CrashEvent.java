package rs.jamie.serverpatches.api;

import com.github.retrooper.packetevents.protocol.player.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CrashEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();

    private final User user;
    private final CrashType type;

    public CrashEvent(User user, CrashType type) {
        this.user = user;
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public CrashType getType() {
        return type;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
