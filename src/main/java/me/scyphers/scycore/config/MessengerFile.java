package me.scyphers.scycore.config;

import me.scyphers.scycore.api.Messenger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.Nameable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.*;
import java.util.regex.Pattern;

public class MessengerFile extends ConfigFile implements Messenger {

    private Map<String, String> messages;

    private Map<String, List<String>> listMessages;

    public static final Pattern hex = Pattern.compile("&#[a-fA-F0-9]{6}");

    // If using this as a template, feel free to customise this exact char - it was chosen at random
    private static final char interactChar = '%';

    private String prefix;

    public MessengerFile(FileManager manager) throws Exception {
        super(manager, "messages.yml");
    }

    @Override
    public void load(YamlConfiguration configuration, YamlConfiguration defaults) throws Exception {
        this.messages = new HashMap<>();
        this.listMessages = new HashMap<>();

        // Loop through every key
        for (String key : configuration.getKeys(true)) {
            if (key.equalsIgnoreCase("prefix")) continue;

            // Check if message is a single line message
            String message = configuration.getString(key, "");
            if (!message.equalsIgnoreCase("")) {
                messages.put(key, message);
                continue;
            }

            // Check if message is a multi-line message
            List<String> listMessage = configuration.getStringList(key);
            if (listMessage.size() != 0) {
                listMessages.put(key, listMessage);
                continue;
            }

            // Something that isn't a message found in config - log it to console
            getManager().getPlugin().getLogger().info("Invalid format for message found at " + key + " in messages.yml");

        }

        // Loop through the defaults to check for any values missing
        for (String key : defaults.getKeys(true)) {
            if (key.equalsIgnoreCase("prefix")) continue;

            // Check if message is a single line message
            String message = defaults.getString(key, "");
            if (!message.equalsIgnoreCase("") && !messages.containsKey(key)) {
                messages.put(key, message);
                continue;
            }

            // Check if message is a multi-line message
            List<String> listMessage = defaults.getStringList(key);
            if (listMessage.size() != 0 && !listMessages.containsKey(key)) {
                listMessages.put(key, listMessage);
            }

        }

        String rawPrefix = configuration.getString("prefix");
        if (rawPrefix != null) this.prefix = rawPrefix;
        else this.prefix = "[COULD_NOT_LOAD_PREFIX] ";

    }

    //  === UTILITY ===

    // Generic message sent if a message cannot be found in messages.yml
    public static Component messageNotFound(String messagePath) {
        return Component.text("Could not not message at " + messagePath);
    }

    public static Component toMessageComponent(String message) {

        Component base = Component.empty();

        StringBuilder sb = new StringBuilder();
        Style.Builder styleBuilder = Style.style();
        for (int i = 0; i < message.length(); i++) {

            char letter = message.charAt(i);

            if ( letter != '&') {
                sb.append(letter);
            } else {
                // Add text and formatting
                if (!sb.toString().equalsIgnoreCase("")) {
                    Component component = Component.text(sb.toString()).style(styleBuilder);
                    base.append(component);
                    sb = new StringBuilder();
                    styleBuilder = Style.style();
                }

                if (message.charAt(i + 1) == '#') {
                    String hex = message.substring(i + 1, i + 8);
                    styleBuilder.color(TextColor.fromCSSHexString(hex));
                    i += 7;
                } else {
                    char colourCode = message.charAt(i + 1);
                    switch (colourCode) {
                        case '0':
                            styleBuilder.color(NamedTextColor.BLACK);
                            break;
                        case '1':
                            styleBuilder.color(NamedTextColor.DARK_BLUE);
                            break;
                        case '2':
                            styleBuilder.color(NamedTextColor.DARK_GREEN);
                            break;
                        case '3':
                            styleBuilder.color(NamedTextColor.DARK_AQUA);
                            break;
                        case '4':
                            styleBuilder.color(NamedTextColor.DARK_RED);
                            break;
                        case '5':
                            styleBuilder.color(NamedTextColor.DARK_PURPLE);
                            break;
                        case '6':
                            styleBuilder.color(NamedTextColor.GOLD);
                            break;
                        case '7':
                            styleBuilder.color(NamedTextColor.GRAY);
                            break;
                        case '8':
                            styleBuilder.color(NamedTextColor.DARK_GRAY);
                            break;
                        case '9':
                            styleBuilder.color(NamedTextColor.BLUE);
                            break;
                        case 'a':
                            styleBuilder.color(NamedTextColor.GREEN);
                            break;
                        case 'b':
                            styleBuilder.color(NamedTextColor.AQUA);
                            break;
                        case 'c':
                            styleBuilder.color(NamedTextColor.RED);
                            break;
                        case 'd':
                            styleBuilder.color(NamedTextColor.LIGHT_PURPLE);
                            break;
                        case 'e':
                            styleBuilder.color(NamedTextColor.YELLOW);
                            break;
                        case 'f':
                            styleBuilder.color(NamedTextColor.WHITE);
                            break;
                        case 'k':
                            styleBuilder.decorate(TextDecoration.OBFUSCATED);
                            break;
                        case 'l':
                            styleBuilder.decorate(TextDecoration.BOLD);
                            break;
                        case 'm':
                            styleBuilder.decorate(TextDecoration.STRIKETHROUGH);
                            break;
                        case 'n':
                            styleBuilder.decorate(TextDecoration.UNDERLINED);
                            break;
                        case 'o':
                            styleBuilder.decorate(TextDecoration.ITALIC);
                            break;
                        case 'r':
                            styleBuilder.decoration(TextDecoration.OBFUSCATED, false);
                            styleBuilder.decoration(TextDecoration.BOLD, false);
                            styleBuilder.decoration(TextDecoration.STRIKETHROUGH, false);
                            styleBuilder.decoration(TextDecoration.UNDERLINED, false);
                            styleBuilder.decoration(TextDecoration.ITALIC, false);
                        default:
                            // bad colour code given, just append text without formatting
                            sb.append("&").append(colourCode);
                    }
                    i += 1;
                }
            }
        }

        Component component = Component.text(sb.toString()).style(styleBuilder);
        return base.append(component);

    }

    public static String replace(String message, String... replacements) {
        if (replacements != null && replacements[0] != null) {

            if (replacements.length % 2 != 0) throw new IllegalArgumentException("Not all placeholders have a corresponding replacement");

            for (int i = 0; i < replacements.length; i += 2) {
                String placeholder = replacements[i];
                String replacement = replacements[i + 1];
                message = message.replaceAll(placeholder, replacement);
            }

        }
        return message;

    }

    private boolean isEmpty(Component component) {
        return Component.EQUALS.test(component, Component.empty());
    }

    // === MESSAGE RETRIEVAL ===

    @Override
    public Component get(@NotNull String path, @NotNull String... replacements) {

        String rawMessage = messages.get(path);
        if (rawMessage == null) return messageNotFound(path);

        if (rawMessage.equals("")) return Component.empty();

        String messagePrefix = "";
        if (!rawMessage.startsWith("[NO_PREFIX]")) {
            messagePrefix = prefix;
        } else {
            rawMessage = rawMessage.substring(11);
        }

        rawMessage = replace(rawMessage, replacements);

        return toMessageComponent(messagePrefix + rawMessage);

    }

    @Override
    public String getRaw(@NotNull String path, @NotNull String... replacements) {

        String rawMessage = messages.get(path);

        if (rawMessage == null) return "Could not find message at " + path;

        if (rawMessage.equalsIgnoreCase("")) return "";

        String messagePrefix = "";

        if (!rawMessage.startsWith("[NO_PREFIX]")) {
            messagePrefix = prefix + " ";
        } else {
            rawMessage = rawMessage.substring(11);
        }

        rawMessage = replace(rawMessage, replacements);

        return messagePrefix + rawMessage;

    }

    @Override
    public List<Component> getList(@NotNull String path, @NotNull String... replacements) {

        List<String> rawList = listMessages.get(path);

        if (rawList == null) {
            getManager().getPlugin().getLogger().warning("No list message found at " + path + " in messages.yml");
            return Collections.emptyList();
        }

        List<Component> list = new LinkedList<>();

        if (rawList.size() == 0) return Collections.singletonList(messageNotFound(path));

        for (String item : rawList) {

            item = replace(item, replacements);

            list.add(toMessageComponent(item));

        }

        return list;

    }

    @Override
    public List<String> getRawList(@NotNull String path, @NotNull String... replacements) {

        List<String> rawList = listMessages.get(path);

        if (rawList == null) {
            getManager().getPlugin().getLogger().warning("No list message found at " + path + " in messages.yml");
            return Collections.emptyList();
        }
        List<String> list = new LinkedList<>();

        if (rawList.size() == 0) return Collections.singletonList("Could not find message at " + path);

        for (String item : rawList) {

            item = replace(item, replacements);
            list.add(item);

        }

        return list;

    }

    // === CHAT MESSAGES ===

    @Override
    public void sendChat(@NotNull Audience audience, @NotNull String message) {
        this.chat(audience, toMessageComponent(message));
    }

    @Override
    public void chat(@NotNull Audience audience, @NotNull Component message) {
        audience.sendMessage(message);
    }

    @Override
    public void chat(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements) {
        Component message = this.get(path, replacements);
        if (isEmpty(message)) return;
        this.chat(audience, message);
    }

    @Override
    public void chatList(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements) {
        for (Component message : this.getList(path, replacements)) {
            this.chat(audience, message);
        }
    }

    // === ACTION BAR MESSAGES ===

    @Override
    public void sendActionBar(@NotNull Audience audience, @NotNull String message) {
        this.actionBar(audience, toMessageComponent(message));
    }

    @Override
    public void actionBar(@NotNull Audience audience, @NotNull String path, String... replacements) {
        String message = this.messages.get(path);
        if ("".equalsIgnoreCase(message)) return;

        // action bars do not have prefixes

        message = replace(message, replacements);

        this.actionBar(audience, toMessageComponent(message));
    }

    // === TITLE MESSAGES ===

    @Override
    public void sendTitle(@NotNull Audience audience, @NotNull String title, @NotNull String subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        Component titleComponent = toMessageComponent(title);
        Component subtitleComponent = toMessageComponent(title);
        Times times = Times.of(fadeIn, stay, fadeOut);
        audience.showTitle(Title.title(titleComponent, subtitleComponent, times));
    }

    @Override
    public void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut, @NotNull String... replacements) {

        String title = messages.getOrDefault(titlePath, "TITLE_NOT_FOUND");
        String subtitle = messages.getOrDefault(subtitlePath, "SUBTITLE_NOT_FOUND");

        title = replace(title, replacements);
        subtitle = replace(subtitle, replacements);

        Component titleComponent = toMessageComponent(title);
        Component subtitleComponent = toMessageComponent(subtitle);

        this.title(audience, titleComponent, subtitleComponent, fadeIn, stay, fadeOut);

    }

    @Override
    public void title(@NotNull Audience audience, @NotNull Component title, @NotNull Component subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        Times times = Times.of(fadeIn, stay, fadeOut);
        audience.showTitle(Title.title(title, subtitle, times));
    }

    /* === OBJECT NAMING === */

    @Override
    public void formatName(Nameable nameable, String name) {
        nameable.customName(toMessageComponent(name));
    }

    @Override
    public void formatNameFromPath(Nameable nameable, String path, String... replacements) {
        String name = messages.getOrDefault(path, path);
        name = replace(name, replacements);

    }

}
