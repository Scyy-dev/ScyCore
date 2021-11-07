package me.scyphers.scycore.command;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.api.Messenger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CommandFactory implements TabExecutor {

    private final BasePlugin plugin;

    private final Messenger m;

    private final Map<String, BaseCommand> commands;

    private final Consumer<CommandSender> noArgFunction;

    public CommandFactory(BasePlugin plugin, String baseCommandName, Map<String, BaseCommand> commands) {
        this(plugin, baseCommandName, commands, sender -> {
            for (String line : plugin.getSplashText()) {
                plugin.getMessenger().sendChat(sender, line);
            }
        });
    }

    public CommandFactory(BasePlugin plugin, String baseCommandName, Map<String, BaseCommand> commands, Consumer<CommandSender> noArgFunction) {
        this.plugin = plugin;
        this.m = plugin.getMessenger();
        this.commands = commands;
        this.noArgFunction = noArgFunction;

        // Register the command
        PluginCommand command = plugin.getCommand(baseCommandName);
        if (command == null) throw new IllegalArgumentException("Could not find command with name " + baseCommandName);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // No argument handling
        if (args.length == 0) {
            noArgFunction.accept(sender);
            return true;
        }

        BaseCommand baseCommand = commands.get(args[0]);

        if (baseCommand == null) {
            m.chat(sender, "errorMessages.invalidCommand");
            return true;
        }

        return baseCommand.onBaseCommand(sender, args);

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            return commands.keySet().stream().filter(s -> sender.hasPermission(commands.get(s).getPermission())).collect(Collectors.toList());
        }

        BaseCommand baseCommand = commands.get(args[0]);
        if (baseCommand == null) return Collections.emptyList();

        List<String> messages = baseCommand.onTabComplete(sender, args);

        // Filter the responses for what the sender has partially typed
        return messages.stream()
                .filter(s -> s.toLowerCase(Locale.ROOT).startsWith(args[args.length - 1].toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());

    }
}
