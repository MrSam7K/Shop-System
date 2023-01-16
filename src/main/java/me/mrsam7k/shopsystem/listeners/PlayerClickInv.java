package me.mrsam7k.shopsystem.listeners;

import me.mrsam7k.shopsystem.Main;
import me.mrsam7k.shopsystem.PlayerStats;
import me.mrsam7k.shopsystem.menu.ShopMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class PlayerClickInv implements Listener {

    private final Main plugin;

    public PlayerClickInv(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e) throws SQLException {
        if(!e.getView().getTitle().equals("Shop")) return;
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if(item == null || item.getType().equals(Material.BLUE_STAINED_GLASS_PANE)) return;

        String upgradeClicked = ChatColor.stripColor(item.getItemMeta().getDisplayName()).toLowerCase().replace(" ", "_");
        List<Component> lore = item.getItemMeta().lore();
        for(Component comp : lore){
            if(comp.toString().contains("MAXED OUT!")) return;
        }
        PlayerStats stats = this.plugin.getDatabase().findPlayerStatsByUUID(e.getWhoClicked().getUniqueId().toString(), upgradeClicked);
        stats.setTier(stats.getTier() + 1);
        this.plugin.getDatabase().updatePlayerStats(stats, upgradeClicked);
        ShopMenu.open((Player) e.getWhoClicked());

    }
}
