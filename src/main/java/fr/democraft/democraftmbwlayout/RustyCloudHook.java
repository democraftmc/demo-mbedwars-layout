package fr.democraft.democraftmbwlayout;

import group.aelysium.rustyconnector.toolkit.mc_loader.central.IMCLoaderTinder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import de.marcely.bedwars.api.hook.CloudSystemHook;

import group.aelysium.rustyconnector.toolkit.RustyConnector;
import group.aelysium.rustyconnector.toolkit.mc_loader.server_info.IServerInfoService;

public class RustyCloudHook implements CloudSystemHook {
    private Plugin hookedPlugin;
    private IServerInfoService RustyServer;

    public Plugin getManagingPlugin() {
        Plugin managingPlugin = Bukkit.getPluginManager().getPlugin("DemoCraftMBwLayout");
        this.hookedPlugin = Bukkit.getPluginManager().getPlugin("rustyconnector-paper");
        return managingPlugin;
    }

    public Plugin getHookedPlugin() {
        return this.hookedPlugin;
    }

    public String updateServerUUID() {
        return RustyServer.uuid().toString();
    }

    @Override
    public String getServerName() {
        return RustyServer.uuid().toString();
    }

    @Override
    public void applyInfo(ServerInfoDto serverInfoDto) {

    }
}