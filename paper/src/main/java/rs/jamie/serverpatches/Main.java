package rs.jamie.serverpatches;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bstats.bukkit.Metrics;
import rs.jamie.serverpatches.listeners.VersionCheckListener;
import rs.jamie.serverpatches.utils.Command;
import rs.jamie.serverpatches.utils.Common;
import rs.jamie.serverpatches.utils.Manager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static YamlDocument config;
    public static Manager manager;
    public static boolean hasUpdate = false;
    private Common common = new Common();

    public static void logMessage(String msg) {
        Main.getPlugin(Main.class).getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        try {
            config = YamlDocument.create(new File(this.getDataFolder()+ "/config.yml"), getResource("config.yml"), GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        manager = new Manager();
        manager.reload();
        new Metrics(this, 20975);
        this.getCommand("spatches").setExecutor(new Command());
        common.getVersion(this);
        this.getServer().getPluginManager().registerEvents(new VersionCheckListener(), this);
        logMessage("Loaded Plugin (Paper)");
    }

    @Override
    public void onDisable() {
        manager.unload();
    }
}
