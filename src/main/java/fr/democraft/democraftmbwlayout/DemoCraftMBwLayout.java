package fr.democraft.democraftmbwlayout;

import de.marcely.bedwars.tools.gui.GUI;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import de.marcely.bedwars.tools.gui.GUIItem;
import org.bukkit.plugin.java.JavaPlugin;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.layout.ShopLayoutHandler;
import de.marcely.bedwars.api.game.shop.layout.ShopLayout;
import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;
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
    }
}
