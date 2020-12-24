package me.Scyy.Util.GenericJavaPlugin;

import me.Scyy.Util.GenericJavaPlugin.Command.AdminCommand;
import me.Scyy.Util.GenericJavaPlugin.Config.Managers.SimpleConfigManager;
import me.Scyy.Util.GenericJavaPlugin.Config.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Plugin extends JavaPlugin {

    private SimpleConfigManager configManager;

    @Override
    public void onEnable() {

        // Register the Config Manager
        this.configManager = new SimpleConfigManager(this);

        // Register the Admin Command
        AdminCommand adminCommand = new AdminCommand(this);
        this.getCommand("admin").setExecutor(adminCommand);
        this.getCommand("admin").setTabCompleter(adminCommand);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Reload all configs registered by the {@link SimpleConfigManager} for this plugin
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

    /**
     * Get the Settings for this plugin, each defined in config.yml
     * @return the Settings
     */
    public Settings getSettings() {
        return configManager.getSettings();
    }

    /**
     * Provides a bit of information about the plugin
     * @return the splash text
     */
    public List<String> getSplashText() {
        return Arrays.asList(
                ChatColor.translateAlternateColorCodes('&', "PLUGIN_NAME v" + getDescription().getVersion()), "Built by _Scyy");
    }

    /**
     * @return the Config Manager
     */
    public SimpleConfigManager getConfigManager() {
        return configManager;
    }
}
