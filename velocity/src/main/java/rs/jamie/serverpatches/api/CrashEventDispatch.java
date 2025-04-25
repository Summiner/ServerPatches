package rs.jamie.serverpatches.api;

import java.util.ArrayList;
import java.util.List;

public class CrashEventDispatch {

    private final List<CrashEventListener> listeners = new ArrayList<>();

    public void addListener(CrashEventListener listener) {
        listeners.add(listener);
    }

    public void dispatchEvent(CrashEvent event) {
        for (CrashEventListener listener : listeners) listener.onCrashEvent(event);
    }


}
