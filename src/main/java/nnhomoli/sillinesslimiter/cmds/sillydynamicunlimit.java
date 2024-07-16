package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillydynamicunlimit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player p){
            if(sillyconfirm.confirmations.containsKey(p)) {
                p.sendMessage(SillinessLimiter.lang.get("confirm_busy"));
                return true;
            }

            Object dyn = SillinessLimiter.udata.getDynamicIP(p.getName());
            if(dyn == null) {
                p.sendMessage(SillinessLimiter.lang.get("dynamic_unlimit_not_linked"));

            } else {
                sillyconfirm.confirmations.put(p, dyn);
                p.sendMessage(SillinessLimiter.lang.get("dynamic_unlimit"));
            }
        }
        return true;
    }
}
