package summiner.serverpatches;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import summiner.serverpatches.utils.Command;
import summiner.serverpatches.utils.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    public static YamlDocument config;
    public static Manager manager;

    public static void logMessage(String msg) {
        Main.getPlugin(Main.class).getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        try {
            config = YamlDocument.create(new File(this.getDataFolder()+"/config.yml"), getResource("config.yml"), GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        manager = new Manager();
        manager.reload();
        this.getCommand("spatches").setExecutor(new Command());
        new Metrics(this, 20975);
        logMessage("Loaded Plugin (Paper)");
    }

    @Override
    public void onDisable() {
        manager.unload();
    }
}
