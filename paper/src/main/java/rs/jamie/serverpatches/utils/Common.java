package rs.jamie.serverpatches.utils;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.intellij.lang.annotations.RegExp;
import rs.jamie.serverpatches.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class Common {

    public TextComponent formatColor(String str) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(str);
    }

    public Component replaceText(Component str, @RegExp String toReplace, String replaceWith) {
        return str.replaceText(TextReplacementConfig.builder().match(toReplace).replacement(replaceWith).build());
    }

    public Component replaceText(Component str, @RegExp String toReplace, Component replaceWith) {
        return str.replaceText(TextReplacementConfig.builder().match(toReplace).replacement(replaceWith).build());
    }

    public Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException ignored) {
            return 0;
        }
    }

    public void getVersion(Plugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL url = new URL("https://api.github.com/repos/summiner/ServerPatches/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
                String tag = jsonObject.get("tag_name").getAsString().replaceAll("[V|\\-release]", "");
                bufferedReader.close();
                connection.disconnect();
                Bukkit.getScheduler().runTask(plugin, () -> Main.hasUpdate = checkVersion(plugin.getDescription().getVersion(), tag));
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.WARNING, e.toString());
            }
        });
    }

    public boolean checkVersion(String current, String compare) {
        if(current==null||compare==null) return false;
        int[] i1 = splitVersion(compare);
        int[] i2 = splitVersion(current);
        return i1[0]>i2[0]||i1[1]>i2[1]||i1[2]>i2[2];
    }

    private int[] splitVersion(String version) {
        String[] spl = version == null ? null : version.split("\\.");
        if (spl == null || spl.length < 3) {
            return new int[0];
        }
        int[] arr = new int[spl.length];
        for (int i = 0; i < spl.length; i++) {
            arr[i] = parseInt(spl[i]);
        }
        return arr;
    }

}
