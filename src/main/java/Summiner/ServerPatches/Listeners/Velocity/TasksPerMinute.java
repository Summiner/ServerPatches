package Summiner.ServerPatches.Listeners.Velocity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TasksPerMinute {

    private final String name;
    private final HashMap<UUID, Integer> execs = new HashMap<>();
    Timer timer = new Timer();

    public TasksPerMinute(String a, Long b) {
        this.name = a;
        TimerTask reload = new TimerTask() {@Override public void run() {execs.clear();}};
        long c = b*1000;
        timer.scheduleAtFixedRate(reload, c, c);
    }

    public String getName() {
        return name;
    }

    public Integer getExecutions(UUID a) {
        return execs.get(a);
    }

    public Integer addExecution(UUID a) {// returns for ease of use
        execs.putIfAbsent(a, 0);
        execs.replace(a, execs.get(a)+1);
        return execs.get(a);
    }
}
