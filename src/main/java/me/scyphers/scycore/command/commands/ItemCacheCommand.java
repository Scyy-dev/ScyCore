package me.scyphers.scycore.command.commands;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.command.PlayerCommand;
import me.scyphers.scycore.util.ItemCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemCacheCommand extends PlayerCommand {

    private final ItemCache itemCache;

    public ItemCacheCommand(BasePlugin plugin, String permission, ItemCache itemCache) {
        super(plugin, permission, 3);
        this.itemCache = itemCache;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        String operation = args[1];
        String key = args[2];

        switch (operation.toLowerCase(Locale.ROOT)) {
            case "add" -> {
                ItemStack item = player.getInventory().getItemInMainHand();
                itemCache.addItem(key, item);
                return true;
            }
            case "remove" -> {
                itemCache.removeItem(key);
                return true;
            }
            default -> {
                m.msg(player, "errorMessages.invalidCommandArgument", "%arg%", operation, "%expected%", "add, remove");
                return true;
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        switch (args.length) {
            case 1:
                return new ArrayList<>(Arrays.asList("add", "remove"));
            case 2:
                if (args[1].equalsIgnoreCase("remove")) {
                    return new ArrayList<>(itemCache.getKeys());
                }
            default:
                return Collections.emptyList();
        }

    }

}
