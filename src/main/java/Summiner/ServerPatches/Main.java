package Summiner.ServerPatches;

import Summiner.ServerPatches.Utils.Command;
import Summiner.ServerPatches.Utils.Manager;
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
        logMessage("Loaded Plugin (Paper)");
    }

    @Override
    public void onDisable() {
        manager.unload();
    }
}
