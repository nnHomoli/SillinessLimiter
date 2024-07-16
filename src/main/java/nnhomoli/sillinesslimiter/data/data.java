package nnhomoli.sillinesslimiter.data;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class data {
    private final HashMap<String, Object> data = new HashMap<>();
    private YamlConfiguration yaml;

    public void load(SillinessLimiter plugin) {
        File f = new File(plugin.getDataFolder() + "/data.yml");
        if(!f.exists()) {
            try {
                InputStream in = plugin.getResource("default/data.yml");
                Files.copy(in, f.toPath());
            } catch (Exception e) {
                SillinessLimiter.log.info("Failed to copy default data.yml:\n" + e.getMessage());
            }
        }
        yaml = YamlConfiguration.loadConfiguration(f);
        for(String key : yaml.getKeys(false)) {
            data.put(key,yaml.get(key));
        }
    }
    private Object get(String key) {
        return data.get(key);
    }
    public List<Object> getList(String key) {
        Object out = data.get(key);
        if(out instanceof List) {
            return new ArrayList<>((List<?>) out);
        }
        return null;
    }

    public String getDynamicIP(String PlayerName) {
        Object out = data.get(PlayerName + ";dynamic");
        if(out instanceof String) return (String) out;
        return null;
    }

    public Boolean isEnabled(String PlayerName) {
        Object out = data.get(PlayerName + ";enabled");
        if(out instanceof Boolean) return (Boolean) out;
        return null;
    }

    public void set(String key, Object value) {
        data.put(key,value);
    }

    public void save() {
        data.keySet().forEach(key -> yaml.set(key,data.get(key)));
        try {
            yaml.save(new File(SillinessLimiter.getPlugin(SillinessLimiter.class).getDataFolder() + "/data.yml"));
        } catch (IOException e) {
            SillinessLimiter.log.info("Failed to save data.yml " + e.getMessage());
        }
    }

    public void reload() {
        data.clear();
        load(SillinessLimiter.getPlugin(SillinessLimiter.class));
    }
}
