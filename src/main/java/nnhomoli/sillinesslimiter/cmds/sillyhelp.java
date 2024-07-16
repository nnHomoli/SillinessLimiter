package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sillyhelp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player) sender.sendMessage(SillinessLimiter.lang.get("help"));
        return true;
    }
}
