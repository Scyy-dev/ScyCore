package me.scyphers.plugins.pluginname.config;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager implements ConfigManager {
    
    private final me.scyphers.plugins.pluginname.Plugin plugin;
    
    private final Map<UUID, PlayerDataFile> dataFiles;

    private boolean safeToSave = true;

    private final int saveTaskID;
    
    public PlayerDataManager(Plugin plugin) {
        this.plugin = plugin;
        this.dataFiles = new HashMap<>();

        int saveTicks = plugin.getSettings().getSaveTicks();
        this.saveTaskID = scheduleSaveTask(saveTicks);

    }
    
    // This is data storage, as such there is only reading and writing, no reloading.
    @Override
    public void reloadConfigs() throws Exception {
        
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
                    for (PlayerDataFile file : dataFiles.values()) {
                        file.save();
                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Could not save player data config files!");
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

    public PlayerDataFile getPlayerDataFile(UUID uuid) {
        if (dataFiles.containsKey(uuid)) {
            return dataFiles.get(uuid);
        } else {
            PlayerDataFile dataFile = new PlayerDataFile(this, uuid);
            dataFiles.put(uuid, dataFile);
            return dataFile;
        }
    }


    
}
