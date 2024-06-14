package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillydynamicunlimit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(IPLock.confirmations.containsKey(p)) {
                p.sendMessage(IPLock.lang.get("confirm_busy"));
                return true;
            }

            Object dyn = IPLock.pdata.get(p.getName() + ";dynamic");
            if(dyn == null) {
                p.sendMessage(IPLock.lang.get("dynamic_unlimit_not_linked"));
            } else {
                IPLock.confirmations.put(p, dyn);
                p.sendMessage(IPLock.lang.get("dynamic_unlimit"));
            }
        }
        return true;
    }
}
