package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class sillylist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player p) {
            List<Object> ip = SillinessLimiter.udata.getList(p.getName());
            String authstatus = SillinessLimiter.udata.isEnabled(p.getName()) ? SillinessLimiter.lang.get("auth_on") : SillinessLimiter.lang.get("auth_off");
            Object dynamic = SillinessLimiter.udata.getDynamicIP(p.getName());

            StringBuilder text = new StringBuilder("\n" + SillinessLimiter.lang.get("header").replace("%AUTHSTATUS%", authstatus));
            if(ip != null) ip.forEach(st -> text.append("\n").append(SillinessLimiter.lang.get("ips").replace("%IP%", st.toString())));
            else text.append("\n").append(SillinessLimiter.lang.get("no_ips"));
            text.append("\n").append(dynamic != null ? SillinessLimiter.lang.get("dynamic_ip").replace("%IPD%", dynamic.toString()) : SillinessLimiter.lang.get("no_dynamic_ip"));
            text.append(SillinessLimiter.lang.get("bottom").replace("%AUTHSTATUS%", authstatus)).append("\n ");

            p.sendMessage(text.toString());
        }
        return true;
    }
}
