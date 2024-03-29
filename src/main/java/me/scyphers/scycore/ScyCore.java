package me.scyphers.scycore;

import me.scyphers.scycore.api.PluginSettings;
import me.scyphers.scycore.api.Messenger;
import me.scyphers.scycore.config.FileManager;
import me.scyphers.scycore.api.HeadMetaProvider;
import me.scyphers.scycore.gui.InventoryListener;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ScyCore extends BasePlugin {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        HeadMetaProvider.init(this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
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
        throw new UnsupportedOperationException("Base plugin cannot host a FileManager");
    }

    @Override
    public PluginSettings getSettings() {
        throw new UnsupportedOperationException("Base plugin cannot host a Settings file");
    }

    @Override
    public Messenger getMessenger() {
        throw new UnsupportedOperationException("Base plugin cannot host a Messenger");
    }

    @Override
    public List<String> getSplashText() {
        throw new UnsupportedOperationException("Base plugin cannot host splash text");
    }
}
