package Summiner.ServerPatches.Listeners.Spigot;

import Summiner.ServerPatches.Spigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TasksPerMinute {

    private final String name;
    private final HashMap<Player, Integer> execs = new HashMap<>();

    public TasksPerMinute(String a, Long b) {
        this.name = a;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.getPlugin(Spigot.class), execs::clear, b, b);
    }

    public String getName() {
        return name;
    }

    public Integer getExecutions(Player a) {
        return execs.get(a);
    }

    public Integer addExecution(Player a) {// returns for ease of use
        execs.putIfAbsent(a, 0);
        execs.replace(a, execs.get(a)+1);
        return execs.get(a);
    }
}
