package rs.jamie.serverpatches.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.intellij.lang.annotations.RegExp;

public class TextUtil {
    public static Component formatColor(String str) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(str);
    }

    public static Component replaceText(Component str, @RegExp String toReplace, String replaceWith) {
        return str.replaceText(TextReplacementConfig.builder().match(toReplace).replacement(replaceWith).build());
    }

    public static Component replaceText(Component str, @RegExp String toReplace, Component replaceWith) {
        return str.replaceText(TextReplacementConfig.builder().match(toReplace).replacement(replaceWith).build());
    }

    public static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException ignored) {
            return 0;
        }
    }
}
