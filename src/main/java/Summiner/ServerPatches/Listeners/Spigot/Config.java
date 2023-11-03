package Summiner.ServerPatches.Listeners.Spigot;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public final Boolean Packet_enabled;
    public final Long Packet_interval;
    public final Integer Packet_maxRate;
    public final String Packet_kickMessage;
    public final Boolean ClickEventExploit_enabled;
    public final String ClickEventExploit_kickMessage;
    public final Boolean Lectern_enabled;
    public final String Lectern_kickMessage;
    public final Boolean SimplePhysics_enabled;
    public final String Swap_kickMessage;
    public final Boolean Swap_enabled;

    public Config(final FileConfiguration config) {
        this.Packet_enabled = config.getBoolean("PacketLimiter.enabled");
        this.Packet_interval = config.getLong("PacketLimiter.check-interval");
        this.Packet_maxRate = config.getInt("PacketLimiter.max-rate");
        this.Packet_kickMessage = config.getString("PacketLimiter.kick-message");
        this.ClickEventExploit_enabled = config.getBoolean("ClickEventExploit.enabled");
        this.ClickEventExploit_kickMessage = config.getString("ClickEventExploit.kick-message");
        this.Lectern_enabled = config.getBoolean("LecternExploit.enabled");
        this.Lectern_kickMessage = config.getString("LecternExploit.kick-message");
        this.SimplePhysics_enabled = config.getBoolean("SimplePhysics.enabled");
        this.Swap_kickMessage = config.getString("ClickSwapExploit.kick-message");
        this.Swap_enabled = config.getBoolean("ClickSwapExploit.enabled");
    }
}
