package me.scyphers.plugins.pluginname.command;

import me.scyphers.plugins.pluginname.Plugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends BaseCommand {

    public PlayerCommand(Plugin plugin, String permission, int minArgLength) {
        super(plugin, permission, minArgLength);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            m.msg(sender, "errorMessages.mustBePlayer");
            return true;
        }
        return onCommand(player, args);
    }

    public abstract boolean onCommand(Player player, String[] args);
}
