package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class sillylimit implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            List<?> ip = IPLock.pdata.getList(p.getName());
            if (IPLock.confirmations.containsKey(p)) {
                p.sendMessage(IPLock.lang.get("confirm_busy"));
                return true;
            }

            if(ip != null && ip.size() >= IPLock.getPlugin(IPLock.class).getConfig().getInt("Max-IP-Allowed")) {
                p.sendMessage(IPLock.lang.get("maximum_reached"));
                return true;
            }
            String out = "";
            String msg = "";

            if (args.length == 1) {
                if (!IPLock.ip_pattern.matcher(args[0]).matches()) {
                    p.sendMessage(IPLock.lang.get("invalid_ip"));
                    return true;
                }

                out = args[0];
                msg = IPLock.lang.get("limit_that");

            } else {

                out = p.getAddress().getAddress().getHostAddress();
                msg = IPLock.lang.get("limit_this");
            }

            if (ip != null && ip.contains(out)) {
                p.sendMessage(IPLock.lang.get("limit_already_there"));
                return true;
            }

            IPLock.confirmations.put(p, out);
            p.sendMessage(msg);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List ip = IPLock.pdata.getList(sender.getName());
        if(strings.length == 1 && ip != null) return ip;
        return null;
    }
}
