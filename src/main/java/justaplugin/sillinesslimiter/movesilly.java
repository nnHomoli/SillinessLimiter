package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class movesilly implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player && strings.length == 1) {
            Player p = (Player) sender;

            if(IPLock.confirmations.containsKey(p)) {
                p.sendMessage(ChatColor.RED + "You have yet to confirm previous change");
                return true;
            }

            IPLock.confirmations.put(p, strings[0]);
            p.sendMessage(ChatColor.YELLOW + "Are you sure you want to move your name to this " + ChatColor.YELLOW + "address?"
                    + ChatColor.RED + "\n You will be disconnected if you are not on the same address!"
            + ChatColor.GOLD + "\nif so, please use " + ChatColor.AQUA + "/silly-confirm" + ChatColor.GOLD + " or " + ChatColor.AQUA + "/silly-deny");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length == 1) return List.of("<<ip here>>");
        return null;
    }
}
