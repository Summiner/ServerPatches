package summiner.serverpatches.utils;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.intellij.lang.annotations.RegExp;

public class StringFormatter {

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

}
