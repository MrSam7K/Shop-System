package me.mrsam7k.shopsystem.menu;

import me.mrsam7k.shopsystem.Main;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopMenu {
    private static final Plugin plugin = Main.getPlugin(Main.class);

    public static void open(Player player) {
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
            itemIndex++;
            while (glassSlots.contains(itemIndex)) {
                itemIndex++;
            }

            ItemStack item = new ItemStack(shopItem.material);
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.DARK_GRAY + "Upgrade");
            lore.add("");
            lore.add(ChatColor.LIGHT_PURPLE + "Description:");

            lore.add(net.md_5.bungee.api.ChatColor.of("#5d91a6") + "\u23F5 " + net.md_5.bungee.api.ChatColor.of("#B1E6FC") + shopItem.description[0].replace("[RESET]", net.md_5.bungee.api.ChatColor.of("#B1E6FC") + ""));
            int i = -1;
            for (String s : shopItem.description) {
                i++;
                s.replace("[RESET]", net.md_5.bungee.api.ChatColor.of("#B1E6FC") + "");
                if (i == 0) continue;
                lore.add("  " + net.md_5.bungee.api.ChatColor.of("#B1E6FC") + s);
            }
            lore.add("");

                lore.add(ChatColor.LIGHT_PURPLE + "Current Tier:");
                int unlockedBars = (int) Math.floor((20d / shopItem.tiers) * 50);
                int nextBars = (int) Math.floor(Math.floor((21d / shopItem.tiers) * 50) - unlockedBars);
                int remainingBars = (int) Math.floor(50 - (unlockedBars + nextBars));
                String unlockedBar = net.md_5.bungee.api.ChatColor.of(shopItem.hexColor) + "|";

                String progressBar = net.md_5.bungee.api.ChatColor.of("#5d91a6") + "\u23F5 " +
                        ChatColor.GRAY + "[" +
                        unlockedBar.repeat(unlockedBars) +
                        ChatColor.GRAY + "|".repeat(nextBars) +
                        ChatColor.DARK_GRAY + "|".repeat(remainingBars) +
                        ChatColor.GRAY + "]";
                lore.add(progressBar);

                lore.add("");
                lore.add(net.md_5.bungee.api.ChatColor.GRAY + "Current Tier: " + ShopItems.getTier(shopItem, 20));
                lore.add("");
                lore.add(net.md_5.bungee.api.ChatColor.of(shopItem.hexColor) + "Click to purchase!");

                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setLore(lore);
                itemMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of(shopItem.hexColor) + shopItem.name);
                item.setItemMeta(itemMeta);
                inv.setItem(itemIndex, item);
            }


            player.openInventory(inv);
        }
    }

