package fr.democraft.democraftmbwlayout;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.layout.ShopLayoutHandler;
import de.marcely.bedwars.api.game.shop.layout.ShopLayout;
import de.marcely.bedwars.api.game.shop.layout.ShopLayoutType;
public final class DemoCraftMBwLayout extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MyPlugin has been enabled!");
        GameAPI.get().registerShopLayout(new ShopLayout() {
            final DemoShopLayout handler = new DemoShopLayout();

            @Override
            public String getName() {
                return "DemoShopLayout"; // the name within your shop.yml
            }

            @Override
            public boolean isBeta() {
                return false;
            }

            @Override
            public JavaPlugin getPlugin() {
                return DemoCraftMBwLayout.this;
            }

            @Override
            public ShopLayoutHandler getHandler() {
                return this.handler;
            }
        });
    }

    @Override
    public void onDisable() {
    }
}
