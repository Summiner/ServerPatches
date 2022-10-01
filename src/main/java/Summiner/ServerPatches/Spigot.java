package Summiner.ServerPatches;

import Summiner.ServerPatches.Listeners.Spigot.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class Spigot extends JavaPlugin {

    /*
      Dev note: Fix random kicked from auto clickers? Experienced a few times at random but can't reproduce at will.
     */


    public static void logMessage(String msg) {
        Spigot.getPlugin(Spigot.class).getLogger().info(msg);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        new Manager();
        /*if(Manager.config.SimplePhysics_enabled) {
            this.getServer().getPluginManager().registerEvents(new PhysicsEvent(), this);
        }*/
        logMessage("Loaded Plugin (Spigot)");
    }

}
