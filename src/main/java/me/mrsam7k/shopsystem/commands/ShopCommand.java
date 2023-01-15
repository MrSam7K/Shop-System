package me.mrsam7k.shopsystem.commands;

import me.mrsam7k.shopsystem.menu.ShopMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender  instanceof  Player player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be a player to do this!"));
            return true;
        }
        ShopMenu.open(player);

        return true;
    }
}
