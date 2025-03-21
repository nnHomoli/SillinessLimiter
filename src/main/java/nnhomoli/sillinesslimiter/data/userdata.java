package nnhomoli.sillinesslimiter.data;

import nnhomoli.sillinesslimiter.misc.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.regex.Pattern;

public final class userdata extends data {
    private final Pattern ipv4 = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private final ArrayList<Pattern> ipv4DynamicRanges = new ArrayList<>(Arrays.asList(
            Pattern.compile("(\\d{1,3}\\.)\\*"),
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.)\\*"),
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)\\*")));
    private final Pattern ipv6 = Pattern.compile("^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$");

    private final HashMap<Player, Object> confirmations = new HashMap<>();
    public userdata(JavaPlugin plugin) {
        super(plugin, "data.yml","default/data.yml");
    }

    public void convert(String player_name){
        if(getList(player_name) == null && get(player_name+";enabled") == null) {
            JavaPlugin pl = getPlugin();

            FileConfiguration cfg = pl.getConfig();
            String single = cfg.getString(player_name);
            List<?> multiple = cfg.getList(player_name);

            if(single != null || multiple != null) {
                if (single != null && multiple == null) multiple = List.of(Objects.requireNonNull(cfg.getString(player_name)));

                set(player_name, multiple);
                set(player_name + ";enabled", true);

                cfg.set(player_name, null);
                pl.saveConfig();
                pl.reloadConfig();

                pl.getLogger().info("Converted " + player_name + " to 1.2");
            } else set(player_name + ";enabled", true);
            save();
        }
    }

    public void setConfirmation(Player player, Object confirm) {confirmations.put(player, confirm);}
    public Object getConfirmation(Player player){return confirmations.get(player);}
    public boolean confirmationContainsPlayer(Player player) {return confirmations.containsKey(player);}
    public void removeConfirmation(Player player) {confirmations.remove(player);}

    public String getDynamicIP(String PlayerName) {
        Object out = this.get(PlayerName + ";dynamic");
        if(out instanceof String) return (String) out;
        return null;
    }
    public Boolean isEnabled(String PlayerName) {
        Object out = this.get(PlayerName + ";enabled");
        if(out instanceof Boolean) return (Boolean) out;
        return false;
    }
    public Boolean isIPv4(String address) {return ipv4.matcher(address).matches();}
    public Boolean isDynamicIPv4(String address) {
        for(Pattern pat : ipv4DynamicRanges) {
            if (pat.matcher(address).matches()) {
                return true;
            }
        }
        return false;
    }
    public Boolean isIPv6(String address) {
        if(Objects.equals(address, "::1")) return false;
        return ipv6.matcher(address).matches();
    }

    public boolean disallowAddress(String PlayerName, String IP) {
        String dynamic = this.getDynamicIP(PlayerName);
        List<Object> ip = this.getList(PlayerName);

        boolean allow = false;
        if(dynamic != null) {
            if(IP.contains(dynamic.replace("*", ""))) allow = true;
        }
        if(ip != null) {
            if(ip.contains(IP)) allow = true;
        }
        if(!this.isEnabled(PlayerName) || this.isEnabled(PlayerName) && ip == null && dynamic == null) {
            allow = true;
        }
        return !allow;
    }
}
