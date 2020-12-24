package me.Scyy.Util.GenericJavaPlugin.Config.Managers;

import me.Scyy.Util.GenericJavaPlugin.Config.ConfigFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

/**
 * A manager for a non-specific collection of config files, intended for use with storing data for unknown amounts of config files.
 * e.g. storing data for each player on a server
 */
public class IterableConfigManager implements ConfigManager {

    /**
     * The collection of ConfigFiles
     */
    private final Collection<ConfigFile> configFiles;

    /**
     * Default constructor, configFiles collection is assigned as an empty list
     */
    public IterableConfigManager() {
        this.configFiles = Collections.emptyList();
    }

    /**
     * A config manager with the collection predefined
     * @param configFiles the config files to add - can be empty
     */
    public IterableConfigManager(@NotNull Collection<ConfigFile> configFiles) {
        this.configFiles = configFiles;
    }

    /**
     * Iterates over the config file collection and reloads each of them
     */
    @Override
    public void reloadConfigs() {
        for (ConfigFile file : configFiles) {
            file.reloadConfig();
        }
    }

    /**
     * Gets the config files
     * @return the collection of config files
     */
    public Collection<ConfigFile> getConfigFiles() {
        return this.configFiles;
    }

    /**
     * Follows the format of {@link Collection#add(Object)}
     * @param file the ConfigFile to add
     * @return if the config file was added
     */
    public boolean addConfigFile(ConfigFile file) {
        return this.configFiles.add(file);
    }

    /**
     * Follows the format of {@link Collection#remove(Object)}
     * @param file the ConfigFile to remove
     * @return if the config file was removed
     */
    public boolean removeConfigFile(ConfigFile file) {
        return this.configFiles.remove(file);
    }
}
