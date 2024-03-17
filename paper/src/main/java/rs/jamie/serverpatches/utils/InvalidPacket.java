package rs.jamie.serverpatches.utils;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import rs.jamie.serverpatches.Main;
import rs.jamie.serverpatches.api.CrashEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class InvalidPacket {

    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final Common COMMON = new Common();

    public static void kickUser(User user, String reason, CrashEvent event) {
        user.sendPacket(new WrapperPlayServerDisconnect(COMMON.formatColor(reason)));
        user.closeConnection();
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(event));
    }

}
