package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyswitch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (IPLock.confirmations.containsKey(p)) {
                p.sendMessage(IPLock.lang.get("confirm_busy"));
                return true;
            }

            boolean out;
            if(IPLock.pdata.isEnabled(p.getName())) {
                IPLock.confirmations.put(p, false);
                out = false;
            } else {
                IPLock.confirmations.put(p, true);
                out = true;
            }
            p.sendMessage(IPLock.lang.get(out ? "switch_to_true" : "switch_to_false"));
        }
        return true;
    }
}
