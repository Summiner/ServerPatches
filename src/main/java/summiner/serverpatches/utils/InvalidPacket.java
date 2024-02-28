package summiner.serverpatches.utils;

import com.github.retrooper.packetevents.protocol.player.User;
import net.kyori.adventure.text.Component;
import summiner.serverpatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import summiner.serverpatches.api.CrashEvent;
import summiner.serverpatches.api.CrashType;

public class InvalidPacket {

    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final StringFormatter stringFormatter = new StringFormatter();

    public static void kickFromAsync(User user, String reason, CrashType type) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPlayer(user.getUUID()).kick(stringFormatter.formatColor(reason));
            Bukkit.getPluginManager().callEvent(new CrashEvent(user, CrashType.LECTERN_CRASH));
        });
    }

}
