package justaplugin.sillinesslimiter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unlimitsilly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (IPLock.getPlugin(IPLock.class).getConfig().get(player.getName()) == null) {
                player.sendMessage(ChatColor.GOLD + "No silliness to unlimit");
                return true;
            }
            IPLock.getPlugin(IPLock.class).getConfig().set(player.getName(), null);
            IPLock.getPlugin(IPLock.class).saveConfig();
            IPLock.getPlugin(IPLock.class).reloadConfig();

            player.sendMessage(ChatColor.RED +"ip unlinked" + ChatColor.AQUA + ", silliness has been unlimited");
            IPLock.getPlugin(IPLock.class).getLogger().info(player.getName() + " has been unlinked");
        }
        return true;
    }
}
