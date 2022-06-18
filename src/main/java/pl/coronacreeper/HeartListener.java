package pl.coronacreeper;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HeartListener implements Listener {

    @EventHandler
    public void onHeartUse(PlayerInteractEvent event) {
        if(event.getItem()==null) return;
        if(!event.getItem().getType().equals(Material.RED_DYE)) return;

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.RED+"Magiczny przedmiot, która dodaje ci serce!");
        if(!event.getItem().getItemMeta().getLore().equals(lore)) return;
        if(PlayerProfile.getHearts(event.getPlayer())>=10) {
            event.getPlayer().sendMessage(ChatColor.RED+"Masz już maksymalną ilość serc");
            return;
        }
        PlayerProfile.setHearts(event.getPlayer(), PlayerProfile.getHearts(event.getPlayer())+1);
        event.getPlayer().sendMessage(ChatColor.GREEN+"Dodano serce!");
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        itemInMainHand.setAmount(itemInMainHand.getAmount()-1);
        event.getPlayer().getInventory().setItemInMainHand(itemInMainHand);
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if(PlayerProfile.isExist(event.getPlayer())) return;
        PlayerProfile.setHearts(event.getPlayer(), 10);
    }
}