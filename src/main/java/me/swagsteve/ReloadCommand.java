package me.swagsteve;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (p.isOp() || p.hasPermission("100egg.commands.reload")) {

                //Reload
                Egg.getInstance().reloadConfig();

                //Cache new config
                Egg.egg_percentage = Egg.getInstance().getConfig().getInt("Setup.egg-percentage");
                Egg.is_adult = Egg.getInstance().getConfig().getBoolean("Setup.is-adult");

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&e100Egg&r] &a&lConfig successfully reloaded!"));
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&e100Egg&r] &c&lYou don't have permission to use this command!"));
            }
        }
        return false;
    }
}
