package Summiner.ServerPatches.Listeners.Spigot;

import Summiner.ServerPatches.Spigot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class TasksPerMinute {

    private final String name;
    private final ConcurrentHashMap<Player, Integer> execs = new ConcurrentHashMap<>();

    public TasksPerMinute(String name, long interval) {
        this.name = name;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Spigot.getPlugin(Spigot.class), execs::clear, interval, interval);
    }

    public String getName() {
        return name;
    }

    public int getExecutions(Player player) {
        return execs.getOrDefault(player, 0);
    }

    public int addExecution(Player player) {
        return execs.compute(player, (key, value) -> value == null ? 1 : value + 1);
    }
}
