package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public final class sillylimit implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private final userdata user;
    private final LangLoader lang;
    public sillylimit(JavaPlugin p, userdata d, LangLoader l) {
        this.plugin = p;
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender,@Nonnull final Command command, @Nonnull final String s,@Nonnull final String[] args) {
        if (sender instanceof Player p) {
            List<?> ip = this.user.getList(p.getName());
            if (user.confirmationContainsPlayer(p)) {
                p.sendMessage(this.lang.getString("confirm_busy"));
                return true;
            }

            if(ip != null && ip.size() >= this.plugin.getConfig().getInt("Max-IP-Allowed")) {
                p.sendMessage(this.lang.getString("maximum_reached"));
                return true;
            }
            String out;
            String msg;

            if (args.length == 1) {
                if (!user.isIPv4(args[0]) && !user.isIPv6(args[0])) {
                    p.sendMessage(this.lang.getString("invalid_ip"));
                    return true;
                }

                out = args[0];
                msg = this.lang.getString("limit_that");

            } else {

                out = p.getAddress().getAddress().getHostAddress();
                msg = this.lang.getString("limit_this");
            }

            if (ip != null && ip.contains(out)) {
                p.sendMessage(this.lang.getString("limit_already_there"));
                return true;
            }

            user.setConfirmation(p, out);
            p.sendMessage(msg);
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
