package Summiner.ServerPatches;

import Summiner.ServerPatches.Listeners.Spigot.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    public static void logMessage(String msg) {
        Spigot.getPlugin(Spigot.class).getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        new Manager();
        logMessage("Loaded Plugin (Spigot)");
    }

}
