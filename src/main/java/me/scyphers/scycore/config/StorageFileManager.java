package me.scyphers.scycore.config;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class StorageFileManager<T extends StorageFile> implements FileManager {
    
    private final BasePlugin plugin;
    
    private final Map<UUID, T> dataFiles;

    private final Set<UUID> fileKeys;

    private final File enclosingFolder;

    private boolean safeToSave = true;

    private final int saveTaskID;
    
    public StorageFileManager(BasePlugin plugin, String dataFolderName) {
        this.plugin = plugin;
        this.dataFiles = new HashMap<>();

        this.enclosingFolder = new File(plugin.getDataFolder(), dataFolderName);
        enclosingFolder.mkdirs();

        int saveTicks = plugin.getSettings().getSaveTicks();
        this.saveTaskID = scheduleSaveTask(saveTicks);

        this.fileKeys = new HashSet<>();

        String[] files = enclosingFolder.list();
        if (files == null || files.length == 0) return;

        for (String fileName : files) {
            String rawUUID = fileName.replace(".yml", "");
            UUID uuid = UUID.fromString(rawUUID);
            fileKeys.add(uuid);
        }

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
        fileKeys.add(uuid);
    }

    public T getStorageFile(UUID uuid) {
        return dataFiles.get(uuid);
    }

    public Set<UUID> getFileKeys() {
        return fileKeys;
    }

    public void clear() {
        this.dataFiles.clear();
    }

    public void removeStorageFile(UUID uuid) {
        if (!dataFiles.containsKey(uuid)) return;
        StorageFile file = dataFiles.get(uuid);
        file.remove();
        dataFiles.remove(uuid);
        this.fileKeys.remove(uuid);
    }

    public void forEachLoadedFile(Consumer<T> consumer) {
        dataFiles.values().forEach(consumer);
    }
    
}
