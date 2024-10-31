package fr.democraft.mrusty;

import de.marcely.bedwars.api.hook.HookAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public final class MRustyBridge extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("Loading RustyConnector MBedWars Hook...");
        HookAPI.get().registerCloudSystemHook(new RustyCloudHook());
        getLogger().info("Rusty Cloud System has been successfully injected. Have fun!");
    }
    @Override
    public void onDisable() {
        getLogger().info("DEMOCRAFT's QuickBuy modifier has been disabled!");
    }
}
