package me.mrsam7k.shopsystem.menu;

import me.mrsam7k.shopsystem.Database;
import me.mrsam7k.shopsystem.Main;
import me.mrsam7k.shopsystem.PlayerStats;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopMenu {
    private static final Plugin plugin = Main.getPlugin(Main.class);

    public static void open(Player player) throws SQLException {
        Inventory inv = plugin.getServer().createInventory(null, 45, "Shop");
        Integer[] glassSlotsArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 41, 42, 43, 44};
        List<Integer> glassSlots = Arrays.asList(glassSlotsArray);

        for (int glassInt : glassSlots) {
            ItemStack glass = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
            ItemMeta glassMeta = glass.getItemMeta();
            glassMeta.displayName(Component.text(""));
            glass.setItemMeta(glassMeta);

            inv.setItem(glassInt, glass);
        }
        int itemIndex = -1;
        for (ShopItems shopItem : ShopItems.values()) {
            //Get player's data from database
            PlayerStats ps = Database.findPlayerStatsByUUID(player.getUniqueId().toString(), shopItem.name);
            if(ps == null){
                for(ShopItems shopItem2 : ShopItems.values()){
                    ps = new PlayerStats(player.getUniqueId().toString() + "_" + shopItem2.name.replace(" ", "_").toLowerCase(), 0);
                   Database.createPlayerStats(ps);
                }
            }
            double tier = (double) ps.getTier();

            itemIndex++;
            while (glassSlots.contains(itemIndex)) {
                itemIndex++;
            }

            ItemStack item = new ItemStack(shopItem.material);
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.DARK_GRAY + "Upgrade");
            lore.add("");
            lore.add(ChatColor.LIGHT_PURPLE + "Description:");

            lore.add(Main.fromHex("#5d91a6") + "\u23F5 " +
                    Main.fromHex("#B1E6FC") +
                    Main.toColor(shopItem.description[0]).replace("[RESET]", Main.fromHex("#B1E6FC") +
                            ""));
            int i = -1;
            for (String s : shopItem.description) {
                i++;
                s.replace("[RESET]", Main.fromHex("#B1E6FC") + "");
                if (i == 0) continue;
                lore.add("  " + Main.fromHex("#B1E6FC") + Main.toColor(s));
            }
            lore.add("");

                lore.add(ChatColor.LIGHT_PURPLE + "Current Tier:");
                int unlockedBars = (int) Math.floor((tier / shopItem.tiers) * 50);
                int nextBars = (int) Math.floor(Math.floor(((double) (tier + 1) / shopItem.tiers) * 50) - unlockedBars);
                int remainingBars = (int) Math.floor(50 - (unlockedBars + nextBars));
                String unlockedBar = Main.fromHex(shopItem.hexColor) + "|";

                String progressBar = Main.fromHex("#5d91a6") + "\u23F5 " +
                        ChatColor.GRAY + "[" +
                        unlockedBar.repeat(unlockedBars) +
                        ChatColor.GRAY + "|".repeat(nextBars) +
                        ChatColor.DARK_GRAY + "|".repeat(remainingBars) +
                        ChatColor.GRAY + "]";
                lore.add(progressBar);
                lore.add("");
                if(tier > 0) {
                    lore.add(ChatColor.GRAY + "Current Tier: " + ShopItems.getTier(shopItem, (int) tier));
                    lore.add("");
                }
                if(tier == shopItem.tiers){
                    lore.add(Main.fromHex("#65CF15") + Main.toColor("&lMAXED OUT!"));
                } else{
                    lore.add(Main.fromHex(shopItem.hexColor) + "Click to purchase!");
                }

                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(Main.fromHex(shopItem.hexColor) + shopItem.name);
                item.setItemMeta(itemMeta);
                inv.setItem(itemIndex, item);
            }


            player.openInventory(inv);
        }
    }

