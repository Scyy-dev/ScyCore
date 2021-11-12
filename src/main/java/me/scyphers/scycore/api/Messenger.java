package me.scyphers.scycore.api;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Nameable;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;

/**
 * Interface that abstracts the messaging system from an underlying configuration file
 * <br><br>
 *
 * a 'path' will vary depending on implementation, however will generally use a node-like system
 * such that {@link org.bukkit.configuration.file.YamlConfiguration#get(String)} will return the message.
 * Whichever system is used by the implementation, the 'path' must uniquely identify the message.
 * <br><br>
 *
 * While not explicitly defined in this interface, 'replacements' should be treated as a series of key-value pairs
 * where the 'key' is the target string to replace and the 'value' is what is replaced. Since these 'replacements'
 * are treated as pairs, <code>replacements.length % 2 == 0</code> should always return true.
 */
public interface Messenger {

    /* === MESSAGE RETRIEVAL === */

    /**
     * Gets the formatted message from the message file
     * @param path the path of the message in the message file
     * @return the formatted message
     */
    default Component get(@NotNull String path) {
        return this.get(path, new String[0]);
    }

    /**
     * Get a message from the message file
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     * @return The message in a component form
     */
    Component get(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a message from the message file
     * @param path path of the message in the message file
     * @return The unformatted message from the message file
     */
    default String getRaw(@NotNull String path) {
        return this.getRaw(path, new String[0]);
    }

    /**
     * Get a message from the message file
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     * @return the unformatted message from the message file, with with all replacements resolved
     */
    String getRaw(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a list of messages from the message file
     * @param path path of the message in the message file
     * @return the list of messages from the message file, in a form to suit spigots messaging system
     */
    default List<Component> getList(@NotNull String path) {
        return this.getList(path, new String[0]);
    }

    /**
     * Get a list of messages from the message file
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     * @return the list of messages from the message file, in a form to suit spigots messaging system, with all replacements resolved
     */
    List<Component> getList(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a list of messages from the message file
     * @param path path of the message in the message file
     * @return the unformatted list of messages from the message file
     */
    default List<String> getRawList(@NotNull String path) {
        return this.getRawList(path, new String[0]);
    }

    /**
     * Get a list of messages from the message file
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     * @return the unformatted list of the message file, with all replacements resolved
     */
    List<String> getRawList(@NotNull String path, @NotNull String... replacements);

    /* === CHAT MESSAGES === */

    /**
     * Send an applicable recipient a message
     * @param audience recipient(s) of the message
     * @param message the message to send
     */
    void sendChat(@NotNull Audience audience, @NotNull String message);

    /**
     * Send an applicable recipient a message
     * @param audience recipient(s) of the message
     * @param message the message to send
     */
    void chat(@NotNull Audience audience, @NotNull Component message);

    /**
     * Send an applicable recipient a message
     * @param audience recipient of the message
     * @param path path of the message in the message file
     */
    default void chat(@NotNull Audience audience, @NotNull String path) {
        this.chat(audience, path, new String[0]);
    }

    /**
     * Send an applicable recipient a message
     * @param audience recipient(s) of the message
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    void chat(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements);


    /**
     * Send an applicable recipient a list of messages
     * @param audience recipient(s) of the message
     * @param path path of the message in the message file
     */
    default void chatList(@NotNull Audience audience, @NotNull String path) {
        this.chatList(audience, path, new String[0]);
    }

    /**
     * Send an applicable recipient a list of messages
     * @param audience recipient(s) of the message
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    void chatList(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements);

    /* === ACTION BARS === */

    /**
     * Send a player an action bar message
     * @param audience recipient(s) of the action bar message
     * @param message the message to send to the audience
     */
    void sendActionBar(@NotNull Audience audience, @NotNull String message);

    /**
     * Send a player an action bar message
     * @param audience recipient(s) of the action bar message
     * @param message the component form of a message to send to the audience
     */
    default void actionBar(@NotNull Audience audience, @NotNull Component message) {
        audience.sendActionBar(message);
    }

    /**
     * Send a player an action bar message
     * @param audience recipient(s) of the action bar message
     * @param path path of the message in the message file
     */
    default void actionBar(@NotNull Audience audience, @NotNull String path) {
        this.actionBar(audience, path, new String[0]);
    }

    /**
     * Send an audience an action bar message
     * @param audience recipient(s) of the message
     * @param path path of the message in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    void actionBar(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements);
    
    /* === TITLES === */

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param title title to display
     * @param subtitle subtitle to display
     */
    default void sendTitle(@NotNull Audience audience, @NotNull String title, @NotNull String subtitle) {
        this.sendTitle(audience, title, subtitle, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut());
    }

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param title title to display
     * @param subtitle subtitle to display
     * @param fadeIn how long title takes to display
     * @param stay how long the title stays for
     * @param fadeOut how long the title takes to disappear
     */
    void sendTitle(@NotNull Audience audience, @NotNull String title, @NotNull String subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut);

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param titlePath path of the title in the message file
     * @param subtitlePath path of the subtitle in the message file
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath) {
        this.title(audience, titlePath, subtitlePath, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut(), new String[0]);
    }

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param titlePath path of the title in the message file
     * @param subtitlePath path of the subtitle in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull String... replacements) {
        this.title(audience, titlePath, subtitlePath, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut(), replacements);
    }

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param titlePath path of the title in the message file
     * @param subtitlePath path of the subtitle in the message file
     * @param fadeIn how long title takes to display
     * @param stay how long the title stays for
     * @param fadeOut how long the title takes to disappear
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        this.title(audience, titlePath, subtitlePath, fadeIn, stay, fadeOut, new String[0]);
    }

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param titlePath path of the title in the message file
     * @param subtitlePath path of the subtitle in the message file
     * @param fadeIn how long title takes to display
     * @param stay how long the title stays for
     * @param fadeOut how long the title takes to disappear
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut, @NotNull String... replacements);

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param title title to display
     * @param subtitle subtitle to display
     */
    default void title(@NotNull Audience audience, @NotNull Component title, @NotNull Component subtitle) {
        this.title(audience, title, subtitle, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut());
    }

    /**
     * Sends an audience a title message
     * @param audience recipient(s) of the title
     * @param title title to display
     * @param subtitle subtitle to display
     * @param fadeIn how long title takes to display
     * @param stay how long the title stays for
     * @param fadeOut how long the title takes to disappear
     */
    void title(@NotNull Audience audience, @NotNull Component title, @NotNull Component subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut);

    /* === OBJECT NAMING === */

    /**
     * Formats a nameable object (e.g. entity or itemstack)
     * @param nameable the nameable object to name
     * @param name the name to apply to the object
     */
    void formatName(Nameable nameable, String name);

    /**
     * Formats a nameable object, using a name from the message file
     * @param nameable the nameable object to name
     * @param path the path of the name to apply to the object
     */
    default void formatNameFromPath(Nameable nameable, String path) {
        this.formatNameFromPath(nameable, path, new String[0]);
    }

    /**
     * Formats a nameable object, using a name from the message file
     * @param nameable object to name
     * @param path path of the name in the message file
     * @param replacements value-replacement pairs to replace parts of the stored message
     */
    void formatNameFromPath(Nameable nameable, String path, String... replacements);

}
