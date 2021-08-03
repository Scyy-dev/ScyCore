package me.scyphers.plugins.pluginname.event;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.event.Listener;

public record EventListener(Plugin plugin) implements Listener {

}
