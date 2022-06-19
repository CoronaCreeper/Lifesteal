package pl.coronacreeper;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static pl.coronacreeper.PlayerProfile.getHearts;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        PlayerProfile.setHearts(p, getHearts(p)-1);
        p.getInventory().addItem(Main.heart);
        p.sendMessage(ChatColor.GREEN+"Wypłacono serce!");
        return false;
    }
}
