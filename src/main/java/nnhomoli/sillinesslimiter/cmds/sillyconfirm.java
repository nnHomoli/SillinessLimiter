package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class sillyconfirm implements CommandExecutor {
    private final IPLock plugin;
    private final Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static final HashMap<Player, Object> confirmations = new HashMap<>();

    public sillyconfirm(IPLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            List<Object> ip = IPLock.pdata.getList(p.getName());
            if (!confirmations.containsKey(p)) {
                sender.sendMessage(IPLock.lang.get("confirm_nothing"));
                return true;
            }
            Object value = confirmations.get(p);
            Object out = null;
            String key = "";
            String msg = "";
            String log_msg = "";
            if (value instanceof String || value == null) {
                if(value == null || this.ip_pattern.matcher((String)value).matches()) {
                    if (value == null) ip.remove(p.getAddress().getAddress().getHostAddress());
                    else if (ip != null && ip.contains(value)) {
                        ip.remove(value);
                    } else {
                        if (ip == null) ip = List.of(value);
                        else ip.add(value);
                    }

                    if (ip.isEmpty()) ip = null;

                    key = p.getName();
                    out = ip;

                    msg = (ip != null && ip.contains(value)) ? IPLock.lang.get("limit_success") : IPLock.lang.get("unlimit_success");
                    log_msg = p.getName() + ((value != null) ? " has been linked" : " has been unlinked");
                } else {
                    key = p.getName() + ";dynamic";
                    out = value;

                    Object check = IPLock.pdata.get(key);
                    if(check != null) out = null;

                    msg = check != null ? IPLock.lang.get("dynamic_unlimit_success") : IPLock.lang.get("dynamic_success") ;
                    log_msg = check != null ? p.getName() + " has unlinked dynamic ip" : p.getName() + " has linked dynamic ip";
                }

            } else if (value instanceof Boolean) {
                key = p.getName() + ";enabled";
                out = value;

                msg = (boolean)value ? IPLock.lang.get("auth_enable") : IPLock.lang.get("auth_disable");
                log_msg = p.getName() + ((boolean)value ? " enabled " : " disabled ") + "their auth";
            }

            IPLock.pdata.set(key, out);
            IPLock.pdata.save();

            p.sendMessage(msg);
            IPLock.log.info(log_msg);

            if (this.plugin.getConfig().getBoolean("check-after-confirm") && IPLock.pdata.isEnabled(p.getName())) {
                if(IPLock.checkIP(p.getName(), p.getAddress().getAddress().getHostAddress())) p.kickPlayer(IPLock.lang.get("kick_reason"));
            }

            confirmations.remove(p);
        }
        return true;
    }
}

