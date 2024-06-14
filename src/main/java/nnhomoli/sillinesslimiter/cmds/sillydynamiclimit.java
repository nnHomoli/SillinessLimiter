package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class sillydynamiclimit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (IPLock.confirmations.containsKey(p)) {
                p.sendMessage(IPLock.lang.get("confirm_busy"));
                return true;
            }
            if(IPLock.pdata.get(p.getName() + ";dynamic") == null) {
                if(args.length == 1) {
                   for(Pattern pat : IPLock.dynamic_ranges) {
                       if(pat.matcher(args[0]).matches()) {
                           p.sendMessage(IPLock.lang.get("dynamic_limit_that"));
                           IPLock.confirmations.put(p, args[0]);
                           return true;
                       }
                   }
                   p.sendMessage(IPLock.lang.get("invalid_dynamic_ip"));
                } else {
                    String[] out = p.getAddress().getAddress().getHostAddress().split("\\.");
                    IPLock.confirmations.put(p, out[0] + "." + out[1] + ".*");
                    p.sendMessage(IPLock.lang.get("dynamic_limit_this"));
                    return true;
                }
            } else p.sendMessage(IPLock.lang.get("dynamic_already_there"));

        }
        return true;
    }
}
