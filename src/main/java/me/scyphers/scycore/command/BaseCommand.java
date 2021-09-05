package me.scyphers.scycore.command;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.api.Messenger;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommand {

    private final BasePlugin plugin;

    protected final Messenger m;

    private final String permission;

    private final int minArgLength;

    public BaseCommand(BasePlugin plugin, String permission, int minArgLength) {
        this.plugin = plugin;
        this.m = plugin.getMessenger();
        this.permission = permission;
        this.minArgLength = minArgLength;
    }

    public BasePlugin getPlugin() {
        return plugin;
    }

    public String getPermission() {
        return permission;
    }

    public boolean onBaseCommand(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) {
            m.msg(sender, "errorMessages.noPermission"); return true;
        }
        if (args.length < minArgLength) {
            m.msg(sender, "errorMessages.invalidCommandLength"); return true;
        }
        return onCommand(sender, args);
    }

    public abstract boolean onCommand(CommandSender sender, String[] args);

    public List<String> onBaseTabComplete(CommandSender sender, String[] args) {
        if (!sender.hasPermission(permission)) return null;
        return onTabComplete(sender, args);
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    protected List<String> itemAmountList() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 65; i++) {
            list.add(Integer.toString(i));
        }
        return list;
    }

}
