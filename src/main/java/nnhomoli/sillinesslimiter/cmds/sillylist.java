package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class sillylist implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            List<Object> ip = IPLock.pdata.getList(p.getName());
            String authstatus = IPLock.pdata.isEnabled(p.getName()) ? IPLock.lang.get("auth_on") : IPLock.lang.get("auth_off");
            Object dynamic = IPLock.pdata.get(p.getName() + ";dynamic");

            StringBuilder text = new StringBuilder("\n" + IPLock.lang.get("header").replace("%AUTHSTATUS%", authstatus));
            if(ip != null) ip.forEach(st -> text.append("\n").append(IPLock.lang.get("ips").replace("%IP%", st.toString())));
            else text.append("\n").append(IPLock.lang.get("no_ips"));
            text.append("\n").append(dynamic != null ? IPLock.lang.get("dynamic_ip").replace("%IPD%", dynamic.toString()) : IPLock.lang.get("no_dynamic_ip"));
            text.append(IPLock.lang.get("bottom").replace("%AUTHSTATUS%", authstatus)).append("\n ");

            p.sendMessage(text.toString());
        }
        return true;
    }
}
