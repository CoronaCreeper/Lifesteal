package pl.coronacreeper;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public static Plugin instance;
    public static BukkitScheduler scheduler;

    @Override
    public void onEnable() {
        instance = this;
        scheduler = Bukkit.getScheduler();
        registerRecipe();
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new HeartListener(), this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    double hearts = PlayerProfile.getHearts(p)*2;
                    p.setMaxHealth(hearts);
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
    }

    void registerRecipe() {
        ItemStack heartItem = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heartItem.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN+"Serce");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED+"Magiczny przedmiot, która dodaje ci serce!");
        meta.setLore(lore);
        heartItem.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("heart"), heartItem);
        recipe.shape(
                " D ",
                "DED",
                " D ");
        recipe.setIngredient('D', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('E', Material.ENCHANTED_GOLDEN_APPLE);
        Bukkit.getServer().addRecipe(recipe);
    }
}
