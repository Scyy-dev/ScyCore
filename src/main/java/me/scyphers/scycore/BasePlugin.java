package me.scyphers.scycore;

import me.scyphers.scycore.api.Messenger;
import me.scyphers.scycore.config.FileManager;
import me.scyphers.scycore.config.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class BasePlugin extends JavaPlugin {

    /**
     * Called when a user tries to reload the plugin with a command
     * @param sender The target that sent the reload command (Console/Player/Command Block)
     */
    public abstract void reload(CommandSender sender);

    /**
     * Measures if the plugin ran into any issues while calling {@link BasePlugin#onEnable()}
     * @return If the plugin was successfully enabled
     */
    public abstract boolean wasSuccessfulEnable();

    /**
     * Gets the file manager for this plugin
     * @return The file manager for this plugin
     */
    public abstract FileManager getFileManager();

    /**
     * Gets the settings for this plugin. Settings are taken from <code>config.yml</code>
     * @return the settings
     */
    public abstract Settings getSettings();

    /**
     * Gets the messenger for this plugin. Messages are configured in <code>messages.yml</code>
     * @return the messenger
     */
    public abstract Messenger getMessenger();

    /**
     * Gets splash text which is displayed when the user provides no arguments for a command, or through an about command
     * @return a list of strings that hold splash text about the plugin
     */
    public abstract List<String> getSplashText();

}
