package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.List;

public final class sillyconfirm implements CommandExecutor {
    private final JavaPlugin plugin;
    private final LangLoader lang;
    private final userdata user;
    public sillyconfirm(JavaPlugin plugin,LangLoader l,userdata u) {
        this.plugin = plugin;
        this.lang = l;
        this.user = u;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] strings) {
        if (sender instanceof Player p) {
            List<Object> ip = user.getList(p.getName());
            if (!user.confirmationContainsPlayer(p)) {
                sender.sendMessage(lang.getString("confirm_nothing"));
                return true;
            }

            Object out = null;
            Object raw = user.getConfirmation(p);
            String PlayerName = p.getName();
            String current_ip = p.getAddress().getAddress().getHostAddress();
            String key = "";
            String msg = "";
            String log_msg = "";
            if(raw == null || raw instanceof String) {
                String value = (String) raw;
                if(value == null || user.isIPv4(value) || user.isIPv6(value)) {
                    if (value == null) ip.remove(current_ip);
                    else if (ip != null && ip.contains(value)) {
                        ip.remove(value);
                    } else {
                        if (ip == null) ip = List.of(value);
                        else ip.add(value);
                    }

                    if (ip.isEmpty()) ip = null;

                    key = PlayerName;
                    out = ip;

                    msg = (ip != null && ip.contains(value)) ? lang.getString("limit_success") : lang.getString("unlimit_success");
                    log_msg = PlayerName + ((value != null) ? " has linked IP address" : " has unlinked IP address");
                } else {
                    key = PlayerName + ";dynamic";
                    out = value;

                    Object check = user.getDynamicIP(PlayerName);
                    if(check != null) out = null;

                    msg = check != null ? lang.getString("dynamic_unlimit_success") : lang.getString("dynamic_success") ;
                    log_msg = check != null ? PlayerName + " has unlinked dynamic ip" : PlayerName + " has linked dynamic ip";
                }

            } else if(raw instanceof Boolean value) {
                key = PlayerName + ";enabled";
                out = value;

                msg = value ? lang.getString("auth_enable") : lang.getString("auth_disable");
                log_msg = PlayerName + (value ? " enabled " : " disabled ") + "their auth";
            }

            user.set(key, out);
            user.save();

            p.sendMessage(msg);
            plugin.getLogger().info(log_msg);

            if (this.plugin.getConfig().getBoolean("check-after-confirm") && user.isEnabled(PlayerName)) {
                if(user.disallowAddress(PlayerName, current_ip)) p.kickPlayer(lang.getString("kick_reason"));
            }

            user.removeConfirmation(p);
        }
        return true;
    }
}

