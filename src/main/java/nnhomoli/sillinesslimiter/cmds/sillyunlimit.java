package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public final class sillyunlimit implements CommandExecutor, TabCompleter {
    private final userdata user;
    private final LangLoader lang;
    public sillyunlimit(userdata d, LangLoader l) {
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] args) {
        if (sender instanceof Player p) {
            List<?> ip = user.getList(p.getName());
            if(user.confirmationContainsPlayer(p)) {p.sendMessage(lang.getString("confirm_busy"));return true;}
            if(ip == null) {p.sendMessage(lang.getString("unlimit_fail"));return true;}

            if(args.length == 1) {
                if(!user.isIPv4(args[0]) && !user.isIPv6(args[0])) {
                    p.sendMessage(lang.getString("invalid_ip"));
                    return true;
                }
                if (!ip.contains(args[0])) {
                    sender.sendMessage(lang.getString("unlimit_fail"));
                    return true;
                }

                user.setConfirmation(p, args[0]);
                p.sendMessage(lang.getString("unlimit_that"));


            } else {

                if(!ip.contains(p.getAddress().getAddress().getHostAddress())) {
                    p.sendMessage(lang.getString("unlimit_fail"));
                    return true;
                }

                user.setConfirmation(p, null);
                p.sendMessage(lang.getString("unlimit_this"));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, @Nonnull final Command command, @Nonnull final String s, String[] strings) {
        List<?> ip = user.getList(sender.getName());
        if(strings.length == 1 && ip != null) return ip.stream().map(e -> (String) e).toList();
        return null;
    }
}
