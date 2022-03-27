package me.scyphers.scycore.command.commands;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.command.BaseCommand;
import me.scyphers.scycore.command.CommandFactory;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleHelpCommand extends BaseCommand {

    private final CommandFactory commandFactory;

    public SimpleHelpCommand(BasePlugin plugin, String permission, CommandFactory commandFactory) {
        super(plugin, permission, 1);
        this.commandFactory = commandFactory;
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        for (BaseCommand command : commandFactory.getCommands()) {
            if (!sender.hasPermission(command.getPermission())) continue;
            sender.sendMessage(command.getHelpMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) return new ArrayList<>(commandFactory.getCommandNames());
        return Collections.emptyList();
    }

}
