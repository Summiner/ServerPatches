package summiner.serverpatches.Utils;

import summiner.serverpatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class InvalidPacket {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private final StringFormatter stringFormatter = new StringFormatter();

    public void kickFromAsync(Player player, String reason) {
        Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(stringFormatter.formatColor(reason)));
    }

}
