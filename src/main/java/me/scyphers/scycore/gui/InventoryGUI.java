package me.scyphers.scycore.gui;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.api.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class InventoryGUI implements InventoryHolder, GUI<InventoryClickEvent> {

    public static final ItemStack BACKGROUND = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(Component.text(" ")).build();

    private static final LegacyComponentSerializer titleFormatter = LegacyComponentSerializer.legacyAmpersand();

    /**
     * The GUI that was open before this one
     */
    protected final GUI<?> lastGUI;

    /**
     * The plugin class
     */
    protected final BasePlugin plugin;

    /**
     * The player viewing this inventory
     */
    protected final Player player;

    /**
     * The array of items in the inventory
     */
    protected ItemStack[] inventoryItems;

    /**
     * The inventory to be displayed to the player
     */
    protected final Inventory inventory;

    /**
     * Name of the GUI, displayed at the top of the inventory
     */
    protected final String name;

    /**
     * If the GUI should close on the next tick instead of reopening a new GUI. Change this in {@link }
     */
    protected boolean shouldClose;

    /**
     * Size of the GUI, also found from {@code inventoryItems.length}
     */
    protected final int size;

    /**
     * @param lastGUI The GUI that was open before this one, or <code>null</code> if opened for the first time
     * @param plugin  The main plugin instance
     * @param player  The player that this GUI is being presented to
     * @param name    Name of the GUI to be displayed
     */
    public InventoryGUI(@Nullable GUI<?> lastGUI, @NotNull BasePlugin plugin, @NotNull Player player, @NotNull String name, int size) {
        this.lastGUI = lastGUI;
        this.plugin = plugin;
        this.player = player;
        this.name = name;
        this.size = size;
        this.inventory = Bukkit.createInventory(this, size, titleFormatter.deserialize(name));
        this.inventoryItems = inventory.getContents();
    }

    @Override
    public abstract @NotNull GUI<?> handleInteraction(InventoryClickEvent event);

    public void onClose(InventoryCloseEvent event) {

    }

    public void draw() {

    }

    protected void prepare() {
        this.draw();
        inventory.setContents(inventoryItems);
    }

    @Override
    public void open(Player player) {
        this.draw();
        inventory.setContents(inventoryItems);
        player.openInventory(inventory);
    }

    public void fill() {
        this.fill(BACKGROUND);
    }

    public void fill(ItemStack itemStack) {
        Arrays.fill(inventoryItems, itemStack);
    }

    /**
     * Utility method for saving time when registering listeners for the GUI.<br>
     * All subclasses of {@link InventoryGUI} will use this listener for triggering their interaction handlers
     * @return the listener for this GUI and all GUI subclasses for it
     * @deprecated in favour of {@link me.scyphers.scycore.gui.InventoryListener}
     */
    @Deprecated
    public static Listener getListener() {
        return new InventoryListener();
    }

    @Override
    public @Nullable GUI<?> getLastGUI() {
        return lastGUI;
    }

    @Override
    public @NotNull BasePlugin getPlugin() {
        return plugin;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean shouldClose() {
        return shouldClose;
    }

    public void setShouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
    }

    /**
     * A check if the player can perform actions in their inventory
     * @return if the player can perform actions in their inventory
     */
    public abstract boolean allowPlayerInventoryEdits();

    private static class InventoryListener implements Listener {
        @EventHandler(priority = EventPriority.HIGHEST)
        public void onInventoryClickEvent(InventoryClickEvent event) {

            // Verify if the inventory interacted with was an InventoryGUI
            // If the inventory interacted with is not a valid GUI then we do not handle this event
            if (!(event.getView().getTopInventory().getHolder() instanceof InventoryGUI oldGUI)) return;

            // Check if the inventory allows player inventory edits, and if so, cancel the interaction
            if (!oldGUI.allowPlayerInventoryEdits() && event.getClickedInventory() instanceof PlayerInventory) {
                event.setCancelled(true);
                return;
            }

            // If the new GUI should close instead of trying to handle new interactions
            if (oldGUI.shouldClose()) {
                Bukkit.getScheduler().runTask(oldGUI.plugin, () -> event.getWhoClicked().closeInventory());
                return;
            }

            // Handle the interact event and open the new inventory
            GUI<?> newGUI = oldGUI.handleInteraction(event);
            Bukkit.getScheduler().runTask(oldGUI.plugin, () -> newGUI.open(oldGUI.getPlayer()));
        }
    }

}
