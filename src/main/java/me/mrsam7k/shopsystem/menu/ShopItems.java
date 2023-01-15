package me.mrsam7k.shopsystem.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.text.NumberFormat;
import java.util.Locale;

public enum ShopItems {
    BUNNY_MULTIPLIER("Bunny Multiplier", "#9FFF1F", new String[]{"Gain more " + ChatColor.GREEN + "Bunny Points" + "[RESET] from feeding", "bunnies"}, 70, 10, 10, Material.RABBIT_SPAWN_EGG),
    STAR_MULTIPLIER("Star Multiplier", "#FCCA24", new String[]{"Gain more &aBunny Points [RESET] from feeding", "bunnies"}, 70, 25, 25, Material.HONEYCOMB),
    LUCKY_BASKET("Lucky Basket", "#EFFC41", new String[]{"Gain more " + ChatColor.GREEN + "Bunny Points" + "[RESET] from feeding", "bunnies"}, 51, 50, 50, Material.BROWN_SHULKER_BOX);


    //SPEED(13);

    public final String name;
    public final String hexColor;
    public final String[] description;
    public final int tiers;
    public final int startPrice;
    public final int priceStep;
    public final Material material;
    ShopItems(String name, String hexColor, String[] description, int tiers, int startPrice, int priceStep, Material material){
        this.name = name;
        this.hexColor = hexColor;
        this.description = description;
        this.tiers = tiers;
        this.startPrice = startPrice;
        this.priceStep = priceStep;
        this.material = material;
    }

    public static String getTier(ShopItems item, int currentTier){
        switch (item){
            case BUNNY_MULTIPLIER -> { return ChatColor.GREEN + "+" + currentTier + " Points"; }
            case STAR_MULTIPLIER -> { return ChatColor.GOLD + "+" + currentTier + " Stars"; }
            case LUCKY_BASKET -> {
                int min = currentTier * 25;
                int max = currentTier * 50;
                return ChatColor.GOLD + NumberFormat.getInstance(Locale.US).format(min) + "-" + NumberFormat.getInstance(Locale.US).format(max) + " Stars";

            }

        }
        return "";
    }
}
