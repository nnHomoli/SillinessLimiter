package nnhomoli.sillinesslimiter.data;

import nnhomoli.sillinesslimiter.IPLock;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.List;

public class converter {
    public static void convert(String player_name, IPLock plugin){
        if(IPLock.pdata.get(player_name) == null && IPLock.pdata.isEnabled(player_name) == null) {
            FileConfiguration cfg = plugin.getConfig();
            String single = cfg.getString(player_name);
            List<?> multiple = cfg.getList(player_name);

            if(single != null || multiple != null) {
                if (single != null && multiple == null) multiple = List.of(cfg.getString(player_name));

                IPLock.pdata.set(player_name, multiple);
                IPLock.pdata.set(player_name + ";enabled", true);

                cfg.set(player_name, null);
                plugin.saveConfig();
                plugin.reloadConfig();

                IPLock.log.info("Converted " + player_name + " to 1.2");
            } else IPLock.pdata.set(player_name + ";enabled", true);
            IPLock.pdata.save();
        }
    }
}
