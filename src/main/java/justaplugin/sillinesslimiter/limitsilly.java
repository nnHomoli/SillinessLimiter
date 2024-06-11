package justaplugin.sillinesslimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class limitsilly implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            List<?> ip = IPLock.getPlugin(IPLock.class).getConfig().getList(p.getName());
            if (IPLock.confirmations.containsKey(p)) {
                p.sendMessage(IPLock.lang.get("confirm_busy"));
                return true;
            }

            if(ip != null && ip.size() >= IPLock.getPlugin(IPLock.class).getConfig().getInt("Max-IP-Allowed")) {
                p.sendMessage(IPLock.lang.get("maximum_reached"));
                return true;
            }

            if (args.length == 1) {
                if (!IPLock.ip_pattern.matcher(args[0]).matches()) {
                    p.sendMessage(IPLock.lang.get("invalid_ip"));
                    return true;
                }
                if (ip != null && ip.contains(args[0])) {
                    p.sendMessage(IPLock.lang.get("limit_already_there"));
                    return true;
                }

                IPLock.confirmations.put(p, args[0]);
                p.sendMessage(IPLock.lang.get("limit_that"));

            } else {
                if (ip != null && ip.contains(p.getAddress().getAddress().getHostAddress())) {
                    p.sendMessage(IPLock.lang.get("limit_already_there"));
                    return true;
                }

                IPLock.confirmations.put(p, p.getAddress().getAddress().getHostAddress());
                p.sendMessage(IPLock.lang.get("limit_this"));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        List ip = IPLock.getPlugin(IPLock.class).getConfig().getList(sender.getName());
        if(strings.length == 1 && ip != null) return ip;
        return null;
    }
}
