package rs.jamie.serverpatches.api;

import com.github.retrooper.packetevents.protocol.player.User;

public class CrashEvent {

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
}
