package fr.democraft.democraftmbwlayout;

import de.marcely.bedwars.api.game.shop.layout.ShopLayoutHandler;
import de.marcely.bedwars.tools.Helper;
import de.marcely.bedwars.tools.NMSHelper;
import de.marcely.bedwars.tools.gui.GUI;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import de.marcely.bedwars.api.game.shop.ShopPage;
import de.marcely.bedwars.api.game.shop.ShopItem;
import de.marcely.bedwars.api.game.shop.price.ShopPrice;
import de.marcely.bedwars.api.game.shop.BuyGroup;
import de.marcely.bedwars.tools.gui.CenterFormat;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DemoShopLayout implements ShopLayoutHandler {

    private static final ItemStack GLASS_CENTER = Helper.get().parseItemStack("WHITE_STAINED_GLASS_PANE {DisplayName:\" \"}");
    private static final ItemStack GLASS_SELECTED = Helper.get().parseItemStack("RED_STAINED_GLASS_PANE {DisplayName:\" \"}");

    @Override
    public GUI build(OpenEvent event) {
        final Player player = event.getPlayer();
        final ChestGUI gui = new ChestGUI(6, event.getDefaultGUITitle());

        // pages
        if (event.getOpenPage() != null) {
            event.getOpenPage().setIcon(NMSHelper.get().setGlowEffect(event.getOpenPage().getIcon(), true));
            gui.setTitle(event.getOpenPage().getDisplayName());
        }

        for (ShopPage page : event.getPages())
            gui.addItem(event.build(page));

        for (int x = 0; x < 9; x++)
            gui.setItem(GLASS_CENTER, x, 2);

        final List<BuyGroup> items = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            // Add group icon
            

        // items
        if (event.getOpenPage() != null) {
            // glass
            gui.setHeight(gui.getHeight() + 1);


            // items
            int height = gui.getHeight() - 2;

            for (int iRow = 0; iRow < (event.getPageItems().size() + 8) / 9; iRow++) {
                if (gui.getHeight() + 2 > 6) {
                    getLogger(event).warning("The shop layout does not support that amount of content!");
                    break;
                }

                final List<ShopItem> items = new ArrayList<>();
                final int length = Math.min(event.getPageItems().size() - iRow * 9, 9);
                for (int i = 0; i < length; i++)
                    items.add(event.getPageItems().get(i + iRow * 9));

                gui.setHeight(gui.getHeight() + 2);
                height += 2;

            }
        }

        gui.formatAnyRow(CenterFormat.ALIGNED);

        return gui;
    }

    private static Logger getLogger(OpenEvent event) {
        return event.getLayout().getPlugin().getLogger();
    }

    private static ItemStack setDisplayName(ItemStack is, String name) {
        final ItemMeta im = is.getItemMeta();

        im.setDisplayName(name);

        is.setItemMeta(im);

        return is;
    }
}
