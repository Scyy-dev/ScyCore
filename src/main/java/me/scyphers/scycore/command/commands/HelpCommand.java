package me.scyphers.scycore.command.commands;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.command.BaseCommand;
import me.scyphers.scycore.command.CommandFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HelpCommand extends BaseCommand {

    private final CommandFactory commandFactory;

    public HelpCommand(BasePlugin plugin, String permission, CommandFactory commandFactory) {
        super(plugin, permission, 1);
        this.commandFactory = commandFactory;

    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        if (args.length == 1) {
            m.chat(sender, "helpMessage");
            return true;
        }

        // Get the command
        String commandName = args[1].toLowerCase(Locale.ROOT);
        BaseCommand command = commandFactory.getCommand(commandName);
        if (command == null) {
            m.chat(sender, "errorMessages.invalidCommand");
            return true;
        }

        // Ensure the sender has permission to run the command
        if (!sender.hasPermission(command.getPermission())) {
            m.chat(sender, "errorMessages.noPermission");
            return true;
        }

        // Send the help message
        sender.sendMessage(command.getHelpMessage());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) return new ArrayList<>(commandFactory.getCommandNames());
        return Collections.emptyList();
    }

    @Override
    public Component getHelpMessage() {
        return m.get("helpMessage");
    }
}
