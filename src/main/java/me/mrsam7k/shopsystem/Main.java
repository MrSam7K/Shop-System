package me.mrsam7k.shopsystem;

import me.mrsam7k.shopsystem.commands.ShopCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("shop")).setExecutor(new ShopCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
