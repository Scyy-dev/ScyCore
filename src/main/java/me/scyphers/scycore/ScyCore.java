package me.scyphers.scycore;

import me.scyphers.scycore.api.FileSettings;
import me.scyphers.scycore.api.Messenger;
import me.scyphers.scycore.config.FileManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ScyCore extends BasePlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void reload(CommandSender sender) {

    }

    @Override
    public boolean wasSuccessfulEnable() {
        return true;
    }

    @Override
    public FileManager getFileManager() {
        throw new IllegalCallerException("Base plugin cannot host a FileManager");
    }

    @Override
    public FileSettings getSettings() {
        throw new IllegalCallerException("Base plugin cannot host a Settings file");
    }

    @Override
    public Messenger getMessenger() {
        throw new IllegalCallerException("Base plugin cannot host a Messenger");
    }

    @Override
    public List<String> getSplashText() {
        throw new IllegalCallerException("Base plugin cannot host splash text");
    }
}
