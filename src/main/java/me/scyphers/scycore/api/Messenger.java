package me.scyphers.scycore.api;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Nameable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;


public interface Messenger {

    /* === MESSAGE RETRIEVAL === */

    /**
     * Gets the formatted message from messages.yml
     * @param path the path of the message in messages.yml
     * @return the formatted message
     */
    default Component get(@NotNull String path) {
        return this.get(path, new String[0]);
    }

    /**
     * Get a message from messages.yml
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     * @return The message in a component form
     */
    Component get(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a message from messages.yml
     * @param path path of the message as defined in messages.yml
     * @return The unformatted message from messages.yml
     */
    default String getRaw(@NotNull String path) {
        return this.getRaw(path, new String[0]);
    }

    /**
     * Get a message from messages.yml
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     * @return the unformatted message from messages.yml, with with all replacements resolved
     */
    String getRaw(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a list of messages from messages.yml
     * @param path path of the message as defined in messages.yml
     * @return the list of messages from messages.yml, in a form to suit spigots messaging system
     */
    default List<Component> getList(@NotNull String path) {
        return this.getList(path, new String[0]);
    }

    /**
     * Get a list of messages from messages.yml
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     * @return the list of messages from messages.yml, in a form to suit spigots messaging system, with all replacements resolved
     */
    List<Component> getList(@NotNull String path, @NotNull String... replacements);

    /**
     * Get a list of messages from messages.yml
     * @param path path of the message as defined in messages.yml
     * @return the unformatted list of messages from messages.yml
     */
    default List<String> getRawList(@NotNull String path) {
        return this.getRawList(path, new String[0]);
    }

    /**
     * Get a list of messages from messages.yml
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     * @return the unformatted list of messages.yml, with all replacements resolved
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
     * @param path path of the message as defined in messages.yml
     */
    default void chat(@NotNull Audience audience, @NotNull String path) {
        this.chat(audience, path, new String[0]);
    }

    /**
     * Send an applicable recipient a message
     * @param audience recipient(s) of the message
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     */
    void chat(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements);


    /**
     * Send an applicable recipient a list of messages
     * @param audience recipient(s) of the message
     * @param path path of the message as defined in messages.yml
     */
    default void chatList(@NotNull Audience audience, @NotNull String path) {
        this.chatList(audience, path, new String[0]);
    }

    /**
     * Send an applicable recipient a list of messages
     * @param audience recipient(s) of the message
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
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
     * @param path path of the message as defined in messages.yml
     */
    default void actionBar(@NotNull Audience audience, @NotNull String path) {
        this.actionBar(audience, path, new String[0]);
    }

    /**
     * Send an audience an action bar message
     * @param audience recipient(s) of the message
     * @param path path of the message as defined in messages.yml
     * @param replacements replacements of the message.
     *                     Replacements are formatted as a pair of strings, with the first string being the string to replace, and the second being the new string.
     *                    <br> For example, to replace PLAYER_NAME with Steve and PLAYER_PREFIX with [VIP], the replacements would be
     *                    <code>"PLAYER_NAME", "Steve", "PLAYER_PREFIX", "[VIP]"</code>
     */
    void actionBar(@NotNull Audience audience, @NotNull String path, @NotNull String... replacements);
    
    /* === TITLES === */

    /**
     *
     * @param audience
     * @param title
     * @param subtitle
     */
    default void sendTitle(@NotNull Audience audience, @NotNull String title, @NotNull String subtitle) {
        this.sendTitle(audience, title, subtitle, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut());
    }

    /**
     *
     * @param audience
     * @param title
     * @param subtitle
     * @param fadeIn
     * @param stay
     * @param fadeOut
     */
    void sendTitle(@NotNull Audience audience, @NotNull String title, @NotNull String subtitle, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut);

    /**
     *
     * @param audience
     * @param titlePath
     * @param subtitlePath
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath) {
        this.title(audience, titlePath, subtitlePath, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut(), new String[0]);
    }

    /**
     *
     * @param audience
     * @param titlePath
     * @param subtitlePath
     * @param replacements
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull String... replacements) {
        this.title(audience, titlePath, subtitlePath, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut(), replacements);
    }

    /**
     *
     * @param audience
     * @param titlePath
     * @param subtitlePath
     * @param fadeIn
     * @param stay
     * @param fadeOut
     */
    default void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut) {
        this.title(audience, titlePath, subtitlePath, fadeIn, stay, fadeOut, new String[0]);
    }

    /**
     *
     * @param audience
     * @param titlePath
     * @param subtitlePath
     * @param fadeIn
     * @param stay
     * @param fadeOut
     * @param replacements
     */
    void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut, @NotNull String... replacements);

    /**
     *
     * @param audience
     * @param title
     * @param subtitle
     */
    default void title(@NotNull Audience audience, @NotNull Component title, @NotNull Component subtitle) {
        this.title(audience, title, subtitle, Title.DEFAULT_TIMES.fadeIn(), Title.DEFAULT_TIMES.stay(), Title.DEFAULT_TIMES.fadeOut());
    }

    /**
     *
     * @param audience
     * @param title
     * @param subtitle
     * @param fadeIn
     * @param stay
     * @param fadeOut
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
     * Formats a nameable object, using a name from messages.yml
     * @param nameable the nameable object to name
     * @param path the path of the name to apply to the object
     */
    default void formatNameFromPath(Nameable nameable, String path) {
        this.formatNameFromPath(nameable, path, new String[0]);
    }

    /**
     *
     * @param nameable
     * @param path
     * @param replacements
     */
    void formatNameFromPath(Nameable nameable, String path, String... replacements);

}
