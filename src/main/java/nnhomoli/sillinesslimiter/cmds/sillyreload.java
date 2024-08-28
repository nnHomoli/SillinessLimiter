package nnhomoli.sillinesslimiter.cmds;

import nnhomoli.sillinesslimiter.SillinessLimiter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class sillyreload implements CommandExecutor, TabCompleter {
    private SillinessLimiter plugin;

    public sillyreload(SillinessLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0 || args[0].equals("Config")) this.plugin.reloadConfig();
        if(args.length == 0 || args[0].equals("Language")) SillinessLimiter.lang.reload();
        if(args.length == 0 || args[0].equals("PlayerData")) SillinessLimiter.udata.reload();

        SillinessLimiter.log.info("Reloaded SillinessLimiter");
        if(sender instanceof Player) sender.sendMessage(SillinessLimiter.lang.get("plugin_reload"));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) return List.of("Config", "Language", "PlayerData");
        return null;
    }
}
