package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.AbstractMap.SimpleEntry;

public class unlimitsilly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if(args.length == 0) {
                Player p = (Player) sender;

                if (IPLock.getPlugin(IPLock.class).getConfig().get(p.getName()) == null || IPLock.confirmations.containsKey(p)) {
                    if(IPLock.confirmations.containsKey(p)) p.sendMessage(ChatColor.RED + "You have yet to confirm previous change");
                    else p.sendMessage(ChatColor.GOLD + "No silliness to unlimit");
                    return true;
                }

                IPLock.confirmations.put(p, null);

                p.sendMessage(ChatColor.YELLOW + "Are you sure you want to unlink " + ChatColor.RED + "address" + ChatColor.YELLOW + " from this name?"
                        + ChatColor.GOLD + "\nif so, please use " + ChatColor.AQUA + "/silly-confirm" + ChatColor.GOLD + " or " + ChatColor.AQUA + "/silly-deny");
            }
        }
        if(sender.isOp() && args.length == 1) {
            if (IPLock.getPlugin(IPLock.class).getConfig().get(args[0]) == null) {
                sender.sendMessage(ChatColor.GOLD + "No silliness to unlimit");
                return true;
            }

            IPLock.getPlugin(IPLock.class).getConfig().set(args[0], null);
            IPLock.getPlugin(IPLock.class).saveConfig();
            IPLock.getPlugin(IPLock.class).reloadConfig();

            if(sender instanceof Player) sender.sendMessage(ChatColor.RED + args[0] + " has been unlinked");
            IPLock.getPlugin(IPLock.class).getLogger().info(args[0] + " has been unlinked");
        }
        return true;
    }
}
