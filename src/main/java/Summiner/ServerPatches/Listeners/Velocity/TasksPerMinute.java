package Summiner.ServerPatches.Listeners.Velocity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TasksPerMinute {

    private final String name;
    private final HashMap<UUID, Integer> execs = new HashMap<>();
    protected Timer timer = new Timer();

    // i have tried my best to figure out what the hell these names were. this is why you use descriptive names!!
    public TasksPerMinute(String name, Long seconds) {
        this.name = name;
        TimerTask reload = new TimerTask() {@Override public void run() {execs.clear();}};
        long MS = seconds*1000;
        timer.scheduleAtFixedRate(reload, MS, MS);
    }

    public String getName() {
        return name;
    }

    public int getExecutions(UUID uuid) {
        return execs.getOrDefault(uuid, 0);
    }

    public int addExecution(UUID uuid) { // hehe i like lambdas
        return execs.compute(uuid, (key, value) -> value == null ? 1 : value + 1);
    }
}
