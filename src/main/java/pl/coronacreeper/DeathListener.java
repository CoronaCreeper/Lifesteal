package pl.coronacreeper;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

import static pl.coronacreeper.Main.scheduler;
import static pl.coronacreeper.PlayerProfile.getHearts;

public class DeathListener implements Listener {

    private static HashMap<Player, Integer> tasks = new HashMap<>();
    public static HashMap<Player, Player> killers = new HashMap<>();

    @EventHandler
    public void death(PlayerDeathEvent event) {
        Player p = event.getPlayer();
        PlayerProfile.setHearts(p, getHearts(p)-1);
        if(getHearts(p)==0) {
            p.kickPlayer(ChatColor.RED+"Nie masz już serc!");
        }
        if(killers.get(p) == null) return;
        Player damager = killers.get(p);
        if(PlayerProfile.getHearts(damager)<20) {
            PlayerProfile.setHearts(damager, getHearts(damager)+1);
            damager.sendMessage(ChatColor.GREEN+"Zyskałeś 1 serce zabijając "+p.getName()+"!");
        }
        if(!(tasks.get(p)==null)) {
            scheduler.cancelTask(tasks.get(p));
        }
        killers.remove(p);
    }

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent event) {
        Player damager = null;
        Player taker = null;
        BukkitScheduler scheduler = Main.scheduler;
        if (!(event.getEntity() instanceof Player)) return;
        if(event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if(!(arrow.getShooter() instanceof Player)) return;
            damager = (Player) arrow.getShooter();
            taker = (Player) event.getEntity();
        }
        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
            taker = (Player) event.getEntity();
        }
        if(damager==null || taker==null) return;
        if(damager.equals(taker)) return;
        if(!(tasks.get(taker)==null)) {
            scheduler.cancelTask(tasks.get(taker));
        }
        killers.put(taker, damager);
        Player finalTaker = taker;
        tasks.put(taker, scheduler.runTaskLater(Main.instance, new Runnable() {
            @Override
            public void run() {
                killers.remove(finalTaker);
            }
        }, 20*60).getTaskId());
    }
}
