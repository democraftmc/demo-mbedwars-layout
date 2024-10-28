package fr.democraft.democraftmbwlayout;

import de.marcely.bedwars.tools.gui.GUI;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import de.marcely.bedwars.tools.gui.GUIItem;
import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;

import de.marcely.bedwars.api.hook.CloudSystemHook;
import de.marcely.bedwars.api.hook.HookAPI;
import de.marcely.bedwars.api.hook.HookCategory;

import group.aelysium.rusty.connector.toolkit.RustyConnectorAPI;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public final class DemoCraftMBwLayout extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getLogger().info("Injecting our malicous editing robot into MBedWars...");
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DEMOCRAFT's QuickBuy modifier has been enabled!");

        this.rustyAPI = RustyConnectorAPI.initialize(this);

        if (this.rustyAPI != null) {
            getLogger().info("Rusty Connector initialized successfully.");
        } else {
            getLogger().warning("Rusty Connector failed to initialize.");
        }
        HookAPI.registerCloudSystemHook(new RustyCloudSystemHook(this, this.rustyAPI));
        getLogger().info("Rusty Cloud System has been successfully injected. Have fun!");

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

    // Custom CloudSystemHook class for Rusty integration
    static class RustyCloudSystemHook extends CloudSystemHook {
        @Override
        public String getServerName() {
            // Define the logic to retrieve the server's name within the Rusty system
            return "RustyServer"; // Replace with actual logic as needed
        }

        @Override
        public HookCategory getCategory() {
            // Return the category of the cloud system. Adjust based on Rusty’s category.
            return HookCategory.CLOUD_SYSTEM; // Choose appropriate category if available
        }

        public void applyInfo(ServerInfoDto serverInfo) {
            // Define logic to apply cloud-related info to the given game instance
            System.out.println("Applying Rusty Cloud System info to game...");
            // Implement any Rusty-specific logic for setting up game info here
        }

        @Override
        public Plugin getManagingPlugin() {
            return this.plugin;
        }

    }
}