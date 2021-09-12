package me.scyphers.scycore.util;

import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface ItemDisplayRegistry {

    ItemStack getDisplay(String key);

    void addDisplay(String key, ItemStack display);

    void removeDisplay(String key);

    Set<String> getKeys();

}
