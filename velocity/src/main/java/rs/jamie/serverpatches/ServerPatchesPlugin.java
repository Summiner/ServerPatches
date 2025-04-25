package rs.jamie.serverpatches;

import com.google.inject.Inject;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.kyori.adventure.text.event.ClickEvent;
import org.bstats.velocity.Metrics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import rs.jamie.serverpatches.crashes.CrashManager;
import rs.jamie.serverpatches.utils.TextUtil;
import rs.jamie.serverpatches.utils.UpdateUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Plugin(
    id = "serverpatches",
    name = "ServerPatches",
    version = "1.1.0"
    ,url = "https://jamie.rs"
    ,authors = {"Summiner"}
)
public class ServerPatchesPlugin {

    // TODO: Implement common classes between paper/velocity

    @Inject private Logger logger;
    @Inject private ProxyServer server;
    @Inject private Metrics.Factory metricsFactory;

    private final ClassLoader classLoader = this.getClass().getClassLoader();
    private final Path folderPath = Path.of(System.getProperty("user.dir")+"/plugins/ServerPatches");
    private final File folder = new File(folderPath.toString());
    private YamlDocument config;
    private Boolean hasUpdate;


    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        folder.mkdirs();
        PluginContainer plugin = server.getPluginManager().getPlugin("serverpatches").get();
        try {
            config = YamlDocument.create(new File(folder+ "/config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CrashManager crashManager = new CrashManager(config, server, plugin, logger, folderPath);
        crashManager.reload();
        metricsFactory.make(this, 20976);
        CompletableFuture.runAsync(() -> {
            this.hasUpdate = UpdateUtil.hasUpdate(plugin);
        });
    }

    @Subscribe
    public void onServerConnect(ServerPostConnectEvent event) {
        if(!config.getBoolean("misc.version-check")) return;
        if(!hasUpdate) return;
        event.getPlayer().sendMessage(TextUtil.formatColor("&fA newer version of &dServerPatches &fis available. Download it from: ")
                .append(TextUtil.formatColor("&7https://github.com/Summiner/ServerPatches").clickEvent(ClickEvent.openUrl("https://github.com/Summiner/ServerPatches/"))));
    }

    // Bukkit
    public @Nullable InputStream getResource(@NotNull String filename) {
        try {
            URL url = classLoader.getResource(filename);
            if (url == null) {
                return null;
            } else {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException var4) {
            return null;
        }
    }
}
