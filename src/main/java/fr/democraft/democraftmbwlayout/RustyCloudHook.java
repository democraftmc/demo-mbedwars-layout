package fr.democraft.democraftmbwlayout;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import de.marcely.bedwars.api.hook.CloudSystemHook;

import group.aelysium.rustyconnector.toolkit.mc_loader.server_info.IServerInfoService;

public class RustyCloudHook implements CloudSystemHook {
    private Plugin managingPlugin;
    private Plugin hookedPlugin;
    private IServerInfoService RustyServer;

    public void RustyConnectorPlugin(JavaPlugin plugin) {
        this.managingPlugin = plugin;
        this.hookedPlugin = Bukkit.getPluginManager().getPlugin("rustyconnector-paper");
    }

    public Plugin getManagingPlugin() {
        return this.managingPlugin;
    }

    public Plugin getHookedPlugin() {
        return this.hookedPlugin;
    }

    @Override
    public String getServerName() {
        return RustyServer.uuid().toString();
    }

    @Override
    public void applyInfo(ServerInfoDto serverInfoDto) {

    }
}