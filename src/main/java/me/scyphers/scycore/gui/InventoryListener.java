package me.scyphers.scycore.gui;

import me.scyphers.scycore.BasePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

public record InventoryListener(BasePlugin plugin) implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClickEvent(InventoryClickEvent event) {

        // Verify if the inventory interacted with was an InventoryGUI
        // If the inventory interacted with is not a valid GUI then we do not handle this event
        if (!(event.getView().getTopInventory().getHolder() instanceof InventoryGUI oldGUI)) return;

        // Only allow the attached plugin to listen to this click event
        if (!oldGUI.getPlugin().getName().equals(plugin.getName())) return;

        // Check if the inventory allows player inventory edits, and if so, cancel the interaction
        if (!oldGUI.allowPlayerInventoryEdits() && event.getClickedInventory() instanceof PlayerInventory) {
            event.setCancelled(true);
            return;
        }

        // If the new GUI should close instead of trying to handle new interactions
        if (oldGUI.shouldClose()) {
            plugin.getServer().getScheduler().runTask(oldGUI.plugin, () -> event.getWhoClicked().closeInventory());
            return;
        }

        // Handle the interact event and open the new inventory
        GUI<?> newGUI = oldGUI.handleInteraction(event);
        plugin.getServer().getScheduler().runTask(oldGUI.plugin, () -> newGUI.open(oldGUI.getPlayer()));
    }

}
