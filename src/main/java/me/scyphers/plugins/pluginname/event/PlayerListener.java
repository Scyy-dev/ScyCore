package me.scyphers.plugins.pluginname.event;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {

    private final Plugin plugin;

    public PlayerListener(Plugin plugin) {
        this.plugin = plugin;
    }

}
