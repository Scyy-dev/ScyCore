package me.scyphers.scycore.config;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.Bukkit;

import java.util.List;

public abstract class BaseConfigFileManager implements FileManager {

    private final BasePlugin plugin;

    // Config Files
    private final Settings settings;
    private final MessengerFile messengerFile;

    private final ConfigFile[] otherFiles;

    private boolean safeToSave = true;

    private final int saveTaskID;

    public BaseConfigFileManager(BasePlugin plugin) throws Exception {
        this.plugin = plugin;
        this.settings = new Settings(this);
        this.messengerFile = new MessengerFile(this);

        this.otherFiles = this.registerOtherFiles();

        // Schedule a repeating task to save the configs
        int saveTicks = settings.getSaveTicks();
        this.saveTaskID = scheduleSaveTask(saveTicks);
    }

    public abstract ConfigFile[] registerOtherFiles();

    @Override
    public void reloadConfigs() throws Exception {
        settings.reload();
        messengerFile.reload();
        for (ConfigFile file : otherFiles) {
            file.reload();
        }
    }

    @Override
    public void saveAll() throws Exception {
        settings.save();
        messengerFile.save();
        for (ConfigFile file : otherFiles) {
            file.reload();
        }
    }

    @Override
    public BasePlugin getPlugin() {
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
