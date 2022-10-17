package Summiner.ServerPatches;

import Summiner.ServerPatches.Listeners.Velocity.Config;
import Summiner.ServerPatches.Listeners.Velocity.Data;
import Summiner.ServerPatches.Listeners.Velocity.Manager;
import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@Plugin(
        id = "serverpatches",
        name = "ServerPatches",
        version = "0.0.5",
        dependencies = {@Dependency(id = "protocolize")}
)
public class Velocity {

    static Logger logger;
    public static ProxyServer server;
    public final Path dataDirectory;
    public static Config config;

    public static void logMessage(String msg) {
        logger.info(msg);
    }

    @Inject
    public Velocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        Velocity.server = server;
        Velocity.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onInitialize(ProxyInitializeEvent event) throws IOException {
        Path path = Path.of(dataDirectory+"/config.yml");
        File file = new File(path.toUri());
        new File(dataDirectory.toString()).mkdirs();
        if(!file.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
            writer.write(Config.getDefault());
            writer.close();
        }
        Yaml yaml = new Yaml();
        HashMap<String, Object> a = yaml.load(Files.readString(path));
        config = new Config(new Data(a.get("PacketLimiter"), a.get("ClickEventExploit"), a.get("LecternExploit")));
        new Manager();
        logMessage("Loaded Plugin (Velocity)");
    }
}
