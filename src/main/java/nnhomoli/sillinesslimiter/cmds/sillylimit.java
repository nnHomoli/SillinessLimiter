package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Pattern;

public class sillylimit implements CommandExecutor, TabCompleter {
    private final SillinessLimiter plugin;
    private final Pattern ip_pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public sillylimit(SillinessLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            List<?> ip = SillinessLimiter.udata.getList(p.getName());
            if (sillyconfirm.confirmations.containsKey(p)) {
                p.sendMessage(SillinessLimiter.lang.get("confirm_busy"));
                return true;
            }

            if(ip != null && ip.size() >= this.plugin.getConfig().getInt("Max-IP-Allowed")) {
                p.sendMessage(SillinessLimiter.lang.get("maximum_reached"));
                return true;
            }
            String out = "";
            String msg = "";

            if (args.length == 1) {
                if (!this.ip_pattern.matcher(args[0]).matches()) {
                    p.sendMessage(SillinessLimiter.lang.get("invalid_ip"));
                    return true;
                }

                out = args[0];
                msg = SillinessLimiter.lang.get("limit_that");

            } else {

                out = p.getAddress().getAddress().getHostAddress();
                msg = SillinessLimiter.lang.get("limit_this");
            }

            if (ip != null && ip.contains(out)) {
                p.sendMessage(SillinessLimiter.lang.get("limit_already_there"));
                return true;
            }

            sillyconfirm.confirmations.put(p, out);
            p.sendMessage(msg);
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
