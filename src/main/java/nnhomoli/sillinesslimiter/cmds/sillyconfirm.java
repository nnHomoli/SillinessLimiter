package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static nnhomoli.sillinesslimiter.misc.ConfirmationTimeout.Timeout;

public class sillyconfirm implements CommandExecutor {
    private final SillinessLimiter plugin;
    private final Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static final HashMap<Player, Object> confirmations = new HashMap<>();

    public sillyconfirm(SillinessLimiter plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player p) {
            List<Object> ip = SillinessLimiter.udata.getList(p.getName());
            if (!confirmations.containsKey(p)) {
                sender.sendMessage(SillinessLimiter.lang.get("confirm_nothing"));
                return true;
            }

            Object out = null;
            Object raw = confirmations.get(p);
            String PlayerName = p.getName();
            String current_ip = p.getAddress().getAddress().getHostAddress();
            String key = "";
            String msg = "";
            String log_msg = "";
            if(raw == null || raw instanceof String) {
                String value = (String) raw;
                if(value == null || this.ip_pattern.matcher(value).matches()) {
                    if (value == null) ip.remove(current_ip);
                    else if (ip != null && ip.contains(value)) {
                        ip.remove(value);
                    } else {
                        if (ip == null) ip = List.of(value);
                        else ip.add(value);
                    }

                    if (ip.isEmpty()) ip = null;

                    key = PlayerName;
                    out = ip;

                    msg = (ip != null && ip.contains(value)) ? SillinessLimiter.lang.get("limit_success") : SillinessLimiter.lang.get("unlimit_success");
                    log_msg = PlayerName + ((value != null) ? " has been linked" : " has been unlinked");
                } else {
                    key = PlayerName + ";dynamic";
                    out = value;

                    Object check = SillinessLimiter.udata.getDynamicIP(PlayerName);
                    if(check != null) out = null;

                    msg = check != null ? SillinessLimiter.lang.get("dynamic_unlimit_success") : SillinessLimiter.lang.get("dynamic_success") ;
                    log_msg = check != null ? PlayerName + " has unlinked dynamic ip" : PlayerName + " has linked dynamic ip";
                }

            } else if(raw instanceof Boolean value) {
                key = PlayerName + ";enabled";
                out = value;

                msg = value ? SillinessLimiter.lang.get("auth_enable") : SillinessLimiter.lang.get("auth_disable");
                log_msg = PlayerName + (value ? " enabled " : " disabled ") + "their auth";
            }

            SillinessLimiter.udata.set(key, out);
            SillinessLimiter.udata.save();

            p.sendMessage(msg);
            SillinessLimiter.log.info(log_msg);

            if (this.plugin.getConfig().getBoolean("check-after-confirm") && SillinessLimiter.udata.isEnabled(PlayerName)) {
                if(!SillinessLimiter.isAllowed(PlayerName, current_ip)) p.kickPlayer(SillinessLimiter.lang.get("kick_reason"));
            }

            confirmations.remove(p);
            Timeout.remove(p);
        }
        return true;
    }
}

