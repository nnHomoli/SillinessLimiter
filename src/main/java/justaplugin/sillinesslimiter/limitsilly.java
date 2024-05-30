package justaplugin.sillinesslimiter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class limitsilly implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 0) {
                if (IPLock.getPlugin(IPLock.class).getConfig().get(player.getName()) != null || IPLock.confirmations.containsKey(player)) {
                    if(IPLock.confirmations.containsKey(player)) player.sendMessage(ChatColor.RED + "You have yet to confirm previous change");
                    else player.sendMessage(ChatColor.GOLD + "Silliness has already been limited");
                    return true;
                }
                IPLock.getPlugin(IPLock.class).getConfig().set(player.getName(), player.getAddress().getAddress().getHostAddress());
                IPLock.getPlugin(IPLock.class).saveConfig();
                IPLock.getPlugin(IPLock.class).reloadConfig();

                player.sendMessage(ChatColor.RED + "ip linked" + ChatColor.GOLD + ", silliness has been limited");
                IPLock.getPlugin(IPLock.class).getLogger().info(player.getName() + " has been linked");
            }
        }
        if(sender.isOp() && args.length == 2) {
            if (IPLock.getPlugin(IPLock.class).getConfig().get(args[0]) != null) {
                sender.sendMessage(ChatColor.GOLD + "Silliness has already been limited");
                return true;
            }
            IPLock.getPlugin(IPLock.class).getConfig().set(args[0], args[1]);
            IPLock.getPlugin(IPLock.class).saveConfig();
            IPLock.getPlugin(IPLock.class).reloadConfig();

            if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])) && !Objects.equals(IPLock.getPlugin(IPLock.class).getConfig().get(args[0]), Bukkit.getPlayer(args[0]).getAddress().getAddress().getHostAddress())) {
                Bukkit.getPlayer(args[0]).kickPlayer(ChatColor.RED + "trying to limit your silliness\n\n" + ChatColor.AQUA + " you joined on account with other ip already linked to it");
            }

            if(sender instanceof Player) sender.sendMessage(ChatColor.RED + args[0] + " has been linked");
            IPLock.getPlugin(IPLock.class).getLogger().info(args[0] + " has been linked");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        if(sender.isOp() && strings.length == 2) return List.of("<<ip here>>");;
        return null;
    }
}
