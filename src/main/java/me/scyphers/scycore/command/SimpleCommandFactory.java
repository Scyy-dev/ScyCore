package me.scyphers.scycore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public abstract class SimpleCommandFactory implements TabExecutor {

    private final Consumer<CommandSender> noArgConsumer;

    public SimpleCommandFactory(Consumer<CommandSender> noArgConsumer) {
        this.noArgConsumer = noArgConsumer;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // No argument handling
        if (args.length == 0) {
            noArgConsumer.accept(sender);
            return true;
        }

        return this.onCommand(sender, args);

    }

    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args);

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return this.onTabComplete(sender, args);
    }

    public abstract List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args);

}
