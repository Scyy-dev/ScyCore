package me.scyphers.plugins.pluginname.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class Settings extends ConfigFile {

    private int saveTicks;

    public Settings(ConfigManager manager) {
        super(manager, "config.yml", true);
    }

    @Override
    public void load(YamlConfiguration configuration) throws Exception {
        this.saveTicks = configuration.getInt("fileSaveTicks", 72000);
    }

    // Settings are never updated through code
    @Override
    public boolean saveData(YamlConfiguration configuration) throws Exception {
        return false;
    }

    public int getSaveTicks() {
        return saveTicks;
    }
}
