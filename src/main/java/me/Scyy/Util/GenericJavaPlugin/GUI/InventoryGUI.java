package me.Scyy.Util.GenericJavaPlugin.GUI;

import me.Scyy.Util.GenericJavaPlugin.Plugin;
import me.Scyy.Util.GenericJavaPlugin.Util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryGUI implements InventoryHolder {

    /**
     * The inventory to be displayed to the user
     */
    protected final Inventory inventory;

    /**
     * The array of items in the inventory
     */
    protected ItemStack[] inventoryItems;

    /**
     * A reference to the GUI before this one. Null if the GUI system was just opened
     */
    protected final InventoryGUI lastGUI;

    /**
     * Main plugin reference
     */
    protected final Plugin plugin;

    /**
     * Player currently viewing this GUI
     */
    protected final Player player;

    /**
     * Name of the GUI that is displayed at the top
     */
    protected final String name;

    /**
     * Size of the GUI, also found from {@code inventoryItems.length}
     */
    protected final int size;

    /**
     * Flag for if the listener should close the inventory after processing the click
     */
    protected boolean close = false;

    public InventoryGUI(InventoryGUI lastGUI, Plugin plugin, Player player, String name, int size) {
        this.lastGUI = lastGUI;
        this.plugin = plugin;
        this.player = player;
        this.name = name;
        this.size = size;
        this.inventoryItems = initialiseDefaultPage(size);
        this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', name));
    }

    /**
     * Updates the inventory with the inventory items
     * @return the inventory
     */
    @Override
    public @NotNull Inventory getInventory() {

        // Assign the inventory items to the inventory
        inventory.setContents(inventoryItems);

        // Return the inventory
        return inventory;
    }

    /**
     * Provides an array of the inventory items
     * @return the inventory item array
     */
    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }

    /**
     * Sets the inventory items
     * @param inventoryItems the inventory items to set
     */
    public void setInventoryItems(ItemStack[] inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    /**
     * Get the InventoryGUI that was open before this one
     * @return the InventoryGUI
     */
    public InventoryGUI getLastGUI() {
        return lastGUI;
    }

    /**
     * Get the plugin
     * @return the plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Get the Player who is currently viewing this inventory
     * @return the player viewing the inventory
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the name of this GUI shown in the top left of the visual interface
     * @return the name of the GUI
     */
    public String getName() {
        return name;
    }

    /**
     * Get the size of the GUI in inventory slots
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * If the inventory should close on the next tick. Highly recommended to open a 
     * @return
     */
    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    /**
     * Creates a chest of size {@code size} filled with nameless Gray Stained glass panes
     * @param size size of the GUI
     * @return the itemstack array with the glass panes
     */
    private ItemStack[] initialiseDefaultPage(int size) {
        ItemStack[] defaultInv = new ItemStack[size];
        for (int i = 0; i < size; i++) {
            defaultInv[i] = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build();
        }

        return defaultInv;

    }

    public abstract InventoryGUI handleClick(InventoryClickEvent event);



    public abstract boolean allowPlayerInventoryEdits();

}
