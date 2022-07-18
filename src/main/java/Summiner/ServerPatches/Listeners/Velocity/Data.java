package Summiner.ServerPatches.Listeners.Velocity;

import java.util.HashMap;

public class Data {
    private final HashMap<String, Object> PacketLimiter;
    private final HashMap<String, Object> ClickEventExploit;

    private HashMap<String, Object> getProperMap(Object obj) {
        HashMap<String, Object> a = new HashMap<>();
        String s = obj.toString().replace("{", "").replace("}", "");
        String[] pairs = s.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            a.put(keyValue[0].replaceAll(" ", ""), keyValue[1]);
        }
        return a;
    }

    public Data(Object a, Object b) {
        this.PacketLimiter = getProperMap(a);
        this.ClickEventExploit = getProperMap(b);
    }

    public HashMap<String, Object> getPacketLimiter() {
        return this.PacketLimiter;
    }

    public HashMap<String, Object> getClickEventExploit() {
        return ClickEventExploit;
    }
}
