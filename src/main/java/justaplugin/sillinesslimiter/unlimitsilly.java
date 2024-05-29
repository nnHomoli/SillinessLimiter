package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unlimitsilly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            if(args.length == 0) {
                Player player = (Player) sender;
                if (IPLock.getPlugin(IPLock.class).getConfig().get(player.getName()) == null) {
                    player.sendMessage(ChatColor.GOLD + "No silliness to unlimit");
                    return true;
                }
                IPLock.getPlugin(IPLock.class).getConfig().set(player.getName(), null);
                IPLock.getPlugin(IPLock.class).saveConfig();
                IPLock.getPlugin(IPLock.class).reloadConfig();

                player.sendMessage(ChatColor.RED + "ip unlinked" + ChatColor.AQUA + ", silliness has been unlimited");
                IPLock.getPlugin(IPLock.class).getLogger().info(player.getName() + " has been unlinked");
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
