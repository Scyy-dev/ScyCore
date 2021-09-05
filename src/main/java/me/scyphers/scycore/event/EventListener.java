package me.scyphers.scycore.event;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.event.Listener;

public record EventListener(BasePlugin plugin) implements Listener { }
