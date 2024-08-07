package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class sillydynamiclimit implements CommandExecutor {
    private final ArrayList<Pattern> dynamic_ranges = new ArrayList<>(Arrays.asList(
            Pattern.compile("(\\d{1,3}\\.)\\*"),
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.)\\*"),
            Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)\\*")));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player p) {
            if (sillyconfirm.confirmations.containsKey(p)) {
                p.sendMessage(SillinessLimiter.lang.get("confirm_busy"));
                return true;
            }

            String PlayerName = p.getName();
            if(SillinessLimiter.udata.getDynamicIP(PlayerName) == null) {
                if(args.length == 1) {
                   for(Pattern pat : this.dynamic_ranges) {
                       if(pat.matcher(args[0]).matches()) {
                           p.sendMessage(SillinessLimiter.lang.get("dynamic_limit_that"));
                           sillyconfirm.confirmations.put(p, args[0]);
                           return true;
                       }
                   }
                   p.sendMessage(SillinessLimiter.lang.get("invalid_dynamic_ip"));
                } else {
                    String[] out = p.getAddress().getAddress().getHostAddress().split("\\.");
                    sillyconfirm.confirmations.put(p, out[0] + "." + out[1] + ".*");
                    p.sendMessage(SillinessLimiter.lang.get("dynamic_limit_this"));
                    return true;
                }
            } else p.sendMessage(SillinessLimiter.lang.get("dynamic_already_there"));

        }
        return true;
    }
}
