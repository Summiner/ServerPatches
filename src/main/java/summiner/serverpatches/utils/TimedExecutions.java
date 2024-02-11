package summiner.serverpatches.utils;

import summiner.serverpatches.Main;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class TimedExecutions {

    private final HashMap<UUID, Integer> execs = new HashMap<>();

    public TimedExecutions(Long max) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), execs::clear, max, max);
    }

    public int getExecutions(UUID plr) {
        return execs.get(plr);
    }

    public int addExecution(UUID plr) {
        if(execs.containsKey(plr)) execs.put(plr, execs.get(plr)+1);
        else execs.put(plr, 0);
        return execs.get(plr);
    }

}
