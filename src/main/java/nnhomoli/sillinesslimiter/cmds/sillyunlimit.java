package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Pattern;

public class sillyunlimit implements CommandExecutor, TabCompleter {
    private final Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            List<?> ip = SillinessLimiter.udata.getList(p.getName());
            if(sillyconfirm.confirmations.containsKey(p) || ip == null) {
                if(sillyconfirm.confirmations.containsKey(p)) p.sendMessage(SillinessLimiter.lang.get("confirm_busy"));
                else p.sendMessage(SillinessLimiter.lang.get("unlimit_fail"));
                return true;
            }

            if(args.length == 1) {
                if(!this.ip_pattern.matcher(args[0]).matches()) {
                    p.sendMessage(SillinessLimiter.lang.get("invalid_ip"));
                    return true;
                }
                if (!ip.contains(args[0])) {
                    sender.sendMessage(SillinessLimiter.lang.get("unlimit_fail"));
                    return true;
                }

                sillyconfirm.confirmations.put(p, args[0]);
                p.sendMessage(SillinessLimiter.lang.get("unlimit_that"));


            } else {

                if(!ip.contains(p.getAddress().getAddress().getHostAddress())) {
                    p.sendMessage(SillinessLimiter.lang.get("unlimit_fail"));
                    return true;
                }

                sillyconfirm.confirmations.put(p, null);
                p.sendMessage(SillinessLimiter.lang.get("unlimit_this"));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List<?> ip = SillinessLimiter.udata.getList(sender.getName());
        if(strings.length == 1 && ip != null) return (List<String>) ip;
        return null;
    }
}
