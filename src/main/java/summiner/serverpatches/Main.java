package summiner.serverpatches;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.Bukkit;
import summiner.serverpatches.utils.Command;
import summiner.serverpatches.utils.Manager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static Manager manager;

    public static void logMessage(String msg) {
        Main.getPlugin(Main.class).getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();
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
