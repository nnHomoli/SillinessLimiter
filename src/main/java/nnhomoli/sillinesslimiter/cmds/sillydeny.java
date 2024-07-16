package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillydeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player p) {
            if (sillyconfirm.confirmations.containsKey(p)) {
                sillyconfirm.confirmations.remove(p);
                p.sendMessage(SillinessLimiter.lang.get("deny"));
            } else p.sendMessage(SillinessLimiter.lang.get("deny_nothing"));
        }
        return true;
    }
}
