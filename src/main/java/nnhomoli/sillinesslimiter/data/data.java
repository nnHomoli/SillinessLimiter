package nnhomoli.sillinesslimiter.data;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class data {
    HashMap<String, Object> data = new HashMap<>();
    YamlConfiguration yaml;

    public void load(IPLock plugin) {
        File f = new File(plugin.getDataFolder() + "/data.yml");
        if(!f.exists()) {
            try {
                InputStream in = plugin.getResource("default/data.yml");
                Files.copy(in, f.toPath());
            } catch (Exception e) {
                IPLock.log.info("Failed to create data.yml " + e.getMessage());
            }
        }
        yaml = YamlConfiguration.loadConfiguration(f);
        for(String key : yaml.getKeys(false)) {
            data.put(key,yaml.get(key));
        }
    }
    public Object get(String key) {
        return data.get(key);
    }
    public List<Object> getList(String key) {
        Object out = data.get(key);
        if(out instanceof List) {
            return new ArrayList<>((List<Object>) out);
        };
        return null;
    }
    public Boolean getBoolean(String key) {
        Object out = data.get(key);
        if(out instanceof Boolean) return (Boolean) out;
        return null;
    }

    public Boolean isEnabled(String key) {
        Object out = data.get(key + ";enabled");
        if(out instanceof Boolean) return (Boolean) out;
        return null;
    }

    public void set(String key, Object value) {
        data.put(key,value);
    }

    public void save() {
        data.keySet().forEach(key -> yaml.set(key,data.get(key)));
        try {
            yaml.save(new File(IPLock.getPlugin(IPLock.class).getDataFolder() + "/data.yml"));
        } catch (IOException e) {
            IPLock.log.info("Failed to save data.yml " + e.getMessage());
        }
    }

    public void reload() {
        data.clear();
        load(IPLock.getPlugin(IPLock.class));
    }
}
