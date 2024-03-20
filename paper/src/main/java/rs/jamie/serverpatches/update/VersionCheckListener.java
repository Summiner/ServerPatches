package rs.jamie.serverpatches.update;

import dev.dejvokep.boostedyaml.YamlDocument;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import rs.jamie.serverpatches.utils.TextUtil;
import rs.jamie.serverpatches.utils.UpdateUtil;

import java.util.concurrent.CompletableFuture;

public class VersionCheckListener implements Listener {
    private final YamlDocument config;
    private final CompletableFuture<Boolean> hasUpdate;

    public VersionCheckListener(YamlDocument config, Plugin plugin) {
        this.config = config;
        hasUpdate = UpdateUtil.hasUpdate(plugin);
    }

    @EventHandler
    public void onPacketLoginReceive(PlayerJoinEvent event) {
        if (!config.getBoolean("misc.version-check")) return;
        if (!event.getPlayer().hasPermission("serverpatches.updates")) return;

        hasUpdate.thenAccept(hasUpdate -> {
            event.getPlayer().sendMessage(TextUtil.formatColor("&fA newer version of &dServerPatches &fis available. Download it from: ")
                    .append(TextUtil.formatColor("&7https://github.com/Summiner/ServerPatches").clickEvent(ClickEvent.openUrl("https://github.com/Summiner/ServerPatches"))));
        });
    }
}
