package justaplugin.sillinesslimiter;

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
            List ip = IPLock.getPlugin(IPLock.class).getConfig().getList(p.getName());


            StringBuilder text = new StringBuilder("\n" + IPLock.lang.get("header"));
            if(ip != null) ip.forEach(st -> text.append("\n").append(IPLock.lang.get("ips").replace("%IP%", st.toString())));
            else text.append("\n").append(IPLock.lang.get("no_ips"));
            text.append(IPLock.lang.get("bottom")).append("\n ");

            p.sendMessage(text.toString());
        }
        return true;
    }
}
