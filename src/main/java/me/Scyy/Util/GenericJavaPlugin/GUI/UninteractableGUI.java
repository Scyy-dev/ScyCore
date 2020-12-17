package me.Scyy.Util.GenericJavaPlugin.GUI;

import me.Scyy.Util.GenericJavaPlugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UninteractableGUI extends InventoryGUI {

    public UninteractableGUI(InventoryGUI lastGUI, Plugin plugin, Player player) {
        super(lastGUI, plugin, player, lastGUI.name, lastGUI.size);

        this.inventoryItems = lastGUI.getInventoryItems();
    }

    @Override
    public InventoryGUI handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        return this;
    }

    @Override
    public boolean allowPlayerInventoryEdits() {
        return false;
    }
}
