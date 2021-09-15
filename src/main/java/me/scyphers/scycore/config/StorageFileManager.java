package me.scyphers.scycore.config;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class StorageFileManager<T extends StorageFile> implements FileManager {
    
    private final BasePlugin plugin;
    
    private final Map<UUID, T> dataFiles;

    private final File enclosingFolder;

    private boolean safeToSave = true;

    private final int saveTaskID;
    
    public StorageFileManager(BasePlugin plugin, String dataFolderName) {
        this.plugin = plugin;
        this.dataFiles = new HashMap<>();

        this.enclosingFolder = new File(plugin.getDataFolder(), dataFolderName);
        enclosingFolder.mkdirs();

        int saveTicks = plugin.getFileSettings().getSaveTicks();
        this.saveTaskID = scheduleSaveTask(saveTicks);

    }
    
    // This is data storage, as such there is only reading and writing, no reloading.
    @Override
    public void reloadConfigs() {
        
    }

    @Override
    public void saveAll() throws Exception {
        for (T file : dataFiles.values()) {
            file.save();
        }
    }

    @Override
    public BasePlugin getPlugin() {
        return plugin;
    }

    @Override
    public File getEnclosingFolder() {
        return enclosingFolder;
    }

    @Override
    public int scheduleSaveTask(int saveTicks) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (safeToSave) {
                try {
                    saveAll();
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

    public Set<UUID> getKeys() {
        return dataFiles.keySet();
    }

    public boolean contains(UUID uuid) {
        return dataFiles.containsKey(uuid);
    }

    public void addStorageFile(UUID uuid, T file) {
        dataFiles.put(uuid, file);
    }

    public T getStorageFile(UUID uuid) {
        return dataFiles.get(uuid);
    }

    public Set<UUID> getFileKeys() {

        String[] files = enclosingFolder.list();
        if (files == null || files.length == 0) return Collections.emptySet();

        Set<UUID> keys = new HashSet<>();

        for (String fileName : files) {

            if (fileName.length() != 40) {
                plugin.getLogger().warning("Invalid filename found with name " + fileName);
                continue;
            }

            String rawUUID = fileName.substring(0, 36);

            UUID uuid = UUID.fromString(rawUUID);
            keys.add(uuid);
        }

        return keys;

    }

    public void clear() {
        this.dataFiles.clear();
    }
    
}
