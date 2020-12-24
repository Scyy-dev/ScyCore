package me.Scyy.Util.GenericJavaPlugin.Config.Managers;

import me.Scyy.Util.GenericJavaPlugin.Config.PlayerMessenger;
import me.Scyy.Util.GenericJavaPlugin.Config.Settings;
import me.Scyy.Util.GenericJavaPlugin.Plugin;

public class SimpleConfigManager implements ConfigManager {

    private final PlayerMessenger playerMessenger;
    private final Settings settings;

    /**
     * Load all configs in
     * @param plugin the plugin to get Plugin data folder references
     */
    public SimpleConfigManager(Plugin plugin) {
        this.playerMessenger = new PlayerMessenger(plugin);
        this.settings = new Settings(plugin);
    }

    /**
     * Reloads all ConfigFiles registered to this handler
     */
    @Override
    public void reloadConfigs() {
        playerMessenger.reloadConfig();
        settings.reloadConfig();
    }

    /**
     * Get the Player Messenger ConfigFile
     * @return the Player Messenger
     */
    public PlayerMessenger getPlayerMessenger() {
        return playerMessenger;
    }

    /**
     * Get the default Settings ConfigFile
     * @return the Settings
     */
    public Settings getSettings() {
        return settings;
    }

}
