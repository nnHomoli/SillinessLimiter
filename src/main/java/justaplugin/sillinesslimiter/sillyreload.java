package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyreload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        IPLock.getPlugin(IPLock.class).reloadConfig();

        if(commandSender instanceof Player) commandSender.sendMessage(ChatColor.GOLD + "Reloaded the silly");
        IPLock.getPlugin(IPLock.class).getLogger().info("Reloaded the silly");

        return true;
    }
}
