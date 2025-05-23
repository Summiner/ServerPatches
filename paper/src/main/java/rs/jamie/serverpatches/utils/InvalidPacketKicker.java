package rs.jamie.serverpatches.utils;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class InvalidPacketKicker {
    private final Plugin plugin;

    public InvalidPacketKicker(Plugin plugin) {
        this.plugin = plugin;
    }

    public void kickUser(User user, String reason) {
        user.sendPacket(new WrapperPlayServerDisconnect(TextUtil.formatColor(reason)));
        user.closeConnection();
    }
}
