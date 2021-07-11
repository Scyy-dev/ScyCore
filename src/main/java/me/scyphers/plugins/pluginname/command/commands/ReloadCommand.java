package me.scyphers.plugins.pluginname.command.commands;

import me.scyphers.plugins.pluginname.Plugin;
import me.scyphers.plugins.pluginname.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(Plugin plugin, String permission) {
        super(plugin, permission, 0);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        getPlugin().reload(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
