package me.scyphers.scycore.config;

import me.scyphers.scycore.util.ItemDisplayRegistry;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemDisplayRegistryFile extends ConfigStorageFile implements ItemDisplayRegistry {

    private Map<String, ItemStack> displayItems;

    /**
     * Loads a file that the plugin manages using the FileManager
     *
     * @param manager  the manager for this file
     * @param filePath the path of the file, starting from this plugins data folder
     * @throws Exception if an exception occurs while initialising the file or reading data from it
     */
    public ItemDisplayRegistryFile(FileManager manager, String filePath) throws Exception {
        super(manager, filePath);
    }

    @Override
    public void load(YamlConfiguration yamlConfiguration) throws Exception {
        this.displayItems = new HashMap<>();

        for (String displayKey : yamlConfiguration.getKeys(false)) {
            ItemStack itemStack = yamlConfiguration.getItemStack(displayKey);
            displayItems.put(displayKey, itemStack);
        }

    }

    @Override
    public void saveData(YamlConfiguration yamlConfiguration) throws Exception {
        for (String displayKey : displayItems.keySet()) {
            yamlConfiguration.set(displayKey, displayItems.get(displayKey));
        }
    }

    @Override
    public ItemStack getDisplay(String key) {
        ItemStack display = displayItems.get(key);
        return display == null ? new ItemStack(Material.STONE) : display.clone();
    }

    @Override
    public void addDisplay(String key, ItemStack display) {
        displayItems.put(key, display);
    }

    @Override
    public void removeDisplay(String key) {
        displayItems.remove(key);
    }

    @Override
    public Set<String> getKeys() {
        return displayItems.keySet();
    }
}
