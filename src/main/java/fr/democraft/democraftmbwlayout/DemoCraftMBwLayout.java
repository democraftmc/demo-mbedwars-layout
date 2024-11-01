package fr.democraft.democraftmbwlayout;

import de.marcely.bedwars.api.hook.HookAPI;
import de.marcely.bedwars.tools.gui.GUI;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import de.marcely.bedwars.tools.gui.GUIItem;
import group.aelysium.rustyconnector.toolkit.RustyConnector;
import org.bukkit.plugin.java.JavaPlugin;
import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;
import org.bukkit.event.EventHandler;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import de.marcely.bedwars.api.BedwarsAPI;

import group.aelysium.rustyconnector.toolkit.mc_loader.events.magic_link.ConnectedEvent;
import group.aelysium.rustyconnector.toolkit.mc_loader.magic_link.IMagicLinkService;
import group.aelysium.rustyconnector.toolkit.core.events.Event;
import group.aelysium.rustyconnector.toolkit.core.events.Listener;
import group.aelysium.rustyconnector.toolkit.core.events.EventManager;
import group.aelysium.rustyconnector.toolkit.velocity.events.mc_loader.MCLoaderRegisterEvent;
import group.aelysium.rustyconnector.toolkit.mc_loader.central.IMCLoaderTinder;


import java.util.Optional;

public final class DemoCraftMBwLayout extends JavaPlugin implements org.bukkit.event.Listener {

    @Override
    public void onEnable() {
        getLogger().info("Injecting our malicious editing robot into MBedWars...");
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DEMOCRAFT's QuickBuy modifier has been enabled!");
        getLogger().info("Loading RustyConnector MBedWars Hook...");
        HookAPI.get().registerCloudSystemHook(new RustyCloudHook());
        IMCLoaderTinder tinder;
        tinder = RustyConnector.Toolkit.mcLoader().orElseThrow();
        tinder.onStart(flame -> {
            getLogger().warning(flame.versionAsString());
            flame.services().events().on(MCLoaderRegisterEvent.class, new OnMagicLinkLoaded());
        });
        getLogger().info("Rusty Cloud System has been successfully injected. Have fun!");
    }

    public class OnMagicLinkLoaded implements Listener<ConnectedEvent> {
        public void handler(ConnectedEvent event) {
            getLogger().info(new RustyCloudHook().updateServerUUID());
            getLogger().info("Rusty Magic Link booted. Restarting MBedWars instance...");
            BedwarsAPI.reload(null);
        }
    }

    @EventHandler
    public void onShopGUIPostProcess(ShopGUIPostProcessEvent event) {
        // Get the inventory view of the shop GUI
        GUI shopGui = event.getGUI();
        if (shopGui instanceof ChestGUI) {
            ChestGUI chestGui = (ChestGUI) shopGui;

            // Define the original and new positions
            int originalSlot = getConfig().getInt("initial-position");
            int newSlot = getConfig().getInt("edited-position");

            // Get the item in the original slot
            GUIItem itemInQuickSlot = chestGui.getItem(originalSlot);
            GUIItem MENU_ENABLED = chestGui.getItem(9);
            GUIItem MENU_DISABLED = chestGui.getItem(10);

            // Move the item from the original slot to the new slot
            if (itemInQuickSlot != null) {
                chestGui.setItem(itemInQuickSlot, newSlot); // Set item to new slot
                chestGui.setItem(new ItemStack(Material.AIR), originalSlot); // Set item to new slot
                chestGui.setItem(MENU_ENABLED, newSlot + 9);// Set item to new slot
                chestGui.setItem(MENU_DISABLED, 9); // Set item to new slot
            }

            // Set the modified GUI back into the event
            event.setGUI(chestGui);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("DEMOCRAFT's QuickBuy modifier has been disabled!");
    }
}
