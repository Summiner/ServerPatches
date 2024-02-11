package summiner.serverpatches.utils;

import net.kyori.adventure.text.Component;
import summiner.serverpatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InvalidPacket {

    private static final Plugin plugin = Main.getPlugin(Main.class);
    private static final StringFormatter stringFormatter = new StringFormatter();

    public static void kickFromAsync(Player player, String reason) {
        Bukkit.getScheduler().runTask(plugin, () -> player.kick(Component.text(stringFormatter.formatColor(reason))));
    }

}
