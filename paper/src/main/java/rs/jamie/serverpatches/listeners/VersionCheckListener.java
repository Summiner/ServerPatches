package rs.jamie.serverpatches.listeners;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rs.jamie.serverpatches.Main;
import rs.jamie.serverpatches.utils.Common;

public class VersionCheckListener implements Listener {

    private static final Common common = new Common();

    @EventHandler
    public void onPacketLoginReceive(PlayerJoinEvent event) {
        if(!Main.config.getBoolean("Misc.version-check")) return;
        if(Main.hasUpdate && event.getPlayer().hasPermission("op")) {;
            event.getPlayer().sendMessage(common.formatColor("&fA newer version of &dServerPatches &fis available. Download it from: ").append(common.formatColor("&7https://github.com/Summiner/ServerPatches").clickEvent(ClickEvent.openUrl("https://github.com/Summiner/ServerPatches"))));
        }
    }

}
