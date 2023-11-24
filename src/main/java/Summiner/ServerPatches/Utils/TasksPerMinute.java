package Summiner.ServerPatches.Utils;

import Summiner.ServerPatches.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class
TasksPerMinute {

    private final String name;
    private final HashMap<UUID, Integer> execs = new HashMap<>();

    public TasksPerMinute(String a, Long b) {
        this.name = a;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), execs::clear, b, b);
    }

    public String getName() {
        return name;
    }

    public Integer getExecutions(UUID a) {
        return execs.get(a);
    }

    public Integer addExecution(UUID a) {// returns for ease of use
        if(execs.containsKey(a)) execs.put(a, execs.get(a)+1);
        else execs.put(a, 0);
        return execs.get(a);
    }
}
