package Summiner.ServerPatches.Utils;

import Summiner.ServerPatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class InvalidPacket {

    private final Plugin plugin = Main.getPlugin(Main.class);
    private final StringFormatter stringFormatter = new StringFormatter();

    public void kickFromAsync(Player player, String reason) {
        Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(stringFormatter.formatColor(reason)));
    }

}
