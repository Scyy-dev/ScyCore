package me.scyphers.scycore.config;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.Bukkit;

public class SimpleFileManager implements FileManager {

    private final BasePlugin plugin;

    private final MessengerFile messengerFile;

    private boolean safeToSave = true;

    private int saveTaskID;

    public SimpleFileManager(BasePlugin plugin) throws Exception {
        this.plugin = plugin;
        this.messengerFile = new MessengerFile(this);

        // Schedule a repeating task to save the configs
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            int saveTicks = plugin.getSettings().getSaveTicks();
            this.saveTaskID = scheduleSaveTask(saveTicks);
        });
    }

    @Override
    public void reloadConfigs() throws Exception {
        messengerFile.reload();
    }

    @Override
    public void saveAll() throws Exception {
        messengerFile.save();
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

    public MessengerFile getMessenger() {
        return messengerFile;
    }
}
