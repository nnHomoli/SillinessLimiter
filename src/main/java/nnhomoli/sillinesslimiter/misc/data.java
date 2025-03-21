package nnhomoli.sillinesslimiter.misc;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class data {
    private final HashMap<Object,Object> data = new HashMap<>();
    private YamlConfiguration yaml;

    private final JavaPlugin plugin;
    private final String filename;
    private final String resource;

    public data(JavaPlugin p,String f,String defaultPath) {
        this.plugin = p;
        this.filename = f;
        this.resource = defaultPath;

        setup();
    }

    private void setup() {
        onLoad();
        Load();
        yaml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + filename));
        yamLoad();
    }

    public JavaPlugin getPlugin() {return this.plugin;}
    public YamlConfiguration getYaml() {return this.yaml;}

    public void yamLoad() {
        for(String key : yaml.getKeys(false)) {
            data.put(key,yaml.get(key));
        }
    }

    public void onLoad() {}

    public void Load() {
        File f = new File(plugin.getDataFolder() + "/" + filename);
        if(!f.exists()) {
            if(resource != null) {
                try {
                    f.mkdirs();
                    Files.copy(Objects.requireNonNull(plugin.getResource(resource)), f.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    plugin.getLogger().warning("Failed to copy default "+filename+":\n" +e.getClass()+"\n"+e.getMessage()+"\n"+ Arrays.toString(e.getStackTrace()));
                }
            }
        }
    }

    public Object get(String key) {
        return data.get(key);
    }
    public List<Object> getList(String key) {
        Object out = data.get(key);
        if(out instanceof List) {
            return new ArrayList<>((List<?>) out);
        }
        return null;
    }

    public void set(Object key, Object value) {
        data.put(key,value);
    }

    public void save() {
        data.keySet().forEach(key -> yaml.set((String) key,data.get(key)));
        try {
            yaml.save(new File(plugin.getDataFolder() + "/"+filename));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save "+filename+" " + e.getMessage());
        }
    }

    public void reload() {
        data.clear();
        setup();
    }
}
