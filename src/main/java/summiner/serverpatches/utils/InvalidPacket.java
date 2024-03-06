package summiner.serverpatches.utils;

import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDisconnect;
import summiner.serverpatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import summiner.serverpatches.api.CrashEvent;

public class InvalidPacket {

    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final StringFormatter stringFormatter = new StringFormatter();

    public static void kickUser(User user, String reason, CrashEvent event) {
        user.sendPacket(new WrapperPlayServerDisconnect(stringFormatter.formatColor(reason)));
        user.closeConnection();
        Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getPluginManager().callEvent(event));
    }

}
