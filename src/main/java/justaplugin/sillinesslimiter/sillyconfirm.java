package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.List;

public class sillyconfirm implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            List ip = IPLock.getPlugin(IPLock.class).getConfig().getList(p.getName());
            if(IPLock.confirmations.containsKey(p)) {

                if(IPLock.confirmations.get(p) == null) ip.remove(p.getAddress().getAddress().getHostAddress());
                else if(ip != null && ip.contains(IPLock.confirmations.get(p))) ip.remove(IPLock.confirmations.get(p));

                else if (ip == null) ip = List.of(IPLock.confirmations.get(p));
                else ip.add(IPLock.confirmations.get(p));

                if(ip.isEmpty()) ip = null;


                IPLock.getPlugin(IPLock.class).getConfig().set(sender.getName(), ip);
                IPLock.getPlugin(IPLock.class).saveConfig();
                IPLock.getPlugin(IPLock.class).reloadConfig();

                p.sendMessage(ChatColor.GOLD + ((IPLock.confirmations.get(p) != null) ? IPLock.lang.get("limit_success") : IPLock.lang.get("unlimit_success")));
                IPLock.log.info(sender.getName() + ((IPLock.confirmations.get(p) != null) ? " has been linked" : " has been unlinked"));
                if(ip != null && !ip.contains(p.getAddress().getAddress().getHostAddress())) p.kickPlayer(IPLock.lang.get("kick_reason"));

                IPLock.confirmations.remove(p);
            } else sender.sendMessage(IPLock.lang.get("confirm_nothing"));
        }
        return true;
    }
}
