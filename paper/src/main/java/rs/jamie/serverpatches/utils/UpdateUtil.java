package rs.jamie.serverpatches.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class UpdateUtil {
    public static CompletableFuture<Boolean> hasUpdate(Plugin plugin) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL("https://api.github.com/repos/summiner/ServerPatches/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
                String tag = jsonObject.get("tag_name").getAsString().replaceAll("[V|\\-release|\\-HOTFIX]" , "");
                bufferedReader.close();
                connection.disconnect();
                return checkVersion(plugin.getDescription().getVersion(), tag);
            } catch (IOException e) {
                Bukkit.getLogger().log(Level.WARNING, e.toString());
            }
            return false;
        });
    }

    public static boolean checkVersion(String current, String compare) {
        if (current == null || compare == null) return false;
        int[] i1 = splitVersion(compare);
        int[] i2 = splitVersion(current);
        return i1[0] > i2[0] || i1[1] > i2[1] || i1[2] > i2[2];
    }

    private static int[] splitVersion(String version) {
        String[] spl = version == null ? null : version.split("\\.");
        if (spl == null || spl.length < 3) {
            return new int[0];
        }
        int[] arr = new int[spl.length];
        for (int i = 0; i < spl.length; i++) {
            arr[i] = TextUtil.parseInt(spl[i]);
        }
        return arr;
    }
}
