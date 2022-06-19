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
        getCommand("withdraw").setExecutor(new WithdrawCommand());
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

    public static ItemStack heart;

    void registerRecipe() {
        ItemStack heartItem = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heartItem.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN+"Serce");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED+"Magiczny przedmiot, która dodaje ci serce!");
        meta.setLore(lore);
        heartItem.setItemMeta(meta);
        heart = heartItem;
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("heart"), heartItem);
        recipe.shape(
                "TDT",
                "DED",
                "TDT");
        recipe.setIngredient('D', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('E', Material.ENCHANTED_GOLDEN_APPLE);
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        Bukkit.getServer().addRecipe(recipe);
    }
}
