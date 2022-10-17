package Summiner.ServerPatches.Listeners.Velocity;

import java.util.HashMap;

public class Config {

    public Boolean Packet_enabled;
    public Long Packet_interval;
    public Integer Packet_maxRate;
    public String Packet_kickMessage;
    public Boolean ClickEventExploit_enabled;
    public String ClickEventExploit_kickMessage;
    public Boolean LecternExploit_enabled;
    public String LecternExploit_kickMessage;

    public Config(Data config) {
        HashMap<String, Object> PacketLimiter = config.getPacketLimiter();
        HashMap<String, Object> ClickEventExploit = config.getClickEventExploit();
        HashMap<String, Object> LecternExploit = config.getLecternExploit();
        this.Packet_enabled = Boolean.valueOf(PacketLimiter.get("enabled").toString());
        this.Packet_interval = Long.valueOf(PacketLimiter.get("check-interval").toString());
        this.Packet_maxRate = Integer.valueOf(PacketLimiter.get("max-rate").toString());
        this.Packet_kickMessage = PacketLimiter.get("kick-message").toString();
        this.ClickEventExploit_enabled = Boolean.valueOf(ClickEventExploit.get("enabled").toString());
        this.ClickEventExploit_kickMessage = ClickEventExploit.get("kick-message").toString();
        this.LecternExploit_enabled = Boolean.valueOf(LecternExploit.get("enabled").toString());
        this.LecternExploit_kickMessage = LecternExploit.get("kick-message").toString();
    }

    public static String getDefault() {
        return "PacketLimiter: #Global\n" +
                "  enabled: false\n" +
                "  check-interval: 60 #Time in ticks\n" +
                "  max-rate: 200\n" +
                "  kick-message: \"Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host\"\n" +
                "ClickEventExploit: #1.17.x & Below\n" +
                "  enabled: true\n" +
                "  kick-message: \"Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host\"\n" +
                "LecternExploit:\n" +
                "  enabled: true\n" +
                "  kick-message: \"Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host\"\n" +
                "#Adding More Soon!";
    }

}
