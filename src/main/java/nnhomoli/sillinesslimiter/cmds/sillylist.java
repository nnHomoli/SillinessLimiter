package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.data.userdata;
import nnhomoli.sillinesslimiter.lang.LangLoader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public final class sillylist implements CommandExecutor {
    private final userdata user;
    private final LangLoader lang;
    public sillylist(userdata d, LangLoader l) {
        this.user = d;
        this.lang = l;
    }

    @Override
    public boolean onCommand(@Nonnull final CommandSender sender, @Nonnull final Command command, @Nonnull final String s, @Nonnull final String[] strings) {
        if(sender instanceof Player p) {
            List<Object> ip = user.getList(p.getName());
            String authstatus = user.isEnabled(p.getName()) ? lang.getString("auth_on") : lang.getString("auth_off");
            Object dynamic = user.getDynamicIP(p.getName());

            StringBuilder text = new StringBuilder("\n" + lang.getString("header").replace("%AUTHSTATUS%", authstatus));
            if(ip != null) ip.forEach(st -> text.append("\n").append(lang.getString("ips").replace("%IP%", st.toString())));
            else text.append("\n").append(lang.getString("no_ips"));
            text.append("\n").append(dynamic != null ? lang.getString("dynamic_ip").replace("%IPD%", dynamic.toString()) : lang.getString("no_dynamic_ip"));
            text.append(lang.getString("bottom").replace("%AUTHSTATUS%", authstatus)).append("\n ");

            p.sendMessage(text.toString());
        }
        return true;
    }
}
