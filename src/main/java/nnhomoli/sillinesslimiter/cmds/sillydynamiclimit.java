package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class sillydynamiclimit implements CommandExecutor {
    private final userdata user;
    private final LangLoader lang;
    public sillydynamiclimit(userdata d, LangLoader l) {
        this.user = d;
        this.lang = l;
    }


    @Override
    public boolean onCommand(@Nonnull final CommandSender sender,@Nonnull final  Command command, @Nonnull final String s, @Nonnull final String[] args) {
        if(sender instanceof Player p) {
            if (user.confirmationContainsPlayer(p)) {
                p.sendMessage(lang.getString("confirm_busy"));
                return true;
            }

            String PlayerName = p.getName();
            if(user.getDynamicIP(PlayerName) == null) {
                if(args.length == 1 && user.isIPv6(args[0]) ||args.length < 1 && user.isIPv6(p.getAddress().getAddress().getHostAddress())) {
                    p.sendMessage(lang.getString("dynamic_unsupported"));
                    return true;
                }

                if(args.length == 1) {
                   if(user.isDynamicIPv4(args[0])) {
                       p.sendMessage(lang.getString("dynamic_limit_that"));
                       user.setConfirmation(p, args[0]);
                       return true;
                   }
                   p.sendMessage(lang.getString("invalid_dynamic_ip"));
                } else {
                    String[] out = p.getAddress().getAddress().getHostAddress().split("\\.");
                    user.setConfirmation(p, out[0] + "." + out[1] + ".*");
                    p.sendMessage(lang.getString("dynamic_limit_this"));
                    return true;
                }
            } else p.sendMessage(lang.getString("dynamic_already_there"));

        }
        return true;
    }
}
