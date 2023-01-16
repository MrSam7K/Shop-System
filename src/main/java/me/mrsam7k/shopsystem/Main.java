package me.mrsam7k.shopsystem;

import me.mrsam7k.shopsystem.commands.ShopCommand;
import me.mrsam7k.shopsystem.listeners.PlayerClickInv;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin implements CommandExecutor {

    private Database database;

    @Override
    public void onEnable() {
        try {
            this.database = new Database(this, Database.getConnection());
            database.init();
        } catch (Exception ignored){}

        Objects.requireNonNull(getCommand("shop")).setExecutor(new ShopCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerClickInv(this), this);

    }

    public Database getDatabase() {
        return database;
    }

    public static String toColor(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static net.md_5.bungee.api.ChatColor fromHex(String hex){
        return net.md_5.bungee.api.ChatColor.of(hex);
    }
}
