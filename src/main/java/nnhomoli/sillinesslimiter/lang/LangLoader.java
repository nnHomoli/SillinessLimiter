package nnhomoli.sillinesslimiter.lang;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

public class LangLoader {
    HashMap<String, ChatColor> Color = new HashMap<>() {{
        put("%BLACK%", ChatColor.BLACK);
        put("%DARK_BLUE%", ChatColor.DARK_BLUE);
        put("%DARK_GREEN%", ChatColor.DARK_GREEN);
        put("%DARK_AQUA%", ChatColor.DARK_AQUA);
        put("%DARK_RED%", ChatColor.DARK_RED);
        put("%DARK_PURPLE%", ChatColor.DARK_PURPLE);
        put("%GOLD%", ChatColor.GOLD);
        put("%GRAY%", ChatColor.GRAY);
        put("%DARK_GRAY%", ChatColor.DARK_GRAY);
        put("%BLUE%", ChatColor.BLUE);
        put("%GREEN%", ChatColor.GREEN);
        put("%AQUA%", ChatColor.AQUA);
        put("%RED%", ChatColor.RED);
        put("%LIGHT_PURPLE%", ChatColor.LIGHT_PURPLE);
        put("%YELLOW%", ChatColor.YELLOW);
        put("%WHITE%", ChatColor.WHITE);
        put("%MAGIC%", ChatColor.MAGIC);
        put("%BOLD%", ChatColor.BOLD);
        put("%ITALIC%", ChatColor.ITALIC);
        put("%STRIKETHROUGH%", ChatColor.STRIKETHROUGH);
        put("%UNDERLINE%", ChatColor.UNDERLINE);
        put("%RESET%", ChatColor.RESET);
    }};
    HashMap<String,String> map = new HashMap<>();

    public void load(IPLock plugin) {
        File f = new File(plugin.getDataFolder() + "/lang.yml");
        if(!f.exists()) {
            try {
                InputStream in = plugin.getResource("default/lang.yml");
                Files.copy(in, f.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(f);
        for(String key : yaml.getKeys(false)) {
            String out = yaml.getString(key);

            if(out != null) for(String color : Color.keySet()) out = out.replace(color, Color.get(color).toString());
            map.put(key,out);
        }

    }
    public String get(String key) {
        return map.get(key);
    }

    public void reload() {
        map.clear();
        load(IPLock.getPlugin(IPLock.class));
    }
}
