package rs.jamie.serverpatches;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import rs.jamie.serverpatches.crashes.CrashManager;
import rs.jamie.serverpatches.update.VersionCheckListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class ServerPatchesPlugin extends JavaPlugin {
    private static CrashManager crashManager;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        YamlDocument config;
        try {
            config = YamlDocument.create(new File(this.getDataFolder()+ "/config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        crashManager = new CrashManager(this, config);
        crashManager.reload();
        new Metrics(this, 20975);

        getCommand("spatches").setExecutor(new ServerPatchesCommand(config, crashManager));
        Bukkit.getPluginManager().registerEvents(new VersionCheckListener(config, this), this);

        getLogger().info("Plugin finished loading (Paper)");
    }

    @Override
    public void onDisable() {
        crashManager.unload();
    }
}
