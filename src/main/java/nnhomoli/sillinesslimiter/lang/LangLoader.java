package nnhomoli.sillinesslimiter.lang;

import nnhomoli.sillinesslimiter.misc.data;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public final class LangLoader extends data{

    public LangLoader(JavaPlugin p) {
        super(p,"lang.yml","default/lang.yml");
    }

    @Override
    public void yamLoad() {
        final HashMap<String, ChatColor> colors = new HashMap<>() {{
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

        YamlConfiguration yaml = getYaml();
        for(String key : yaml.getKeys(false)) {
            String out = yaml.getString(key);

            if(out != null) for(String color : colors.keySet()) out = out.replace(color, colors.get(color).toString());
            set(key,out);
        }
    }

    @Override
    public void onLoad() {
        JavaPlugin plugin = this.getPlugin();
        File f = new File(plugin.getDataFolder() + "/lang.yml");
        try {
            if(f.exists()) {
                Update(f,plugin);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to update language file:\n" + e);
        }
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public void Update(File f,JavaPlugin plugin) throws IOException {
        YamlConfiguration existent = YamlConfiguration.loadConfiguration(f);
        YamlConfiguration needed = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(plugin.getResource("default/lang.yml"))));
        needed.getKeys(false).forEach(key -> {
            if(existent.get(key) == null) {
                existent.set(key, needed.get(key));
            }
        });
        existent.save(f);
    }
}
