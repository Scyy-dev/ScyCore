package me.scyphers.plugins.pluginname.config;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class SimpleConfigManager implements ConfigManager {

    private final Plugin plugin;

    // Config Files
    private final Settings settings;
    private final MessengerFile messengerFile;

    private boolean safeToSave = true;

    private final int saveTaskID;

    public SimpleConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.settings = new Settings(this);
        this.messengerFile = new MessengerFile(this);

        // Schedule a repeating task to save the configs
        int saveTicks = settings.getSaveTicks();
        this.saveTaskID = scheduleSaveTask(saveTicks);
    }

    @Override
    public void reloadConfigs() throws Exception {
        settings.reload();
        messengerFile.reload();
    }

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public int scheduleSaveTask(int saveTicks) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (safeToSave) {
                try {
                    YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settings.getConfigFile());
                    settings.saveData(settingsConfig);
                    YamlConfiguration messengerConfig = YamlConfiguration.loadConfiguration(messengerFile.getConfigFile());
                    messengerFile.saveData(messengerConfig);
                } catch (Exception e) {
                    plugin.getLogger().warning("Could not save config files!");
                    e.printStackTrace();
                    safeToSave = false;
                }
            }

        }, saveTicks, saveTicks);
    }

    @Override
    public void cancelSaveTask() {
        Bukkit.getScheduler().cancelTask(saveTaskID);
    }

    public Settings getSettings() {
        return settings;
    }

    public MessengerFile getMessenger() {
        return messengerFile;
    }
}
