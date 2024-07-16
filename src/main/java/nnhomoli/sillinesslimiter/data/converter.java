package nnhomoli.sillinesslimiter.data;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class converter {
    public static void convert(String player_name, SillinessLimiter plugin){
        if(SillinessLimiter.udata.getList(player_name) == null && SillinessLimiter.udata.isEnabled(player_name) == null) {
            FileConfiguration cfg = plugin.getConfig();
            String single = cfg.getString(player_name);
            List<?> multiple = cfg.getList(player_name);

            if(single != null || multiple != null) {
                if (single != null && multiple == null) multiple = List.of(cfg.getString(player_name));

                SillinessLimiter.udata.set(player_name, multiple);
                SillinessLimiter.udata.set(player_name + ";enabled", true);

                cfg.set(player_name, null);
                plugin.saveConfig();
                plugin.reloadConfig();

                SillinessLimiter.log.info("Converted " + player_name + " to 1.2");
            } else SillinessLimiter.udata.set(player_name + ";enabled", true);
            SillinessLimiter.udata.save();
        }
    }
}
