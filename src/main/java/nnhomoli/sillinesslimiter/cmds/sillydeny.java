package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillydeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (IPLock.confirmations.containsKey(p)) {
                IPLock.confirmations.remove(p);
                p.sendMessage(IPLock.lang.get("deny"));
            } else p.sendMessage(IPLock.lang.get("deny_nothing"));
        }
        return true;
    }
}
