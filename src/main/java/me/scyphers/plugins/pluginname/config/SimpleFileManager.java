package me.scyphers.plugins.pluginname.config;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.Bukkit;

public class SimpleFileManager implements FileManager {

    private final Plugin plugin;

    // Config Files
    private final Settings settings;
    private final MessengerFile messengerFile;

    private boolean safeToSave = true;

    private final int saveTaskID;

    public SimpleFileManager(Plugin plugin) throws Exception {
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
    public void saveAll() throws Exception {
        settings.save();
        messengerFile.save();
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
                    saveAll();
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
