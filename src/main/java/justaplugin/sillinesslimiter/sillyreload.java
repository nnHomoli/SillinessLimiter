package justaplugin.sillinesslimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyreload implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        IPLock.getPlugin(IPLock.class).reloadConfig();

        if(commandSender instanceof Player) commandSender.sendMessage(IPLock.lang.get("plugin_reload"));
        IPLock.lang.reload();
        IPLock.log.info("Reloaded SillinessLimiter");

        return true;
    }
}
