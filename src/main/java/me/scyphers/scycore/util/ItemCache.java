package me.scyphers.scycore.util;

import org.bukkit.inventory.ItemStack;

import java.util.Set;

public interface ItemCache {

    ItemStack getItem(String key);

    void addItem(String key, ItemStack item);

    void removeItem(String key);

    Set<String> getKeys();

}
