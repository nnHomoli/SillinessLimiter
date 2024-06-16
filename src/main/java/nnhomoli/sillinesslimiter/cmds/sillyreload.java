package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.IPLock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyreload implements CommandExecutor {
    private final  IPLock plugin;

    public sillyreload(IPLock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        this.plugin.reloadConfig();
        IPLock.pdata.reload();
        IPLock.lang.reload();

        IPLock.log.info("Reloaded SillinessLimiter");
        if(sender instanceof Player) sender.sendMessage(IPLock.lang.get("plugin_reload"));

        return true;
    }
}
