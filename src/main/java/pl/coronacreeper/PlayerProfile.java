package pl.coronacreeper;

import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerProfile {

    public static boolean isExist(Player p) {
        File f = new File("plugins/Lifesteal/data.yml");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        return file.contains(p.getName());
    }

    public static int getHearts(Player p) {
        File f = new File("plugins/Lifesteal/data.yml");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        return file.getInt("hearts."+p.getName());
    }

    public static int getHearts(String p) {
        File f = new File("plugins/Lifesteal/data.yml");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        return file.getInt("hearts."+p);
    }

    @SneakyThrows
    public static void setHearts(Player p, int amount) {
        File f = new File("plugins/Lifesteal/data.yml");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        file.set("hearts."+p.getName(), amount);
        file.save(f);
    }

    @SneakyThrows
    public static void setHearts(String p, int amount) {
        File f = new File("plugins/Lifesteal/data.yml");
        YamlConfiguration file = YamlConfiguration.loadConfiguration(f);
        file.set("hearts."+p, amount);
        file.save(f);
    }
}
