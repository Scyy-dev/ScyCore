package me.Scyy.Util.GenericJavaPlugin;

import me.Scyy.Util.GenericJavaPlugin.Config.ConfigManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {

        // Register the Config Manager
        this.configManager = new ConfigManager(this);


    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Reload all configs registered by the {@link ConfigManager} for this plugin
     * @param sender Output for messages
     */
    public void reload(CommandSender sender) {
        try {
            sender.sendMessage("Reloading...");
            configManager.reloadConfigs();
            sender.sendMessage("Successfully reloaded!");
        } catch (Exception e) {
            sender.sendMessage("Error reloading! Check console for logs!");
            e.printStackTrace();
        }
    }

}
