package me.scyphers.scycore.command;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.api.Messenger;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class BaseCommand {

    private final BasePlugin plugin;

    protected final Messenger m;

    private final Component defaultHelpMessage;

    private final String permission;

    private final int minArgLength;

    /**
     * For creating commands that work with {@link CommandFactory}
     * @param plugin The plugin that the {@link CommandFactory} is linked to
     * @param permission The permission this command requires
     * @param minArgLength The number of arguments this command requires (not including the base command name)
     */
    public BaseCommand(BasePlugin plugin, String permission, int minArgLength) {
        this.plugin = plugin;
        this.m = plugin.getMessenger();
        this.permission = permission;
        this.defaultHelpMessage = defaultHelpMessage();
        this.minArgLength = minArgLength;
    }

    public BasePlugin getPlugin() {
        return plugin;
    }

    public String getPermission() {
        return permission;
    }

    /**
     * A getter for a help message that includes any text formatting, links etc. <br><br>
     * This method by default returns a rather unhelpful message by default, as help is contextual, <br>
     * and this method should be overridden to provide a more useful message
     * @return the help message
     */
    public Component getHelpMessage() {
        return defaultHelpMessage;
    }

    public boolean onBaseCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            m.chat(sender, "errorMessages.noPermission"); return true;
        }
        if (args.length < minArgLength) {
            m.chat(sender, "errorMessages.invalidCommandLength"); return true;
        }
        return onCommand(sender, args);
    }

    public abstract boolean onCommand(CommandSender sender, String[] args);

    public List<String> onBaseTabComplete(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) return null;
        return onTabComplete(sender, args);
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    private Component defaultHelpMessage() {
        return Component.text("Permission required: '" + permission + "', minimum number of arguments: " + minArgLength + "'");
    }

}
