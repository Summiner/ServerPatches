package rs.jamie.serverpatches.utils;

import rs.jamie.serverpatches.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Command implements CommandExecutor {

    Common common = new Common();
    Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if(!sender.hasPermission("*")) {
            sender.sendMessage(common.formatColor(Main.config.getString("Misc.no_permission")));
            return true;
        }
        if(strings.length==0||!strings[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(common.formatColor("&7Please try with &d/spatches reload"));
            return true;
        }
        try {
            Main.config.reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.manager.reload();
        sender.sendMessage(common.formatColor(Main.config.getString("Misc.config_reloaded")));
        return true;
    }

}
