package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyswitch implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player p) {
            if (sillyconfirm.confirmations.containsKey(p)) {
                p.sendMessage(SillinessLimiter.lang.get("confirm_busy"));
                return true;
            }

            boolean out;
            if(SillinessLimiter.udata.isEnabled(p.getName())) {
                sillyconfirm.confirmations.put(p, false);
                out = false;
            } else {
                sillyconfirm.confirmations.put(p, true);
                out = true;
            }
            p.sendMessage(SillinessLimiter.lang.get(out ? "switch_to_true" : "switch_to_false"));
        }
        return true;
    }
}
