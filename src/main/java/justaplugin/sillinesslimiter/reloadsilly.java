package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reloadsilly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        IPLock.getPlugin(IPLock.class).reloadConfig();
        commandSender.sendMessage(ChatColor.GOLD + "Reloaded the silly");
        return true;
    }
}
