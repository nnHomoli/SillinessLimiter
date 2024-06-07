package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
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

            StringBuilder text = new StringBuilder("\n");
            text.append(IPLock.lang.get("header"));
            List ip = IPLock.getPlugin(IPLock.class).getConfig().getList(p.getName());
            if(ip != null) ip.forEach(st -> text.append(IPLock.lang.get("ips").replace("%IP%", st.toString())));
            else text.append(IPLock.lang.get("no_ips"));
            text.append(IPLock.lang.get("bottom"));
            text.append("\n ");

            p.sendMessage(text.toString());
        }
        return true;
    }
}
