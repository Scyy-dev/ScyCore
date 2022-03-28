package me.scyphers.scycore.command.commands;

import me.scyphers.scycore.BasePlugin;
import me.scyphers.scycore.command.BaseCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class ReloadCommand extends BaseCommand {

    public ReloadCommand(BasePlugin plugin, String permission) {
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

    @Override
    public Component getHelpMessage() {
        return m.get("help.reload");
    }
}
