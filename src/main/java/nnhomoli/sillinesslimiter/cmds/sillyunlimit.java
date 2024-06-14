package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class sillyunlimit implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            List<?> ip = IPLock.pdata.getList(p.getName());
            if(IPLock.confirmations.containsKey(p) || ip == null) {
                if(IPLock.confirmations.containsKey(p)) p.sendMessage(IPLock.lang.get("confirm_busy"));
                else p.sendMessage(IPLock.lang.get("unlimit_fail"));
                return true;
            }

            if(args.length == 1) {
                if(!IPLock.ip_pattern.matcher(args[0]).matches()) {
                    p.sendMessage(IPLock.lang.get("invalid_ip"));
                    return true;
                }
                if (!ip.contains(args[0])) {
                    sender.sendMessage(IPLock.lang.get("unlimit_fail"));
                    return true;
                }

                IPLock.confirmations.put(p, args[0]);
                p.sendMessage(IPLock.lang.get("unlimit_that"));


            } else {

                if(!ip.contains(p.getAddress().getAddress().getHostAddress())) {
                    p.sendMessage(IPLock.lang.get("unlimit_fail"));
                    return true;
                }

                IPLock.confirmations.put(p, null);
                p.sendMessage(IPLock.lang.get("unlimit_this"));
            }
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
