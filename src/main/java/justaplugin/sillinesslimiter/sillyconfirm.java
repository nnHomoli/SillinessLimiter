package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.Objects;

public class sillyconfirm implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(IPLock.confirmations.containsKey(p)) {

                IPLock.getPlugin(IPLock.class).getConfig().set(sender.getName(), IPLock.confirmations.get(p));
                IPLock.getPlugin(IPLock.class).saveConfig();
                IPLock.getPlugin(IPLock.class).reloadConfig();

                p.sendMessage(ChatColor.GOLD + ((IPLock.confirmations.get(p) != null) ? "Silliness has been limited" : "Silliness has been unlimited"));
                IPLock.getPlugin(IPLock.class).getLogger().info(sender.getName() + ((IPLock.confirmations.get(p) != null) ? " has been linked" : " has been unlinked"));
                if(IPLock.confirmations.get(p) != null && !Objects.equals(IPLock.getPlugin(IPLock.class).getConfig().get(p.getName()), p.getAddress().getAddress().getHostAddress())) p.kickPlayer(IPLock.reason);

                IPLock.confirmations.remove(p);
            } else sender.sendMessage(ChatColor.RED + "Nothing to confirm");
        }
        return true;
    }
}
